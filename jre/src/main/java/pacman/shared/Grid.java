package pacman.shared;

public class Grid {

    private static final String DEFAULT_GRID[] = {
            "1AAAAAAAAAAAA21AAAAAAAAAAAA2",
            "I............DI............D",
            "I.5BB6.5BBB6.DI.5BBB6.5BB6.D",
            "IoD  I.D   I.DI.D   I.D  IoD",
            "I.7AA8.7AAA8.78.7AAA8.7AA8.D",
            "I..........................D",
            "I.5BB6.56.5BBBBBB6.56.5BB6.D",
            "I.7AA8.DI.7AA21AA8.DI.7AA8.D",
            "I......DI....DI....DI......D",
            "3BBBB6.D3BB6 DI 5BB4I.5BBBB4",
            "     I.D1AA8 78 7AA2I.D     ",
            "     I.DI          DI.D     ",
            "     I.DI 5B____B6 DI.D     ",
            "AAAAA8.78 D      I 78.7AAAAA",
            "      .   D ---- I   .      ",
            "BBBBB6.56 D FFFF I 56.5BBBBB",
            "     I.DI 7AAAAAA8 DI.D     ",
            "     I.DI          DI.D     ",
            "     I.DI 5BBBBBB6 DI.D     ",
            "1AAAA8.78 7AA21AA8 78.7AAAA2",
            "I............DI............D",
            "I.5BB6.5BBB6.DI.5BBB6.5BB6.D",
            "I.7A2I.7AAA8.78.7AAA8.D1A8.D",
            "Io..DI................DI..oD",
            "3B6.DI.56.5BBBBBB6.56.DI.5B4",
            "1A8.78.DI.7AA21AA8.DI.78.7A2",
            "I......DI....DI....DI......D",
            "I.5BBBB43BB6.DI.5BB43BBBB6.D",
            "I.7AAAAAAAA8.78.7AAAAAAAA8.D",
            "I..........................D",
            "3BBBBBBBBBBBBBBBBBBBBBBBBBB4"
    };

    public static final int LEFT = 0x1, RIGHT = 0x10, UP = 0x100, DOWN = 0x1000;
    public static final int PELLET_POINTS = 10, POWER_PELLET_POINTS = 50;
    public static final char
            RECTANGULOARRIBA = 'A',
            RECTANGULOABAJO = 'B',
            RECTANGULODERECHA = 'D',
            RECTANGULOIZQUIERDA = 'I',
            ESQUINAABAJODERECHA = '5',
            ESQUINAARRIBADERECHA = '7',
            ESQUINAABAJOIZQUIERDA = '6',
            ESQUINAARRIBAIZQUIERDA = '8',
            ESQUINAARRIBADERECHAGRANDE = '3',
            ESQUINAABAJODERECHAGRANDE = '1',
            ESQUINAARRIBAIZQUIERDAGRANDE = '4',
            ESQUINAABAJOIZQUIERDAGRANDE = '2',
            PUNTOPEQUEÑO = '.',
            PUNTOGRANDE = 'o',
            MURO = '_',
            FANTASMA = 'F',
            LIBRE = ' ';

    public char[][] maze;
    public int maxScore;

    public Grid() {
        int rows = DEFAULT_GRID.length;
        maze = new char[rows][];
        for (int i = 0; i < DEFAULT_GRID.length; i++) {
            maze[i] = DEFAULT_GRID[i].toCharArray();
        }

        for (char[] y : maze) {
            for (char x : y) {
                if (x == PUNTOPEQUEÑO) maxScore += PELLET_POINTS;
                else if (x == PUNTOGRANDE) maxScore += POWER_PELLET_POINTS;
            }
        }
    }

    /** Comer el coco de la celda actual. Devuelve el numero de puntos si come coco, sino 0. */
    public int eat(int x, int y) {
        if (maze[y][x] == PUNTOPEQUEÑO) {
            maze[y][x] = LIBRE;
            return PELLET_POINTS;
        } else if (maze[y][x] == PUNTOGRANDE) {
            maze[y][x] = LIBRE;
            return POWER_PELLET_POINTS;
        } else {
            return 0;
        }
    }

    /** Indica si un personaje se puede mover en una dirección si se encuentra en una determinada celda. */
    public boolean move(int x, int y, int direction) {
        char next = '-';
        try {
            switch (direction) {
                case UP: next = maze[y - 1][x]; break;
                case DOWN: next = maze[y + 1][x]; break;
                case RIGHT: next = maze[y][x + 1]; break;
                case LEFT: next = maze[y][x - 1]; break;
            }
        } catch (ArrayIndexOutOfBoundsException ignore) {
            return false;
        }

        if (next == PUNTOPEQUEÑO || next == PUNTOGRANDE || next == LIBRE) return true;
        else if ((next == MURO && direction == UP) || next == FANTASMA && direction == RIGHT) return true;
        return false;
    }
}
