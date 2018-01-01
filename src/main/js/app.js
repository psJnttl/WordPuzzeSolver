import React from 'react';
import PropTypes from 'prop-types';
import {NavLink, BrowserRouter as Router, Route,  Link, Switch} from 'react-router-dom';
import { Input, Menu } from 'semantic-ui-react';
import Words from './components/Words';
import Symbols from './components/Symbols';
import Game from './components/Game';

const Nav = (props) => (
  <div>
    <Menu>
      <NavLink exact activeClassName="active" to="/">
        <Menu.Item>
          Home
        </Menu.Item>
      </NavLink>
      <NavLink activeClassName="active" to="/game" >
        <Menu.Item>
          Game
        </Menu.Item>
      </NavLink>
      <NavLink activeClassName="active" to="/words" >
        <Menu.Item>
          Words
        </Menu.Item>
      </NavLink>
      <NavLink activeClassName="active" to="/symbols" >
        <Menu.Item>Symbols
        </Menu.Item>
      </NavLink>
      <NavLink activeClassName="active" to="/about">
        <Menu.Item>
          About
        </Menu.Item>
      </NavLink>
    </Menu>
  </div>
);

const Home = () => (
  <div>
    Home page of the application.
  </div>
);

const About = () => (
  <div>
    This is a project for solving Wordament.<br />
    <Input
      placeholder='Search...'
    />
  </div>
);

class App extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <Router>
        <div >
          <Nav />
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/game" component={Game} />
            <Route path="/words" component={Words} />
            <Route path="/symbols" component={Symbols} />
            <Route path="/about" component={About} />
            <Route render={ () => <div><h1>404 - Not Found!</h1></div>} />
          </Switch>
        </div>
      </Router>
    );
  }
}
App.PropTypes = {}
App.defaultProps = {}

export default App;
