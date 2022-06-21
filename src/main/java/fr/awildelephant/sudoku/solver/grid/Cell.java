package fr.awildelephant.sudoku.solver.grid;

import java.util.EnumSet;

public class Cell {

    private EnumSet<Value> possibilities = EnumSet.allOf(Value.class);

    public void set(Value value) {
        possibilities = EnumSet.of(value);
    }

    public boolean remove(Value constraint) {
        return possibilities.remove(constraint);
    }

    public boolean isSolved() {
        return possibilities.size() == 1;
    }

    public boolean isValid() {
        return !possibilities.isEmpty();
    }

    public Value value()  {
        return possibilities.iterator().next();
    }
}
