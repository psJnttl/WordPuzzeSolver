package base.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.controller.SymbolAdd;
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

    public boolean isSymbolAddValid(SymbolAdd symbol) {
        if (null == symbol || null == symbol.getValue() || symbol.getValue().isEmpty() ||
                symbol.getScore() <= 0) {
            return false;
        }
        return true;
    }

    public boolean doesSymbolExist(SymbolAdd symbol) {
        Symbol oldSymbol = symbolRepository.findByValue(symbol.getValue());
        if (null != oldSymbol) {
            return true;
        }
        return false;
    }

    public SymbolDto addSymbol(SymbolAdd symbol) {
        Symbol newSymbol = new Symbol(symbol.getValue(), symbol.getScore());
        newSymbol = symbolRepository.saveAndFlush(newSymbol);
        return createDto(newSymbol);
    }

    public Optional<SymbolDto> findSymbol(long id) {
        Symbol symbol = symbolRepository.findOne(id);
        if (null == symbol) {
            return Optional.empty();
        }
        SymbolDto dto = createDto(symbol);
        return Optional.of(dto);
    }
    
    
}
