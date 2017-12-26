package base.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.command.SymbolAdd;
import base.command.SymbolMod;
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

    @Transactional
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

    @Transactional
    public void deleteSymbol(long id) {
        symbolRepository.delete(id);        
    }

    public boolean isSymbolModValid(SymbolMod symbol) {
        if (null == symbol || null == symbol.getValue() || symbol.getValue().isEmpty() ||
                symbol.getScore() <= 0) {
            return false;
        }
        return true;
    }

    public boolean doesSymbolValueExist(SymbolMod symbol, long id) {
        Symbol oldSymbol = symbolRepository.findByValue(symbol.getValue());
        if (null == oldSymbol || id == oldSymbol.getId()) {
            return false;
        }
        return true;
    }

    @Transactional
    public SymbolDto modifySymbol(long id, SymbolMod symbol) {
        Symbol oldSymbol = symbolRepository.findOne(id);
        oldSymbol.setValue(symbol.getValue());
        oldSymbol.setScore(symbol.getScore());
        oldSymbol = symbolRepository.saveAndFlush(oldSymbol);
        return createDto(oldSymbol);
    }

}
