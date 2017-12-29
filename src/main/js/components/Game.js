import React from 'react';
import PropTypes from 'prop-types';
import { Grid, Input } from 'semantic-ui-react'
import _ from 'lodash';
import GameTile from './GameTile';

class Game extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tileValues: ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p'],
    };
    this.onChangeTileValue = this.onChangeTileValue.bind(this);
  }

  onChangeTileValue(e, index) {
    var array = this.state.tileValues;
    array[index] = e.target.value;
    this.setState({tileValues: _.take(array, 16)})
  }

  render() {

    return (
      <div>
        <h4>Game</h4>
        <Grid columns={4}>
          <Grid.Row>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[0]}
                onChange={this.onChangeTileValue}
                index={0}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[1]}
                onChange={this.onChangeTileValue}
                index={1}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[2]}
                onChange={this.onChangeTileValue}
                index={2}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[3]}
                onChange={this.onChangeTileValue}
                index={3}
              />
            </Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[4]}
                onChange={this.onChangeTileValue}
                index={4}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[5]}
                onChange={this.onChangeTileValue}
                index={5}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[6]}
                onChange={this.onChangeTileValue}
                index={6}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[7]}
                onChange={this.onChangeTileValue}
                index={7}
              />
            </Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[8]}
                onChange={this.onChangeTileValue}
                index={8}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[9]}
                onChange={this.onChangeTileValue}
                index={9}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[10]}
                onChange={this.onChangeTileValue}
                index={10}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[11]}
                onChange={this.onChangeTileValue}
                index={11}
              />
            </Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[12]}
                onChange={this.onChangeTileValue}
                index={12}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[13]}
                onChange={this.onChangeTileValue}
                index={13}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[14]}
                onChange={this.onChangeTileValue}
                index={14}
              />
            </Grid.Column>
            <Grid.Column>
              <GameTile
                value={this.state.tileValues[15]}
                onChange={this.onChangeTileValue}
                index={15}
              />
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </div>
    );
  }
}
Game.PropTypes = {}
Game.defaultProps = {}
export default Game;
