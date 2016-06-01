package pacman.shared;

public class PacMan extends Character {

    public PacMan(Grid grid, int x, int y, int movsPorCelda) {
        super(grid, x, y, movsPorCelda);
    }

    @Override public int move() {
        super.move();
        return grid.eat(x, y);
    }
}
