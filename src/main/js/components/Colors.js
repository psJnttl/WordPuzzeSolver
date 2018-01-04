import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import ColorTile from './ColorTile';
import ColorPanel from './ColorPanel';
import _ from 'lodash';

class Colors extends React.Component {
  constructor(props) {
    super(props);
    this.state = {colors: [], selectedColor: -1, mouseOverColor: -1,}
    this.selectColor = this.selectColor.bind(this);
    this.mouseOverTile = this.mouseOverTile.bind(this);
    this.mouseOnBackgound = this.mouseOnBackgound.bind(this);
    this.handleChangeColor = this.handleChangeColor.bind(this);
  }

  selectColor() {
    this.setState({selectedColor: this.state.mouseOverColor});
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

  mouseOverTile(index) {
    this.setState({mouseOverColor: index});
  }

  mouseOnBackgound() {
    this.setState({mouseOverColor: -1});
  }

  handleChangeColor(e, item) {
    var value = e.target.value;
    var array = _.take(this.state.colors, 16);
    var color = array[this.state.selectedColor];
    if ("alpha" !== item && value !== "") {
      var check = parseInt(value);
      if (check > 255) {
        check = 255;
      }
      else if (check < 0) {
        check = 0;
      }
      color[item] = check;
    }
    else if ("alpha" !== item && value === "") {
      color[item] = "";
    }
    else if ("alpha" === item && value !== ""){
      var check = parseFloat(value);
      if (check > 1) {
        check = 1;
      }
      else if (check < 0) {
        check = 0;
      }
      color[item] = check;
    }
    else if ("alpha" === item && value === ""){
      color[item] = "";
    }
    array[this.state.selectedColor] = color;
    this.setState({colors: array});
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
          mouseOver={this.mouseOverTile}
          mouseLeave={this.mouseOnBackgound}
        />
      </li>
    );
    return (
      <div style={{'marginLeft': 10}} >
        <div onClick={this.selectColor}>
          Color editor<br/>
          <ul style={{'display': 'flex', 'listStyleType': 'none', 'flexWrap': 'wrap'}}>
            {tiles}
          </ul>
        </div>
        <div style={{'marginLeft': 45}}>
          {this.state.selectedColor !== -1 &&
            <ColorPanel
              color={this.state.colors[this.state.selectedColor]}
              changeColor={this.handleChangeColor}
            />}
        </div>
      </div>
    );
  }
}
Colors.PropTypes = {}
Colors.defaultProps = {}
export default Colors;
