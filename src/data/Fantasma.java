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
    public static final int CHOQUE_COMECOCOS=1;
    

    public Fantasma(Rejilla rejilla, int x, int y, int movsPorCelda) {
        super(rejilla, x, y, movsPorCelda);
    }
    
    public void setObjetivo(DatosComecocos pacman){
        objetivo=pacman;
    }
    


    public int mover(boolean modoDios) {
        
        int dx=Math.abs((getX()-objetivo.getX())*getMovimientosCelda()+getOffsetx()-objetivo.getOffsetx());
        int dy=Math.abs((getY()-objetivo.getY())*getMovimientosCelda()+getOffsety()-objetivo.getOffsety());
        
        if(dx<getMovimientosCelda() && dy<getMovimientosCelda())
            return CHOQUE_COMECOCOS;
        
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
        
            if(Math.random()>0.75){
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
                int[] prioridad={Rejilla.ABAJO, Rejilla.DERECHA, Rejilla.ARRIBA, Rejilla.IZQUIERDA};
                
                if(getX()>objetivo.getX()){
                    prioridad[1]=Rejilla.IZQUIERDA;
                    prioridad[3]=Rejilla.DERECHA;
                }
                if(getY()>objetivo.getY()){
                    prioridad[0]=Rejilla.ARRIBA;
                    prioridad[2]=Rejilla.ABAJO;
                }
                   
                int aux;
                
                double prioridadVertical=0.5;
                
                if(prioridad[0]!=getDireccion())
                    prioridadVertical=0.75;
                else if(prioridad[1]==getDireccion())
                    prioridadVertical=0.25;
                
                if(Math.random()<prioridadVertical){
                    aux=prioridad[0];
                    prioridad[0]=prioridad[1];
                    prioridad[1]=aux;
                    aux=prioridad[2];
                    prioridad[2]=prioridad[3];
                    prioridad[3]=aux;
                }
                
                int i=0;
                
                if(modoDios)
                    i=3;
                
                while(direccion==0){
                    direccion=prioridad[i]&posiblesDirecciones;
                    if(modoDios)
                        i--;
                    else
                        i++;
                }
            }
            
            setDireccion(direccion);
        }
        
        return super.mover(); //To change body of generated methods, choose Tools | Templates.
    }
}
