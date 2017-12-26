package base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.command.GameReq;
import base.service.GameService;
import base.service.SolvedGameDto;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;
    
    @RequestMapping(value = "/api/games", method = RequestMethod.POST)
    public ResponseEntity<SolvedGameDto> solveGame(@RequestBody GameReq game) {
        if (!gameService.isGameAreaValid(game)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SolvedGameDto dto = gameService.solve(game);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
