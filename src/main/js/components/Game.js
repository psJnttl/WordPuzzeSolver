import React from 'react';
import PropTypes from 'prop-types';
import { Grid, Input } from 'semantic-ui-react'
import _ from 'lodash';

class Game extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tileValues: ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p'],
    };
    this.onChangeTileValue0 = this.onChangeTileValue0.bind(this);
  }

  onChangeTileValue0(e) {
    const value = e.target.value;
    var array = this.state.tileValues;
    array[0] = value;
    const poista = _.take(array, 16);
    this.setState({tileValues: poista})
  }
  render() {
    return (
      <div>
        <h4>Game</h4>
        <Grid columns={4}>
          <Grid.Row celled>
            <Grid.Column>
              
            </Grid.Column>
            <Grid.Column>2</Grid.Column>
            <Grid.Column>3</Grid.Column>
            <Grid.Column>4</Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column>5</Grid.Column>
            <Grid.Column>6</Grid.Column>
            <Grid.Column>7</Grid.Column>
            <Grid.Column>8</Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column>9</Grid.Column>
            <Grid.Column>10</Grid.Column>
            <Grid.Column>11</Grid.Column>
            <Grid.Column>12</Grid.Column>
          </Grid.Row>
          <Grid.Row>
            <Grid.Column>13</Grid.Column>
            <Grid.Column>14</Grid.Column>
            <Grid.Column>15</Grid.Column>
            <Grid.Column>16</Grid.Column>
          </Grid.Row>
        </Grid>
      </div>
    );
  }
}
Game.PropTypes = {}
Game.defaultProps = {}
export default Game;
