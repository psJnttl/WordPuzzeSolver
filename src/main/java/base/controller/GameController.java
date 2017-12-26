package base.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @RequestMapping(value = "/api/games", method = RequestMethod.POST)
    public void solveGame(@RequestBody GameReq game) {
        
    }
}
