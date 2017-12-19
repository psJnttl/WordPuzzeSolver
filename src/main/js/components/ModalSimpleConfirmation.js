import React from 'react';
import PropTypes from 'prop-types';
import { Button, Icon, Modal } from 'semantic-ui-react'

class ModalSimpleConfirmation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}

  }

  render() {
    const open = this.props.modalOpen;
    return (
      <div>
        <Modal size="tiny" open={open} onClose={() => this.props.reply(false)}>
          <Modal.Header className={this.props.headerStyle}>
            {this.props.title}
          </Modal.Header>
          <Modal.Content>
            <strong>{this.props.question}</strong>
          </Modal.Content>
          <Modal.Actions className="modalFooter">
            <Button negative content="No" onClick={() => this.props.reply(false)} />
            <Button positive content='Yes' onClick={() => this.props.reply(true)} />
          </Modal.Actions>
        </Modal>
      </div>
    );
  }
}
ModalSimpleConfirmation.PropTypes = {
  modalOpen: PropTypes.bool.isRequired,
  title: PropTypes.string,
  question: PropTypes.string,
  reply: PropTypes.func.isRequired,
  headerStyle: PropTypes.string,
}
ModalSimpleConfirmation.defaultProps = {
  title: "Please confirm",
  question: "Are you sure",
  headerStyle: "modalDangerHeader"
}
export default ModalSimpleConfirmation;
