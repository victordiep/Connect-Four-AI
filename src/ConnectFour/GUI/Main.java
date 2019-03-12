package ConnectFour.GUI;

import ConnectFour.Logic.Board;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private static Pane discRoot = new Pane();

    static void addDisc(Circle discShape) {
        discRoot.getChildren().add(discShape);
    }
    static void clearDiscs() {
        discRoot.getChildren().clear();
    }
    static Parent gameMenu;

    private Parent createContent() {
        Pane root = new Pane();

        root.getChildren().add(discRoot);
        root.getChildren().addAll(Painter.makeDropColumns());
        root.getChildren().add(Painter.makeGrid());
        gameMenu = Painter.makePauseMenu();
        root.getChildren().add(gameMenu);

        return root;
    }

    private void setupPrimaryStage(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        scene.setFill(Color.DIMGREY);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                if (!gameMenu.isVisible()) {
                    gameMenu.setVisible(true);
                }
                else {
                    gameMenu.setVisible(false);
                }
            }
        });

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
