package pacman.jre;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import pacman.shared.Ghost;
import pacman.shared.Grid;
import pacman.shared.PacMan;

import static java.lang.Math.sin;

/**
 * Created by alejandrocq on 6/06/16.
 */
public class GameDrawer {

    private final Color[] GHOST_COLORS = { Color.RED, Color.BLUE, Color.PINK, Color.CYAN };

    public enum Mode {NORMAL, GOD}
    private Mode mode;

    private Game game;

    private Canvas canvas;
    private GraphicsContext context;

    private Grid grid;
    private PacMan pacMan;
    private Ghost[] ghosts;
    private int cellWidth;

    public GameDrawer(Game game) {
        this.game = game;
        grid = new Grid();
        cellWidth = Math.min(game.getWindowWidth() / grid.maze[0].length,
                game.getWindowHeight() / grid.maze.length);
        createCanvas();
        game.setCanvas(canvas);
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**************************************/
            /* Drawing methods */
    /**************************************/

    public void rePaint() {
        clearCanvas();
        drawMaze();
        drawPacMan();
        drawGhosts();
    }

    public void rePaint (Grid grid, PacMan pacMan, Ghost[] ghosts) {
        this.grid = grid;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        drawMaze();
        drawPacMan();
        drawGhosts();
    }

    private void clearCanvas() {
        context.beginPath();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, game.getWindowWidth(), game.getWindowHeight());
        context.closePath();
    }

