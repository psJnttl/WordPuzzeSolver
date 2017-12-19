import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Menu } from 'semantic-ui-react'

class Words extends React.Component {
  constructor(props) {
    super(props);
    this.state = {wordCount:0, itemsPerPage:10, activeItem: '10', }
    this.handleItemClick = this.handleItemClick.bind(this);
  }

  handleItemClick(e, { name }) {
    this.setState({ activeItem: name })
  }

  getWordCount() {
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get('/api/words/count', config)
         .then(function (response) {
           const resp = response.data;
           self.setState({wordCount: resp.count});
         })
         .catch(function (error) {
           console.log("fetching count failed");
         });

  }

  componentWillMount() {
    this.getWordCount();
  }

  render() {
    const activeItem = this.state.activeItem;
    return (
      <div>
        <h4>Words in database: {this.state.wordCount}</h4>
        <Menu pagination>
          <Menu.Item name='1' active={activeItem === '1'} onClick={this.handleItemClick} />
          <Menu.Item disabled>...</Menu.Item>
          <Menu.Item name='10' active={activeItem === '10'} onClick={this.handleItemClick} />
          <Menu.Item name='11' active={activeItem === '11'} onClick={this.handleItemClick} />
          <Menu.Item name='12' active={activeItem === '12'} onClick={this.handleItemClick} />
        </Menu>

      </div>
    );
  }
}
Words.PropTypes = {}
Words.defaultProps = {}
export default Words;
