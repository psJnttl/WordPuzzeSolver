package base.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.domain.Symbol;
import base.repository.SymbolRepository;

@Service
public class SymbolService {

    @Autowired
    private SymbolRepository symbolRepository;

    public List<SymbolDto> listAll() {
        List<Symbol> symbols = symbolRepository.findAll();
        return symbols.stream()
                      .map(s -> createDto(s))
                      .collect(Collectors.toList());
    }
    
    private SymbolDto createDto(Symbol symbol) {
        return new SymbolDto(symbol.getId(), symbol.getValue(), symbol.getScore());
    }
}
