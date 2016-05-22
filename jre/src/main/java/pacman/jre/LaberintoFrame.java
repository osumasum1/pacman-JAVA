package pacman.jre;

import static pacman.jre.LaberintoFrame.Modo.DIOS;
import static java.lang.Math.sin;

import pacman.shared.DatosComecocos;
import pacman.shared.Fantasma;
import pacman.shared.Mueve;
import pacman.shared.Rejilla;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/** Clase que contiene todos los métodos necesarios para dibujar cada uno de los elementos del juego. */
public class LaberintoFrame extends JPanel {
    public enum Modo {NORMAL, DIOS}

    private ComecocosFrame comecocos;
    private Rejilla laberinto = new Rejilla();
    private DatosComecocos pacman;
    private Fantasma[] fantasmas;
    private final Color[] colorFantasmas = { Color.RED, Color.BLUE, Color.PINK, Color.CYAN };
    private Mueve movimiento;
    private Modo modo;

    public LaberintoFrame(ComecocosFrame comecocos) {
        this.comecocos = comecocos; // TODO remove cyclic dependency
        setPreferredSize(ComecocosFrame.PREFERRED_SIZE);
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                requestFocus();
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_UP: movimiento.moverComecocos(Rejilla.ARRIBA); break;
                    case KeyEvent.VK_DOWN: movimiento.moverComecocos(Rejilla.ABAJO); break;
                    case KeyEvent.VK_LEFT: movimiento.moverComecocos(Rejilla.IZQUIERDA); break;
                    case KeyEvent.VK_RIGHT: movimiento.moverComecocos(Rejilla.DERECHA); break;
                    case KeyEvent.VK_SPACE: alternarPausa(); break;
                }
            }
        });

        reiniciarPartida();
    }
    public void alternarPausa() {
        if (movimiento.enPausa()) movimiento.reanudar();
        else movimiento.pausa();
    }

    public void reiniciarPartida() {
        if (movimiento != null) movimiento.finalizar();
        laberinto = new Rejilla();
        pacman = new DatosComecocos(laberinto, 1, 1, 5);
        fantasmas = new Fantasma[4];
        int fantasma = 0;
        for (int i = 0; i < laberinto.getAnchura(); i++) {
            for (int j = 0; j < laberinto.getAltura(); j++)
                if (laberinto.getCeldaAt(i, j) == Rejilla.FANTASMA) {
                    fantasmas[fantasma] = new Fantasma(laberinto, i, j, 8);
                    fantasmas[fantasma++].setObjetivo(pacman);
                }
        }
        movimiento = new Mueve(comecocos, this, pacman, fantasmas, laberinto.getMaxPuntos(), 0);
        movimiento.start();
    }

    public void modo(Modo modo) {
        this.modo = modo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            int cellWidth = Math.min(getWidth() / laberinto.getAnchura(), getHeight() / laberinto.getAltura());
            dibujaLaberinto(g, cellWidth);
            dibujaPacman(g, cellWidth);
            dibujaFantasmas(g, cellWidth);
        } catch (NullPointerException e) {
            Logger.getLogger(LaberintoFrame.class.getName()).log(Level.SEVERE, "error painting component: " + e, e);
        }
    }

    private void dibujaLaberinto(Graphics g, int cw) {
        int x, y, ϕ /*offset*/ = (getWidth() - laberinto.getAnchura() * cw) / 2;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (x = 0; x < laberinto.getAnchura(); x++) {
            for (y = 0; y < laberinto.getAltura(); y++) {
                switch (laberinto.getCeldaAt(x, y)) {
                    case Rejilla.RECTANGULOARRIBA:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw / 2);
                        break;
                    case Rejilla.RECTANGULOABAJO:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw + cw / 2, cw, cw / 2);
                        break;
                    case Rejilla.RECTANGULODERECHA:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw + cw / 2, y * cw, cw / 2, cw);
                        break;
                    case Rejilla.RECTANGULOIZQUIERDA:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw / 2, cw);
                        break;
                    case Rejilla.ESQUINAABAJODERECHA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + (x + 1) * cw, ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw },
                                new int[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Rejilla.ESQUINAABAJOIZQUIERDA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Rejilla.ESQUINAARRIBAIZQUIERDA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Rejilla.ESQUINAARRIBADERECHA:
                        g.setColor(Color.BLUE);
                        g.fillPolygon(
                                new int[] { ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw, ϕ + (x + 1) * cw },
                                new int[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Rejilla.ESQUINAABAJOIZQUIERDAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { (y + 1) * cw, y * cw + cw / 2, (y + 1) * cw },
                                3);
                        break;
                    case Rejilla.ESQUINAABAJODERECHAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + (x + 1) * cw, ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw },
                                new int[] { (y + 1) * cw, (y + 1) * cw, y * cw + cw / 2 },
                                3);
                        break;
                    case Rejilla.ESQUINAARRIBADERECHAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + (x * cw + cw / 2), ϕ + (x + 1) * cw, ϕ + (x + 1) * cw },
                                new int[] { y * cw, y * cw, y * cw + cw / 2 }, 3);
                        break;
                    case Rejilla.ESQUINAARRIBAIZQUIERDAGRANDE:
                        g.setColor(Color.BLUE);
                        g.fillRect(ϕ + x * cw, y * cw, cw, cw);
                        g.setColor(Color.BLACK);
                        g.fillPolygon(
                                new int[] { ϕ + x * cw, ϕ + x * cw, ϕ + (x * cw + cw / 2) },
                                new int[] { y * cw, y * cw + cw / 2, y * cw }, 3);
                        break;
                    case Rejilla.PUNTOPEQUEÑO:
                        g.setColor(Color.YELLOW);
                        g.fillOval(ϕ + x * cw + cw / 2, y * cw + cw / 2, cw / 5, cw / 5);
                        break;
                    case Rejilla.PUNTOGRANDE:
                        g.setColor(Color.YELLOW);
                        g.fillOval(ϕ + x * cw + cw / 2, y * cw + cw / 2, cw / 3, cw / 3);
                        break;
                }
            }
        }
    }

    private void dibujaPacman(Graphics g, int cw) {
        int xoffset = (getWidth() - laberinto.getAnchura() * cw) / 2;
        int xoffsetmov = pacman.getOffsetx() * cw / pacman.getMovimientosCelda();
        int yoffsetmov = pacman.getOffsety() * cw / pacman.getMovimientosCelda();
        g.setColor(modo == DIOS ? new Color((int) (Math.random() * 65000)) : Color.YELLOW);

        switch (pacman.getDireccion()) {
            case Rejilla.DERECHA:
                g.fillArc(xoffset + xoffsetmov + pacman.getX() * cw, yoffsetmov + pacman.getY() * cw, cw + 2, cw + 2,
                        (int) (22 - 22 * sin((2 * Math.PI * pacman.getOffsetx()) / pacman.getMovimientosCelda())),
                        (int) (315 + 45 * sin((2 * Math.PI * pacman.getOffsetx()) / pacman.getMovimientosCelda())));
                break;
            case Rejilla.IZQUIERDA:
                g.fillArc(xoffset + xoffsetmov + pacman.getX() * cw, yoffsetmov + pacman.getY() * cw, cw + 2, cw + 2,
                        (int) (202 - 22 * sin((2 * Math.PI * pacman.getOffsetx()) / pacman.getMovimientosCelda())),
                        (int) (315 + 45 * sin((2 * Math.PI * pacman.getOffsetx()) / pacman.getMovimientosCelda())));
                break;
            case Rejilla.ARRIBA:
                g.fillArc(xoffset + xoffsetmov + pacman.getX() * cw, yoffsetmov + pacman.getY() * cw, cw + 2, cw + 2,
                        (int) (112 - 22 * sin((2 * Math.PI * pacman.getOffsety()) / pacman.getMovimientosCelda())),
                        (int) (315 + 45 * sin((2 * Math.PI * pacman.getOffsety()) / pacman.getMovimientosCelda())));
                break;
            case Rejilla.ABAJO:
                g.fillArc(xoffset + xoffsetmov + pacman.getX() * cw, yoffsetmov + pacman.getY() * cw, cw + 2, cw + 2,
                        (int) (292 - 22 * sin((2 * Math.PI * pacman.getOffsety()) / pacman.getMovimientosCelda())),
                        (int) (315 + 45 * sin((2 * Math.PI * pacman.getOffsety()) / pacman.getMovimientosCelda())));
                break;
        }
    }

    private void dibujaFantasmas(Graphics g, int cw) {
        int xoffset = (getWidth() - laberinto.getAnchura() * cw) / 2;

        for (int i = 0; i < fantasmas.length; i++) {
            int xoffsetmov = fantasmas[i].getOffsetx() * cw / fantasmas[i].getMovimientosCelda();
            int yoffsetmov = fantasmas[i].getOffsety() * cw / fantasmas[i].getMovimientosCelda();
            g.setColor(modo == DIOS ? new Color((int) (Math.random() * 65000)) : colorFantasmas[i]);

            //Head
            g.fillArc(xoffset + xoffsetmov + fantasmas[i].getX() * cw,
                    yoffsetmov + fantasmas[i].getY() * cw, cw, cw, 0, 180);

            //Legs
            int[] xPoints = {
                    xoffset + xoffsetmov + fantasmas[i].getX() * cw,
                    xoffset + xoffsetmov + fantasmas[i].getX() * cw,
                    xoffset + xoffsetmov + fantasmas[i].getX() * cw + (2 * cw / 5),
                    xoffset + xoffsetmov + fantasmas[i].getX() * cw + cw / 2,
                    xoffset + xoffsetmov + fantasmas[i].getX() * cw + (4 * cw / 5),
                    xoffset + xoffsetmov + fantasmas[i].getX() * cw + cw,
                    xoffset + xoffsetmov + fantasmas[i].getX() * cw + cw
            };
            int[] yPoints = {
                    yoffsetmov + fantasmas[i].getY() * cw + cw / 2,
                    yoffsetmov + fantasmas[i].getY() * cw + cw,
                    yoffsetmov + fantasmas[i].getY() * cw + (4 * cw / 5),
                    yoffsetmov + fantasmas[i].getY() * cw + cw,
                    yoffsetmov + fantasmas[i].getY() * cw + (4 * cw / 5),
                    yoffsetmov + fantasmas[i].getY() * cw + cw,
                    yoffsetmov + fantasmas[i].getY() * cw + cw / 2
            };
            g.fillPolygon(xPoints, yPoints, 7);

            //Eyes
            g.setColor(Color.WHITE);
            g.fillOval(xoffset + xoffsetmov + fantasmas[i].getX() * cw + (2 * cw / 8),
                    yoffsetmov + fantasmas[i].getY() * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            g.fillOval(xoffset + xoffsetmov + fantasmas[i].getX() * cw + (5 * cw / 8),
                    yoffsetmov + fantasmas[i].getY() * cw + (2 * cw / 8), cw / 4,
                    cw / 4);
            g.setColor(Color.BLACK);
            g.fillOval(xoffset + xoffsetmov + fantasmas[i].getX() * cw + (2 * cw / 8) + (2 * cw / 16),
                    yoffsetmov + fantasmas[i].getY() * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
            g.fillOval(xoffset + xoffsetmov + fantasmas[i].getX() * cw + (5 * cw / 8) + (5 * cw / 32),
                    yoffsetmov + fantasmas[i].getY() * cw + (2 * cw / 8) + (2 * cw / 24),
                    cw / 6, cw / 6);
        }
    }
}
