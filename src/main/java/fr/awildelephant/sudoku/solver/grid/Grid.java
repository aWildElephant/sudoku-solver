package fr.awildelephant.sudoku.solver.grid;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private static final int[] TOP_LEFT_BOX = {0, 1, 2, 9, 10, 11, 18, 19, 20};
    private static final int[] TOP_CENTER_BOX = {3, 4, 5, 12, 13, 14, 21, 22, 23};
    private static final int[] TOP_RIGHT_BOX = {6, 7, 8, 15, 16, 17, 24, 25, 26};
    private static final int[] CENTER_LEFT_BOX = {27, 28, 29, 36, 37, 38, 45, 46, 47};
    private static final int[] CENTER_CENTER_BOX = {30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int[] CENTER_RIGHT_BOX = {33, 34, 35, 42, 43, 44, 51, 52, 53};
    private static final int[] BOTTOM_LEFT_BOX = {54, 55, 56, 63, 64, 65, 72, 73, 74};
    private static final int[] BOTTOM_CENTER_BOX = {57, 58, 59, 66, 67, 68, 75, 76, 77};
    private static final int[] BOTTOM_RIGHT_BOX = {60, 61, 62, 69, 70, 71, 78, 79, 80};

    private final Cell[] backingArray = initializeBackingArray();

    private static Cell[] initializeBackingArray() {
        final Cell[] cells = new Cell[81];
        for (int i = 0; i < 81; i++) {
            cells[i] = new Cell();
        }
        return cells;
    }

    public void set(Coordinate coordinate, Value value) {
        get(coordinate).set(value);
    }

    public Cell get(Coordinate coordinate) {
        return get(coordinate.index());
    }

    public Cell get(int index) {
        return backingArray[index];
    }

    public List<Coordinate> sameRow(Coordinate coordinate) {
        final int index = coordinate.index();
        final int rowIndex = index / 9;
        final List<Coordinate> neighbours = new ArrayList<>(8);
        for (int i = rowIndex * 9; i < (rowIndex + 1) * 9; i++) {
            if (i != index) {
                neighbours.add(new Coordinate(i));
            }
        }
        return neighbours;
    }

    public List<Coordinate> sameColumn(Coordinate coordinate) {
        final int index = coordinate.index();
        final int columnIndex = index % 9;
        final List<Coordinate> neighbours = new ArrayList<>(8);
        for (int i = columnIndex; i < 81; i += 9) {
            if (i != index) {
                neighbours.add(new Coordinate(i));
            }
        }
        return neighbours;
    }

    public List<Coordinate> sameBox(Coordinate coordinate) {
        final int index = coordinate.index();
        final List<Coordinate> coordinates = new ArrayList<>(8);
        for (int i : getIndexesInSameBox(index)) {
            if (i != index) {
                coordinates.add(new Coordinate(i));
            }
        }
        return coordinates;
    }

    public int[] getIndexesInSameBox(int index) {
        final int rowIndex = index / 9;
        final int columnIndex = index % 9;

        if (rowIndex < 3) {
            if (columnIndex < 3) {
                return TOP_LEFT_BOX;
            } else if (columnIndex < 6){
                return TOP_CENTER_BOX;
            } else {
                return TOP_RIGHT_BOX;
            }
        } else if (rowIndex < 6) {
            if (columnIndex < 3) {
                return CENTER_LEFT_BOX;
            } else if (columnIndex < 6) {
                return CENTER_CENTER_BOX;
            } else {
                return CENTER_RIGHT_BOX;
            }
        } else {
            if (columnIndex < 3) {
                return BOTTOM_LEFT_BOX;
            } else if (columnIndex < 6) {
                return BOTTOM_CENTER_BOX;
            } else {
                return BOTTOM_RIGHT_BOX;
            }
        }
    }
}
