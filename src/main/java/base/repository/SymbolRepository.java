package base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import base.domain.Symbol;

public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    Symbol findByValue(String value);
}
