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
        ["i", "l", "i", "b", "t", "s", "e", "i", "i", "p", "r", "s", "e", "s", "o", "n"],
        ["a", "a", "a", "a", "a", "e", "d", "a", "a", "o", "ll", "a", "p", "a", "a", "a"],
        ["a", "a", "a", "a", "a", "b", "a", "a", "a", "o", "in-", "a", "a", "a", "x", "a"],
        ["-est", "a", "a", "a", "a", "t", "a", "a", "r", "e", "a", "a", "g", "a", "a", "a"],
        ["a", "a", "c", "a", "a", "i/m", "a", "h", "a", "r", "a", "e", "b", "f", "e", "t"]
      ],
      title: [
        "single 1",
        "single 2",
        "single 3",
        "digram",
        "digram first",
        "digram last",
        "either or"
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
        text={this.state.title[index]}
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
