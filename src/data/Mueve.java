/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import guicomecocos.LaberintoFrame;

/**
 *
 * @author alejandrocq
 */
public class Mueve extends Thread{
    private DatosComecocos comecocos;
    private boolean finJuego=false, enPausa=false;
    private LaberintoFrame panel;
    
    public Mueve(LaberintoFrame frame, DatosComecocos pacman, int nivel){
        comecocos=pacman;
        panel=frame;
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
                comecocos.mover();
                panel.repaint();
                Thread.sleep(50);
            }
        }
        catch(InterruptedException ex){
            System.out.println("Hilo interrumpido.");
        }
    }
        
    
}
