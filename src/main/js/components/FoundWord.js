import React from 'react';
import PropTypes from 'prop-types';

const NewComponent = (props) => (
  <div>
    <ul style={{'display': 'flex', 'listStyleType': 'none'}} onClick={props.onClick(index)}>
      <li>{props.points}</li>
      <li>{props.word}</li>
    </ul>
  </div>
);
NewComponent.PropTypes = {
  points: PropTypes.number,
  word: PropTypes.string,
  path: PropTypes.array,
  onClick: PropTypes.func,
  index: PropTypes.number,
}
NewComponent.defaultProps = {
  points: 1,
  word: "a",
  path: [0],
  onClick: {() => {}},
  index: 0,
}
export default NewComponent;
