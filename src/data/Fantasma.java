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
public class Fantasma extends Personaje{
    
    private DatosComecocos objetivo;

    public Fantasma(Rejilla rejilla, int x, int y) {
        super(rejilla, x, y);
    }
    
    public void setObjetivo(DatosComecocos pacman){
        objetivo=pacman;
    }

    @Override
    public int mover() {
        if(getOffsetx()==0 && getOffsety()==0){
            int posiblesDirecciones=0;
            if(rejilla.mover(getX(), getY(), Rejilla.ABAJO) && getDireccion()!=Rejilla.ARRIBA)
                posiblesDirecciones|=Rejilla.ABAJO;
            if(rejilla.mover(getX(), getY(), Rejilla.ARRIBA) && getDireccion()!=Rejilla.ABAJO)
                posiblesDirecciones|=Rejilla.ARRIBA;
            if(rejilla.mover(getX(), getY(), Rejilla.DERECHA) && getDireccion()!=Rejilla.IZQUIERDA )
                posiblesDirecciones|=Rejilla.DERECHA;
            if(rejilla.mover(getX(), getY(), Rejilla.IZQUIERDA) && getDireccion()!=Rejilla.DERECHA)
                posiblesDirecciones|=Rejilla.IZQUIERDA;
            
            int direccion=0;
        
            if(Math.random()>0){
                while(direccion==0){
                    double d=Math.random();
                    if(d<0.25)
                        direccion=Rejilla.ABAJO&posiblesDirecciones;
                    else if(d<0.5)
                        direccion=Rejilla.ARRIBA&posiblesDirecciones;
                    else if(d<0.75)
                        direccion=Rejilla.DERECHA&posiblesDirecciones;
                    else
                        direccion=Rejilla.IZQUIERDA&posiblesDirecciones;
                }
            }
            else{
            }
            
            setDireccion(direccion);
        }
        return super.mover(); //To change body of generated methods, choose Tools | Templates.
    }
}
