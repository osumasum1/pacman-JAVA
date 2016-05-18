
package data;

/**
 * Subclase de Personaje que representa al comecocos.
 * @author Alejandro Castilla Quesada e Ismael Yeste Espín
 */
public class DatosComecocos extends Personaje {
    

   /**
     * Constructor.
     * @param rejilla Objeto Rejilla que representa el laberinto.
     * @param x Coordenada x de la posición inicial.
     * @param y Coordenada y de la posición inicial.
     * @param movsPorCelda  Número de movimientos necesarios para pasar de una celda a otra. 
     * Cuanto mayor sea menor velocidad.
     */
    public DatosComecocos(Rejilla rejilla, int x, int y, int movsPorCelda) {
        super(rejilla, x, y, movsPorCelda);
    }
    
    /**
     * Cuando se llama, se mueve al comecocos en la direccion correspondiente.
     * @return Puntos obtenidos. Puede ser 0, Rejilla.PUNTOS_COCO o Rejilla.PUNTOS_COCO_GRANDE.
     */
    @Override
    public int mover() {
        super.mover();
        int puntos=rejilla.comer(getX(), getY());
        return puntos;
    }
    
}
