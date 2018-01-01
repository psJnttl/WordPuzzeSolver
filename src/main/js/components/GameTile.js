import React from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';

class GameTile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputStyle : {
        fontFamily: "Lato,'Helvetica Neue',Arial,Helvetica,sans-serif",
        fontSize: "32px",
        border: "5px solid rgba(34,36,38,.15)",
        textAlign: "center" },
    };

  }

  render() {
    const red = this.props.background.red;
    const green = this.props.background.green;
    const blue = this.props.background.blue;
    const alpha = this.props.background.alpha;
    const background = {background: "rgba("+red+","+green+","+blue+","+alpha+")"}
    const inputStyle = _.assign({}, this.state.inputStyle, background);
    return (
      <div>
        <input
          value={this.props.value}
          onChange={ (e) => this.props.onChange(e, this.props.index)}
          autoComplete="off"
          size={2}
          style={inputStyle}
        />
      </div>
    );
  }
}

GameTile.PropTypes = {
  value: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  index: PropTypes.number.isRequired,
  background: PropTypes.object,
}
GameTile.defaultProps = {
  background: {red: 255, green: 255, blue: 255, alpha: 1},
}
export default GameTile;
