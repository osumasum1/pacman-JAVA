package pacman.jre;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by alejandrocq on 4/06/16.
 */
public class JRECanvasDrawer {

    private final int CANVAS_WIDTH = 600;
    private final int CANVAS_HEIGHT = 600;

    private Canvas canvas;
    private GraphicsContext gc;

    public JRECanvasDrawer() {
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
    }


}
