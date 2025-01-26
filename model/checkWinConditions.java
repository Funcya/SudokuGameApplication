
package javafx.fxtest.model.model;

public class checkWinConditions {

    private checkWinConditions() {}

    /**
     * Checks if all rows in the Sudoku board have unique numbers (1-9).
     *
     * @param currentBoard The 2D array of {@code Cell} objects representing the Sudoku board.
     * @return true if each row contains unique numbers, otherwise false.
     */
    public static boolean isRowUnique(Cell[][] currentBoard) {
        for (int row = 0; row < 9; row++) {
            boolean[] seen = new boolean[9];

            for (int col = 0; col < 9; col++) {
                int currentValue = currentBoard[row][col].getCurrentValue();

                if(currentValue == 0) {
                    continue;
                }


                if (seen[currentValue - 1] == true) {
                    return false;
                }
                seen[currentValue - 1] = true;
            }
        }
        return true;
    }

    /**
     * Checks if all columns in the Sudoku board have unique numbers (1-9).
     *
     * @param currentBoard The 2D array of {@code Cell} objects representing the Sudoku board.
     * @return true if each column contains unique numbers, otherwise false.
     */
    public static boolean isColumnUnique(Cell[][] currentBoard) {
        for (int col = 0; col < 9; col++) {
            boolean[] seen = new boolean[9];

            for (int row = 0; row < 9; row++) {
                int currentValue = currentBoard[row][col].getCurrentValue();

                if(currentValue == 0) {
                    continue;
                }

                if (seen[currentValue - 1] == true) {
                    return false;
                }
                seen[currentValue - 1] = true;
            }
        }
        return true;
    }

    /**
     * Checks if all 3x3 boxes in the Sudoku board have unique numbers (1-9).
     *
     * @param currentBoard The 2D array of {@code Cell} objects representing the Sudoku board.
     * @return true if each 3x3 box contains unique numbers, otherwise false.
     */
    public static boolean isBoxUnique(Cell[][] currentBoard) {
        // Loopar igenom varje 3x3 box (hela spelplanen)
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                boolean[] seen = new boolean[9];

                for (int subBoxRow = 0; subBoxRow < 3; subBoxRow++) {
                    for (int subBoxCol = 0; subBoxCol < 3; subBoxCol++) {
                        int row = boxRow * 3 + subBoxRow;
                        int col = boxCol * 3 + subBoxCol;

                        int currentValue = currentBoard[row][col].getCurrentValue();

                        if (currentValue != 0) {
                            if (seen[currentValue - 1]) {
                                return false;
                            }
                            seen[currentValue - 1] = true;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if a given number can be placed in a specific cell without violating Sudoku rules.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param number The number to be checked for uniqueness.
     * @param currentBoard The 2D array of Cell objects representing the Sudoku board.
     * @return true if the number is unique in the given row, column, and 3x3 box, otherwise false.
     */
    public static boolean isNumberUnique(int row, int col, int number, Cell[][] currentBoard) {
        for (int i = 0; i < 9; i++) {
            if (currentBoard[row][i].getCurrentValue() == number) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (currentBoard[i][col].getCurrentValue() == number) {
                return false;
            }
        }
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if (currentBoard[r][c].getCurrentValue() == number) {
                    return false;
                }
            }
        }
        return true;
    }
}