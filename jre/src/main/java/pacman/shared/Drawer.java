package pacman.shared;

import pacman.shared.MainLoop.Mode;

import java.util.function.Consumer;

import static java.lang.Math.random;
import static java.lang.Math.sin;
import static pacman.shared.MainLoop.Mode.GOD;

public class Drawer {
    private final String[] GHOST_COLORS = {
            "#FF0000" /*red*/,
            "#1A00FF" /*blue*/,
            "#1A00FF" /*pink*/,
            "#00FFFF" /*cyan*/
    };
    private final String COLOR_BLACK = "#000000";
    private final String COLOR_WHITE = "#FFFFFF";
    private final String COLOR_BLUE = "#0000FF";
    private final String COLOR_YELLOW = "#FFFF00";

    private final Screen screen;
    private final Consumer<Integer> score;
    private final Consumer<String> alert;

    public Drawer(Screen screen, Consumer<Integer> score, Consumer<String> alert) {
        this.screen = screen;
        this.score = score;
        this.alert = alert;
    }

    /** Show alert message. */
    public void alert(String message) {
        alert.accept(message);
    }

    /** Draw current state. */
    public void draw(MainLoop state) {
        // TODO support resizable screen, so all this values might change in each draw
        double w = screen.getWidth(), h = screen.getHeight();
        int cellWidth = (int) Math.min(w / state.maze.maze[0].length, h / state.maze.maze.length);
        double xOffset = (w - state.maze.maze[0].length * cellWidth) / 2;

        // clear
        screen.beginPath();
        screen.setFill(COLOR_BLACK);
        screen.fillRect(0, 0, w, h);
        screen.closePath();

        // draw
        drawMaze(state.maze, cellWidth, xOffset);
        drawPacMan(state.pacMan, cellWidth, xOffset);
        drawGhosts(state.ghosts, cellWidth, (int) (xOffset), state.mode);
        score.accept(state.score);
    }

