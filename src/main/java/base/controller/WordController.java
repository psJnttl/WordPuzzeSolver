package base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.service.WordDto;
import base.service.WordService;

@RestController
public class WordController {

    @Autowired
    private WordService wordService;
    
    @RequestMapping(value = "/api/words", method = RequestMethod.GET)
    public List<WordDto> listAllWords() {
        return wordService.listAll();
    }
    
}
