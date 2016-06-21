package pacman.shared;

public class Character {
    public final Maze maze;
    public final int movesPerCell;
    public final int x0, y0;

    public int x, y;
    public int xOffset, yOffset;
    public int currentDirection = Maze.RIGHT;
    public int nextDirection = Maze.RIGHT;

    Character(Maze maze, int movesPerCell, int x0, int y0) {
        this.maze = maze;
        this.movesPerCell = movesPerCell;
        this.x0 = x0;
        this.y0 = y0;
        restart();
    }

    public void restart() {
        x = x0;
        y = y0;
        xOffset = yOffset = 0;
        currentDirection = nextDirection = Maze.RIGHT;
    }

    public int move() {
        int sign = 0;
        boolean vertical = false;

        if (xOffset == 0 && yOffset == 0 && currentDirection != nextDirection)
            if (maze.move(x, y, nextDirection))
                currentDirection = nextDirection;

        switch (currentDirection) {
            case Maze.LEFT: sign = -1; break;
            case Maze.RIGHT: sign = 1; break;
            case Maze.UP: sign = -1; vertical = true; break;
            case Maze.DOWN: sign = 1; vertical = true; break;
        }

        if (vertical) {
            //noinspection Duplicates
            if (yOffset + sign == movesPerCell) {
                y++;
                yOffset = 0;
            } else if (yOffset + sign == -movesPerCell) {
                y--;
                yOffset = 0;
            } else if ((yOffset + sign != 1 && yOffset + sign != -1) || maze.move(x, y, currentDirection)) {
                yOffset += sign;
            }
        } else {
            //noinspection Duplicates
            if (xOffset + sign == movesPerCell) {
                x++;
                xOffset = 0;
            } else if (xOffset + sign == -movesPerCell) {
                x--;
                xOffset = 0;
            } else if ((xOffset + sign != 1 && xOffset + sign != -1) || maze.move(x, y, currentDirection)) {
                xOffset += sign;
            }
        }

        if (x == 0 && xOffset == 0) x = maze.maze[0].length - 1;
        else if (x == maze.maze[0].length - 1 && xOffset == 0) x = 0;

        return 0;
    }
}
