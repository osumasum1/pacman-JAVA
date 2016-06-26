package pacman.client;

import com.google.gwt.canvas.dom.client.Context2d;
import pacman.shared.Canvas;

public class GwtCanvas implements Canvas {

    private com.google.gwt.canvas.client.Canvas canvas;
    private Context2d c2d;

    public GwtCanvas (com.google.gwt.canvas.client.Canvas canvas) {
        this.canvas = canvas;
        this.c2d = canvas.getContext2d();
    }

    @Override public double getWidth() {
        return canvas.getCoordinateSpaceWidth();
    }

    @Override public double getHeight() {
        return canvas.getCoordinateSpaceHeight();
    }

    @Override public void beginPath() {
        c2d.beginPath();
    }

    @Override public void closePath() {
        c2d.closePath();
    }

    @Override public void setFill(String color) {
        c2d.setFillStyle(color);
    }

    @Override public void fillRect(double x, double y, double w, double h) {
        c2d.fillRect(x, y, w, h);
    }

    @Override public void fillPolygon(double[] xPoints, double[] yPoints, int nPoints) {
        for (int i = 0 ; i < nPoints ; i++) {
            c2d.moveTo(xPoints[i], yPoints[i]);

            if (i+1 < nPoints) {
                c2d.lineTo(xPoints[i+1], yPoints[i+1]);
            } else {
                c2d.lineTo(xPoints[0], yPoints[0]);
            }
        }

        c2d.fill();
    }

    @Override public void fillArc(double x, double y, double w, double h, double startAngle, double arcExtent) {
        //Radius is width/2 = (cw + 2)/2
        c2d.arc(x, y, w/2, startAngle, arcExtent);
    }

    @Override public void fillOval(double x, double y, double w, double h) {
        c2d.arc(x, y, w/2, 0, Math.PI * 2);
    }
}
