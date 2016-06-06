package pacman.jre;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pacman.shared.Ghost;
import pacman.shared.Grid;
import pacman.shared.MainLoop;
import pacman.shared.PacMan;

/**
 * Created by alejandrocq on 6/06/16.
 */
public class Game extends Application {

    private final String WINDOW_TITLE = "PACMAN";
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 800;

    private MenuBar menuBar;
    private Menu menuScore;

    private GameDrawer drawer;
    private Canvas canvas;
    private GraphicsContext context;

    private Grid grid;
    private PacMan pacMan;
    private Ghost[] ghosts;

    private MainLoop loop;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        createMenuBar();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar);

        drawer = new GameDrawer(this);
        vBox.getChildren().add(canvas);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            canvas.requestFocus();
        });

        canvas.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP: loop.pacMan.nextDirection = Grid.UP; break;
                case DOWN: loop.pacMan.nextDirection = Grid.DOWN; break;
                case LEFT: loop.pacMan.nextDirection = Grid.LEFT; break;
                case RIGHT: loop.pacMan.nextDirection = Grid.RIGHT; break;
                case SPACE: togglePause(); break;
            }
        });

        Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();

        restartGame();
        canvas.requestFocus();

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

        drawer.rePaint(grid, pacMan, ghosts);

        loop = new MainLoop(this, drawer, pacMan, ghosts, grid.maxScore, 0);
        loop.start();
    }

    public void togglePause() {
        if (loop.isPaused()) loop.resumeGame();
        else loop.pauseGame();
    }

    public void setScore(int score) {
        menuScore.setText("Score: " + score);
    }

    private void createMenuBar() {
        menuBar = new MenuBar();
        Menu menuGame = new Menu("Game");

        MenuItem newGame = new MenuItem("New");
        newGame.setOnAction(actionEvent -> restartGame());

        MenuItem pauseGame = new MenuItem("Pause");
        pauseGame.setOnAction(actionEvent -> togglePause());

        MenuItem exitGame = new MenuItem("Exit");
        exitGame.setOnAction(actionEvent -> System.exit(0));

        menuScore = new Menu("Score: 0");

        menuGame.getItems().addAll(newGame, pauseGame, exitGame);
        menuBar.getMenus().addAll(menuGame, menuScore);
    }

    public void showWinDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PACMAN");
        alert.setHeaderText(null);
        alert.setContentText("¡Has ganado!");

        alert.showAndWait();
    }

    public void showLoseDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PACMAN");
        alert.setHeaderText(null);
        alert.setContentText("¡Has perdido!");

        alert.showAndWait();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public int getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}
