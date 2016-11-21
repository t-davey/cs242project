package application;

import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;


/**
 * @author Gus Naughton
 *
 */
public class Hexagon {

    private Polyline hexagon;

    public Hexagon() {
        this.hexagon = new Polyline();
    }

    /**
     * @param width integer representing the shape's width
     * @param height integer representing the shape's height
     * @param x integer representing the shape's x coordinate
     * @param y integer representing the shape's y coordinate
     */
    public Hexagon(int width, int height, int x, int y) {
        this.hexagon = new Polyline();

        this.hexagon.getPoints().addAll(new Double[]{
                (double)x, (double)y,
                (double)x + width/2, (double)y + height/3,
                (double)x + width/2, (double)y + 2*height/3,
                (double)x, (double)y + height,
                (double)x - width/2, (double)y + 2*height/3,
                (double)x - width/2, (double)y + height/3,
                (double)x, (double)y
        });
    }

    public Shape getHexagon() {
        return this.hexagon;
    }

}
