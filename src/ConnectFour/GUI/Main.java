package ConnectFour.GUI;

import ConnectFour.Logic.Board;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
    private static Board board;

    private Parent createContent() {
        Pane root = new Pane();

        root.getChildren().addAll(Painter.makeDropColumns(Board.ROWS, Board.COLUMNS));
        root.getChildren().add(Painter.makeGrid(Board.ROWS, Board.COLUMNS));

        return root;
    }

    private void setupPrimaryStage(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        scene.setFill(Color.DIMGREY);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    private void setupBoard() {
        board = new Board();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setupPrimaryStage(primaryStage);
        setupBoard();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
