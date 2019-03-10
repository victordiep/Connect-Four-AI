package ConnectFour.GUI;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Painter {
    private static final int TILE_SIZE = 80;
    private static final int DISTANCE_BETWEEN_CIRCLES = 5;

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

    static List<Rectangle> makeDropColumns(final int rows, final int columns) {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < columns; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (rows+1) * TILE_SIZE);
            rect.setTranslateX((double) (x * (TILE_SIZE + DISTANCE_BETWEEN_CIRCLES) + TILE_SIZE / 3));
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            list.add(rect);
        }

        return list;
    }
}
