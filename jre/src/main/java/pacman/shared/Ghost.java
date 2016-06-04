package pacman.shared;

public class Ghost extends Character {
    static final int PAC_MAN_COLLISION = 1;
    public PacMan target;

    public Ghost(Grid grid, int x, int y, int movesPerCell) {
        super(grid, x, y, movesPerCell);
    }

    /**
     * Control del movimiento del fantasma. Cada vez que el fantasma esta en el centro de una celda (offsets=0) se
     * calculan las posibles direcciones. Con una probabilidad de 0.25 el fantasma eligirá el camino de forma
     * aleatoria. Si no, se calcularán las distancias x e y entre el fantasma y el comecocos dando prioridad a las
     * menores en cada eje y con un 0.5 de probabilidad se establecerá prioridad para el eje vertical. Se establecerá
     * la direccion prioritaria dentro de las posibles. El fantasma nunca cambia de sentido.
     */
    public int move(boolean godMode) {
        int dx = Math.abs((x - target.x) * movesPerCell + xOffset - target.xOffset);
        int dy = Math.abs((y - target.y) * movesPerCell + yOffset - target.yOffset);

        if (dx < movesPerCell && dy < movesPerCell)
            return PAC_MAN_COLLISION;

        if (xOffset == 0 && yOffset == 0) {
            int possibleDirections = 0;
            if (grid.move(x, y, Grid.DOWN) && currentDirection != Grid.UP) possibleDirections |= Grid.DOWN;
            if (grid.move(x, y, Grid.UP) && currentDirection != Grid.DOWN) possibleDirections |= Grid.UP;
            if (grid.move(x, y, Grid.RIGHT) && currentDirection != Grid.LEFT) possibleDirections |= Grid.RIGHT;
            if (grid.move(x, y, Grid.LEFT) && currentDirection != Grid.RIGHT) possibleDirections |= Grid.LEFT;

            int direction = 0;

            if (Math.random() > 0.75) {
                while (direction == 0) {
                    double d = Math.random();
                    if (d < 0.25) direction = Grid.DOWN & possibleDirections;
                    else if (d < 0.5) direction = Grid.UP & possibleDirections;
                    else if (d < 0.75) direction = Grid.RIGHT & possibleDirections;
                    else direction = Grid.LEFT & possibleDirections;
                }
            } else {
                int[] priority = { Grid.DOWN, Grid.RIGHT, Grid.UP, Grid.LEFT };

                if (x > target.x) {
                    priority[1] = Grid.LEFT;
                    priority[3] = Grid.RIGHT;
                }
                if (y > target.y) {
                    priority[0] = Grid.UP;
                    priority[2] = Grid.DOWN;
                }

                int aux;
                double verticalPriority = 0.5;

                if (priority[0] != currentDirection) verticalPriority = 0.75;
                else if (priority[1] == currentDirection) verticalPriority = 0.25;

                if (Math.random() < verticalPriority) {
                    aux = priority[0];
                    priority[0] = priority[1];
                    priority[1] = aux;
                    aux = priority[2];
                    priority[2] = priority[3];
                    priority[3] = aux;
                }

                int i = 0;
                if (godMode) i = 3;

                while (direction == 0) {
                    direction = priority[i] & possibleDirections;
                    if (godMode) i--;
                    else i++;
                }
            }

            this.nextDirection = direction;
        }

        return super.move();
    }
}
