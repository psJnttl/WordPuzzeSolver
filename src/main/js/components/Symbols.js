import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Button, Popup, Table } from 'semantic-ui-react'
import ModalSimpleConfirmation from './ModalSimpleConfirmation';

class Symbols extends React.Component {
  constructor(props) {
    super(props);
    this.state = {symbols: [], symbol: {}, delConfirmationVisible: false,
    };
    this.setDeleteConfirmModalVisible = this.setDeleteConfirmModalVisible.bind(this);
    this.deleteReply = this.deleteReply.bind(this);
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
           console.log("deleting symbol failed");
         });
  }

  getAllSymbols() {
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get('/api/symbols', config)
         .then(function (response) {
           self.setState({symbols: response.data});
         })
         .catch(function (error) {
           console.log("getting all symbols failed");
         });
  }

  componentWillMount() {
    this.getAllSymbols();
  }

  render() {
    const dataRows = this.state.symbols.map((item, index) =>
       <Table.Row key={index} size="small">
         <Table.Cell>{item.id}</Table.Cell>
         <Table.Cell>{item.value}</Table.Cell>
         <Table.Cell>{item.score}</Table.Cell>
         <Table.Cell>
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
      <div>
        <h4>Symbols</h4>

        <ModalSimpleConfirmation
          modalOpen={this.state.delConfirmationVisible}
          title="Delete symbol"
          question={"Are you sure you want to delete '" + this.state.symbol.value + "' ?"}
          reply={this.deleteReply}
        />

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
