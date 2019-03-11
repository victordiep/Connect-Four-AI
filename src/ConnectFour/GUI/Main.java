package ConnectFour.GUI;

import ConnectFour.Logic.Board;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private static Pane discRoot = new Pane();

    static void addDisc(Circle discShape) {
        discRoot.getChildren().add(discShape);
    }

    private Parent createContent() {
        Pane root = new Pane();

        root.getChildren().add(discRoot);
        root.getChildren().addAll(Painter.makeDropColumns());
        root.getChildren().add(Painter.makeGrid());
        root.getChildren().add(Painter.makePauseMenu());

        return root;
    }

    private void setupPrimaryStage(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        scene.setFill(Color.DIMGREY);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setupPrimaryStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
