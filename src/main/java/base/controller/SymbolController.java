package base.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.command.SymbolAdd;
import base.command.SymbolMod;
import base.dto.ErrorsDto;
import base.dto.SymbolDto;
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
    public ResponseEntity<SymbolDto> addSymbol(@RequestBody @Valid SymbolAdd symbol,
            BindingResult result) throws URISyntaxException {
        if (result.hasErrors()) {
            throw new ItemNotValidException("Symbol", result.getAllErrors());
        }
        if (symbolService.doesSymbolExist(symbol)) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
        SymbolDto dto = symbolService.addSymbol(symbol);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/symbols/" + dto.getId()));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);

    }
    
    @RequestMapping(value = "/api/symbols/{id}", method = RequestMethod.GET) 
    public ResponseEntity<SymbolDto> fetchSymbol(@PathVariable long id) {
        Optional<SymbolDto> oSdto = symbolService.findSymbol(id);
        if (!oSdto.isPresent()) {
            throw new ItemNotFoundException(id, "Symbol");
        }
        return new ResponseEntity<>(oSdto.get(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/symbols/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<SymbolDto> deleteSymbol(@PathVariable long id) {
        if (!symbolService.findSymbol(id).isPresent()) {
            throw new ItemNotFoundException(id, "Symbol");
        }
        symbolService.deleteSymbol(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/symbols/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SymbolDto> modifySymbol(@PathVariable long id, 
            @RequestBody @Valid SymbolMod symbol, BindingResult result) {
        if (!symbolService.findSymbol(id).isPresent()) {
            throw new ItemNotFoundException(id, "Symbol");
        }
        if (result.hasErrors()) {
            throw new ItemNotValidException("Symbol", result.getAllErrors());
        }      
        if (symbolService.doesSymbolValueExist(symbol, id)) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
        SymbolDto dto = symbolService.modifySymbol(id, symbol);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorsDto> symbolNotFound(ItemNotFoundException e) {
        String message = e.getItemName() + " with id " + e.getId() + " not found.";
        List<String> msgs = new ArrayList<>(Arrays.asList(message));
        ErrorsDto dto = new ErrorsDto("Symbol", msgs);
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ItemNotValidException.class)
    public ResponseEntity<ErrorsDto> symbolNotValid(ItemNotValidException e) {
        List<String> msgs = e.getValidationMessages().stream()
                             .map(m -> m.getDefaultMessage()).collect(Collectors.toList());
        ErrorsDto dto = new ErrorsDto("Symbol", msgs);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
