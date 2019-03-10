package ConnectFour.GUI;

import ConnectFour.Logic.Board;
import ConnectFour.Logic.Disc;
import ConnectFour.Logic.Point;
import ConnectFour.Logic.Turn;

import javafx.animation.TranslateTransition;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Painter {
    private static final int TILE_SIZE = 80;
    private static final int DISTANCE_BETWEEN_CIRCLES = 5;

    private static final Color playerColor = Color.RED;
    private static final Color aiColor = Color.YELLOW;

    static Shape makeGrid(final int rows, final int columns) {
        Shape gridShape = new Rectangle((columns+1) * TILE_SIZE, (rows+1) * TILE_SIZE);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
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
            super((double) TILE_SIZE / 2, Turn.isPlayerTurn() ? playerColor : aiColor);

            setCenterX((double) TILE_SIZE / 2);
            setCenterY((double) TILE_SIZE / 2);
        }
    }

    static void dropDisc(DiscShape discShape, final int column) {
        Point point = Board.placeDisc(new Disc(Turn.isPlayerTurn()), column);

        if (point.getY() >= 0) {
            Main.addDisc(discShape);
            discShape.setTranslateX((double) (column * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));

            TranslateTransition dropAnimation = new TranslateTransition(Duration.seconds(0.5), discShape);
            dropAnimation.setToY((double) (point.getY() * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));
            dropAnimation.setOnFinished(e -> {
                /* TODO: Implement end game
                if (gameOver()) {
                    endGame();
                }
                */
            });
            Turn.changeToAITurn();
            dropAnimation.play();
        }
    }

    static List<Rectangle> makeDropColumns(final int rows, final int columns) {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < columns; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (rows+1) * TILE_SIZE);
            rect.setTranslateX((double) (x * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;
            rect.setOnMouseClicked(e -> {
                if (Turn.isPlayerTurn()){
                    dropDisc(new DiscShape(), column);
                }
            });

            list.add(rect);
        }

        return list;
    }


}
