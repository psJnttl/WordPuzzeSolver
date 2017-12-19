import React from 'react';
import PropTypes from 'prop-types';
import { Button, Icon, Modal } from 'semantic-ui-react'

class ModalSimpleConfirmation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}

  }

  render() {
    const {modalOpen} = this.props;
    return (
      <div>
        <Modal size="tiny"> open={modalOpen}
          <Modal.Header>
            Delete Your Account
          </Modal.Header>
          <Modal.Content>
            <p>Are you sure you want to delete your account</p>
          </Modal.Content>
          <Modal.Actions>
            <Button negative>
              No
            </Button>
            <Button positive icon='checkmark' labelPosition='right' content='Yes' />
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
}
ModalSimpleConfirmation.defaultProps = {
  title: "Please confirm",
  question: "Are you sure?",
}
export default ModalSimpleConfirmation;
