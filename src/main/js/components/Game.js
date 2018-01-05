import React from 'react';
import PropTypes from 'prop-types';
import { Button, Grid, Input, Popup } from 'semantic-ui-react'
import _ from 'lodash';
import GameTile from './GameTile';
import axios from 'axios';
import FoundWord from './FoundWord';
import PresetGames from './PresetGames';
import defaultColors from './defaultColors';

class Game extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tileValues: ["d", "a", "a", "p", "e", "l", "o", "a", "a", "l", "a", "a", "a", "a", "a", "e"],
      result: [], selectedWordIndex: '', usedColors: [],
    };
    this.onChangeTileValue = this.onChangeTileValue.bind(this);
    this.sendGameToServer = this.sendGameToServer.bind(this);
    this.selectResultWord = this.selectResultWord.bind(this);
    this.setTileValues = this.setTileValues.bind(this);
    this.loadColors = this.loadColors.bind(this);
  }

  onChangeTileValue(e, index) {
    var array = this.state.tileValues;
    array[index] = e.target.value;
    this.setState({tileValues: _.take(array, 16)})
  }

  sendGameToServer() {
    this.setState({result: [], selectedWordIndex:'', usedColors: []});
    const self = this;
    const command = {gameTiles: _.take(this.state.tileValues, 16)};
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.post('/api/games/', command, config)
         .then(function (response) {
           const words = response.data.words;
           self.setState({result: words});
         })
         .catch(function (error) {
           console.log("Failed to solve game.");
         });
  }

  selectResultWord(e, index) {
    const word = this.state.result[index];
    var array = _.times(16, _.constant({red: 255, green: 255, blue: 255, alpha: 1} ));
    for (var i = 0; i < word.path.length; i++) {
      var wIdx = word.path[i];
      array[wIdx] = this.state.defaultColors[i];
    }
    this.setState({usedColors: array, selectedWordIndex: index});
  }

  setTileValues(tiles) {
    this.setState({tileValues: tiles, result: [], selectedWordIndex:'', usedColors: []});
  }

 componentWillMount() {
   this.loadColors();
 }

 loadColors() {
   const self = this;
   const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
   axios.get('/api/colors/', config)
        .then(function (response) {
          const colors = response.data;
          console.log("colors:");
          console.log(colors);
          self.setState({defaultColors: response.data});
        })
        .catch(function (error) {
          console.log("Failed to load colors from server.");
        });
 }

  render() {
    const resultData = this.state.result.map( (item, index) =>
      <li key={index}>
        <FoundWord
          points={item.points}
          word={item.value}
          onClick={this.selectResultWord}
          index={index}
          selected={this.state.selectedWordIndex === index}
        />
      </li>
    );
    let resultList;
    if (this.state.result.length > 0) {
      resultList =
      <ul style={{'listStyleType': 'none'}}>{resultData}</ul>
    }
    else {
      resultList = null;
    }

    return (
      <div>
        <h4>Game</h4>
        <Grid >
          <Grid.Row>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[0]}
                onChange={this.onChangeTileValue}
                index={0}
                background={this.state.usedColors[0]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[1]}
                onChange={this.onChangeTileValue}
                index={1}
                background={this.state.usedColors[1]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[2]}
                onChange={this.onChangeTileValue}
                index={2}
                background={this.state.usedColors[2]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[3]}
                onChange={this.onChangeTileValue}
                index={3}
                background={this.state.usedColors[3]}
              />
            </Grid.Column>
            <Grid.Column width="4">
              <PresetGames selectGame={this.setTileValues}/>
            </Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[4]}
                onChange={this.onChangeTileValue}
                index={4}
                background={this.state.usedColors[4]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[5]}
                onChange={this.onChangeTileValue}
                index={5}
                background={this.state.usedColors[5]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[6]}
                onChange={this.onChangeTileValue}
                index={6}
                background={this.state.usedColors[6]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[7]}
                onChange={this.onChangeTileValue}
                index={7}
                background={this.state.usedColors[7]}
              />
            </Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[8]}
                onChange={this.onChangeTileValue}
                index={8}
                background={this.state.usedColors[8]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[9]}
                onChange={this.onChangeTileValue}
                index={9}
                background={this.state.usedColors[9]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[10]}
                onChange={this.onChangeTileValue}
                index={10}
                background={this.state.usedColors[10]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[11]}
                onChange={this.onChangeTileValue}
                index={11}
                background={this.state.usedColors[11]}
              />
            </Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[12]}
                onChange={this.onChangeTileValue}
                index={12}
                background={this.state.usedColors[12]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[13]}
                onChange={this.onChangeTileValue}
                index={13}
                background={this.state.usedColors[13]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[14]}
                onChange={this.onChangeTileValue}
                index={14}
                background={this.state.usedColors[14]}
              />
            </Grid.Column>
            <Grid.Column width="2">
              <GameTile
                value={this.state.tileValues[15]}
                onChange={this.onChangeTileValue}
                index={15}
                background={this.state.usedColors[15]}
              />
            </Grid.Column>
          </Grid.Row>
        </Grid>
        <br />
        <Button
          content="Solve"
          icon="lightning"
          color="green"
          onClick={() => this.sendGameToServer()}
        />
        <br />

        {resultList}
      </div>
    );
  }
}
Game.PropTypes = {}
Game.defaultProps = {}
export default Game;
