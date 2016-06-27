package pacman.client;

import static java.lang.Math.toRadians;

import com.google.gwt.canvas.dom.client.Context2d;
import pacman.shared.Canvas;

public class GwtCanvas implements Canvas {

    private com.google.gwt.canvas.client.Canvas canvas;
    private Context2d c2d;

    public GwtCanvas(com.google.gwt.canvas.client.Canvas canvas) {
        this.canvas = canvas;
        this.c2d = canvas.getContext2d();
    }

    @Override public double getWidth() {
        return canvas.getCoordinateSpaceWidth();
    }

    @Override public double getHeight() {
        return canvas.getCoordinateSpaceHeight();
    }

    @Override public void setFill(String color) {
        c2d.setFillStyle(color);
    }

    @Override public void fillRect(double x, double y, double w, double h) {
        c2d.fillRect(x, y, w, h);
    }

    @Override public void fillPolygon(double[] xPoints, double[] yPoints, int nPoints) {
        c2d.beginPath();
        c2d.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i <= nPoints; i++) {
            c2d.lineTo(xPoints[i], yPoints[i]);
        }
        c2d.closePath();
        c2d.fill();
    }

    @Override public void fillOval(double x, double y, double w, double h) {
        c2d.beginPath();
        c2d.arc(x + w / 2., y + h / 2., w / 2., 0, Math.PI * 2);
        c2d.closePath();
        c2d.fill();
    }

    @Override public void fillArc(double x, double y, double w, double h, double startAngle, double arcExtent) {
        c2d.beginPath();
        c2d.arc(x + w / 2., y + h / 2., w / 2., -toRadians(startAngle), -toRadians(startAngle + arcExtent), true);
        c2d.lineTo(x + w / 2., y + h / 2.);
        c2d.closePath();
        c2d.fill();
    }
}
