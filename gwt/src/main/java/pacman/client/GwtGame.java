package pacman.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import pacman.shared.Drawer;
import pacman.shared.MainLoop;
import pacman.shared.Maze;

public class GwtGame implements EntryPoint {

    private final String BAR_CONTAINER = "barContainer";
    private final String CANVAS_CONTAINER = "canvasContainer";

    private final int CANVAS_WIDTH = 700;
    private final int CANVAS_HEIGHT = 700;

    private MainLoop game;
    private Timer ticker;

    @Override public void onModuleLoad() {
        Label labelScore = new Label("Score: 0");
        RootPanel.get().add(labelScore);

        Drawer.Toaster score = s -> labelScore.setText("Score: " + s);
        Drawer.Toaster alert = text -> Window.alert(text);

        //Create canvas
        Canvas canvas = Canvas.createIfSupported();
        if (canvas == null) {
            Window.alert("Ups! your browser do not support canvas...");
            return;
        }

        canvas.setWidth(CANVAS_WIDTH + "px");
        canvas.setHeight(CANVAS_HEIGHT + "px");
        canvas.setCoordinateSpaceWidth(CANVAS_WIDTH);
        canvas.setCoordinateSpaceHeight(CANVAS_HEIGHT);

        canvas.addKeyDownHandler(event -> {
            switch (event.getNativeKeyCode()) {
                case KeyCodes.KEY_UP: game.pacMan.nextDirection = Maze.UP; break;
                case KeyCodes.KEY_DOWN: game.pacMan.nextDirection = Maze.DOWN; break;
                case KeyCodes.KEY_LEFT: game.pacMan.nextDirection = Maze.LEFT; break;
                case KeyCodes.KEY_RIGHT: game.pacMan.nextDirection = Maze.RIGHT; break;
                case KeyCodes.KEY_SPACE: playPause(GwtGame.this.game); break;
            }
        });

        canvas.addMouseOverHandler(event -> canvas.setFocus(true));

        //Set drawer and menu bar
        Drawer drawer = new Drawer(new GwtCanvas(canvas), score, alert);
        RootPanel.get(CANVAS_CONTAINER).add(canvas);
        Runnable restart = () -> restart(drawer);

        Command newGame = () -> restart.run();
        Command pauseGame = () -> playPause(game);

        MenuBar bar = new MenuBar(true);
        bar.addItem("Nuevo", newGame);
        bar.addItem("Pausar", pauseGame);

        MenuBar rootBar = new MenuBar();
        rootBar.addItem("Juego", bar);

        RootPanel.get(BAR_CONTAINER).add(rootBar);

        canvas.setFocus(true);

        restart.run();

    }

    public void restart(Drawer drawer) {
        if (game != null) {
            game.endGame();
            ticker = null;
        }
        game = new MainLoop(drawer, 0);
        playPause(GwtGame.this.game); // start ticker
    }

    public void playPause(MainLoop game) {
        if (ticker == null) {  // play
            ticker = new Timer() {
                @Override
                public void run() {
                    if (game.gameEnded) cancel();
                    else game.tick();
                }
            };
            ticker.scheduleRepeating(game.delta);
        } else {  // pause
            ticker.cancel();
            ticker = null;
        }
    }

}
