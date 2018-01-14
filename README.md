# Word puzzle solver
Motivation for this project was to bring Wordament game solver I created on Python to the world of web apps. React app is used to input game data and present results created by solver running on Java server.

Thus far I've used Bootstrap in my projects. I wanted to try out and use Sematic UI which I've heard good things about, how it's better component oriented. On server side Bean Validation with exception handlers was something I picked from the excellent book 'Spring in Action' by Graig Walls.

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
- [axios](https://github.com/axios/axios)

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