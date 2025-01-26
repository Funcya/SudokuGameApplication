package javafx.fxtest.model.view;

import javafx.fxtest.model.model.FileHandler;
import javafx.fxtest.model.model.SudokuUtilities;
import javafx.fxtest.model.model.SudokuBoard;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class Controller {

    private SudokuBoard board;
    private GridView view;


    public Controller(SudokuBoard board, GridView view) {
        this.board = board;
        this.view = view;
    }


    void mouseEvent(char button, int x, int y) {
        if (button == 'C') {
            board.clearCell(x, y);
            view.updateTile(x, y);
        } else {
            if (button == '0') {
                board.clearCell(x, y);
                view.updateTile(x, y);
            } else {
                int valueToPlace = Character.getNumericValue(button);

                if (valueToPlace >= 1 && valueToPlace <= 9) {
                    if (board.getCurrentValue(x, y) == 0) {
                        board.placeNumber(x, y, valueToPlace);
                        view.updateTile(x, y);
                    }
                }
            }
        }
    }


    void clearGame(){

        for(int row=0;row< SudokuUtilities.GRID_SIZE;row++){
            for (int col=0;col< SudokuUtilities.GRID_SIZE;col++)
                if (!board.getCell(row,col).isImmutable()) {
                    board.clearCell(row, col);
                }
        }
    } // la till isimmutablecheck



    void getHint() throws IllegalArgumentException{
        board.fillRandomCell();
    }


    void restartGame(SudokuBoard newBoard){
        this.board = newBoard;
    }


    void saveGame(){
        FileChooser fileChooser = new FileChooser();
        File file = null;

        fileChooser.setTitle("Save File (Sudoku)");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
        try {
            file = fileChooser.showSaveDialog(this.view.getScene().getWindow());
            FileHandler.serializeToFile(this.board,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    SudokuBoard loadGame(){
        File file = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File (Sudoku)");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
        try {
            file = fileChooser.showOpenDialog(this.view.getScene().getWindow());
            this.board = FileHandler.deserializeFromFile(file);
            return this.board;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}