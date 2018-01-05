import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import ColorTile from './ColorTile';
import ColorPanel from './ColorPanel';
import _ from 'lodash';
import ModalSimpleInformation from './ModalSimpleInformation';
import ModalSimpleConfirmation from './ModalSimpleConfirmation';

class Colors extends React.Component {
  constructor(props) {
    super(props);
    this.state = {colors: [], selectedColor: -1, mouseOverColor: -1,
      infoModalVisible: false, infoModalData: {},
      saveConfirmationVisible: false,
    };
    this.selectColor = this.selectColor.bind(this);
    this.mouseOverTile = this.mouseOverTile.bind(this);
    this.mouseOnBackgound = this.mouseOnBackgound.bind(this);
    this.handleChangeColor = this.handleChangeColor.bind(this);
    this.saveColor = this.saveColor.bind(this);
    this.closeInfoModal = this.closeInfoModal.bind(this);
    this.saveReply = this.saveReply.bind(this);
    this.setSaveConfirmModalVisible = this.setSaveConfirmModalVisible.bind(this);
  }

  selectColor() {
    this.setState({selectedColor: this.state.mouseOverColor});
  }

  loadColors() {
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get('/api/colors/', config)
         .then(function (response) {
           self.setState({colors: response.data});
         })
         .catch(function (error) {
           self.setState({infoModalVisible: true,
               infoModalData: {
               title:"Loading colors failed",
              notification: "Failed to load colors from server!",
              name: ""}
            });
         });
  }

  saveColor() {
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    const command = this.state.colors[this.state.selectedColor];
    const url = '/api/colors/' + command.id;
    axios.put(url, command, config)
         .then(function (response) {
           self.loadColors();
         })
         .catch(function (error) {
           self.setState({infoModalVisible: true,
               infoModalData: {
               title:"Saving a color failed",
              notification: "Failed to save a color to server!",
              name: ""}
            });
         });
  }

  mouseOverTile(index) {
    this.setState({mouseOverColor: index});
  }

  mouseOnBackgound() {
    this.setState({mouseOverColor: -1});
  }

  handleChangeColor(e, item) {
    var value = e.target.value;
    var array = _.take(this.state.colors, 16);
    var color = array[this.state.selectedColor];
    if ("alpha" !== item && value !== "") {
      var check = parseInt(value);
      if (check > 255) {
        check = 255;
      }
      else if (check < 0) {
        check = 0;
      }
      color[item] = check;
    }
    else if ("alpha" !== item && value === "") {
      color[item] = "";
    }
    else if ("alpha" === item && value !== ""){
      var check = parseFloat(value);
      if (check > 1) {
        check = 1;
      }
      else if (check < 0) {
        check = 0;
      }
      color[item] = check;
    }
    else if ("alpha" === item && value === ""){
      color[item] = "";
    }
    array[this.state.selectedColor] = color;
    this.setState({colors: array});
  }

  closeInfoModal() {
    this.setState({infoModalVisible: false, infoModalData: {}})
  }

  setSaveConfirmModalVisible() {
    if (false === this.state.saveConfirmationVisible) {
      this.setState({saveConfirmationVisible: true});
    }
  }

  saveReply(answer) {
    if (true === answer) {
      this.saveColor();
    }
    this.setState({saveConfirmationVisible: false });
  }

  componentWillMount() {
    this.loadColors();
  }

  render() {
    const tiles = this.state.colors.map((item, index) =>
      <li key={index}>
        <ColorTile
          color={this.state.colors[index]}
          selected={index == this.state.selectedColor}
          index={index}
          select={this.selectColor}
          mouseOver={this.mouseOverTile}
          mouseLeave={this.mouseOnBackgound}
        />
      </li>
    );
    const line1 = tiles.filter((item, index) => index >= 0 && index < 8);
    const line2 = tiles.filter((item, index) => index >= 8 && index < 16);

    return (
      <div style={{'marginLeft': 10}} >
        <ModalSimpleInformation
          modalOpen={this.state.infoModalVisible}
          title={this.state.infoModalData.title}
          notification={this.state.infoModalData.notification}
          name={this.state.infoModalData.name}
          reply={this.closeInfoModal}
        />
        <ModalSimpleConfirmation
          modalOpen={this.state.saveConfirmationVisible}
          title="Save color"
          question={"Are you sure you want to over write color '" + this.state.selectedColor + "' ?"}
          reply={this.saveReply}
        />

        <div onClick={this.selectColor}>
          Color editor<br/>
          <ul style={{'display': 'flex', 'listStyleType': 'none', 'flexWrap': 'wrap'}}>
            {line1}
          </ul>
          <ul style={{'display': 'flex', 'listStyleType': 'none', 'flexWrap': 'wrap'}}>
            {line2}
          </ul>
        </div>
        <div style={{'marginLeft': 45}}>
          {this.state.selectedColor !== -1 &&
            <ColorPanel
              color={this.state.colors[this.state.selectedColor]}
              changeColor={this.handleChangeColor}
              saveColor={this.setSaveConfirmModalVisible}
            />}
        </div>
      </div>
    );
  }
}
Colors.PropTypes = {}
Colors.defaultProps = {}
export default Colors;
