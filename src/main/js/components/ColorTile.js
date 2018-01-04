import React from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';

class ColorTile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tileStyle : {
        height: 100,
        width: 100,
    },
  };
  this.onMouseEnter = this.onMouseEnter.bind(this);
  this.onMouseLeave = this.onMouseLeave.bind(this);
  }

  onMouseEnter() {
    const newStyle = _.assign({}, this.state.tileStyle, {'cursor': 'pointer'});
    this.setState({tileStyle: newStyle});
  }

  onMouseLeave() {
    const newStyle = _.omit(this.state.tileStyle, 'cursor');
    this.setState({tileStyle: newStyle});
  }

  render() {
    const red = this.props.color.red;
    const green = this.props.color.green;
    const blue = this.props.color.blue;
    const alpha = this.props.color.alpha;
    const backg = {background: "rgba("+red+","+green+","+blue+","+alpha+")"}
    let border;
    if (this.props.selected) {
      border = {border:  "5px solid rgba(34,36,38,.15)"};
    }
    else {
      border = {border:  "2px solid rgba(34,36,38,.15)"};
    }
    const styles = _.assign({}, this.state.tileStyle, backg, border);

    return (
      <div>
        <div style={styles}
          onMouseOver={this.onMouseEnter}
          onMouseLeave={this.onMouseLeave}
        ></div>
      </div>
    );
  }
}
ColorTile.PropTypes = {
  color: PropTypes.object.isRequired,
  selected: PropTypes.bool.isRequired,
}
ColorTile.defaultProps = {
  color: {red: 127, green: 127, blue: 127, alpha: 1},
}
export default ColorTile;
