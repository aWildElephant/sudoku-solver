package fr.awildelephant.sudoku.solver;

import fr.awildelephant.sudoku.solver.exception.InvalidGridException;
import fr.awildelephant.sudoku.solver.grid.Cell;
import fr.awildelephant.sudoku.solver.grid.Coordinate;
import fr.awildelephant.sudoku.solver.grid.Grid;
import fr.awildelephant.sudoku.solver.grid.Value;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

public class SudokuAssertions {

    public static void assertSolve(String input, String expected) {
        final Grid actualGrid = parse(input);
        try {
            new Solver().solve(actualGrid);
        } catch (InvalidGridException e) {
            fail("Error while solving grid", e);
        }

        final Grid expectedGrid = parse(expected);

        for (int i = 0; i < 81; i++) {
            final Cell actualCell = actualGrid.get(i);
            final Cell expectedCell = expectedGrid.get(i);

            if (actualCell.isSolved() && !expectedCell.isSolved()) {
                fail("Expected coordinate " + new Coordinate(i) + " to not be solvable but found " + actualCell.value());
            } else if (!actualCell.isSolved() && expectedCell.isSolved()) {
                fail("Expected value " + expectedCell.value() + " at coordinate " + new Coordinate(i) + " but could not be solved");
            } else if (actualCell.isSolved() && actualCell.value() != expectedCell.value()) {
                fail("Expected value " + expectedCell.value() + " at coordinate " + new Coordinate(i) + " but got " + actualCell.value());
            }
        }
    }

    private static Grid parse(String input) {
        final String[] cells = Arrays.stream(input.split("\n"))
                .flatMap(row -> Arrays.stream(row.split(" ")))
                .toArray(String[]::new);

        if (cells.length != 81) {
            throw new IllegalArgumentException("Cannot parse sudoku : invalid number of cells " + cells.length);
        }

        final Grid grid = new Grid();

        for (int i = 0; i < 81; i++) {
            final Coordinate coordinate = new Coordinate(i);
            final String cell = cells[i];

            parseValue(cell, coordinate).ifPresent(value -> grid.set(coordinate, value));
        }

        return grid;
    }

    private static Optional<Value> parseValue(String input, Coordinate coordinate) {
        if (".".equals(input)) {
            return Optional.empty();
        }
        try {
            final Value value = switch (Integer.parseInt(input)) {
                case 1 -> Value.V1;
                case 2 -> Value.V2;
                case 3 -> Value.V3;
                case 4 -> Value.V4;
                case 5 -> Value.V5;
                case 6 -> Value.V6;
                case 7 -> Value.V7;
                case 8 -> Value.V8;
                case 9 -> Value.V9;
                default -> null;
            };

            if (value != null) {
                return Optional.of(value);
            }
        } catch (NumberFormatException e) {
            // NOP
        }

        throw new IllegalArgumentException("Cannot parse sudoku : invalid value " + input + " at " + coordinate);
    }
}
