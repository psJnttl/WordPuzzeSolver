import React from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';
import { Button, Input, List, Modal } from 'semantic-ui-react'

class ModalSymbol extends React.Component {
  constructor(props) {
    super(props);
    this.state = {symbol:{id:0, value:"", score: 1}, }
    this.onChangeValue = this.onChangeValue.bind(this);
    this.onChangeScore = this.onChangeScore.bind(this);
    this.validateSymbol = this.validateSymbol.bind(this);
  }

  onChangeValue(e) {
    const smbl = _.assign({}, this.state.symbol, {value: e.target.value});
    this.setState({symbol: smbl});
  }

  onChangeScore(e) {
    if (e.target.value < 1) {
      return;
    }
    const smbl = _.assign({}, this.state.symbol, {score: e.target.value});
    this.setState({symbol: smbl});
  }

  validateSymbol() {
    return this.state.symbol.value.length > 0;
  }

  componentDidMount() {
    const smbl = _.assign({}, this.props.symbol);
    this.setState({symbol: smbl});
  }

  render() {
    return (
      <Modal size="mini" open={this.props.modalOpen} onClose={() => this.props.close()}>
        <Modal.Header className={this.props.headerStyle}>
          {this.props.title}
        </Modal.Header>
        <Modal.Content>
          <List>
            <List.Item>
              <Input
                onChange={this.onChangeValue}
                placeholder="symbol"
                value={this.state.symbol.value}
              /> <br />
            </List.Item>
            <List.Item>
              <Input
                onChange={this.onChangeScore}
                placeholder="score"
                value={this.state.symbol.score}
                type="number"
              />
            </List.Item>
          </List>
        </Modal.Content>
        <Modal.Actions className="modalFooter">
          <Button negative content="Cancel" onClick={() => this.props.close()} />
          <Button positive content='Save' onClick={() => this.props.save(this.state.symbol)}
            disabled={ ! this.validateSymbol() } />
        </Modal.Actions>
      </Modal>
    );
  }
}
ModalSymbol.PropTypes = {
  modalOpen: PropTypes.bool.isRequired,
  title: PropTypes.string,
  save: PropTypes.func.isRequired,
  close: PropTypes.func.isRequired,
  header: PropTypes.string,
  symbol: PropTypes.object,
}
ModalSymbol.defaultProps = {
  title: "Add symbol",
  headerStyle: "modalSuccessHeader",
  symbol: {id:0, value:"", score: 1},
}
export default ModalSymbol;
