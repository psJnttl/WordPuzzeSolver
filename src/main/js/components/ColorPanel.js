import React from 'react';
import PropTypes from 'prop-types';
import { Label } from 'semantic-ui-react'

class ColorPanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    return (
      <div>the color panel for adjusting r,g,b and alpha<br />
        <table>
          <tbody>
            <tr>
              <td><Label color="red">Red</Label></td>
              <td>
                <input
                  size={2}
                />
              </td>
            </tr>
            <tr>
              <td><Label color="green">Green</Label></td>
              <td>
                <input
                  size={2}
                />
              </td>
            </tr>
            <tr>
              <td><Label color="blue">Blue</Label></td>
              <td>
                <input
                  size={2}
                />
              </td>
            </tr>
            <tr>
              <td>ALPHA:</td>
              <td>
                <input
                  size={2}
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
}
ColorPanel.defaultProps = {
  color: {red: 127, green: 127, blue: 127, alpha: 1},
}

export default ColorPanel;
