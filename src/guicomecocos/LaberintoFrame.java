/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guicomecocos;

import data.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author alejandrocq
 */
public class LaberintoFrame extends javax.swing.JPanel {

    private ComecocosFrame comecocosFrame;
    private Rejilla laberinto = new Rejilla();
    private int anchoCelda = -1;
    
    /**
     * Creates new form Laberinto
     */
    public LaberintoFrame() {
        initComponents();
    }
    
    public LaberintoFrame (ComecocosFrame c) {
        this();
        comecocosFrame = c;
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
        
        
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (anchoCelda == -1) {
            
            try {
                anchoCelda = Math.min(getWidth() / comecocosFrame.getRejilla().getAnchura(), (getHeight() - 10) / comecocosFrame.getRejilla().getAltura());
                g.fillRect(0, 0, getWidth(), getHeight());
            } catch (Exception e) {
            }
            
        }
        
        dibujaLaberinto(g);
        
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

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
