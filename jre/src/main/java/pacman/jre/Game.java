package pacman.jre;

import static javafx.application.Platform.runLater;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

import java.util.function.Consumer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pacman.shared.MainLoop;
import pacman.shared.Maze;

public class Game extends Application {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 800;

    private MainLoop loop;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MenuBar menuBar = new MenuBar();
        Menu menuGame = new Menu("Game");
        MenuItem newGame = new MenuItem("New");
        MenuItem pauseGame = new MenuItem("Pause");
        MenuItem exitGame = new MenuItem("Exit");
        menuGame.getItems().addAll(newGame, pauseGame, exitGame);
        menuBar.getMenus().add(menuGame);

        Menu menuScore = new Menu("Score: 0");
        menuBar.getMenus().add(menuScore);

        Consumer<Integer> score = s -> runLater(() -> menuScore.setText("Score: " + (int) s));
        Consumer<String> alert = text -> runLater(() -> {
            Alert a = new Alert(INFORMATION);
            a.setTitle("PAC-MAN");
            a.setHeaderText(null);
            a.setContentText(text);
            a.showAndWait();
        });

        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        Drawer drawer = new Drawer(canvas, score, alert);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar);
        vBox.getChildren().add(canvas);

        Runnable restart = () -> restart(drawer);
        newGame.setOnAction(actionEvent -> restart.run());
        pauseGame.setOnAction(actionEvent -> loop.pauseToggle());
        exitGame.setOnAction(actionEvent -> System.exit(0));

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> canvas.requestFocus());
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP: loop.pacMan.nextDirection = Maze.UP; break;
                case DOWN: loop.pacMan.nextDirection = Maze.DOWN; break;
                case LEFT: loop.pacMan.nextDirection = Maze.LEFT; break;
                case RIGHT: loop.pacMan.nextDirection = Maze.RIGHT; break;
                case SPACE: loop.pauseToggle(); break;
            }
        });

        Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("PAC-MAN");
        stage.setScene(scene);
        stage.show();

        restart.run();
        canvas.requestFocus();
    }

    public void restart(Drawer drawer) {
        if (loop != null) loop.endGame();
        loop = new MainLoop(drawer, 0);
        loop.start();
    }
}
