package pacman.shared;

public class PacMan extends Character {

    public PacMan(Maze maze, int movesPerCell, int x0, int y0) {
        super(maze, movesPerCell, x0, y0);
    }

    @Override public int move() {
        super.move();
        return maze.eat(x, y);
    }
}
