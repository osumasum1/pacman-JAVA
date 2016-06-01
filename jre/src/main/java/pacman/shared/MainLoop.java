package pacman.shared;

import javax.swing.JOptionPane;
import pacman.jre.GameFrame;
import pacman.jre.MazeFrame;
import pacman.jre.MazeFrame.Mode;

public class MainLoop extends Thread {
    public PacMan pacMan;
    Ghost[] ghosts;
    public boolean gameEnded = false;
    public boolean paused = false;
    GameFrame frame;
    MazeFrame panel;
    int score = 0;
    int godMode = 0;
    int maxScore;
    int Δ;

    public MainLoop(GameFrame cf, MazeFrame lf, PacMan pacMan, Ghost[] ghosts, int maxScore, int level) {
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.frame = cf;
        this.panel = lf;
        this.maxScore = maxScore;
        this.Δ = 50 - level * 5;
    }

    public synchronized void endGame() { gameEnded = true; }

    public boolean isPaused() { return paused; }

    public synchronized void pauseGame() { paused = true; }

    public synchronized void resumeGame() { paused = false; notifyAll(); }

    @Override
    public void run() {
        try {
            while (!gameEnded) {
                panel.repaint();
                synchronized (this) {
                    while (paused) wait();
                }
                int score = pacMan.move();
                this.score += score;
                frame.score(this.score);
                if (score == Grid.POWER_PELLET_POINTS) godMode = 10000 / Δ;

                if (godMode != -1) {
                    if (godMode == 0) panel.mode = Mode.NORMAL;
                    else if (godMode == 10000 / Δ) panel.mode = Mode.GOD;
                    godMode--;
                }

                for (Ghost ghost : ghosts) {
                    int result = ghost.mover(godMode != -1);
                    if (result == Ghost.PAC_MAN_COLLISION) {
                        if (godMode != -1) {
                            ghost.restart();
                        } else {
                            JOptionPane.showMessageDialog(panel, "YOU LOUSE!");
                            gameEnded = true;
                            break;
                        }
                    }
                }

                if (this.score == maxScore) {
                    JOptionPane.showMessageDialog(panel, "YOU WIN!");
                    gameEnded = true;
                }

                Thread.sleep(Δ);
            }
        } catch (InterruptedException ignore) { }
    }
}
