import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { Table } from 'semantic-ui-react'

class Symbols extends React.Component {
  constructor(props) {
    super(props);
    this.state = {symbols: [], }

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
         <Table.Cell></Table.Cell>
       </Table.Row>
    );
    return (
      <div>
        <h4>Symbols</h4>
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
