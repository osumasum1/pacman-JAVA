package pacman.client;

import pacman.shared.Canvas;

public class GwtCanvas  implements Canvas{
    @Override public double getWidth() {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public double getHeight() {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void beginPath() {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void closePath() {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void setFill(String color) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void fillRect(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void fillPolygon(double[] xPoints, double[] yPoints, int nPoints) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void fillArc(double x, double y, double w, double h, double startAngle, double arcExtent) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void fillOval(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
