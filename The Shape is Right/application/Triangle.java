package application;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


/**
 * @author Gus Naughton
 *
 */
public class Triangle {

    private Polygon triangle;

    public Triangle() {
        this.triangle = new Polygon();
    }

    /**
     * @param width integer representing the shape's width
     * @param height integer representing the shape's height
     * @param x integer representing the shape's x coordinate
     * @param y integer representing the shape's y coordinate
     */
    public Triangle(int width, int height, int x, int y) {
        this.triangle = new Polygon();

        this.triangle.getPoints().addAll(new Double[]{
                (double)x, (double)y,
                (double)x - width, (double)y + height,
                (double)x + width, (double)y + height
        });
    }

    public Shape getTriangle() {
        return this.triangle;
    }

}
