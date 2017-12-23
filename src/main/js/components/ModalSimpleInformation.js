import React from 'react';
import PropTypes from 'prop-types';
import { Button, Modal } from 'semantic-ui-react'

const ModalSimpleInformation = (props) => (
  <div>
    <Modal size="tiny" open={props.modalOpen} onClose={() => props.reply(false)}>
      <Modal.Header className={props.headerStyle}>
        {props.title}
      </Modal.Header>
      <Modal.Content>
        {
          props.name === "" ?
            <strong>{props.notification}</strong> :
            <strong>{props.notification} '{props.name}' !</strong>
        }
      </Modal.Content>
      <Modal.Actions className="modalFooter">
        <Button positive content='OK' onClick={() => props.reply()} />
      </Modal.Actions>
    </Modal>
  </div>
);

ModalSimpleInformation.PropTypes = {
  modalOpen: PropTypes.bool.isRequired,
  title: PropTypes.string.isRequired,
  notification: PropTypes.string.isRequired,
  name: PropTypes.string,
  reply: PropTypes.func.isRequired,
  headerStyle: PropTypes.string,
}
ModalSimpleInformation.defaultProps = {
  title: "Failure occurred",
  notification: "Failed to ",
  name: "",
  headerStyle: "modalDangerHeader",
}
export default ModalSimpleInformation;
