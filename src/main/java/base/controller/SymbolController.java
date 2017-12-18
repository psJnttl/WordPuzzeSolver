package base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
