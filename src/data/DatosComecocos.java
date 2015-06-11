/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import guicomecocos.ComecocosFrame;

/**
 *
 * @author alejandrocq
 */
public class DatosComecocos extends Personaje {
    

    
    public DatosComecocos(Rejilla rejilla, int x, int y) {
        super(rejilla, x, y);
    }
    
    @Override
    public int mover() {
        super.mover();
        int puntos=rejilla.comer(getX(), getY());
        return puntos;
    }
    
}
