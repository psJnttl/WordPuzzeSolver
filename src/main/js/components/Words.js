import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Button, Icon, Input, List, Menu, Popup, Table } from 'semantic-ui-react'
import _ from 'lodash';
import ModalSimpleConfirmation from './ModalSimpleConfirmation';
import ModalWord from './ModalWord';

class Words extends React.Component {
  constructor(props) {
    super(props);
    this.state = {wordCount:0, itemsPerPage:5, activePage: 1,
      visiblePages: [1,2,3], words: [], word:{},
      delConfirmationVisible: false, addModalVisible:false,
      editModalVisible: false, searchValue:"", };
    this.handleItemClick = this.handleItemClick.bind(this);
    this.handleBack = this.handleBack.bind(this);
    this.handleForward = this.handleForward.bind(this);
    this.setDeleteConfirmModalVisible = this.setDeleteConfirmModalVisible.bind(this);
    this.deleteReply = this.deleteReply.bind(this);
    this.getWordPage = this.getWordPage.bind(this);
    this.deleteWord = this.deleteWord.bind(this);
    this.updateVisibleItems = this.updateVisibleItems.bind(this);
    this.openAddModal = this.openAddModal.bind(this);
    this.closeAddModal = this.closeAddModal.bind(this);
    this.addWord = this.addWord.bind(this);
    this.openEditModal = this.openEditModal.bind(this);
    this.closeEditModal = this.closeEditModal.bind(this);
    this.modifyWord = this.modifyWord.bind(this);
    this.setItemsPerPage = this.setItemsPerPage.bind(this);
    this.onChangeSearchValue = this.onChangeSearchValue.bind(this);
    this.searchWordPaged = this.searchWordPaged.bind(this);
    this.proxyGetPage = this.proxyGetPage.bind(this);
    this.clearSearch = this.clearSearch.bind(this);
  }

  clearSearch() {
    console.log("clearSearch()");
    this.setState({searchValue: "" },
      () => this.proxyGetPage());
  }

  proxyGetPage() {
    if (this.state.searchValue.length > 1) {
      this.searchWordPaged(this.state.activePage, this.state.itemsPerPage);
    }
    else {
      this.getWordPage(this.state.activePage, this.state.itemsPerPage);
    }
  }
  onChangeSearchValue(e) {
    const value = e.target.value;
    this.setState({searchValue: value, activePage: 1},
      () => this.proxyGetPage());

  }

  searchWordPaged(pageNumber, count) {
    let page = pageNumber - 1;
    page = page > -1 ? page : 0;
    const searchValue = this.state.searchValue;
    const url = '/api/words/search/page/' + searchValue + '/' + page + '/' + count;
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get(url, config)
         .then(function (response) {
           const resp = response.data;
           self.setState({words: resp.content, wordCount: resp.totalElements});
         })
         .catch(function (error) {
           console.log("searching a word failed!");
         });
  }

  setItemsPerPage(nbrItems) {
    this.setState({itemsPerPage: nbrItems, activePage: 1},
    ()=> this.proxyGetPage());
  }

  handleItemClick(e, { name } ) {
    const number = parseInt(name);
    this.setState({ activePage: number },
      () => this.proxyGetPage());
  }

  handleBack(e) {
    const visible = this.state.visiblePages;
    const lowest = visible[0] - 1;
    if (lowest === 0) {
      return;
    }
    const array = _.concat([], lowest, visible[0], visible[1]);
    this.setState({visiblePages: array});
  }

  handleForward(e) {
    const visible = this.state.visiblePages;
    const highest = visible[2] + 1;
    const pageCount = this.getPageCount();
    if (highest > pageCount) {
      return;
    }
    const array = _.concat([], visible[1], visible[2], highest);
    this.setState({visiblePages: array});
  }

  updateVisibleItems() {
    const visible = this.state.visiblePages;
    const pageCount = this.getPageCount();
    if (pageCount >= 3 && visible[2] > pageCount) {
      this.handleBack(null);
    }
    const activePage = this.state.activePage;
    if (pageCount > 0 && activePage > pageCount) {
      this.setState({activePage: pageCount},
        () => this.proxyGetPage());
    }

  }

  setDeleteConfirmModalVisible(item) {
    if (false === this.state.delConfirmationVisible) {
      this.setState({delConfirmationVisible: true, word: item});
    }
  }

