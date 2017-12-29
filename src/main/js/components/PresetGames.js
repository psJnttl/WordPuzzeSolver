import React from 'react';
import PropTypes from 'prop-types';
import {Dropdown} from 'semantic-ui-react';

class PresetGames extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      games: [
        ["d", "a", "a", "p", "e", "l", "o", "a", "a", "l", "a", "a", "a", "a", "a", "e"],
        ["u", "b", "i", "q", "a", "a", "a", "u", "a", "a", "a", "i", "s", "u", "o", "t"],
        ["i", "l", "i", "b", "t", "s", "e", "i", "i", "p", "r", "s", "e", "s", "o", "n"]
      ],
    };
    this.handleItemClick = this.handleItemClick.bind(this)
  }

  handleItemClick(e, item) {
    this.props.selectGame(item);
  }

  render() {
    const menuItems = this.state.games.map((item,index) =>
      <Dropdown.Item
        key={index}
        text={""+index}
        onClick={(e) => this.handleItemClick(e, item)}
      />
    );
    return (
      <div>
        <Dropdown text='Game presets'>
          <Dropdown.Menu>
            {menuItems}
          </Dropdown.Menu>
        </Dropdown>
      </div>
    );
  }
}
PresetGames.PropTypes = {
  selectGame: PropTypes.func.isRequired,
}
PresetGames.defaultProps = {}
export default PresetGames;
