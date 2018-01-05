import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Button, Popup, Table } from 'semantic-ui-react'
import ModalSimpleConfirmation from './ModalSimpleConfirmation';
import ModalSymbol from './ModalSymbol';
import ModalSimpleInformation from './ModalSimpleInformation';

class Symbols extends React.Component {
  constructor(props) {
    super(props);
    this.state = {symbols: [], symbol: {}, delConfirmationVisible: false,
      addModalVisible: false, editModalVisible: false, infoModalVisible: false,
      infoModalData: {},
    };
    this.setDeleteConfirmModalVisible = this.setDeleteConfirmModalVisible.bind(this);
    this.deleteReply = this.deleteReply.bind(this);
    this.openAddModal = this.openAddModal.bind(this);
    this.closeAddModal = this.closeAddModal.bind(this);
    this.addSymbol = this.addSymbol.bind(this);
    this.openEditModal = this.openEditModal.bind(this);
    this.closeEditModal = this.closeEditModal.bind(this);
    this.modifySymbol = this.modifySymbol.bind(this);
    this.closeInfoModal = this.closeInfoModal.bind(this);
  }

  setDeleteConfirmModalVisible(item) {
    if (false === this.state.delConfirmationVisible) {
      this.setState({delConfirmationVisible: true, symbol: item});
    }
  }

  deleteReply(answer) {
    if (true === answer) {
      this.deleteSymbol(this.state.symbol);
    }
    this.setState({delConfirmationVisible: false, symbol: {} });
  }

  deleteSymbol(item) {
    const symbol = _.assign({}, item);
    const self = this;
    const url = '/api/symbols/' + symbol.id;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.delete(url, config)
         .then(function (response) {
           self.getAllSymbols();
         })
         .catch(function (error) {
           self.setState({infoModalVisible: true,
               infoModalData: {
               title:"Deleting a symbol failed",
              notification: "Could not delete symbol",
              name: symbol.value}
            });
         });
  }

  openAddModal() {
    this.setState({addModalVisible: true});
  }

  closeAddModal() {
    this.setState({addModalVisible: false});
  }

  addSymbol(symbol) {
    this.closeAddModal();
    const self = this;
    const command = _.assign({}, symbol);
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.post('/api/symbols', command, config)
         .then(function (response) {
           self.getAllSymbols();
         })
         .catch(function (error) {
           self.setState({infoModalVisible: true,
               infoModalData: {
               title:"Adding a symbol failed",
              notification: "Could not add symbol",
              name: command.value}
            });
         });
  }

  openEditModal(symbol) {
    this.setState({editModalVisible: true, symbol: symbol});
  }

  closeEditModal() {
    this.setState({editModalVisible: false, symbol: {}});
  }

  modifySymbol(symbol) {
    this.closeEditModal();
    const self = this;
    const command = _.assign({}, symbol);
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.put('/api/symbols/' + command.id, command, config)
         .then(function (response) {
           self.getAllSymbols();
         })
         .catch(function (error) {
           self.setState({infoModalVisible: true,
               infoModalData: {
               title:"Modifying a symbol failed",
              notification: "Could not modify symbol",
              name: command.value}
            });
         });
  }

  closeInfoModal() {
    this.setState({infoModalVisible: false, infoModalData: {}})
  }

  getAllSymbols() {
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get('/api/symbols', config)
         .then(function (response) {
           self.setState({symbols: response.data});
         })
         .catch(function (error) {
           self.setState({infoModalVisible: true,
               infoModalData: {
               title:"Fetch symbols failed",
              notification: "Could not get the list of symbols from server!"}
            });
         });
  }

  componentWillMount() {
    this.getAllSymbols();
  }

  render() {
    let symbolModal;
    if (this.state.addModalVisible) {
      symbolModal =
      <ModalSymbol
        modalOpen={this.state.addModalVisible}
        title="Add symbol"
        close={this.closeAddModal}
        save={this.addSymbol}
      />
    }
    else if (this.state.editModalVisible) {
      symbolModal =
      <ModalSymbol
        modalOpen={this.state.editModalVisible}
        title="Edit symbol"
        close={this.closeEditModal}
        save={this.modifySymbol}
        symbol={this.state.symbol}
      />
    }
    else {
      symbolModal = null;
    }
    const dataRows = this.state.symbols.map((item, index) =>
       <Table.Row key={index} size="small">
         <Table.Cell>{item.id}</Table.Cell>
         <Table.Cell>{item.value}</Table.Cell>
         <Table.Cell>{item.score}</Table.Cell>
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
       </Table.Row>
    );
    return (
      <div style={{'margin': 10}}>
        <h4>Symbols</h4>

        <ModalSimpleInformation
          modalOpen={this.state.infoModalVisible}
          title={this.state.infoModalData.title}
          notification={this.state.infoModalData.notification}
          name={this.state.infoModalData.name}
          reply={this.closeInfoModal}
        />
        <ModalSimpleConfirmation
          modalOpen={this.state.delConfirmationVisible}
          title="Delete symbol"
          question={"Are you sure you want to delete '" + this.state.symbol.value + "' ?"}
          reply={this.deleteReply}
        />
        {symbolModal}
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
        </ul>
        <Table celled unstackable>
          <Table.Header>
            <Table.Row>
              <Table.HeaderCell>id</Table.HeaderCell>
              <Table.HeaderCell>value</Table.HeaderCell>
              <Table.HeaderCell>score</Table.HeaderCell>
              <Table.HeaderCell>actions</Table.HeaderCell>
            </Table.Row>
          </Table.Header>
          <Table.Body>
            {dataRows}
          </Table.Body>
        </Table>
      </div>
    );
  }
}
Symbols.PropTypes = {}
Symbols.defaultProps = {}
export default Symbols;
