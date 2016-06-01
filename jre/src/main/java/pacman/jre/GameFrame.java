package pacman.jre;

import static java.awt.EventQueue.invokeLater;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame {
    static final Dimension PREFERRED_SIZE = new Dimension(420, 509);

    public static void main(String args[]) {
        invokeLater(() -> new GameFrame().setVisible(true));
    }

    private JMenuItem score;

    private GameFrame() {
        setTitle("Pac-Man");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(PREFERRED_SIZE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

        MazeFrame maze = new MazeFrame(this);

        setJMenuBar(apply(new JMenuBar(), menuBar -> {
            menuBar.add(apply(new JMenu(), gameMenu -> {
                gameMenu.setText("Game");

                JMenuItem newGame = new JMenuItem();
                newGame.setText("New");
                newGame.addActionListener(evt -> maze.restartGame());
                gameMenu.add(newGame);

                JMenuItem pause = new JMenuItem();
                pause.setText("Pause");
                pause.addActionListener(evt -> maze.togglePause());
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

        getContentPane().add(maze);

        pack();
    }

    public void score(int score) {
        if (this.score != null) this.score.setText("Score: " + score);
    }

    public static <T> T apply(T t, Consumer<T> fn) {
        fn.accept(t);
        return t;
    }
}
