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

import base.command.ColorAdd;
import base.service.ColorDto;
import base.service.ColorService;

@RestController
public class ColorController {
    
    @Autowired
    private ColorService colorService;
    
    @RequestMapping(value="/api/colors", method = RequestMethod.POST)
    ResponseEntity<ColorDto> addColor(@RequestBody @Valid ColorAdd color,
            BindingResult result) throws URISyntaxException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ColorDto dto = colorService.addColor(color);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/colors/" + dto.getId()));
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }
}
