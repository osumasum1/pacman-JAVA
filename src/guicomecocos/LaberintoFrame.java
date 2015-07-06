/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guicomecocos;

import data.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author alejandrocq
 */
public class LaberintoFrame extends javax.swing.JPanel {

    private ComecocosFrame comecocosFrame;
    private Rejilla laberinto = new Rejilla();
    private DatosComecocos pacman;
    private Fantasma[] fantasmas;
    private final Color[] colorFantasmas = {Color.RED,Color.BLUE,Color.PINK,Color.CYAN};
    private Mueve movimiento;
    private int anchoCelda = -1;
    private boolean modoDios;
    
    /**
     * Creates new form Laberinto
     */
    public LaberintoFrame() {
        initComponents();
    }
    
    public LaberintoFrame (ComecocosFrame c) {
        this();
        comecocosFrame = c;
        reiniciar();
    }
    
    public void reiniciar(){
        if(movimiento!=null)
            movimiento.finalizar();
        laberinto=new Rejilla();
        pacman=new DatosComecocos(laberinto,1, 1, 5);
        fantasmas=new Fantasma[4];
        int fantasma=0;
        for(int i=0;i<laberinto.getAnchura();i++){
            for(int j=0;j<laberinto.getAltura();j++)
                if(laberinto.getCeldaAt(i, j)==Rejilla.FANTASMA){
                    fantasmas[fantasma]=new Fantasma(laberinto, i, j, 8);
                    fantasmas[fantasma++].setObjetivo(pacman);
                }
        }
        movimiento=new Mueve(comecocosFrame, this, pacman, fantasmas, laberinto.getMaxPuntos(), 0);
        movimiento.start();
    }
    
    public void pausa(){
        if(movimiento.enPausa())
            movimiento.reanudar();
        else
            movimiento.pausa();
    }
    
    public void modoDios(boolean activar){
        modoDios=activar;
    }
    
