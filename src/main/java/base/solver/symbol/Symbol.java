package base.solver.symbol;

public interface Symbol {
    int verifyNextSymbol(String nextWord, int wordIndex);
    int points();
    Symbol copyOf();
}
