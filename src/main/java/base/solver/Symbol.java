package base.solver;

public interface Symbol {
    int verifyNextSymbol(String nextWord, int wordIndex);
    int points();
}
