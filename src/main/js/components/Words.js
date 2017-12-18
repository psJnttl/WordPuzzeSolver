import React from 'react';
import PropTypes from 'prop-types';

class Words extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
    this.method = this.method.bind(this);
  }
  method() {}
  render() {
    return (
      <div>Words.</div>
    );
  }
}
Words.PropTypes = {}
Words.defaultProps = {}
export default Words;
