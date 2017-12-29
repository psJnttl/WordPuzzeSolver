package base.solver.symbol;

import java.util.Optional;

/**
 * Builds correct class implementing Symbol interface based on letters passed.
 * E.g. single letter -> Letter class
 * 
 * @author Pasi
 *
 */
public class SymbolFactory {

    /**
     * Returns a symbol representing characters in a game area tile. A tile can contain
     * single letter, several 1-3 letters - a digram, a word starting or ending digram 
     * or it can be an either/or letter choice. Every tile is worth some amount of points.
     * 
     * @param letters         Letter(s) symbol consist of.
     * @param gameAreaIndex   Symbol location in game area 0...15
     * @param points          The amount of points symbol is worth in the game.
     * @return                The game area Symbol
     * @throws IllegalArgumentException if Symbol can't be created from given letters.
     */
    public static Symbol create(String letters, int gameAreaIndex, int points) {
        if (letters.length() == 1 && letters.matches("[a-zA-Z]")) {
            return new Letter(letters, gameAreaIndex, points);
        }
        else if (letters.matches("[0-9]{1,2}")) {
            return new IndexNumber(letters, gameAreaIndex, points);
        }
        else if (letters.length() > 1 && letters.length() < 4 && letters.matches("[a-zA-Z]{2,3}")) {
            return new Digram(letters, gameAreaIndex, points);
        }
        else if (letters.length() > 2 && letters.length() < 5 && letters.matches("[a-zA-Z]{2,3}-")) {
            return new DigramFirst(letters, gameAreaIndex, points);
        }
        else if (letters.length() > 2 && letters.length() < 5 && letters.matches("-[a-zA-Z]{2,3}")) {
            return new DigramLast(letters, gameAreaIndex, points);
        }
        else if (letters.length() == 3 && letters.matches("[a-zA-Z]/[a-zA-Z]")) {
            return new EitherOr(letters, gameAreaIndex, points);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nletters: ")
          .append(letters)
          .append("\nindex: ")
          .append(gameAreaIndex)
          .append("\npoints: ")
          .append(points);
        throw new IllegalArgumentException("Can't determine Symbol from given parameter." + sb.toString());
    }
}