package fr.awildelephant.sudoku.solver.grid;

public record Coordinate(int index) {
    @Override
    public String toString() {
        return "(" + (index / 9 + 1) + "," + (index % 9 + 1) + ')';
    }
}
