package ConnectFour.GUI;

import ConnectFour.AI.AI;
import ConnectFour.Logic.*;

import ConnectFour.Logic.Point;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/*
 * Painter handles creating the UI and interactions with the UI
 *
 * It should probably be broken into a classes for menu options vs. the game UI
 */
public class Painter {
    private static final int TILE_SIZE = 80;
    private static final int DISTANCE_BETWEEN_CIRCLES = 5;

    private static final Color playerColor = Color.RED;
    private static final Color aiColor = Color.YELLOW;

    public static final int WIDTH = (Board.COLUMNS+1) * TILE_SIZE;
    public static final int HEIGHT = (Board.ROWS+1) * TILE_SIZE;

    private static boolean isGamePaused = false;

     static Shape makeGrid() {
        Shape gridShape = new Rectangle(WIDTH, HEIGHT);

        for (int y = 0; y < Board.ROWS; y++) {
            for (int x = 0; x < Board.COLUMNS; x++) {
                Circle circle = new Circle((double) TILE_SIZE / 2);

                circle.setCenterX((double) TILE_SIZE / 2);
                circle.setCenterY((double) TILE_SIZE / 2);

                circle.setTranslateX((double) (x * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));
                circle.setTranslateY((double) (y * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));

                gridShape = Shape.subtract(gridShape, circle);
            }
        }

        Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        gridShape.setFill(Color.BLUE);
        gridShape.setEffect(lighting);

        return gridShape;
    }

    private static class DiscShape extends Circle {
         DiscShape() {
            super((double) TILE_SIZE / 2, GlobalBoard.getTurn() ? playerColor : aiColor);

            setCenterX((double) TILE_SIZE / 2);
            setCenterY((double) TILE_SIZE / 2);
        }
    }

    public static void dropDisc(DiscShape discShape, int column) {
        Point point = GlobalBoard.placeDisc(new Disc(GlobalBoard.getTurn()), column);

        if (point.getY() >= 0) {
            Main.addDisc(discShape);
            discShape.setTranslateX((double) (column * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));

            TranslateTransition dropAnimation = new TranslateTransition(Duration.seconds(0.5), discShape);
            dropAnimation.setToY((double) (point.getY() * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));
            dropAnimation.setOnFinished(e -> {
                if (!GlobalBoard.getTurn() && !GlobalBoard.isGameOver()) {
                    int aiMove = AI.minimaxDecision().getX();
                    dropDisc(new DiscShape(), aiMove);
                }
                /* TODO: Implement end game
                if (gameOver()) {
                    endGame();
                }
                */
            });

            dropAnimation.play();
        }
    }

    static List<Rectangle> makeDropColumns() {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < Board.COLUMNS; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, HEIGHT);
            rect.setTranslateX((double) (x * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> {
                if (!GlobalBoard.isGameOver()) {
                    rect.setFill(Color.rgb(200, 200, 50, 0.3));
                }
            });
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;
            rect.setOnMouseClicked(e -> {
                if (GlobalBoard.getTurn() && !GlobalBoard.isGameOver()){
                    dropDisc(new DiscShape(), column);
                }
            });

            list.add(rect);
        }

        return list;
    }

    private static class MenuButton extends StackPane {
         private Text text;

         public MenuButton(String name) {
             text = new Text(name);
             text.setFont(text.getFont().font(20));
             text.setFill(Color.WHITE);
             text.setTranslateX(10);

             Rectangle bg = new Rectangle(400, 30);
             bg.setOpacity(0.6);
             bg.setFill(Color.BLACK);
             bg.setEffect(new GaussianBlur(3.5));

             setAlignment(Pos.CENTER_LEFT);
             getChildren().addAll(bg, text);

             setOnMouseEntered(e -> {
                 bg.setFill(Color.WHITE);
                 text.setFill(Color.BLACK);
             });

             setOnMouseExited(e -> {
                 bg.setFill(Color.BLACK);
                 text.setFill(Color.WHITE);
             });

             DropShadow shadow = new DropShadow(50, Color.WHITE);
             shadow.setInput(new Glow());

             setOnMousePressed(e -> setEffect(shadow));
             setOnMouseReleased(e-> setEffect(null));
         }
    }

    private static class GameMenu extends Parent {
        private Text text;

        public GameMenu() {
            setVisible(false);

            VBox menu = new VBox(10);

            menu.setTranslateX(100);
            menu.setTranslateY(200);

            MenuButton classicModeBtn = new MenuButton("Classic: Player vs. Computer");
            MenuButton remixModeBtn = new MenuButton("Remix: Player vs. Computer");
            MenuButton classicAIModeBtn = new MenuButton("Classic: Computer vs. Computer");
            MenuButton remixAIModeBtn = new MenuButton("Remix: Computer vs. Computer");

            classicModeBtn.setOnMouseClicked(e -> {
                GlobalBoard.setGameMode(GameMode.CLASSIC_MODE);
            });

            remixModeBtn.setOnMouseClicked(e -> {
                GlobalBoard.setGameMode(GameMode.REMIX_MODE);
            });

            classicAIModeBtn.setOnMouseClicked(e -> {
                GlobalBoard.setGameMode(GameMode.AI_CLASSIC_MODE);
            });

            remixAIModeBtn.setOnMouseClicked(e -> {
                GlobalBoard.setGameMode(GameMode.AI_REMIX_MODE);
            });

            menu.getChildren().addAll(classicModeBtn, remixModeBtn, classicAIModeBtn, remixAIModeBtn);

            Rectangle bg = new Rectangle(WIDTH, HEIGHT);
            bg.setFill(Color.BLACK);
            bg.setOpacity(0.7);

            getChildren().addAll(bg, menu);
        }
    }

    static Parent makePauseMenu() {
        return new GameMenu();
    }
}
