import React from 'react';
import PropTypes from 'prop-types';

class FoundWord2 extends React.Component {
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
      return {'cursor': 'pointer',
              'background': '#bbdefb',
             };
    }
    else if (this.state.mouseOver === index) {
      return {'cursor': 'pointer',
              'background': '#e3f2fd',
             };
    }
    return {'cursor': 'pointer',
            'background': '#e0e0e0',
           };
  }

  render() {
    return (
      <tr
        onMouseEnter={() =>  this.onMouseEnter(this.props.index)}
        onMouseLeave={ () => this.onMouseLeave()}
        style={this.selectStyle(this.props.index) }
        onClick={(e) => this.props.onClick(e, this.props.index)} >
        <td>{this.props.index + 1}</td>
        <td>{this.props.points}</td>
        <td>{this.props.word}</td>
      </tr>
    );
  }
}

FoundWord2.PropTypes = {
  points: PropTypes.number,
  word: PropTypes.string,
  onClick: PropTypes.func,
  index: PropTypes.number,
  selected: PropTypes.boolean,
}
FoundWord2.defaultProps = {
  points: 1,
  word: "a",
  onClick: () => {},
  index: 0,
  selected: false,
}
export default FoundWord2;
