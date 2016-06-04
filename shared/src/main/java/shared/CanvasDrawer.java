package shared;

public interface CanvasDrawer {

    //TODO add parameters for each method.
    void setColor();
    void fillRect(double x, double y, double w, double h);
    void fillPolygon();
    void fillArc();

}