# Word puzzle solver
Motivation for this project was to bring Wordament game solver I created on [Python](https://github.com/psJnttl/RaspberryPI/tree/master/python/wordPuzzleSolver) to the world of web apps. React app is used to input game data and present results created by solver running on Java server.

Also I wanted to use Sematic UI instead of Bootstrap which I've used in my project thus far. On server side Bean Validation with exception handlers was something I picked from the excellent book 'Spring in Action' by Graig Walls.

##### Profiles
- development
  - in memory database
  - integration tests
- test
  - MySQL
##### Back-end
- Spring Boot 1.5.8
- Java Bean Validation (JS-303)

##### Front-end
- React
- React-Router v4
- [Semantic-ui](https://react.semantic-ui.com)

###### How to use:
1. install UI, in project directory run commands:
```sh
$ npm install
$ npm run-script watch
```
2. start server (will copy UI to correct location). Run command
```sh
$ mvn spring-boot:run
```
3. Application is served on address: http://localhost:8080/

Or import Maven project to IDE of your choosing.
