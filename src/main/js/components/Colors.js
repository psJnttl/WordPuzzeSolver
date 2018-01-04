import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import ColorTile from './ColorTile';

class Colors extends React.Component {
  constructor(props) {
    super(props);
    this.state = {colors: [], selectedColor: -1, }
    this.selectColor = this.selectColor.bind(this);
  }

  selectColor(index) {
    this.setState({selectedColor: index});
  }

  loadColors() {
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get('/api/colors/', config)
         .then(function (response) {
           self.setState({colors: response.data});
         })
         .catch(function (error) {
           console.log("Failed to load colors from server.");
         });
  }

  componentWillMount() {
    this.loadColors();
  }

  render() {
    const tiles = this.state.colors.map((item, index) =>
      <li key={index}>
        <ColorTile
          color={this.state.colors[index]}
          selected={index == this.state.selectedColor}
          index={index}
          select={this.selectColor}
        />
      </li>
    );
    return (
      <div style={{'marginLeft': 10}}>
        Color editor<br/>
        <ul style={{'display': 'flex', 'listStyleType': 'none', 'flexWrap': 'wrap'}}>
          {tiles}
        </ul>
      </div>
    );
  }
}
Colors.PropTypes = {}
Colors.defaultProps = {}
export default Colors;
