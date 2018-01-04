import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

class Colors extends React.Component {
  constructor(props) {
    super(props);
    this.state = {colors: [], }
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
    return (
      <div style={{'marginLeft': 10}}>Color editor</div>
    );
  }
}
Colors.PropTypes = {}
Colors.defaultProps = {}
export default Colors;
