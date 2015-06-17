/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import guicomecocos.ComecocosFrame;
import guicomecocos.LaberintoFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author alejandrocq
 */
public class Mueve extends Thread{
    private DatosComecocos comecocos;
    private Fantasma[] fantasmas;
    private boolean finJuego=false, enPausa=false;
    private ComecocosFrame frame;
    private LaberintoFrame panel;
    private int puntuacion=0;
    private int modoDios=0;
    private int maxPuntos;
    private int deltaT;

    
    public Mueve(ComecocosFrame cf, LaberintoFrame lf, DatosComecocos pacman, Fantasma[] fantasmas, 
            int maxPuntos, int nivel){
        comecocos=pacman;
        this.fantasmas=fantasmas;
        frame=cf;
        panel=lf;
        this.maxPuntos=maxPuntos;
        deltaT=50-nivel*5;
    }
    
    public void moverComecocos(int direccion){
        comecocos.setDireccion(direccion);
    }
    
    public synchronized void pausa(){
        enPausa=true;
    }
    
    public synchronized void reanudar(){
        enPausa=false;
    }
    
    @Override
    public void run() {
        try{
            while(!finJuego){
                panel.repaint();
                synchronized(this){
                    while(enPausa)
                        wait();
                }
                int puntos=comecocos.mover();
                puntuacion+=puntos;
                frame.puntuacion(puntuacion);
                if(puntos==Rejilla.PUNTOS_COCO_GRANDE){
                    modoDios=10000/deltaT;
                }
                
                if(modoDios!=-1){
                    if(modoDios==0)
                        panel.modoDios(false);
                    else if(modoDios==10000/deltaT)
                        panel.modoDios(true);
                     modoDios--;
                }
                
                for(Fantasma fantasma:fantasmas){
                    int resultado=fantasma.mover(modoDios!=-1);
                    if(resultado==Fantasma.CHOQUE_COMECOCOS){
                        if(modoDios!=-1){
                            fantasma.reiniciar();
                        }
                        else{
                            JOptionPane.showMessageDialog(panel, "HAS PERDIDO");
                            finJuego=true;
                            break;
                        }
                    }
                }
                
                if(puntuacion==maxPuntos){
                    JOptionPane.showMessageDialog(panel, "¡HAS GANADO!");
                    finJuego=true;
                }
                
                Thread.sleep(deltaT);
            }
        }
        catch(InterruptedException ex){
            System.out.println("Hilo interrumpido.");
        }
    }
        
    
}
