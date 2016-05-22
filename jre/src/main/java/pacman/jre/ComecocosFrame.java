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

public class ComecocosFrame extends JFrame {
    static final Dimension PREFERRED_SIZE = new Dimension(420, 509);

    public static void main(String args[]) {
        invokeLater(() -> new ComecocosFrame().setVisible(true));
    }

    private JMenuItem score;

    private ComecocosFrame() {
        setTitle("Pacman");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(PREFERRED_SIZE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

        LaberintoFrame laberinto = new LaberintoFrame(this);

        setJMenuBar(apply(new JMenuBar(), menuBar -> {
            menuBar.add(apply(new JMenu(), menuJuego -> {
                menuJuego.setText("Juego");

                JMenuItem juegoNuevo = new JMenuItem();
                juegoNuevo.setText("Nuevo");
                juegoNuevo.addActionListener(evt -> laberinto.reiniciarPartida());
                menuJuego.add(juegoNuevo);

                JMenuItem juegoPausar = new JMenuItem();
                juegoPausar.setText("Pausar");
                juegoPausar.addActionListener(evt -> laberinto.alternarPausa());
                menuJuego.add(juegoPausar);

                JMenuItem juegoSalir = new JMenuItem();
                juegoSalir.setText("Salir");
                juegoSalir.addActionListener(evt -> System.exit(0));
                menuJuego.add(juegoSalir);
            }));

            score = new JMenuItem();
            score.setText("Puntuación : 0");
            menuBar.add(score);
        }));

        getContentPane().add(laberinto);

        pack();
    }

    public void puntuacion(int score) {
        if (this.score != null) this.score.setText("Puntuación: " + score);
    }

    public static <T> T apply(T t, Consumer<T> fn) {
        fn.accept(t);
        return t;
    }
}
