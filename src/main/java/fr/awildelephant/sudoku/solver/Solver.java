package fr.awildelephant.sudoku.solver;

import fr.awildelephant.sudoku.solver.exception.InvalidGridException;
import fr.awildelephant.sudoku.solver.grid.Cell;
import fr.awildelephant.sudoku.solver.grid.Coordinate;
import fr.awildelephant.sudoku.solver.grid.Grid;
import fr.awildelephant.sudoku.solver.grid.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Solver {

    public void solve(Grid grid) throws InvalidGridException {
        Set<Coordinate> nextStep = solvedCoordinates(grid);
        do {
            nextStep = processStep(grid, nextStep);
        } while (!nextStep.isEmpty());
    }

    private Set<Coordinate> processStep(Grid grid, Set<Coordinate> currentStep) throws InvalidGridException {
        final Set<Coordinate> nextStep = new HashSet<>();

        for (Coordinate coordinate : currentStep) {
            nextStep.addAll(applyConstraint(grid, coordinate));
        }

        return nextStep;
    }

    /**
     * @return all coordinates solved by applying the given constraint
     */
    private Set<Coordinate> applyConstraint(Grid grid, Coordinate coordinate) throws InvalidGridException {
        final Set<Coordinate> neighbours = new HashSet<>(grid.sameBox(coordinate));
        neighbours.addAll(grid.sameRow(coordinate));
        neighbours.addAll(grid.sameColumn(coordinate));

        final Value value = grid.get(coordinate).value();

        final Set<Coordinate> solved = new HashSet<>();
        for (Coordinate neighbour : neighbours) {
            final Cell cell = grid.get(neighbour);
            final boolean impacted = cell.remove(value);
            if (impacted) {
                if (!cell.isValid()) {
                    throw new InvalidGridException(); // TODO: would be nice to know why
                } else if (cell.isSolved()) {
                    solved.add(neighbour);
                }
            }
        }
        return solved;
    }

    private Set<Coordinate> solvedCoordinates(Grid grid) {
        return allCoordinates().stream()
                .filter(coordinate -> grid.get(coordinate).isSolved())
                .collect(Collectors.toSet());
    }

    private Set<Coordinate> allCoordinates() {
        final Set<Coordinate> result = new HashSet<>(81);
        for (int i = 0; i < 81; i++) {
            result.add(new Coordinate(i));
        }
        return result;
    }
}
