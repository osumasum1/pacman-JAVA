/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Xismy
 */
public class Personaje {
    private int x, y;
    private int offsetx, offsety;
    private final int MOVIMIENTOS_POR_CELDA;
    private int direccionActual=Rejilla.PARADO, direccionSiguiente=Rejilla.PARADO;
    private Rejilla rejilla;
    
    Personaje(Rejilla rejilla, int x, int y){
        this.rejilla=rejilla;
        this.x=x;
        this.y=y;
        MOVIMIENTOS_POR_CELDA=5;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOffsetx() {
        return offsetx;
    }

    public int getOffsety() {
        return offsety;
    }

    public void setDireccion(int direccionSiguiente) {
        this.direccionSiguiente = direccionSiguiente;
    }
    
    
    
    public void mover(){
        
        int signo=0;
        boolean vertical=false;
       
        
        if(offsetx==0 && offsety==0 && direccionActual!=direccionSiguiente)
            if(rejilla.mover(x, y, direccionSiguiente))
                direccionActual=direccionSiguiente;
     
        switch(direccionActual){
            case Rejilla.IZQUIERDA:
                signo=-1;
                break;
            case Rejilla.DERECHA:
                signo=1;
                break;
            case Rejilla.ARRIBA:
                signo=-1;
                vertical=true;
                break;
            case Rejilla.ABAJO:
                signo=1;
                vertical=true;
                break;
        }
               
        if(!vertical){
            if(offsetx+signo==MOVIMIENTOS_POR_CELDA){
                x++;
                offsetx=0;
            }
            else if(offsetx+signo==-MOVIMIENTOS_POR_CELDA){
                x--;
                offsetx=0;
            }
            else if((offsetx+signo!=1 && offsetx+signo!=-1) || rejilla.mover(x, y, direccionActual)){
                offsetx+=signo;
            }
        }
        else {
            if(offsety+signo==MOVIMIENTOS_POR_CELDA){
                y++;
                offsety=0;
            }
            else if(offsety+signo==-MOVIMIENTOS_POR_CELDA){
                y--;
                offsety=0;
            }
            else if((offsety+signo!=1 && offsety+signo!=-1) || rejilla.mover(x, y, direccionActual)){
                offsety+=signo;
            }
        }
        
        if(x==0 && offsetx==0){
            x=rejilla.getAnchura();
        }
        else if(x==rejilla.getAnchura() && offsetx==0)
            x=0;
    }
}
