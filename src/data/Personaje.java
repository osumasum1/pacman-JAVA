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
    private final int RESOLUCION;
    private int direccionActual=Rejilla.PARADO, direccionSiguiente=Rejilla.PARADO;
    private Rejilla rejilla;
    
    Personaje(Rejilla rejilla){
        this.rejilla=rejilla;
        RESOLUCION=5;
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
        int horizontal=0;
        int vertical=0;
        switch(direccionActual){
            case Rejilla.IZQUIERDA:
                horizontal=-1;
                break;
            case Rejilla.DERECHA:
                horizontal=1;
                break;
            case Rejilla.ARRIBA:
                vertical=-1;
                break;
            case Rejilla.ABAJO:
                vertical=+1;
                break;
        }
        
        if(offsetx+horizontal==RESOLUCION){
            x++;
            offsetx=0;
        }
        else if(offsetx+horizontal==-RESOLUCION){
            x--;
            offsetx=0;
        }
        else if((offsetx+horizontal!=1 && offsetx+horizontal!=-1) || rejilla.mover(x, y, direccionActual)){
            offsetx+=horizontal;
        }
        else if(offsety+vertical==RESOLUCION){
            y++;
            offsety=0;
        }
        else if(offsety+vertical==-RESOLUCION){
            y--;
            offsety=0;
        }
        else if((offsety+vertical!=1 && offsety+vertical!=-1) || rejilla.mover(x, y, direccionActual)){
            offsety+=vertical;
        }
    }
}
