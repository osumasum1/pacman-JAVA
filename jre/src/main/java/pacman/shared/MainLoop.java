package pacman.shared;

import javafx.application.Platform;
import pacman.jre.GameDrawer;
import pacman.jre.GameDrawer.Mode;
import pacman.jre.Game;

public class MainLoop extends Thread {
    public PacMan pacMan;
    Ghost[] ghosts;
    public boolean gameEnded = false;
    public boolean paused = false;
    Game game;
    GameDrawer drawer;
    int score = 0;
    int godMode = 0;
    int maxScore;
    int Δ;

    public MainLoop(Game gameFrame, GameDrawer game,
                    PacMan pacMan, Ghost[] ghosts, int maxScore, int level) {
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.game = gameFrame;
        this.drawer = game;
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
                drawer.rePaint();
                synchronized (this) {
                    while (paused) wait();
                }
                int score = pacMan.move();
                this.score += score;
                Platform.runLater(() -> game.setScore(this.score));
                if (score == Grid.POWER_PELLET_POINTS) godMode = 10000 / Δ;

                if (godMode != -1) {
                    if (godMode == 0) drawer.setMode(Mode.NORMAL);
                    else if (godMode == 10000 / Δ) drawer.setMode(Mode.GOD);
                    godMode--;
                }

                for (Ghost ghost : ghosts) {
                    int result = ghost.move(godMode != -1);
                    if (result == Ghost.PAC_MAN_COLLISION) {
                        if (godMode != -1) {
                            ghost.restart();
                        } else {
                            Platform.runLater(() -> game.showLoseDialog());
                            gameEnded = true;
                            break;
                        }
                    }
                }

                if (this.score == maxScore) {
                    Platform.runLater(() -> game.showWinDialog());
                    gameEnded = true;
                }

                Thread.sleep(Δ);
            }
        } catch (InterruptedException ignore) { }
    }
}
