package javafx.fxtest.model.model;

import java.io.Serializable;

public class Cell implements Serializable {

    private int currentCellValue;
    private boolean isImmutable;
    private final int correctValue;

    /**
     * Constructs a new Cell with the given values.
     *
     * @param currentCellValue The current value of the cell (0 for empty).
     * @param isImmutable Whether the cell is immutable.
     * @param correctValue The correct solution value for the cell.
     */
    public Cell(int currentCellValue, boolean isImmutable, int correctValue) {
        this.currentCellValue = currentCellValue;
        this.isImmutable = isImmutable;
        this.correctValue = correctValue;
    }
    /**
     * Gets the correct solution value of the cell.
     *
     * @return The correct value for the cell.
     */
    public int getCorrectValue() {
        return this.correctValue;
    }

    /**
     * Gets the current value of the cell.
     *
     * @return The current value of the cell, or 0 if it is empty.
     */
    public int getCurrentValue() {
        return currentCellValue;
    }

    /**
     * Sets the current value of the cell.
     *
     * @param currentValue The new value for the cell.
     * @throws IllegalArgumentException if the cell is immutable and cannot be changed.
     */
    public void setCurrentValue(int currentValue) throws IllegalArgumentException {
        if(!isImmutable()) {
            currentCellValue = currentValue;
        } else {
            throw new IllegalArgumentException("Cell is immutable, ett finns tal redan");
        }
    }

    /**
     * Sets the immutability status of the cell.
     *
     * @param isImmutable {@code true} if the cell should be immutable, {@code false} otherwise.
     */
    public void setIsImmutable(boolean isImmutable) {
        this.isImmutable = isImmutable;
    }

    /**
     * Checks if the cell is immutable.
     *
     * @return {@code true} if the cell is immutable, {@code false} otherwise.
     */
    public boolean isImmutable() {
        return isImmutable;
    }

    /**
     * Checks if the cell is empty (i.e., if its current value is 0).
     *
     * @return {@code true} if the cell is empty, {@code false} otherwise.
     */
    public boolean isCellEmpty() {
        return currentCellValue == 0;
    }

    /**
     * Sets the cell to be empty by setting its value to 0.
     *
     * @throws IllegalArgumentException if the cell is immutable and cannot be cleared.
     */
    public void setCellToEmpty() throws IllegalArgumentException {
        if(!isImmutable()) {
            currentCellValue = 0;
        } else {
            throw new IllegalArgumentException("Cell is immutable, can't clear");
        }
    }

    /**
     * Returns a string representation of the cell, including its current value and immutability status.
     *
     * @return A string representing the cell.
     */
    @Override
    public String toString() {
        return "Cell{" +
                ", currentValue=" + currentCellValue +
                ", isImmutable=" + isImmutable +
                '}';
    }
}
