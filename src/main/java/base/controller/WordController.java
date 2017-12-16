package base.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @RequestMapping(value = "/api/words", method = RequestMethod.POST)
    public ResponseEntity<WordDto> addWord(@RequestBody WordAdd word) throws URISyntaxException {
        if (null == word || null == word.getValue() || word.getValue().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (wordService.doesWordExistInDb(word)) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
        WordDto dto = wordService.addWord(word);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/words/" + dto.getId()));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/api/words/{id}", method = RequestMethod.GET)
    public ResponseEntity<WordDto> fetchWord(@PathVariable long id) {
        Optional<WordDto> oWdto = wordService.findWord(id);
        if (!oWdto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(oWdto.get(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/words/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<WordDto> deleteWord(@PathVariable long id) {
        if (!wordService.findWord(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        wordService.deleteWord(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/words/{id}", method = RequestMethod.PUT)
    public ResponseEntity<WordDto> modifyWord(@PathVariable long id, @RequestBody WordMod word) {
        if (!wordService.findWord(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (null == word || word.getValue().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WordDto dto = wordService.modifyWord(id, word);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
