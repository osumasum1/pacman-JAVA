package pacman.shared;

public class Character {
    public int x, y;
    public int xOffset, yOffset;
    public final int movesPerCell;
    public int currentDirection = Grid.RIGHT;
    public int nextDirection = Grid.RIGHT;
    public Grid grid;
    public int x0, y0;

    Character(Grid grid, int x, int y, int movesPerCell) {
        this.grid = grid;
        this.x = x0 = x;
        this.y = y0 = y;
        this.movesPerCell = movesPerCell;
    }

    public void restart() {
        x = x0;
        y = y0;
        xOffset = yOffset = 0;
        currentDirection = nextDirection = Grid.RIGHT;
    }

    public int move() {
        int sign = 0;
        boolean vertical = false;

        if (xOffset == 0 && yOffset == 0 && currentDirection != nextDirection)
            if (grid.move(x, y, nextDirection))
                currentDirection = nextDirection;

        switch (currentDirection) {
            case Grid.LEFT: sign = -1; break;
            case Grid.RIGHT: sign = 1; break;
            case Grid.UP: sign = -1; vertical = true; break;
            case Grid.DOWN: sign = 1; vertical = true; break;
        }

        if (vertical) {
            //noinspection Duplicates
            if (yOffset + sign == movesPerCell) {
                y++;
                yOffset = 0;
            } else if (yOffset + sign == -movesPerCell) {
                y--;
                yOffset = 0;
            } else if ((yOffset + sign != 1 && yOffset + sign != -1) || grid.move(x, y, currentDirection)) {
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
            } else if ((xOffset + sign != 1 && xOffset + sign != -1) || grid.move(x, y, currentDirection)) {
                xOffset += sign;
            }
        }

        if (x == 0 && xOffset == 0) x = grid.maze[0].length - 1;
        else if (x == grid.maze[0].length - 1 && xOffset == 0) x = 0;

        return 0;
    }
}
