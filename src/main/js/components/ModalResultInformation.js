import React from 'react';
import PropTypes from 'prop-types';
import { Button, Modal } from 'semantic-ui-react'

const ModalResultInformation = (props) => (
  <div>
    <Modal size="tiny" open={props.modalOpen} onClose={() => props.reply(false)}>
      <Modal.Header className={props.headerStyle}>
        {props.title}
      </Modal.Header>
      <Modal.Content>
        Words found: {props.count}<br />
        Solving took: {props.time} ms
      </Modal.Content>
      <Modal.Actions className="modalFooter">
        <Button positive content='OK' onClick={() => props.reply()} />
      </Modal.Actions>
    </Modal>
  </div>
);
ModalResultInformation.PropTypes = {
  modalOpen: PropTypes.bool.isRequired,
  reply: PropTypes.func.isRequired,
  title: PropTypes.string,
  count: PropTypes.number,
  time: PropTypes.number,
  headerStyle: PropTypes.string,
}
ModalResultInformation.defaultProps = {
  title: "Game solved.",
  count: 0,
  time: 0,
  headerStyle: "modalSuccessHeader",
}
export default ModalResultInformation;
