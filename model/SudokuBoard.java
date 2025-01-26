package javafx.fxtest.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static javafx.fxtest.model.model.SudokuUtilities.GRID_SIZE;

public class SudokuBoard implements Serializable {

    private Cell[][] sudokuBoard;
    private SudokuUtilities.SudokuLevel theDifficulty;

    /**
     * Constructs a SudokuBoard with a given board configuration and difficulty.
     * Initializes each cell with its value, immutability status, and correct value.
     *
     * @param theBoard The 3D array representing the initial board and correct values.
     * @param difficulty The difficulty level of the Sudoku game.
     */
    public SudokuBoard(int[][][] theBoard, SudokuUtilities.SudokuLevel difficulty) {

        sudokuBoard = new Cell[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int col = 0; col < GRID_SIZE; col++)
            {
                int initialValue = theBoard[row][col][0];
                int correctValue = theBoard[row][col][1];
                boolean isImmutable = initialValue != 0;
                sudokuBoard[row][col] = new Cell(initialValue, isImmutable, correctValue);
            }
        }
        theDifficulty = difficulty;
    }

    /**
     * Sets the value of a specific cell on the board.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param cellValue The value to set in the cell.
     */
    public void setCell(int row, int col, int cellValue) {
        sudokuBoard[row][col].setCurrentValue(cellValue);
    }


    /**
     * Retrieves the {@code Cell} object at a specific position on the board.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The {@code Cell} at the specified position.
     */
    public Cell getCell(int row, int col) {
        return sudokuBoard[row][col];
    }


    /**
     * Checks if the Sudoku board is fully filled.
     *
     * @return true if all cells have values, otherwise false.
     */
    public boolean isBoardFull() {
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                if(sudokuBoard[row][col].isCellEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the game is over. The game is considered over if the board is full
     * and all rows, columns, and boxes contain unique values.
     *
     * @return true if the game is over, otherwise false.
     */
    public boolean isGameOver() {
        if(isBoardFull() == true) {
            if (checkWinConditions.isRowUnique(sudokuBoard) && checkWinConditions.isColumnUnique(sudokuBoard) && checkWinConditions.isBoxUnique(sudokuBoard))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Fills a random empty cell with a random valid value that maintains board uniqueness.
     */
    /**
     * Fills a random empty cell with its correct value.
     */
    public void fillRandomCell() {
        Random rand = new Random();
        List<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (sudokuBoard[row][col].isCellEmpty() && !sudokuBoard[row][col].isImmutable()) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            System.out.println("No empty cells available to fill.");
            return;
        }

        int[] randomCell = emptyCells.get(rand.nextInt(emptyCells.size()));
        int row = randomCell[0];
        int col = randomCell[1];
        int correctValue = sudokuBoard[row][col].getCorrectValue();

        sudokuBoard[row][col].setCurrentValue(correctValue);
    }


    /**
     * Fills the entire board with numbers while maintaining Sudoku rules.
     * Uses backtracking to ensure a valid board state.
     *
     * @param row The starting row.
     * @param col The starting column.
     * @return {@code true} if the board is successfully filled, otherwise {@code false}.
     */
    private boolean fillBoard(int row, int col) {
        if (row == 9) {
            return true;
        }

        if (col == 9) {
            return fillBoard(row + 1, 0);
        }

        if (!sudokuBoard[row][col].isCellEmpty()) {
            return fillBoard(row, col + 1);
        }

        Random rand = new Random();
        for (int num = 1; num <= 9; num++) {
            int randomNumber = rand.nextInt(9) + 1;

            if (checkWinConditions.isNumberUnique(row, col, randomNumber, sudokuBoard)) {
                sudokuBoard[row][col].setCurrentValue(randomNumber);
                if (fillBoard(row, col + 1)) {
                    return true;
                }
                sudokuBoard[row][col].setCurrentValue(0);
            }
        }
        return false;
    }

    /**
     * Fills all cells with the board using the fillBoard.
     */
    public void fillAllCells()
    {
        fillBoard(0, 0);
    }

    /**
     * Removes a number of cells from the board based on the selected difficulty level.
     *
     * @param board The Sudoku board.
     * @param level The difficulty level.
     */
    public void removeCells(SudokuBoard board, SudokuUtilities.SudokuLevel level) {
        int nrOfCellsToRemove = getNrOfCellsToRemove(level);
        Random randomCellPosition = new Random();

        int attempts = 0;
        int maxAttempts = 1000;

        while (nrOfCellsToRemove > 0 && attempts < maxAttempts) {
            int row = randomCellPosition.nextInt(GRID_SIZE);
            int col = randomCellPosition.nextInt(GRID_SIZE);

            Cell currentCell = board.getCell(row, col);

            if (currentCell.getCurrentValue() != 0 && !currentCell.isImmutable()) {
                currentCell.setCellToEmpty();
                nrOfCellsToRemove--;
            }
            attempts++;
        }
    }

    /**
     * Determines the number of cells to remove based on the difficulty level.
     *
     * @param level The difficulty level.
     * @return The number of cells to remove.
     * @throws IllegalArgumentException if the difficulty level is invalid.
     */
    private static int getNrOfCellsToRemove(SudokuUtilities.SudokuLevel level) {

        switch (level) {
            case EASY: return 30;
            case MEDIUM: return 50;
            case HARD: return 70;
            default: throw new IllegalArgumentException("Error: Not valid Sudoku level");
        }
    }


    /**
     * Checks if a specific number is allowed to be placed in a given cell.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param number The number to check.
     * @return true if the number is allowed, otherwise false.
     */
    private boolean isNumberAllowed(int row, int col, int number) {
        if(number <= 0 || number >= 10) {
            return false;
        }

        if(row <= -1 || row >= 9){
            return false;
        }

        if(col <= -1 || col >= 9){
            return false;
        }

        if(!sudokuBoard[row][col].isCellEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Places a number in a cell if allowed, otherwise throws an exception.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param number The number to place.
     * @throws IllegalArgumentException if the placement is not allowed.
     */
    public void placeNumber(int row, int col, int number) throws IllegalArgumentException {
        if(isNumberAllowed(row, col, number)) {
            sudokuBoard[row][col].setCurrentValue(number);
            sudokuBoard[row][col].setIsImmutable(false);
        } else {
            throw new IllegalArgumentException("Placement not allowed! Can't place number " + number + " in cell (" + row + ", " + col + ")");
        }
    }

    /**
     * Checks if a cell can be cleared of its value.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return true if the cell can be cleared, otherwise false.
     */
    public boolean isClearCellAllowed(int row, int col) {
        if(row <= -1 || row >= 9){
            return false;
        }

        if(col <= -1 || col >= 9){
            return false;
        }

        if(sudokuBoard[row][col].isImmutable()) {
            return false;
        }
        return true;
    }


    /**
     * Clears the value of a cell if allowed, otherwise throws an exception.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @throws IllegalArgumentException if the cell cannot be cleared.
     */
    public void clearCell(int row, int col) throws IllegalArgumentException {
        if(isClearCellAllowed(row, col)) {
            sudokuBoard[row][col].setCurrentValue(0);
        } else {
            throw new IllegalArgumentException("Placement not allowed! Can't clear cell in (" + row + ", " + col + ")");
        }
    }

    /**
     * Checks if all numbers currently filled in the board are correct.
     *
     * @return {@code true} if the filled numbers meet Sudoku rules, otherwise {@code false}.
     */
    public boolean isFilledNumbersCorrect() {
        if(checkWinConditions.isRowUnique(sudokuBoard) == false) {
            return false;
        }

        if(checkWinConditions.isColumnUnique(sudokuBoard) == false) {
            return false;
        }

        if(checkWinConditions.isBoxUnique(sudokuBoard) == false) {
            return false;
        }
        return true;
    }

    /**
     * Gets the current value of a cell at a specified position.
     *
     * @param x The row index.
     * @param y The column index.
     * @return The current value of the cell.
     */
    public int getCurrentValue(int x, int y){
        return this.sudokuBoard[x][y].getCurrentValue();
    }

    /**
     * Retrieves the Cell object at a specific position in the game board.
     *
     * @param x The row index.
     * @param y The column index.
     * @return The {@code Cell} object.
     */
    public Cell getGameBoardByPos(int x, int y) {
        return sudokuBoard[x][y];
    }

    /**
     * Returns the difficulty of the game
     * @return difficulty
     */
    public SudokuUtilities.SudokuLevel getDifficulty() {
        return theDifficulty;
    }

    /**
     * Returns a string representation of the Sudoku board.
     *
     * @return A string representing the Sudoku board.
     */
    @Override
    public String toString() {
        return "SudokuBoard{" +
                "sudokuBoard=" + Arrays.toString(sudokuBoard) +
                '}';
    }


}