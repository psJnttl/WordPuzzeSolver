package base.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.command.TileColorAdd;
import base.service.TileColorDto;
import base.service.TileColorService;

@RestController
public class TileColorController {
    
    @Autowired
    private TileColorService colorService;
    
    @RequestMapping(value="/api/colors", method = RequestMethod.POST)
    ResponseEntity<TileColorDto> addColor(@RequestBody @Valid TileColorAdd color,
            BindingResult result) throws URISyntaxException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TileColorDto dto = colorService.addColor(color);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/colors/" + dto.getId()));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }
}
