import React from 'react';
import PropTypes from 'prop-types';
import FoundWord2 from './FoundWord2';

class GameResults extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
  }

  componentWillMount() {}
  render() {
    const resultData = this.props.results.map( (item, index) =>
        <FoundWord2
          key={index}
          points={item.points}
          word={item.value}
          onClick={this.props.selectResultWord}
          index={index}
          selected={this.state.selectedWordIndex === index}
        />
    );
    let resultList;
    if (this.props.results.length > 0) {
      resultList =
      <table>
        <tbody>
          {resultData}
        </tbody>
      </table>
    }
    else {
      resultList = null;
    }
    return (
      <div style={{'overflowY':'auto', 'height': '80px', 'width': '360px',
      'marginTop':'10px', 'marginLeft':'40px'}}>
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
