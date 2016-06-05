package pacman.jre;

import pacman.shared.Ghost;
import pacman.shared.Grid;
import pacman.shared.MainLoop;
import pacman.shared.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

import static java.awt.EventQueue.invokeLater;

public class GameFrame extends JFrame {
    static final Dimension PREFERRED_SIZE = new Dimension(600, 600);

    public static void main(String args[]) {
        invokeLater(() -> new GameFrame().setVisible(true));
    }

    private JMenuItem score;
    private GameDrawer drawer;

    private MainLoop loop;
    private Grid grid;
    private PacMan pacMan;
    private Ghost[] ghosts;

    private GameFrame() {
        setTitle("Pac-Man");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(PREFERRED_SIZE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

        drawer = new GameDrawer();

        setJMenuBar(apply(new JMenuBar(), menuBar -> {
            menuBar.add(apply(new JMenu(), gameMenu -> {
                gameMenu.setText("Game");

                JMenuItem newGame = new JMenuItem();
                newGame.setText("New");
                newGame.addActionListener(evt -> restartGame());
                gameMenu.add(newGame);

                JMenuItem pause = new JMenuItem();
                pause.setText("Pause");
                pause.addActionListener(evt -> togglePause());
                gameMenu.add(pause);

                JMenuItem exit = new JMenuItem();
                exit.setText("Exit");
                exit.addActionListener(evt -> System.exit(0));
                gameMenu.add(exit);
            }));

            score = new JMenuItem();
            score.setText("Score: 0");
            menuBar.add(score);
        }));

        getContentPane().add(drawer);
        pack();

        drawer.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                drawer.requestFocus();
            }
        });

        drawer.addKeyListener(new java.awt.event.KeyAdapter() {
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
        pacMan = new PacMan(grid, 1, 1, 5);
        ghosts = new Ghost[4];
        int cnt = 0;
        for (int i = 0; i < grid.maze[0].length; i++) {
            for (int j = 0; j < grid.maze.length; j++)
                if (grid.maze[j][i] == Grid.GHOST) {
                    ghosts[cnt] = new Ghost(grid, i, j, 8);
                    Ghost ghost = ghosts[cnt++];
                    ghost.target = pacMan;
                }
        }

        loop = new MainLoop(this, drawer, pacMan, ghosts, grid.maxScore, 0);
        drawer.setGrid(grid);
        drawer.setPacMan(pacMan);
        drawer.setGhosts(ghosts);
        loop.start();
    }

    public void score(int score) {
        if (this.score != null) this.score.setText("Score: " + score);
    }

    public static <T> T apply(T t, Consumer<T> fn) {
        fn.accept(t);
        return t;
    }
}
