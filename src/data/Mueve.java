/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import guicomecocos.ComecocosFrame;
import guicomecocos.LaberintoFrame;

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
    private int periodo;

    
    public Mueve(ComecocosFrame cf, LaberintoFrame lf, DatosComecocos pacman, Fantasma[] fantasmas, int nivel){
        periodo=50-nivel*10;
        comecocos=pacman;
        this.fantasmas=fantasmas;
        frame=cf;
        panel=lf;
    }
    
    public void moverComecocos(int direccion){
        comecocos.setDireccion(direccion);
    }
   
    
    @Override
    public void run() {
        try{
            while(!finJuego){
                synchronized(this){
                    while(enPausa)
                        wait();
                }
                int puntos=comecocos.mover();
                puntuacion+=puntos;
                frame.puntuacion(puntuacion);
                if(puntos==10){
                    modoDios=100;
                }
                
                if(modoDios!=-1){
                    if(modoDios==0)
                        panel.modoDios(false);
                    else if(modoDios==100)
                        panel.modoDios(true);
                     modoDios--;
                }
                
                for(Fantasma fantasma:fantasmas)
                    fantasma.mover();
                
                panel.repaint();
                Thread.sleep(periodo);
            }
        }
        catch(InterruptedException ex){
            System.out.println("Hilo interrumpido.");
        }
    }
        
    
}
