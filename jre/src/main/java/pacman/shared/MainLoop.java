package pacman.shared;

import pacman.jre.Drawer;

public class MainLoop extends Thread {
    Drawer drawer;

    public Maze maze;
    public PacMan pacMan;
    public Ghost[] ghosts;
    public Mode mode;
    public int score = 0;

    private boolean gameEnded = false;
    private boolean paused = false;
    private int godMode = 0;
    private int Δ;

    public MainLoop(Drawer drawer, int level) {
        this.drawer = drawer;
        this.maze = new Maze();
        this.pacMan = new PacMan(maze, 1, 1, 5);
        this.ghosts = new Ghost[4];
        this.Δ = 50 - level * 5;

        int cnt = 0;
        for (int i = 0; i < maze.maze[0].length; i++) {
            for (int j = 0; j < maze.maze.length; j++)
                if (maze.maze[j][i] == Maze.GHOST) {
                    ghosts[cnt] = new Ghost(maze, i, j, 8);
                    Ghost ghost = ghosts[cnt++];
                    ghost.target = pacMan;
                }
        }
    }

    public synchronized void endGame() { gameEnded = true; }

    public boolean isPaused() { return paused; }

    public void pauseToggle() {
        if (isPaused()) synchronized (this) { paused = false; notifyAll(); }
        else synchronized (this) {paused = true;}
    }

    @Override
    public void run() {
        try {
            while (!gameEnded) {
                synchronized (this) {
                    while (paused) wait();
                }
                int score = pacMan.move();
                this.score += score;
                drawer.draw(this);
                if (score == Maze.POWER_PELLET_POINTS) godMode = 10000 / Δ;

                if (godMode != -1) {
                    if (godMode == 0) mode = Mode.NORMAL;
                    else if (godMode == 10000 / Δ) mode = Mode.GOD;
                    godMode--;
                }

                for (Ghost ghost : ghosts) {
                    int result = ghost.move(godMode != -1);
                    if (result == Ghost.PAC_MAN_COLLISION) {
                        if (godMode != -1) {
                            ghost.restart();
                        } else {
                            drawer.alert("¡Has perdido!");
                            gameEnded = true;
                            break;
                        }
                    }
                }

                if (this.score == maze.maxScore) {
                    drawer.alert("¡Has ganado!");
                    gameEnded = true;
                }

                Thread.sleep(Δ);
            }
        } catch (InterruptedException ignore) { }
    }

    public enum Mode {NORMAL, GOD}
}
