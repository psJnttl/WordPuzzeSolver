import React from 'react';
import PropTypes from 'prop-types';
import { Label } from 'semantic-ui-react'

class ColorPanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      inputStyle : {
        fontFamily: "Lato,'Helvetica Neue',Arial,Helvetica,sans-serif",
        fontSize: "16px",
        border: "2px solid rgba(34,36,38,.15)",
        textAlign: "center",
        width: "50px" },
    };
  }

  render() {
    const inputStyle = this.state.inputStyle;
    return (
      <div><br />
        <table>
          <tbody>
            <tr>
              <td><Label color="red">Red</Label></td>
              <td>
                <input
                  type="number"
                  value={this.props.color.red}
                  onChange={(e) => this.props.changeColor(e, "red")}
                  style={inputStyle}
                />
              </td>
            </tr>
            <tr>
              <td><Label color="green">Green</Label></td>
              <td>
                <input
                  type="number"
                  value={this.props.color.green}
                  onChange={(e) => this.props.changeColor(e, "green")}
                  style={inputStyle}
                />
              </td>
            </tr>
            <tr>
              <td><Label color="blue">Blue</Label></td>
              <td>
                <input
                  type="number"
                  value={this.props.color.blue}
                  onChange={(e) => this.props.changeColor(e, "blue")}
                  style={inputStyle}
                />
              </td>
            </tr>
            <tr>
              <td>Alpha:</td>
              <td>
                <input
                  type="number"
                  value={this.props.color.alpha}
                  onChange={(e) => this.props.changeColor(e, "alpha")}
                  style={inputStyle}
                />
              </td>
            </tr>
          </tbody>
        </table>

      </div>
    );
  }
}
ColorPanel.PropTypes = {
  color: PropTypes.object.isRequired,
  changeColor: PropTypes.func.isRequired,
}
ColorPanel.defaultProps = {
  color: {red: 127, green: 127, blue: 127, alpha: 1},
}

export default ColorPanel;
