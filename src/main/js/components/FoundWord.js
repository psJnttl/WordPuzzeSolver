import React from 'react';
import PropTypes from 'prop-types';

class FoundWords extends React.Component {
  constructor(props) {
    super(props);
    this.state = {mouseOver: '', }
    this.onMouseEnter = this.onMouseEnter.bind(this);
    this.onMouseLeave = this.onMouseLeave.bind(this);
    this.selectStyle = this.selectStyle.bind(this);
  }

  onMouseEnter(index) {
    this.setState({mouseOver: index});
  }

  onMouseLeave() {
    this.setState({mouseOver: ''});
  }

  selectStyle(index) {
    if (this.props.selected) {
      return {'display': 'flex', 'listStyleType': 'none', 'cursor': 'pointer', 'background': '#bbdefb'};
    }
    else if (this.state.mouseOver === index) {
      return {'display': 'flex', 'listStyleType': 'none', 'cursor': 'pointer', 'background': '#e3f2fd'};
    }
    return {'display': 'flex', 'listStyleType': 'none', 'cursor': 'pointer', 'background': '#e0e0e0'};
  }

  render() {
    return (
      <div
        onMouseEnter={() =>  this.onMouseEnter(this.props.index)}
        onMouseLeave={ () => this.onMouseLeave()}
      >
        <ul
          style={this.selectStyle(this.props.index) }
          onClick={(e) => this.props.onClick(e, this.props.index)} >
          <li style={{'padding': '0 10px 0 10px'}}>{this.props.points}</li>
          <span>|</span>
          <li style={{'padding': '0 50px 0 10px'}}>{this.props.word}</li>
        </ul>
      </div>
    );
  }
}

FoundWords.PropTypes = {
  points: PropTypes.number,
  word: PropTypes.string,
  onClick: PropTypes.func,
  index: PropTypes.number,
  selected: PropTypes.boolean,
}
FoundWords.defaultProps = {
  points: 1,
  word: "a",
  onClick: () => {},
  index: 0,
  selected: false,
}
export default FoundWords;
