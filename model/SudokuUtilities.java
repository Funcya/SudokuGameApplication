package javafx.fxtest.model.model;

import java.io.Serializable;

public class SudokuUtilities implements Serializable {

    public enum SudokuLevel {EASY, MEDIUM, HARD}

    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param level The level, i.e. the difficulty, of the initial standing.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        SudokuBoard fullSolvedBoard = generateFullSolvedBoard();
        SudokuBoard solutionCopy = copyBoard(fullSolvedBoard);

        fullSolvedBoard.removeCells(fullSolvedBoard, level);
        setImmutableCells(fullSolvedBoard);

        int[][][] sudokuMatrix = new int[GRID_SIZE][GRID_SIZE][2];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Cell puzzleCell = fullSolvedBoard.getCell(row, col);
                Cell solutionCell = solutionCopy.getCell(row, col);

                sudokuMatrix[row][col][0] = puzzleCell.getCurrentValue();
                sudokuMatrix[row][col][1] = solutionCell.getCurrentValue();
            }
        }
        return sudokuMatrix;
    }

    /**
     * Generates a fully solved Sudoku board.
     *
     * @return A {@code SudokuBoard} object representing a fully solved board.
     */
    public static SudokuBoard generateFullSolvedBoard() {
        SudokuBoard fullSolvedBoard = new SudokuBoard(new int[GRID_SIZE][GRID_SIZE][2], SudokuLevel.EASY);
        fullSolvedBoard.fillAllCells();
        return fullSolvedBoard;
    }

    /**
     * Creates a copy of the given Sudoku board.
     *
     * @param original The original {@code SudokuBoard} to copy.
     * @return A new SudokuBoard with the same values as the original.
     */
    private static SudokuBoard copyBoard(SudokuBoard original) {
        SudokuBoard copy = new SudokuBoard(new int[GRID_SIZE][GRID_SIZE][2], original.getDifficulty());
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = original.getCell(row, col).getCurrentValue();
                copy.setCell(row, col, value);
            }
        }
        return copy;
    }

    /**
     * Sets the immutability of all non-empty cells on the board.
     * This is used to ensure that initial values cannot be modified by the player.
     *
     * @param board The {@code SudokuBoard} whose cells should be set as immutable.
     */
    private static void setImmutableCells(SudokuBoard board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Cell currentCell = board.getCell(row, col);
                if (currentCell.getCurrentValue() != 0) {
                    currentCell.setIsImmutable(true);
                }
            }
        }
    }
}
