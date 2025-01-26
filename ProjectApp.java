package javafx.fxtest;

import javafx.application.Application;
import javafx.fxtest.model.model.SudokuBoard;
import javafx.fxtest.model.model.SudokuUtilities;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxtest.model.view.GridView;
import javafx.fxtest.model.view.Controller;


public class ProjectApp extends Application {


   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage stage) throws Exception {

      int[][][] generatedMatrix = SudokuUtilities.generateSudokuMatrix(SudokuUtilities.SudokuLevel.EASY);
      GridView view = new GridView();
      Controller controller = new Controller(new SudokuBoard(generatedMatrix, SudokuUtilities.SudokuLevel.EASY), view);
      MenuBar menuBar = view.getMenuBar();
      VBox root = new VBox(menuBar,view);
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.sizeToScene();
      stage.setResizable(false);
      stage.setTitle("Sudoku");
      stage.show();
   }
}