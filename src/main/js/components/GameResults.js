import React from 'react';
import PropTypes from 'prop-types';
import FoundWord from './FoundWord';

class GameResults extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
  }

  componentWillMount() {}
  render() {
    const resultData = this.props.results.map( (item, index) =>
      <li key={index}>
        <FoundWord
          points={item.points}
          word={item.value}
          onClick={this.props.selectResultWord}
          index={index}
          selected={this.state.selectedWordIndex === index}
        />
      </li>
    );
    let resultList;
    if (this.props.results.length > 0) {
      resultList =
      <ul style={{'listStyleType': 'none'}}>{resultData}</ul>
    }
    else {
      resultList = null;
    }
    return (
      <div>
        {resultList}
      </div>
    );
  }
}
GameResults.PropTypes = {
  result: PropTypes.array.isRequired,
  selectResultWord: PropTypes.array.isRequired,
  selected: PropTypes.number,
}
GameResults.defaultProps = {
  result: [],
  selected: -1,
}
export default GameResults;
