package base.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.service.SymbolDto;
import base.service.SymbolService;

@RestController
public class SymbolController {

    @Autowired
    private SymbolService symbolService;
    
    @RequestMapping(value = "/api/symbols", method = RequestMethod.GET)
    public List<SymbolDto> listAll() {
        return symbolService.listAll();
    }
    
    @RequestMapping(value = "/api/symbols", method = RequestMethod.POST)
    public ResponseEntity<SymbolDto> addSymbol(@RequestBody SymbolAdd symbol) throws URISyntaxException {
        if (!symbolService.isSymbolAddValid(symbol)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (symbolService.doesSymbolExist(symbol)) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
        SymbolDto dto = symbolService.addSymbol(symbol);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/symbols/" + dto.getId()));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);

    }
}
