import React from 'react';
import PropTypes from 'prop-types';

const GameTile = (props) => (
  <div>
    <input
      value={props.value}
      onChange={ (e) => props.onChange(e, props.index)}
      autoComplete="off"
      size={2}
      className="gametileinput"
    />
  </div>
);
GameTile.PropTypes = {
  value: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  index: PropTypes.number.isRequired,
}
GameTile.defaultProps = {}
export default GameTile;
