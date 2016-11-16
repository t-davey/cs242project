package application;

import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

/**
 * Created by gusnaughton on 11/16/16.
 */
public class Hexagon {

    private Polyline hexagon;

    public Hexagon() {
        this.hexagon = new Polyline();
    }

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
