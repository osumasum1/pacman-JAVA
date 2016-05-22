
package pacman.shared;

/**
 * Contiene una matriz de caracteres que representa un laberinto.
 * @author Alejandro Castilla Quesada e Ismael Yeste Espín
 */

public class Rejilla {
   
    private static final String REJILLA[]={
        "1AAAAAAAAAAAA21AAAAAAAAAAAA2",
        "I............DI............D",
        "I.5BB6.5BBB6.DI.5BBB6.5BB6.D",
        "IoD  I.D   I.DI.D   I.D  IoD",
        "I.7AA8.7AAA8.78.7AAA8.7AA8.D",
        "I..........................D",
        "I.5BB6.56.5BBBBBB6.56.5BB6.D",
        "I.7AA8.DI.7AA21AA8.DI.7AA8.D",
        "I......DI....DI....DI......D",
        "3BBBB6.D3BB6 DI 5BB4I.5BBBB4",
        "     I.D1AA8 78 7AA2I.D     ",
        "     I.DI          DI.D     ",
        "     I.DI 5B____B6 DI.D     ",
        "AAAAA8.78 D      I 78.7AAAAA",
        "      .   D ---- I   .      ",
        "BBBBB6.56 D FFFF I 56.5BBBBB",
        "     I.DI 7AAAAAA8 DI.D     ",
        "     I.DI          DI.D     ",
        "     I.DI 5BBBBBB6 DI.D     ",
        "1AAAA8.78 7AA21AA8 78.7AAAA2",
        "I............DI............D",
        "I.5BB6.5BBB6.DI.5BBB6.5BB6.D",
        "I.7A2I.7AAA8.78.7AAA8.D1A8.D",
        "Io..DI................DI..oD",
        "3B6.DI.56.5BBBBBB6.56.DI.5B4",
        "1A8.78.DI.7AA21AA8.DI.78.7A2",
        "I......DI....DI....DI......D",
        "I.5BBBB43BB6.DI.5BB43BBBB6.D",
        "I.7AAAAAAAA8.78.7AAAAAAAA8.D",
        "I..........................D",
        "3BBBBBBBBBBBBBBBBBBBBBBBBBB4"
    };
    
    /**
     * Constantes de dirección.
     */
    public static final int IZQUIERDA=0x1, DERECHA=0x10, ARRIBA=0x100, ABAJO=0x1000;
    /**
     * Constantes de puntuación.
     */
    public static final int PUNTOS_COCO=10, PUNTOS_COCO_GRANDE=50;
    /**
     * Constantes para definir cada una de las celdas del mapa.
     */
    public static final char RECTANGULOARRIBA='A', RECTANGULOABAJO='B', RECTANGULODERECHA='D', RECTANGULOIZQUIERDA='I', ESQUINAABAJODERECHA='5', ESQUINAARRIBADERECHA='7'
            , ESQUINAABAJOIZQUIERDA='6', ESQUINAARRIBAIZQUIERDA='8', ESQUINAARRIBADERECHAGRANDE='3', ESQUINAABAJODERECHAGRANDE='1', ESQUINAARRIBAIZQUIERDAGRANDE='4'
            , ESQUINAABAJOIZQUIERDAGRANDE='2', PUNTOPEQUEÑO='.', PUNTOGRANDE='o', MURO='_', FANTASMA='F',LIBRE=' ';
    
    
    private char[][] laberinto;
    private int maxPuntos;
    
    
    
    
   /**
    * Crea un array de caracteres que contiene el mapa del juego.
    * @param mapa Array en el que cada caracter representa una celda 
    * del mapa.
    */
   public Rejilla(String[] mapa){
       int filas=mapa.length;
       laberinto=new char[filas][];
       for(int i=0;i<mapa.length;i++){
           laberinto[i]=mapa[i].toCharArray();
       }
       
       for(char[] fila:laberinto){
           for(char elemento:fila){
               if(elemento==PUNTOPEQUEÑO)
                   maxPuntos+=PUNTOS_COCO;
               else if(elemento==PUNTOGRANDE)
                   maxPuntos+=PUNTOS_COCO_GRANDE;
           }
       }
   }
   
   /**
    * Se usa para comprbar que se ha ganado.
    * @return Maxima puntuación obtenible.
    */
    public int getMaxPuntos() {
        return maxPuntos;
    }
     
   /**
    * Crea un array de caracteres que contiene el mapa del juego.
    * Utiliza el mapa por defecto.
    */
   public Rejilla(){
        this(REJILLA);
    }
   
   /**
    * Comer el coco de la celda actual.
    * @param fila Fila actual.
    * @param columna Columna actual.
    * @return 0 si no había coco, 1 si había un coco pequeño y 2 si había un coco grande.
    */
   public int comer(int fila, int columna){
       if(laberinto[columna][fila]==PUNTOPEQUEÑO){
           laberinto[columna][fila]=LIBRE;
           return PUNTOS_COCO;
       }
       else if(laberinto[columna][fila]==PUNTOGRANDE){
           laberinto[columna][fila]=LIBRE;
           return PUNTOS_COCO_GRANDE;
       }
       return 0;
   }
   
   /**
    * Indica si un personaje se puede mover en una dirección
    * si se encuentra en una determinada celda.
    * @param x Posición del personaje en el eje X.
    * @param y Posición del personaje en el eje Y.
    * @param direccion Dirección del movimiento. Puede ser:
    * Rejilla.ARRIBA, Rejilla.ABAJO, Rejilla.IZQUIERDA o Rejilla.DERECHA.
    * @return 
    */
   public boolean mover(int x, int y, int direccion){
        char siguiente='-';
        try{
            switch(direccion){
                case ARRIBA:
                    siguiente=laberinto[y-1][x];
                    break;
                case ABAJO:
                    siguiente=laberinto[y+1][x];
                    break;
                case DERECHA:
                    siguiente=laberinto[y][x+1];
                    break;
                case IZQUIERDA:
                    siguiente=laberinto[y][x-1];
                    break;
            }
        }
        catch(ArrayIndexOutOfBoundsException ex){
            return false;
        }
        
        if(siguiente==PUNTOPEQUEÑO || siguiente==PUNTOGRANDE || siguiente==LIBRE) 
            return true;
        else if((siguiente==MURO && direccion==ARRIBA) || siguiente==FANTASMA && direccion==DERECHA)
            return true;
        return false;
   }
   
    /**
     * Ver elemento del mapa situado en la celda indicada.
     * @param fila Fila de la celda.
     * @param columna Columna de la celda.
     * @return Caracter correspondiente a la celda consultada.
     */
    public char getCeldaAt(int fila, int columna){
        return laberinto[columna][fila];
    }
    
   
    /**
     * Altura en filas del laberinto.
     * @return Número de filas del mapa.
     */
   public int getAltura(){
       return laberinto.length;
   }
   
   /**
    * Anchera en columnas del laberinto.
    * @return Número de columnas del mapa.
    */
   public int getAnchura(){
       return laberinto[0].length;
   }
}
