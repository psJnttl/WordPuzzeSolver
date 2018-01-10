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

import base.command.TileColorAdd;
import base.command.TileColorMod;
import base.dto.ErrorsDto;
import base.dto.TileColorDto;
import base.service.TileColorService;

@RestController
public class TileColorController {
    
    @Autowired
    private TileColorService tileColorService;
    
    @RequestMapping(value="/api/colors", method = RequestMethod.POST)
    public ResponseEntity<TileColorDto> addColor(@RequestBody @Valid TileColorAdd color,
            BindingResult result) throws URISyntaxException {
        if (result.hasErrors()) {
            throw new ItemNotValidException("TileColor", result.getAllErrors());
        }
        TileColorDto dto = tileColorService.addColor(color);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/colors/" + dto.getId()));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/api/colors/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TileColorDto> deleteColor(@PathVariable long id) {
        if (!tileColorService.findTileColor(id).isPresent()) {
            throw new ItemNotFoundException(id, "TileColor");
        }
        tileColorService.deleteColor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/colors/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TileColorDto> modifyColor(@PathVariable long id, 
            @Valid @RequestBody TileColorMod color, 
            BindingResult result) {
        if (!tileColorService.findTileColor(id).isPresent()) {
            throw new ItemNotFoundException(id, "TileColor");
        }
        else if (result.hasErrors()) {
            throw new ItemNotValidException("TileColor", result.getAllErrors());
        }
        TileColorDto dto = tileColorService.modifyTileColor(id, color);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/colors/{id}", method = RequestMethod.GET)
    public ResponseEntity<TileColorDto> findColor(@PathVariable long id) {
        Optional<TileColorDto> cDto = tileColorService.findTileColor(id);  
        if (!cDto.isPresent()) {
            throw new ItemNotFoundException(id, "TileColor");
        }
        return new ResponseEntity<>(cDto.get(), HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/colors", method = RequestMethod.GET)
    public List<TileColorDto> listAll() {
        return tileColorService.listAll();
    }
    
    @ExceptionHandler(ItemNotValidException.class)
    public ResponseEntity<ErrorsDto> colorNotValid(ItemNotValidException e) {
        List<String> msgs = e.getValidationMessages().stream()
                .map(m -> m.getDefaultMessage()).collect(Collectors.toList());
        ErrorsDto dto = new ErrorsDto(e.getItemName(), msgs);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorsDto> colorNotFound(ItemNotFoundException e) {
        String message = e.getItemName() + " with id " + e.getId() + " not found.";
        List<String> msgs = new ArrayList<>(Arrays.asList(message));
        ErrorsDto dto = new ErrorsDto(e.getItemName(), msgs);
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