    private void drawMaze(Maze maze, int cw, double xOffset) {
        int x, y, o = (int) xOffset;
        screen.beginPath();

        for (x = 0; x < maze.maze[0].length; x++) {
            for (y = 0; y < maze.maze.length; y++) {
                switch (maze.maze[y][x]) {
                    case Maze.RECTANGLE_UP:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw, y * cw, cw, cw / 2);
                        break;
                    case Maze.RECTANGLE_DOWN:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw, y * cw + cw / 2, cw, cw / 2);
                        break;
                    case Maze.RECTANGLE_RIGHT:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw + cw / 2, y * cw, cw / 2, cw);
                        break;
                    case Maze.RECTANGLE_LEFT:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw, y * cw, cw / 2, cw);
                        break;
                    case Maze.BOTTOM_RIGHT_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillPolygon(
                                new double[] { o + (x + 1) * cw, o + (x * cw + cw / 2), o + (x + 1) * cw },
                                new double[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Maze.BOTTOM_LEFT_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Maze.UPPER_LEFT_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Maze.UPPER_RIGHT_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillPolygon(
                                new double[] { o + (x * cw + cw / 2), o + (x + 1) * cw, o + (x + 1) * cw },
                                new double[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Maze.BOTTOM_LEFT_BIG_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw, y * cw, cw, cw);
                        screen.setFill(COLOR_BLACK);
                        screen.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Maze.BOTTOM_RIGHT_BIG_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw, y * cw, cw, cw);
                        screen.setFill(COLOR_BLACK);
                        screen.fillPolygon(
                                new double[] { o + (x + 1) * cw, o + (x * cw + cw / 2), o + (x + 1) * cw },
                                new double[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Maze.UPPER_RIGHT_BIG_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw, y * cw, cw, cw);
                        screen.setFill(COLOR_BLACK);
                        screen.fillPolygon(
                                new double[] { o + (x * cw + cw / 2), o + (x + 1) * cw, o + (x + 1) * cw },
                                new double[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Maze.UPPER_LEFT_BIG_CORNER:
                        screen.setFill(COLOR_BLUE);
                        screen.fillRect(o + x * cw, y * cw, cw, cw);
                        screen.setFill(COLOR_BLACK);
                        screen.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Maze.SMALL_DOT:
                        screen.setFill(COLOR_YELLOW);
                        screen.fillOval(o + x * cw + cw / 2, y * cw + cw / 2, cw / 5, cw / 5);
                        break;
                    case Maze.BIG_DOT:
                        screen.setFill(COLOR_YELLOW);
                        screen.fillOval(o + x * cw + cw / 2, y * cw + cw / 2, cw / 3, cw / 3);
                        break;
                }
            }
        }
        screen.closePath();
    }

    private void drawPacMan(PacMan pacMan, int cw, double xOffset) {
        double xOffsetMov = pacMan.xOffset * cw / pacMan.movesPerCell;
        double yOffsetMov = pacMan.yOffset * cw / pacMan.movesPerCell;
        screen.setFill(COLOR_YELLOW);

        switch (pacMan.currentDirection) {
            case Maze.RIGHT:
                screen.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (22 - 22 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)), "ROUND");
                break;
            case Maze.LEFT:
                screen.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (202 - 22 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)), "ROUND");
                break;
            case Maze.UP:
                screen.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (112 - 22 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)), "ROUND");
                break;
            case Maze.DOWN:
                screen.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (292 - 22 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)), "ROUND");
                break;
        }
    }

    private String getRandomColor() {
        int r = (int) (random()*255);
        int g = (int) (random()*255);
        int b = (int) (random()*255);

        return "rgb("+r+","+g+","+b+")";
    }

    private void drawGhosts(Ghost[] ghosts, int cw, int xOffset, Mode mode) {
        for (int i = 0; i < ghosts.length; i++) {
            int xOffsetMov = ghosts[i].xOffset * cw / ghosts[i].movesPerCell;
            int yOffsetMov = ghosts[i].yOffset * cw / ghosts[i].movesPerCell;
            screen.setFill(mode == GOD ? getRandomColor() : GHOST_COLORS[i]);

            //Head
            screen.fillArc(
                    xOffset + xOffsetMov + ghosts[i].x * cw,
                    yOffsetMov + ghosts[i].y * cw,
                    cw, cw, 0, 180, "ROUND");

            //Legs
            double[] xPoints = {
                    xOffset + xOffsetMov + ghosts[i].x * cw,
                    xOffset + xOffsetMov + ghosts[i].x * cw,
                    xOffset + xOffsetMov + ghosts[i].x * cw + (2 * cw / 5),
                    xOffset + xOffsetMov + ghosts[i].x * cw + cw / 2,
                    xOffset + xOffsetMov + ghosts[i].x * cw + (4 * cw / 5),
                    xOffset + xOffsetMov + ghosts[i].x * cw + cw,
                    xOffset + xOffsetMov + ghosts[i].x * cw + cw
            };
            double[] yPoints = {
                    yOffsetMov + ghosts[i].y * cw + cw / 2,
                    yOffsetMov + ghosts[i].y * cw + cw,
                    yOffsetMov + ghosts[i].y * cw + (4 * cw / 5),
                    yOffsetMov + ghosts[i].y * cw + cw,
                    yOffsetMov + ghosts[i].y * cw + (4 * cw / 5),
                    yOffsetMov + ghosts[i].y * cw + cw,
                    yOffsetMov + ghosts[i].y * cw + cw / 2
            };
            screen.fillPolygon(xPoints, yPoints, 7);

            //Eyes
            screen.setFill(COLOR_WHITE);
            screen.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (2 * cw / 8),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            screen.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (5 * cw / 8),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            screen.setFill(COLOR_BLACK);
            screen.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (2 * cw / 8) + (2 * cw / 16),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
            screen.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (5 * cw / 8) + (5 * cw / 32),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
        }
    }
}
