package pacman.jre;

import static java.lang.Math.random;
import static java.lang.Math.sin;
import static pacman.shared.MainLoop.Mode.GOD;

import java.util.function.Consumer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import pacman.shared.Ghost;
import pacman.shared.MainLoop;
import pacman.shared.MainLoop.Mode;
import pacman.shared.Maze;
import pacman.shared.PacMan;

public class Drawer {
    private final Color[] GHOST_COLORS = { Color.RED, Color.BLUE, Color.PINK, Color.CYAN };

    private final Canvas canvas;
    private final GraphicsContext context;
    private final Consumer<Integer> score;
    private final Consumer<String> alert;

    public Drawer(Canvas canvas, Consumer<Integer> score, Consumer<String> alert) {
        this.canvas = canvas;
        this.context = canvas.getGraphicsContext2D();
        this.score = score;
        this.alert = alert;
    }

    /** Show alert message. */
    public void alert(String message) {
        alert.accept(message);
    }

    /** Draw current state. */
    public void draw(MainLoop state) {
        // TODO support resizable canvas, so all this values might change in each draw
        double w = canvas.getWidth(), h = canvas.getHeight();
        int cellWidth = (int) Math.min(w / state.maze.maze[0].length, h / state.maze.maze.length);
        double xOffset = (w - state.maze.maze[0].length * cellWidth) / 2;

        // clear
        context.beginPath();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, w, h);
        context.closePath();

        // draw
        drawMaze(state.maze, cellWidth, xOffset);
        drawPacMan(state.pacMan, cellWidth, xOffset);
        drawGhosts(state.ghosts, cellWidth, (int) (xOffset), state.mode);
        score.accept(state.score);
    }

    private void drawMaze(Maze maze, int cw, double xOffset) {
        int x, y, o = (int) xOffset;
        context.beginPath();

        for (x = 0; x < maze.maze[0].length; x++) {
            for (y = 0; y < maze.maze.length; y++) {
                switch (maze.maze[y][x]) {
                    case Maze.RECTANGLE_UP:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw, y * cw, cw, cw / 2);
                        break;
                    case Maze.RECTANGLE_DOWN:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw, y * cw + cw / 2, cw, cw / 2);
                        break;
                    case Maze.RECTANGLE_RIGHT:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw + cw / 2, y * cw, cw / 2, cw);
                        break;
                    case Maze.RECTANGLE_LEFT:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw, y * cw, cw / 2, cw);
                        break;
                    case Maze.BOTTOM_RIGHT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { o + (x + 1) * cw, o + (x * cw + cw / 2), o + (x + 1) * cw },
                                new double[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Maze.BOTTOM_LEFT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Maze.UPPER_LEFT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Maze.UPPER_RIGHT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { o + (x * cw + cw / 2), o + (x + 1) * cw, o + (x + 1) * cw },
                                new double[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Maze.BOTTOM_LEFT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw, y * cw, cw, cw);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Maze.BOTTOM_RIGHT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw, y * cw, cw, cw);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { o + (x + 1) * cw, o + (x * cw + cw / 2), o + (x + 1) * cw },
                                new double[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Maze.UPPER_RIGHT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw, y * cw, cw, cw);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { o + (x * cw + cw / 2), o + (x + 1) * cw, o + (x + 1) * cw },
                                new double[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Maze.UPPER_LEFT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(o + x * cw, y * cw, cw, cw);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { o + x * cw, o + x * cw, o + (x * cw + cw / 2) },
                                new double[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Maze.SMALL_DOT:
                        context.setFill(Color.YELLOW);
                        context.fillOval(o + x * cw + cw / 2, y * cw + cw / 2, cw / 5, cw / 5);
                        break;
                    case Maze.BIG_DOT:
                        context.setFill(Color.YELLOW);
                        context.fillOval(o + x * cw + cw / 2, y * cw + cw / 2, cw / 3, cw / 3);
                        break;
                }
            }
        }
        context.closePath();
    }

    private void drawPacMan(PacMan pacMan, int cw, double xOffset) {
        double xOffsetMov = pacMan.xOffset * cw / pacMan.movesPerCell;
        double yOffsetMov = pacMan.yOffset * cw / pacMan.movesPerCell;
        context.setFill(Color.YELLOW);

        switch (pacMan.currentDirection) {
            case Maze.RIGHT:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (22 - 22 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
            case Maze.LEFT:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (202 - 22 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
            case Maze.UP:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (112 - 22 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
            case Maze.DOWN:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cw,
                        yOffsetMov + pacMan.y * cw, cw + 2, cw + 2,
                        (292 - 22 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
        }
    }

    private void drawGhosts(Ghost[] ghosts, int cw, int xOffset, Mode mode) {
        for (int i = 0; i < ghosts.length; i++) {
            int xOffsetMov = ghosts[i].xOffset * cw / ghosts[i].movesPerCell;
            int yOffsetMov = ghosts[i].yOffset * cw / ghosts[i].movesPerCell;
            context.setFill(mode == GOD ? new Color(random(), random(), random(), 1.0f) : GHOST_COLORS[i]);

            //Head
            context.fillArc(
                    xOffset + xOffsetMov + ghosts[i].x * cw,
                    yOffsetMov + ghosts[i].y * cw,
                    cw, cw, 0, 180, ArcType.ROUND);

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
            context.fillPolygon(xPoints, yPoints, 7);

            //Eyes
            context.setFill(Color.WHITE);
            context.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (2 * cw / 8),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            context.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (5 * cw / 8),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            context.setFill(Color.BLACK);
            context.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (2 * cw / 8) + (2 * cw / 16),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
            context.fillOval(
                    xOffset + xOffsetMov + ghosts[i].x * cw + (5 * cw / 8) + (5 * cw / 32),
                    yOffsetMov + ghosts[i].y * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
        }
    }
}
