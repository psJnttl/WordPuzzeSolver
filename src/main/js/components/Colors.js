import React from 'react';
import PropTypes from 'prop-types';

class Colors extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
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
