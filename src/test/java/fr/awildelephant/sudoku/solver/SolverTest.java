package fr.awildelephant.sudoku.solver;

import org.junit.jupiter.api.Test;

import static fr.awildelephant.sudoku.solver.SudokuAssertions.assertSolve;

class SolverTest {

    @Test
    void solve() {
        assertSolve(
                """
                        . . . 2 6 . 7 . 1
                        6 8 . . 7 . . 9 .
                        1 9 . . . 4 5 . .
                        8 2 . 1 . . . 4 .
                        . . 4 6 . 2 9 . .
                        . 5 . . . 3 . 2 8
                        . . 9 3 . . . 7 4
                        . 4 . . 5 . . 3 6
                        7 . 3 . 1 8 . . .
                        """,
                """
                        4 3 5 2 6 9 7 8 1
                        6 8 2 5 7 1 4 9 3
                        1 9 7 8 3 4 5 6 2
                        8 2 6 1 9 5 3 4 7
                        3 7 4 6 8 2 9 1 5
                        9 5 1 7 4 3 6 2 8
                        5 1 9 3 2 6 8 7 4
                        2 4 8 9 5 7 1 3 6
                        7 6 3 4 1 8 2 5 9  
                        """
        );
    }
}