    public void createCanvas() {
        canvas = new Canvas(game.getWindowWidth(), game.getWindowHeight());
        context = canvas.getGraphicsContext2D();
        context.beginPath();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, game.getWindowWidth(), game.getWindowHeight());
        context.closePath();
    }

    private void drawMaze() {
        int x, y, offset = (game.getWindowWidth() - grid.maze[0].length * cellWidth) / 2;
        context.beginPath();

        for (x = 0; x < grid.maze[0].length; x++) {
            for (y = 0; y < grid.maze.length; y++) {
                switch (grid.maze[y][x]) {
                    case Grid.RECTANGLE_UP:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x*cellWidth, y*cellWidth, cellWidth, cellWidth/2);
                        break;
                    case Grid.RECTANGLE_DOWN:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x * cellWidth,
                                y * cellWidth + cellWidth / 2, cellWidth, cellWidth / 2);
                        break;
                    case Grid.RECTANGLE_RIGHT:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x * cellWidth + cellWidth / 2,
                                y * cellWidth, cellWidth / 2, cellWidth);
                        break;
                    case Grid.RECTANGLE_LEFT:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x * cellWidth, y * cellWidth, cellWidth / 2, cellWidth);
                        break;
                    case Grid.BOTTOM_RIGHT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { offset + (x + 1) * cellWidth, offset + (x * cellWidth + cellWidth / 2),
                                        offset + (x + 1) * cellWidth },
                                new double[] { (y + 1) * cellWidth, (y + 1) * cellWidth,
                                        y * cellWidth + cellWidth / 2 },
                                3);
                        break;
                    case Grid.BOTTOM_LEFT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { offset + x * cellWidth, offset + x * cellWidth,
                                        offset + (x * cellWidth + cellWidth / 2) },
                                new double[] { (y + 1) * cellWidth, y * cellWidth + cellWidth / 2,
                                        (y + 1) * cellWidth },
                                3);
                        break;
                    case Grid.UPPER_LEFT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { offset + x * cellWidth, offset + x * cellWidth, offset +
                                        (x * cellWidth + cellWidth / 2) },
                                new double[] { y * cellWidth, y * cellWidth + cellWidth / 2, y * cellWidth }, 3);
                        break;
                    case Grid.UPPER_RIGHT_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillPolygon(
                                new double[] { offset + (x * cellWidth + cellWidth / 2),
                                        offset + (x + 1) * cellWidth, offset + (x + 1) * cellWidth },
                                new double[] { y * cellWidth, y * cellWidth, y * cellWidth + cellWidth / 2 }, 3);
                        break;
                    case Grid.BOTTOM_LEFT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x * cellWidth, y * cellWidth, cellWidth, cellWidth);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { offset + x * cellWidth, offset + x * cellWidth,
                                        offset + (x * cellWidth + cellWidth / 2) },
                                new double[] { (y + 1) * cellWidth,
                                        y * cellWidth + cellWidth / 2, (y + 1) * cellWidth },
                                3);
                        break;
                    case Grid.BOTTOM_RIGHT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x * cellWidth, y * cellWidth, cellWidth, cellWidth);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { offset + (x + 1) * cellWidth,
                                        offset + (x * cellWidth + cellWidth / 2), offset + (x + 1) * cellWidth },
                                new double[] { (y + 1) * cellWidth, (y + 1) * cellWidth,
                                        y * cellWidth + cellWidth / 2 },
                                3);
                        break;
                    case Grid.UPPER_RIGHT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x * cellWidth, y * cellWidth, cellWidth, cellWidth);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { offset + (x * cellWidth + cellWidth / 2),
                                        offset + (x + 1) * cellWidth, offset + (x + 1) * cellWidth },
                                new double[] { y * cellWidth, y * cellWidth, y * cellWidth + cellWidth / 2 }, 3);
                        break;
                    case Grid.UPPER_LEFT_BIG_CORNER:
                        context.setFill(Color.BLUE);
                        context.fillRect(offset + x * cellWidth, y * cellWidth, cellWidth, cellWidth);
                        context.setFill(Color.BLACK);
                        context.fillPolygon(
                                new double[] { offset + x * cellWidth, offset + x * cellWidth,
                                        offset + (x * cellWidth + cellWidth / 2) },
                                new double[] { y * cellWidth, y * cellWidth + cellWidth / 2, y * cellWidth }, 3);
                        break;
                    case Grid.SMALL_DOT:
                        context.setFill(Color.YELLOW);
                        context.fillOval(offset + x * cellWidth + cellWidth / 2,
                                y * cellWidth + cellWidth / 2, cellWidth / 5, cellWidth / 5);
                        break;
                    case Grid.BIG_DOT:
                        context.setFill(Color.YELLOW);
                        context.fillOval(offset + x * cellWidth + cellWidth / 2,
                                y * cellWidth + cellWidth / 2, cellWidth / 3, cellWidth / 3);
                        break;
                }
            }
        }
        context.closePath();

    }

    private void drawPacMan() {
        double xOffset = (game.getWindowWidth() - grid.maze[0].length * cellWidth) / 2;
        double xOffsetMov = pacMan.xOffset * cellWidth / pacMan.movesPerCell;
        double yOffsetMov = pacMan.yOffset * cellWidth / pacMan.movesPerCell;
        context.setFill(Color.YELLOW);

        switch (pacMan.currentDirection) {
            case Grid.RIGHT:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cellWidth,
                        yOffsetMov + pacMan.y * cellWidth, cellWidth + 2, cellWidth + 2,
                        (22 - 22 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
            case Grid.LEFT:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cellWidth,
                        yOffsetMov + pacMan.y * cellWidth, cellWidth + 2, cellWidth + 2,
                        (202 - 22 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.xOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
            case Grid.UP:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cellWidth,
                        yOffsetMov + pacMan.y * cellWidth, cellWidth + 2, cellWidth + 2,
                        (112 - 22 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
            case Grid.DOWN:
                context.fillArc(xOffset + xOffsetMov + pacMan.x * cellWidth,
                        yOffsetMov + pacMan.y * cellWidth, cellWidth + 2, cellWidth + 2,
                        (292 - 22 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)),
                        (315 + 45 * sin((2 * Math.PI * pacMan.yOffset) / pacMan.movesPerCell)), ArcType.ROUND);
                break;
        }
    }

    private Color getRandomColor() {
        double R = Math.random();
        double G = Math.random();
        double B = Math.random();
        Color color = new Color(R,G,B, 1.0f);

        return color;
    }

    private void drawGhosts() {
        int xoffset = (game.getWindowWidth() - grid.maze[0].length * cellWidth) / 2;

        for (int i = 0; i < ghosts.length; i++) {
            int xOffsetMov = ghosts[i].xOffset * cellWidth / ghosts[i].movesPerCell;
            int yOffsetMov = ghosts[i].yOffset * cellWidth / ghosts[i].movesPerCell;
            context.setFill(mode == Mode.GOD ? getRandomColor() : GHOST_COLORS[i]);

            //Head
            context.fillArc(xoffset + xOffsetMov + ghosts[i].x * cellWidth,
                    yOffsetMov + ghosts[i].y * cellWidth, cellWidth, cellWidth, 0, 180, ArcType.ROUND);

            //Legs
            double[] xPoints = {
                    xoffset + xOffsetMov + ghosts[i].x * cellWidth,
                    xoffset + xOffsetMov + ghosts[i].x * cellWidth,
                    xoffset + xOffsetMov + ghosts[i].x * cellWidth + (2 * cellWidth / 5),
                    xoffset + xOffsetMov + ghosts[i].x * cellWidth + cellWidth / 2,
                    xoffset + xOffsetMov + ghosts[i].x * cellWidth + (4 * cellWidth / 5),
                    xoffset + xOffsetMov + ghosts[i].x * cellWidth + cellWidth,
                    xoffset + xOffsetMov + ghosts[i].x * cellWidth + cellWidth
            };
            double[] yPoints = {
                    yOffsetMov + ghosts[i].y * cellWidth + cellWidth / 2,
                    yOffsetMov + ghosts[i].y * cellWidth + cellWidth,
                    yOffsetMov + ghosts[i].y * cellWidth + (4 * cellWidth / 5),
                    yOffsetMov + ghosts[i].y * cellWidth + cellWidth,
                    yOffsetMov + ghosts[i].y * cellWidth + (4 * cellWidth / 5),
                    yOffsetMov + ghosts[i].y * cellWidth + cellWidth,
                    yOffsetMov + ghosts[i].y * cellWidth + cellWidth / 2
            };
            context.fillPolygon(xPoints, yPoints, 7);

            //Eyes
            context.setFill(Color.WHITE);
            context.fillOval(xoffset + xOffsetMov + ghosts[i].x * cellWidth + (2 * cellWidth / 8),
                    yOffsetMov + ghosts[i].y * cellWidth + (2 * cellWidth / 8), cellWidth / 4,
                    cellWidth / 4);
            context.fillOval(xoffset + xOffsetMov + ghosts[i].x * cellWidth
                    + (5 * cellWidth / 8),
                    yOffsetMov + ghosts[i].y * cellWidth + (2 * cellWidth / 8), cellWidth / 4,
                    cellWidth / 4);
            context.setFill(Color.BLACK);
            context.fillOval(xoffset + xOffsetMov + ghosts[i].x * cellWidth + (2 * cellWidth / 8)
                    + (2 * cellWidth / 16),
                    yOffsetMov + ghosts[i].y * cellWidth + (2 * cellWidth / 8) + (2 * cellWidth / 24),
                    cellWidth / 6, cellWidth / 6);
            context.fillOval(xoffset + xOffsetMov + ghosts[i].x * cellWidth + (5 * cellWidth / 8)
                    + (5 * cellWidth / 32),
                    yOffsetMov + ghosts[i].y * cellWidth + (2 * cellWidth / 8) + (2 * cellWidth / 24),
                    cellWidth / 6, cellWidth / 6);
        }
    }

}