  deleteReply(answer) {
    if (true === answer) {
      this.deleteWord(this.state.word);
    }
    this.setState({delConfirmationVisible: false, word: {} });
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

  getWordPage(pageNumber, count) {
    let page = pageNumber - 1;
    page = page > -1 ? page : 0;
    const url = '/api/words/page/' + page + '/' + count;
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get(url, config)
         .then(function (response) {
           const resp = response.data;
           self.setState({words: resp.content, wordCount: resp.totalElements});
         })
         .catch(function (error) {
           console.log("fetching word page failed");
         });
  }

  deleteWord(item) {
    const word = _.assign({}, item);
    const self = this;
    const url = '/api/words/' + word.id;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.delete(url, config)
         .then(function (response) {
           self.setState({wordCount: self.state.wordCount - 1},
             () => self.proxyGetPage());
           self.updateVisibleItems();
         })
         .catch(function (error) {
           console.log("deleting word failed");
         });
  }

  componentWillMount() {
    this.proxyGetPage();
  }

  getPageCount() {
    return Math.ceil(this.state.wordCount / this.state.itemsPerPage);
  }

  openAddModal() {
    this.setState({addModalVisible: true});
  }

  closeAddModal() {
    this.setState({addModalVisible: false});
  }

  addWord(word) {
    this.closeAddModal();
    const self = this;
    const command = _.assign({}, word);
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.post('/api/words', command, config)
         .then(function (response) {
           self.setState({wordCount: self.state.wordCount + 1},
             ()=> self.proxyGetPage());
         })
         .catch(function (error) {
           console.log("adding word failed");
         });
  }

  openEditModal(item) {
    this.setState({editModalVisible: true, word: item});
  }

  closeEditModal() {
    this.setState({editModalVisible: false, word: {} });
  }

  modifyWord(word) {
    this.closeEditModal();
    const self = this;
    const command = _.assign({}, word);
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.put('/api/words/' + command.id, command, config)
         .then(function (response) {
           self.setState({wordCount: self.state.wordCount + 1},
             () => self.proxyGetPage());
         })
         .catch(function (error) {
           console.log("adding word failed");
         });
  }

  render() {
    let wordModal;
    if (this.state.addModalVisible) {
      wordModal =
      <ModalWord
        modalOpen={this.state.addModalVisible}
        title="Add word"
        close={this.closeAddModal}
        save={this.addWord}
      />
    }
    else if (this.state.editModalVisible) {
      wordModal =
      <ModalWord
        modalOpen={this.state.editModalVisible}
        title="Edit word"
        close={this.closeEditModal}
        save={this.modifyWord}
        word={this.state.word}
      />
    }
    else {
      wordModal = null;
    }
    const activePage = this.state.activePage;
    const pageCount = this.getPageCount();
    const visible = this.state.visiblePages;
    const choices = [5,10,20,40];
    const itemsPer = choices.map( (item, index) =>
      <Menu.Item key={index}
        name={'' + item}
        active={item === this.state.itemsPerPage}
        onClick={() => this.setItemsPerPage(item)}
      />
    );

    const dataRows = this.state.words.map((item, index) =>
      <Table.Row key={index} size="small">
        <Table.Cell>{item.id}</Table.Cell>
        <Table.Cell>{item.value}</Table.Cell>
        <Table.Cell>
          <Popup
            trigger={
              <Button
                icon="pencil"
                color="yellow"
                onClick={() => this.openEditModal(item)}
              />}
            content="edit"
          />
          <Popup
            trigger={
              <Button
                icon="trash outline"
                color="red"
                onClick={() => this.setDeleteConfirmModalVisible(item)}
              />}
            content="delete"
          />
        </Table.Cell>
      </Table.Row>);
    return (
      <div>
        <ModalSimpleConfirmation
          modalOpen={this.state.delConfirmationVisible}
          title="Delete word"
          question={"Are you sure you want to delete '" + this.state.word.value + "' ?"}
          reply={this.deleteReply}
        />
        {wordModal}
        <h4>Words in database: {this.state.wordCount}</h4>
        <ul style={{'display': 'flex', 'listStyleType': 'none'}}>
          <li>
            <Popup
              trigger={
                <Button
                  icon="plus"
                  color="green"
                  onClick={() => this.openAddModal()}
                />}
              content="add"
            />
          </li>
          <li style={{'marginLeft': '10px'}}>
            <Input
              value={this.state.searchValue}
              placeholder="search"
              onChange={this.onChangeSearchValue}
              icon={{ name: 'remove', link: true, onClick: this.clearSearch }}
            />
          </li>
        </ul>
        <Table celled unstackable>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell>id</Table.HeaderCell>
              <Table.HeaderCell>value</Table.HeaderCell>
              <Table.HeaderCell>actions</Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {dataRows}
          </Table.Body>
        </Table>
        <Menu pagination>
          {pageCount > 3 && ( <Menu.Item icon onClick={this.handleBack} >
            <Icon name='chevron left' />
          </Menu.Item> ) }
          <Menu.Item name={'' + visible[0]} active={activePage === visible[0]} onClick={this.handleItemClick} />
          {pageCount > 1 &&
            <Menu.Item name={'' + visible[1]} active={activePage === visible[1]} onClick={this.handleItemClick} /> }
          {pageCount > 2 &&
            <Menu.Item name={'' + visible[2]} active={activePage === visible[2]} onClick={this.handleItemClick} /> }
          {pageCount > 3 && <Menu.Item icon onClick={this.handleForward}>
            <Icon name='chevron right' />
          </Menu.Item>}
        </Menu>
        <br />
        <Menu pagination size="mini">
          {itemsPer}
        </Menu>
      </div>
    );
  }
}
Words.PropTypes = {}
Words.defaultProps = {}
export default Words;
