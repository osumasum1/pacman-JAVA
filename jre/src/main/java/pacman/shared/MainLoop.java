package pacman.shared;

import pacman.jre.Drawer;

public class MainLoop {
    private final Drawer drawer;

    public final Maze maze;
    public final PacMan pacMan;
    public final Ghost[] ghosts;
    public final int delta;

    public Mode mode;
    public int score = 0;
    public boolean gameEnded = false;
    private int godMode = 0;

    public MainLoop(Drawer drawer, int level) {
        this.drawer = drawer;
        this.maze = new Maze();
        this.pacMan = new PacMan(maze, 5, 1, 1);
        this.ghosts = new Ghost[4];
        this.delta = 50 - level * 5;

        int cnt = 0;
        for (int i = 0; i < maze.maze[0].length; i++) {
            for (int j = 0; j < maze.maze.length; j++) {
                if (maze.maze[j][i] == Maze.GHOST) ghosts[cnt++] = new Ghost(maze, 8, i, j, pacMan);
            }
        }
    }

    public synchronized void endGame() { gameEnded = true; }

    public void tick() {
        assert !gameEnded : "ups, game already ended!";
        int score = pacMan.move();
        this.score += score;
        drawer.draw(this);
        if (score == Maze.POWER_PELLET_POINTS) godMode = 10000 / delta;

        if (godMode != -1) {
            if (godMode == 0) mode = Mode.NORMAL;
            else if (godMode == 10000 / delta) mode = Mode.GOD;
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
    }

    public enum Mode {NORMAL, GOD}
}
