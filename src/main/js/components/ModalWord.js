import React from 'react';
import PropTypes from 'prop-types';
import { Button, Input, Label, Modal } from 'semantic-ui-react'

class ModalWord extends React.Component {
  constructor(props) {
    super(props);
    this.state = {word:{}, }
    this.onChangeValue = this.onChangeValue.bind(this);
  }

  onChangeValue(e) {
    const wrd = _.assign({}, this.state.word, {value: e.target.value});
    this.setState({word: wrd});
  }

  componentDidMount() {
    const wrd = _.assign({}, this.props.word);
    this.setState({word: wrd});
  }

  render() {
    const word = this.state.word;
    let letterCount;
    if (word.value !== undefined) {
      letterCount = <Label>{word.value.length}</Label>
    }
    else {
      letterCount = <Label>0</Label>
    }

    return (
      <div>
        <Modal size="mini" open={this.props.modalOpen} onClose={() => this.props.close()}>
          <Modal.Header className={this.props.headerStyle}>
            {this.props.title}
          </Modal.Header>
          <Modal.Content>
            <Input
              onChange={this.onChangeValue}
              placeholder={this.props.placeholder}
              labelPosition="right"
              label={letterCount}
            />
          </Modal.Content>
          <Modal.Actions className="modalFooter">
            <Button negative content="Cancel" onClick={() => this.props.close()} />
            <Button positive content='Save' onClick={() => this.props.save(this.state.word)} />
          </Modal.Actions>
        </Modal>
      </div>
    );
  }
}
ModalWord.PropTypes = {
  modalOpen: PropTypes.bool.isRequired,
  title: PropTypes.string,
  save: PropTypes.func.isRequired,
  close: PropTypes.func.isRequired,
  header: PropTypes.string,
  word: PropTypes.object,
  placeholder: PropTypes.string,
}
ModalWord.defaultProps = {
  title: "Add word",
  headerStyle: "modalSuccessHeader",
  word: {id:0, value:""},
  placeholder: "a word to add",
}
export default ModalWord;
