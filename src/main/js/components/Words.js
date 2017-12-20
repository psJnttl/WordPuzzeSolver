import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Button, Icon, Menu, Popup, Table } from 'semantic-ui-react'
import _ from 'lodash';
import ModalSimpleConfirmation from './ModalSimpleConfirmation';
import ModalWord from './ModalWord';

class Words extends React.Component {
  constructor(props) {
    super(props);
    this.state = {wordCount:0, itemsPerPage:5, activePage: '1',
      visiblePages: ['1','2','3'], words: [], word:{},
      delConfirmationVisible: false, addModalVisible:false,};
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
  }

  handleItemClick(e, { name }) {
    this.setState({ activePage: name },
      () => this.getWordPage(this.state.activePage, this.state.itemsPerPage));
  }

  handleBack(e) {
    const visible = this.state.visiblePages;
    const lowest = parseInt(visible[0]) - 1;
    if (lowest === 0) {
      return;
    }
    const strPres = "" + lowest;
    const array = _.concat([], strPres, visible[0], visible[1]);
    this.setState({visiblePages: array});
  }

  handleForward(e) {
    const visible = this.state.visiblePages;
    const highest = parseInt(visible[2]) + 1;
    const pageCount = this.getPageCount();
    if (highest > pageCount) {
      return;
    }
    const strPres = "" + highest;
    const array = _.concat([], visible[1], visible[2], strPres);
    this.setState({visiblePages: array});
  }

  updateVisibleItems() {
    const visible = this.state.visiblePages;
    const pageCount = this.getPageCount();
    if (pageCount >= 3 && parseInt(visible[2]) > pageCount) {
      this.handleBack(null);
    }
    const activePage = parseInt(this.state.activePage);
    if (pageCount > 0 && activePage > pageCount) {
      this.setState({activePage: '' + pageCount},
        () => this.getWordPage(this.state.activePage, this.state.itemsPerPage));
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
    let page = parseInt(pageNumber) - 1;
    page = page > -1 ? page : 0;
    const url = '/api/words/page/' + page + '/' + count;
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get(url, config)
         .then(function (response) {
           self.setState({words: response.data});
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
           self.setState({wordCount: self.state.wordCount - 1});
           self.getWordPage(self.state.activePage, self.state.itemsPerPage);
           self.updateVisibleItems();
         })
         .catch(function (error) {
           console.log("deleting word failed");
         });
  }

  componentWillMount() {
    this.getWordCount();
    this.getWordPage(this.state.activePage, this.state.itemsPerPage);
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
           self.setState({wordCount: self.state.wordCount + 1});
           self.getWordPage(self.state.activePage, self.state.itemsPerPage);
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
    else {
      wordModal = null;
    }
    const activePage = this.state.activePage;
    const pageCount = this.getPageCount();
    const visible = this.state.visiblePages;
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
        <Popup
          trigger={
            <Button
              icon="plus"
              color="green"
              onClick={() => this.openAddModal()}
            />}
          content="add"
        />
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
          <Menu.Item name={visible[0]} active={activePage === visible[0]} onClick={this.handleItemClick} />
          {pageCount > 1 &&
            <Menu.Item name={visible[1]} active={activePage === visible[1]} onClick={this.handleItemClick} /> }
          {pageCount > 2 &&
            <Menu.Item name={visible[2]} active={activePage === visible[2]} onClick={this.handleItemClick} /> }
          {pageCount > 3 && <Menu.Item icon onClick={this.handleForward}>
            <Icon name='chevron right' />
          </Menu.Item>}
        </Menu>

      </div>
    );
  }
}
Words.PropTypes = {}
Words.defaultProps = {}
export default Words;
