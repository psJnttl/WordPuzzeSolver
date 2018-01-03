package base.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.command.TileColorAdd;
import base.command.TileColorMod;
import base.service.TileColorDto;
import base.service.TileColorService;

@RestController
public class TileColorController {
    
    @Autowired
    private TileColorService tileColorService;
    
    @RequestMapping(value="/api/colors", method = RequestMethod.POST)
    public ResponseEntity<TileColorDto> addColor(@RequestBody @Valid TileColorAdd color,
            BindingResult result) throws URISyntaxException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TileColorDto dto = tileColorService.addColor(color);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/colors/" + dto.getId()));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/api/colors/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TileColorDto> deleteColor(@PathVariable long id) {
        if (!tileColorService.findTileColor(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tileColorService.deleteColor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/colors/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TileColorDto> modifyColor(@PathVariable long id, 
            @Valid @RequestBody TileColorMod color, 
            BindingResult result) {
        if (!tileColorService.findTileColor(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TileColorDto dto = tileColorService.modifyTileColor(id, color);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/colors/{id}", method = RequestMethod.GET)
    public ResponseEntity<TileColorDto> findColor(@PathVariable long id) {
        Optional<TileColorDto> cDto = tileColorService.findTileColor(id);  
        if (!cDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cDto.get(), HttpStatus.OK);
    }
}