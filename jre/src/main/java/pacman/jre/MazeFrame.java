package pacman.jre;

import static java.lang.Math.sin;
import static pacman.jre.MazeFrame.Mode.GOD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import pacman.shared.Ghost;
import pacman.shared.Grid;
import pacman.shared.MainLoop;
import pacman.shared.PacMan;

public class MazeFrame extends JPanel {
    private static final Logger log = Logger.getLogger(MazeFrame.class.getName());

    public enum Mode {NORMAL, GOD}

    private GameFrame game;
    private Grid grid = new Grid();
    private PacMan data;
    private Ghost[] ghosts;
    private final Color[] ghostColors = { Color.RED, Color.BLUE, Color.PINK, Color.CYAN };
    private MainLoop loop;
    public Mode mode;

    public MazeFrame(GameFrame game) {
        this.game = game; // TODO remove cyclic dependency
        setPreferredSize(GameFrame.PREFERRED_SIZE);
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                requestFocus();
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_UP: loop.pacMan.nextDirection = Grid.UP; break;
                    case KeyEvent.VK_DOWN: loop.pacMan.nextDirection = Grid.DOWN; break;
                    case KeyEvent.VK_LEFT: loop.pacMan.nextDirection = Grid.LEFT; break;
                    case KeyEvent.VK_RIGHT: loop.pacMan.nextDirection = Grid.RIGHT; break;
                    case KeyEvent.VK_SPACE: togglePause(); break;
                }
            }
        });

        restartGame();
    }
    public void togglePause() {
        if (loop.isPaused()) loop.resumeGame();
        else loop.pauseGame();
    }

    public void restartGame() {
        if (loop != null) loop.endGame();
        grid = new Grid();
        data = new PacMan(grid, 1, 1, 5);
        ghosts = new Ghost[4];
        int cnt = 0;
        for (int i = 0; i < grid.maze[0].length; i++) {
            for (int j = 0; j < grid.maze.length; j++)
                if (grid.maze[j][i] == Grid.FANTASMA) {
                    ghosts[cnt] = new Ghost(grid, i, j, 8);
                    Ghost ghost = ghosts[cnt++];
                    ghost.target = data;
                }
        }
        loop = new MainLoop(game, this, data, ghosts, grid.maxScore, 0);
        loop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            int cellWidth = Math.min(getWidth() / grid.maze[0].length, getHeight() / grid.maze.length);
            paintMaze(g, cellWidth);
            paintPacMan(g, cellWidth);
            paintGhosts(g, cellWidth);
        } catch (Exception e) {
            log.log(Level.SEVERE, "painting error: " + e, e);
        }
    }

    private void paintMaze(Graphics g, int cw) {
        int x, y, ϕ /*offset*/ = (getWidth() - grid.maze[0].length * cw) / 2;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (x = 0; x < grid.maze[0].length; x++) {
            for (y = 0; y < grid.maze.length; y++) {
                switch (grid.maze[y][x]) {
                    case Grid.RECTANGULOARRIBA:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw / 2);
                        break;
                    case Grid.RECTANGULOABAJO:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw + cw / 2, cw, cw / 2);
                        break;
                    case Grid.RECTANGULODERECHA:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw + cw / 2, y * cw, cw / 2, cw);
                        break;
                    case Grid.RECTANGULOIZQUIERDA:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw / 2, cw);
                        break;
                    case Grid.ESQUINAABAJODERECHA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + (x + 1) * cw, ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw },
                                new int[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Grid.ESQUINAABAJOIZQUIERDA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Grid.ESQUINAARRIBAIZQUIERDA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Grid.ESQUINAARRIBADERECHA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw, ϕ + (x + 1) * cw },
                                new int[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Grid.ESQUINAABAJOIZQUIERDAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Grid.ESQUINAABAJODERECHAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + (x + 1) * cw, ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw },
                                new int[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Grid.ESQUINAARRIBADERECHAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw, ϕ + (x + 1) * cw },
                                new int[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Grid.ESQUINAARRIBAIZQUIERDAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Grid.PUNTOPEQUEÑO:
                        g.setColor(Color.YELLOW);
                        g.fillOval(ϕ + x * cw + cw / 2, y * cw + cw / 2, cw / 5, cw / 5);
                        break;
                    case Grid.PUNTOGRANDE:
                        g.setColor(Color.YELLOW);
                        g.fillOval(ϕ + x * cw + cw / 2, y * cw + cw / 2, cw / 3, cw / 3);
                        break;
                }
            }
        }
    }

    private void paintPacMan(Graphics g, int cw) {
        int xoffset = (getWidth() - grid.maze[0].length * cw) / 2;
        int xoffsetmov = data.xOffset * cw / data.movesPerCell;
        int yoffsetmov = data.yOffset * cw / data.movesPerCell;
        g.setColor(mode == GOD ? new Color((int) (Math.random() * 65000)) : Color.YELLOW);

        switch (data.currentDirection) {
            case Grid.RIGHT:
                g.fillArc(xoffset + xoffsetmov + data.x * cw, yoffsetmov + data.y * cw, cw + 2, cw + 2,
                        (int) (22 - 22 * sin((2 * Math.PI * data.xOffset) / data.movesPerCell)),
                        (int) (315 + 45 * sin((2 * Math.PI * data.xOffset) / data.movesPerCell)));
                break;
            case Grid.LEFT:
                g.fillArc(xoffset + xoffsetmov + data.x * cw, yoffsetmov + data.y * cw, cw + 2, cw + 2,
                        (int) (202 - 22 * sin((2 * Math.PI * data.xOffset) / data.movesPerCell)),
                        (int) (315 + 45 * sin((2 * Math.PI * data.xOffset) / data.movesPerCell)));
                break;
            case Grid.UP:
                g.fillArc(xoffset + xoffsetmov + data.x * cw, yoffsetmov + data.y * cw, cw + 2, cw + 2,
                        (int) (112 - 22 * sin((2 * Math.PI * data.yOffset) / data.movesPerCell)),
                        (int) (315 + 45 * sin((2 * Math.PI * data.yOffset) / data.movesPerCell)));
                break;
            case Grid.DOWN:
                g.fillArc(xoffset + xoffsetmov + data.x * cw, yoffsetmov + data.y * cw, cw + 2, cw + 2,
                        (int) (292 - 22 * sin((2 * Math.PI * data.yOffset) / data.movesPerCell)),
                        (int) (315 + 45 * sin((2 * Math.PI * data.yOffset) / data.movesPerCell)));
                break;
        }
    }

    private void paintGhosts(Graphics g, int cw) {
        int xoffset = (getWidth() - grid.maze[0].length * cw) / 2;

        for (int i = 0; i < ghosts.length; i++) {
            int xoffsetmov = ghosts[i].xOffset * cw / ghosts[i].movesPerCell;
            int yoffsetmov = ghosts[i].yOffset * cw / ghosts[i].movesPerCell;
            g.setColor(mode == GOD ? new Color((int) (Math.random() * 65000)) : ghostColors[i]);

            //Head
            g.fillArc(xoffset + xoffsetmov + ghosts[i].x * cw,
                    yoffsetmov + ghosts[i].y * cw, cw, cw, 0, 180);

            //Legs
            int[] xPoints = {
                    xoffset + xoffsetmov + ghosts[i].x * cw,
                    xoffset + xoffsetmov + ghosts[i].x * cw,
                    xoffset + xoffsetmov + ghosts[i].x * cw + (2 * cw / 5),
                    xoffset + xoffsetmov + ghosts[i].x * cw + cw / 2,
                    xoffset + xoffsetmov + ghosts[i].x * cw + (4 * cw / 5),
                    xoffset + xoffsetmov + ghosts[i].x * cw + cw,
                    xoffset + xoffsetmov + ghosts[i].x * cw + cw
            };
            int[] yPoints = {
                    yoffsetmov + ghosts[i].y * cw + cw / 2,
                    yoffsetmov + ghosts[i].y * cw + cw,
                    yoffsetmov + ghosts[i].y * cw + (4 * cw / 5),
                    yoffsetmov + ghosts[i].y * cw + cw,
                    yoffsetmov + ghosts[i].y * cw + (4 * cw / 5),
                    yoffsetmov + ghosts[i].y * cw + cw,
                    yoffsetmov + ghosts[i].y * cw + cw / 2
            };
            g.fillPolygon(xPoints, yPoints, 7);

            //Eyes
            g.setColor(Color.WHITE);
            g.fillOval(xoffset + xoffsetmov + ghosts[i].x * cw + (2 * cw / 8),
                    yoffsetmov + ghosts[i].y * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            g.fillOval(xoffset + xoffsetmov + ghosts[i].x * cw + (5 * cw / 8),
                    yoffsetmov + ghosts[i].y * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            g.setColor(Color.BLACK);
            g.fillOval(xoffset + xoffsetmov + ghosts[i].x * cw + (2 * cw / 8) + (2 * cw / 16),
                    yoffsetmov + ghosts[i].y * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
            g.fillOval(xoffset + xoffsetmov + ghosts[i].x * cw + (5 * cw / 8) + (5 * cw / 32),
                    yoffsetmov + ghosts[i].y * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
        }
    }
}