    public void dibujaLaberinto (java.awt.Graphics g) {
        
        int x, y;
        int xoffset = (getWidth()-laberinto.getAnchura()*anchoCelda)/2;
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        for (x=0 ; x<laberinto.getAnchura() ; x++) {
            for (y=0 ; y<laberinto.getAltura() ; y++) {
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.RECTANGULOARRIBA) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda, y*anchoCelda, anchoCelda, anchoCelda/2);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.RECTANGULOABAJO) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda, y*anchoCelda+anchoCelda/2, anchoCelda, anchoCelda/2);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.RECTANGULODERECHA) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda+anchoCelda/2, y*anchoCelda, anchoCelda/2, anchoCelda);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.RECTANGULOIZQUIERDA) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda, y*anchoCelda, anchoCelda/2, anchoCelda);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAABAJODERECHA) {
                    g.setColor(Color.BLUE);
                    g.fillPolygon(new int[] {xoffset+(x+1)*anchoCelda,xoffset+(x*anchoCelda+anchoCelda/2), xoffset+(x+1)*anchoCelda}, new int[] {(y+1)*anchoCelda,(y+1)*anchoCelda, y*anchoCelda+anchoCelda/2}, 3);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAABAJOIZQUIERDA) {
                    g.setColor(Color.BLUE);
                    g.fillPolygon(new int[] {xoffset+x*anchoCelda,xoffset+x*anchoCelda, xoffset+(x*anchoCelda+anchoCelda/2)}, new int[] {(y+1)*anchoCelda,y*anchoCelda+anchoCelda/2, (y+1)*anchoCelda}, 3);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAARRIBAIZQUIERDA) {
                    g.setColor(Color.BLUE);
                    g.fillPolygon(new int[] {xoffset+x*anchoCelda,xoffset+x*anchoCelda, xoffset+(x*anchoCelda+anchoCelda/2)}, new int[] {y*anchoCelda,y*anchoCelda+anchoCelda/2, y*anchoCelda}, 3);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAARRIBADERECHA) {
                    g.setColor(Color.BLUE);
                    g.fillPolygon(new int[] {xoffset+(x*anchoCelda+anchoCelda/2),xoffset+(x+1)*anchoCelda, xoffset+(x+1)*anchoCelda}, new int[] {y*anchoCelda,y*anchoCelda, y*anchoCelda+anchoCelda/2}, 3);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAABAJOIZQUIERDAGRANDE) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda, y*anchoCelda, anchoCelda, anchoCelda);
                    g.setColor(Color.BLACK);
                    g.fillPolygon(new int[] {xoffset+x*anchoCelda,xoffset+x*anchoCelda, xoffset+(x*anchoCelda+anchoCelda/2)}, new int[] {(y+1)*anchoCelda,y*anchoCelda+anchoCelda/2, (y+1)*anchoCelda}, 3);
                    
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAABAJODERECHAGRANDE) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda, y*anchoCelda, anchoCelda, anchoCelda);
                    g.setColor(Color.BLACK);
                    g.fillPolygon(new int[] {xoffset+(x+1)*anchoCelda,xoffset+(x*anchoCelda+anchoCelda/2), xoffset+(x+1)*anchoCelda}, new int[] {(y+1)*anchoCelda,(y+1)*anchoCelda, y*anchoCelda+anchoCelda/2}, 3);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAARRIBADERECHAGRANDE) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda, y*anchoCelda, anchoCelda, anchoCelda);
                    g.setColor(Color.BLACK);
                    g.fillPolygon(new int[] {xoffset+(x*anchoCelda+anchoCelda/2),xoffset+(x+1)*anchoCelda, xoffset+(x+1)*anchoCelda}, new int[] {y*anchoCelda,y*anchoCelda, y*anchoCelda+anchoCelda/2}, 3);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.ESQUINAARRIBAIZQUIERDAGRANDE) {
                    g.setColor(Color.BLUE);
                    g.fillRect(xoffset+x*anchoCelda, y*anchoCelda, anchoCelda, anchoCelda);
                    g.setColor(Color.BLACK);
                    g.fillPolygon(new int[] {xoffset+x*anchoCelda,xoffset+x*anchoCelda, xoffset+(x*anchoCelda+anchoCelda/2)}, new int[] {y*anchoCelda,y*anchoCelda+anchoCelda/2, y*anchoCelda}, 3);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.PUNTOPEQUEÑO) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(xoffset+x*anchoCelda+anchoCelda/2, y*anchoCelda+anchoCelda/2, anchoCelda/5, anchoCelda/5);
                }
                
                if (laberinto.getCeldaAt(x, y) == Rejilla.PUNTOGRANDE) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(xoffset+x*anchoCelda+anchoCelda/2, y*anchoCelda+anchoCelda/2, anchoCelda/3, anchoCelda/3);
                }
            }
        }
        
    }
    
    public void dibujaPacman (java.awt.Graphics g) {
        int xoffset = (getWidth()-laberinto.getAnchura()*anchoCelda)/2;
        int xoffsetmov = pacman.getOffsetx()*anchoCelda/pacman.getMovimientosCelda();
        int yoffsetmov = pacman.getOffsety()*anchoCelda/pacman.getMovimientosCelda();
        if(modoDios)
            g.setColor(new Color((int)(Math.random()*65000)));
        else
            g.setColor(Color.YELLOW);
        int direccionActual = pacman.getDireccion();
        
        if (direccionActual == Rejilla.DERECHA) 
            g.fillArc(xoffset+xoffsetmov+pacman.getX()*anchoCelda, yoffsetmov+pacman.getY()*anchoCelda, 
                    anchoCelda+2, anchoCelda+2, 
                    (int) (22-22*Math.sin((2*Math.PI*pacman.getOffsetx())/pacman.getMovimientosCelda())),
                    (int) (315+45*Math.sin((2*Math.PI*pacman.getOffsetx())/pacman.getMovimientosCelda())));
        
        if (direccionActual == Rejilla.IZQUIERDA) 
            g.fillArc(xoffset+xoffsetmov+pacman.getX()*anchoCelda, yoffsetmov+pacman.getY()*anchoCelda, 
                    anchoCelda+2, anchoCelda+2, 
                    (int) (202-22*Math.sin((2*Math.PI*pacman.getOffsetx())/pacman.getMovimientosCelda())),
                    (int) (315+45*Math.sin((2*Math.PI*pacman.getOffsetx())/pacman.getMovimientosCelda())));
        
        if (direccionActual == Rejilla.ARRIBA) 
            g.fillArc(xoffset+xoffsetmov+pacman.getX()*anchoCelda, yoffsetmov+pacman.getY()*anchoCelda, 
                    anchoCelda+2, anchoCelda+2, 
                    (int) (112-22*Math.sin((2*Math.PI*pacman.getOffsety())/pacman.getMovimientosCelda())),
                    (int) (315+45*Math.sin((2*Math.PI*pacman.getOffsety())/pacman.getMovimientosCelda())));
        
        if (direccionActual == Rejilla.ABAJO) 
            g.fillArc(xoffset+xoffsetmov+pacman.getX()*anchoCelda, yoffsetmov+pacman.getY()*anchoCelda, 
                    anchoCelda+2, anchoCelda+2, 
                    (int) (292-22*Math.sin((2*Math.PI*pacman.getOffsety())/pacman.getMovimientosCelda())),
                    (int) (315+45*Math.sin((2*Math.PI*pacman.getOffsety())/pacman.getMovimientosCelda())));
        
    }
    
    public void dibujaFantasmas(Graphics g){
        int xoffset = (getWidth()-laberinto.getAnchura()*anchoCelda)/2;
        
        for(int i=0 ; i<fantasmas.length ; i++){
            int xoffsetmov = fantasmas[i].getOffsetx()*anchoCelda/fantasmas[i].getMovimientosCelda();
            int yoffsetmov = fantasmas[i].getOffsety()*anchoCelda/fantasmas[i].getMovimientosCelda();
            if(modoDios)
            g.setColor(new Color((int)(Math.random()*65000)));
            else
            g.setColor(colorFantasmas[i]);
            
            //Head
            g.fillArc(xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda, yoffsetmov+fantasmas[i].getY()*anchoCelda, anchoCelda, anchoCelda, 0, 180);
            //Legs (poligon)
            int point1x = xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda; 
            int point1y = yoffsetmov+fantasmas[i].getY()*anchoCelda+anchoCelda/2;
            int point2x = xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda;
            int point2y = yoffsetmov+fantasmas[i].getY()*anchoCelda+anchoCelda;
            int point3x = xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+(2*anchoCelda/5);
            int point3y = yoffsetmov+fantasmas[i].getY()*anchoCelda+(4*anchoCelda/5);
            int point4x = xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+anchoCelda/2;
            int point4y = yoffsetmov+fantasmas[i].getY()*anchoCelda+anchoCelda;
            int point5x = xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+(4*anchoCelda/5);
            int point5y = yoffsetmov+fantasmas[i].getY()*anchoCelda+(4*anchoCelda/5);
            int point6x = xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+anchoCelda;
            int point6y = yoffsetmov+fantasmas[i].getY()*anchoCelda+anchoCelda;
            int point7x = xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+anchoCelda;
            int point7y = yoffsetmov+fantasmas[i].getY()*anchoCelda+anchoCelda/2;
            
            int[] xPoints = {point1x, point2x, point3x, point4x, point5x, point6x, point7x};
            int[] yPoints = {point1y, point2y, point3y, point4y, point5y, point6y, point7y};
            
            g.fillPolygon(xPoints, yPoints, 7);
            
            //Eyes
            
            g.setColor(Color.WHITE);
            g.fillOval(xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+(2*anchoCelda/8), yoffsetmov+fantasmas[i].getY()*anchoCelda+(2*anchoCelda/8), anchoCelda/4, anchoCelda/4);
            g.fillOval(xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+(5*anchoCelda/8), yoffsetmov+fantasmas[i].getY()*anchoCelda+(2*anchoCelda/8), anchoCelda/4, anchoCelda/4);
            g.setColor(Color.BLACK);
            g.fillOval(xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+(2*anchoCelda/8)+(2*anchoCelda/16), yoffsetmov+fantasmas[i].getY()*anchoCelda+(2*anchoCelda/8)+(2*anchoCelda/24), anchoCelda/6, anchoCelda/6);
            g.fillOval(xoffset+xoffsetmov+fantasmas[i].getX()*anchoCelda+(5*anchoCelda/8)+(5*anchoCelda/32), yoffsetmov+fantasmas[i].getY()*anchoCelda+(2*anchoCelda/8)+(2*anchoCelda/24), anchoCelda/6, anchoCelda/6);
            
            
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try{
            if (anchoCelda == -1) {
                    anchoCelda = Math.min(getWidth() / laberinto.getAnchura(), (getHeight() - 10) / laberinto.getAltura());
                    g.fillRect(0, 0, getWidth(), getHeight());

            }

            dibujaLaberinto(g);
            dibujaPacman(g);
            dibujaFantasmas(g);
        }
        catch(NullPointerException ex){
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(550, 550));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LaberintoFrame.this.mouseEntered(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                controlTeclado(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void controlTeclado(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_controlTeclado
        switch (evt.getKeyCode()){
            case KeyEvent.VK_UP:
                movimiento.moverComecocos(Rejilla.ARRIBA);
                break;
            case KeyEvent.VK_DOWN:
                movimiento.moverComecocos(Rejilla.ABAJO);
                break;
                case KeyEvent.VK_LEFT:
                movimiento.moverComecocos(Rejilla.IZQUIERDA);
                break;
            case KeyEvent.VK_RIGHT:
                movimiento.moverComecocos(Rejilla.DERECHA);
                break;
            case KeyEvent.VK_SPACE:
                if(movimiento.enPausa())
                    movimiento.reanudar();
                else
                    movimiento.pausa();
                break;
        }
    }//GEN-LAST:event_controlTeclado

    private void mouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseEntered
        requestFocus();
    }//GEN-LAST:event_mouseEntered

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
