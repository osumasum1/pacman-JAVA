package pacman.jre;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import pacman.shared.Screen;

/**
 * Created by alejandrocq on 21/06/16.
 */
public class CanvasJfx implements Screen {

    private final double CANVAS_WIDTH, CANVAS_HEIGHT;

    private GraphicsContext ctx;

    public CanvasJfx(Canvas canvas, double w, double h) {
        this.CANVAS_WIDTH = w;
        this.CANVAS_HEIGHT = h;
        ctx = canvas.getGraphicsContext2D();
    }

    @Override
    public double getWidth() {
        return CANVAS_WIDTH;
    }

    @Override
    public double getHeight() {
        return CANVAS_HEIGHT;
    }

    @Override
    public void beginPath() {
        ctx.beginPath();
    }

    @Override
    public void closePath() {
        ctx.closePath();
    }

    @Override
    public void setFill(String color) {
        Color c = Color.web(color);
        ctx.setFill(c);
    }

    @Override
    public void fillRect(double x, double y, double w, double h) {
        ctx.fillRect(x, y, w, h);
    }

    @Override
    public void fillPolygon(double[] xPoints, double[] yPoints, int nPoints) {
        ctx.fillPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void fillArc(double x, double y, double w, double h, double startAngle, double arcExtent, String arcType) {
        ArcType arc = null;

        switch (arcType) {
            case "ROUND":
                arc = ArcType.ROUND;
                break;
            case "CHORD":
                arc = ArcType.CHORD;
                break;
            case "OPEN":
                arc = ArcType.OPEN;
                break;
            default:
                arc = ArcType.ROUND;
                break;
        }

        ctx.fillArc(x, y, w, h, startAngle, arcExtent, arc);
    }

    @Override
    public void fillOval(double x, double y, double w, double h) {
        ctx.fillOval(x, y, w, h);
    }
}
