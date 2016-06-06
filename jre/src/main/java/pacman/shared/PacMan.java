package pacman.shared;

public class PacMan extends Character {

    public PacMan(Maze maze, int x, int y, int movsPorCelda) {
        super(maze, x, y, movsPorCelda);
    }

    @Override public int move() {
        super.move();
        return maze.eat(x, y);
    }
}
