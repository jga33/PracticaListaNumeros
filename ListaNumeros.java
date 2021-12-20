/**
 * Un objeto de esta clase
 * guarda una lista de números enteros
 * 
 * La clase incluye una serie de métodos de instancia
 * para hacer operaciones sobre la lista
 * y dos  métodos estáticos para trabajar con
 * arrays de dos dimensiones
 *
 * @author - Grijalba Azpiroz Javier (jgrijalaz)
 * @version - 15/12/21
 */

import java.util.Arrays;
import java.util.Random;
public class ListaNumeros
{
    public static final int DIMENSION = 10;
    public static final int ANCHO_FORMATO = 6;
    public static final char CAR_CABECERA = '-';

    private static final Random generador = new Random();
    private int[] lista;
    private int pos;
    

    /**
     * Constructor de la clase ListaNumeros
     * Crea e inicializa adecuadamente los
     * atributos
     *
     * @param n el tamaño máximo de la lista
     */
    public ListaNumeros(int n)
    {
        lista = new int[n];
        pos = 0;
    }

    /* ===============        ===================
    ==================  INFO  ===================
    ==================        =================*/

    /**
     * @return true si la lista está completa, false en otro caso
     * Hacer sin if
     */
    public boolean estaCompleta()
    {
        return pos == lista.length;
    }

    /**
     * @return true si la lista está vacía, false en otro caso.
     * Hacer sin if
     */
    public boolean estaVacia()
    {
        return pos == 0;
    }

    /**
     * @return el nº de elementos realmente guardados en la lista
     */
    public int getTotalNumeros()
    {
        return pos;
    }

    /* ===============          =================
    ==================  VARIOS  =================
    ==================          ===============*/

    /**
     * Añade un valor al final de la lista
     * siempre que no esté completa
     *
     * @param numero el valor que se añade.
     * @return true si se ha podido añadir, false en otro caso
     */
    public boolean addElemento(int numero)
    {
        if(estaCompleta())
        {
            return false;
        }

        lista[pos] = numero;
        pos++;

        return true;
    }

    /**
     * Vacía la lista
     */
    public void vaciarLista()
    {
        for (int i = 0; i < lista.length; i++)
        {
            lista[i] = 0;
        }
        pos = 0;
    }

    /* ===============             ==============
    ==================  TOsTRINGS  ==============
    ==================             ============*/

    /**
     * @return una cadena con representación textual de la lista 
     * (leer enunciado)
     * 
     * Si la lista está vacía devuelve ""
     */
    public String toString()
    {
        String lineaNumeros = "";
        String caracteres = "";
        String lineaCabecera = "";

        for (int i = 0; i < ANCHO_FORMATO; i++)
        {
            caracteres += CAR_CABECERA;
        }

        for (int i = 0; i < pos; i++)
        {
            lineaNumeros += Utilidades.centrarNumero(lista[i], ANCHO_FORMATO);
            lineaCabecera += caracteres;
        }

        return lineaCabecera + "\n" + lineaNumeros + "\n" + lineaCabecera;
    }

    /**
     * Mostrar en pantalla la lista
     */
    public void escribirLista()
    {
        System.out.println(this.toString());
    }

    /* ===============          =================
    ==================  LALALA  =================
    ==================          ===============*/

    /**
     *  
     * @return el segundo valor máximo en la lista
     * Cuando no haya un segundo máximo el método 
     * devolverá el valor Integer.MIN_VALUE
     * 
     * Si lista = {21, -5, 28, -7, 28, 77, 77, -17, 21, 15, 28, 28, 77} se devuelve 28
     * Si lista = {21, -5, 28, -7, 77} se devuelve 28
     * Si lista = {77, 21} se devuelve 21
     * Si lista = {21} se devuelve Integer.MIN_VALUE
     * Si lista = {21, 21, 21, 21} se devuelve Integer.MIN_VALUE
     * 
     * No se puede usar ningún otro array auxiliar ni hay que ordenar previamente
     * la lista
     */
    public int segundoMaximo()
    {
        int primero = 0;
        int segundo = Integer.MIN_VALUE;

        for (int i = 0; i < pos; i++)
        {
            if(lista[i] > primero)
            {
                primero = lista[i];
            }
        }

        for (int i = 0; i < pos; i++)
        {
            if(  (lista[i] > segundo)  &&  (lista[i] < primero)  )
            {
                segundo = lista[i];
            }
        }

        return segundo;
    }

    /**
     * El método coloca los valores que son segundos máximos al principio de
     * la lista respetando el orden de aparición del resto de elementos
     * 
     * No se puede usar ningún otro array auxiliar ni hay que ordenar previamente
     * la lista
     * 
     * Si lista = {21, -5, 28, -7, 28, 77, 77, -17, 21, 15, 28, 28, 77} 
     * lista queda  {28, 28, 28, 28, 21, -5, -7, 77, 77, -17, 21, 15, 77} y se devuelve true
     * 
     * Si lista = {77, 21} lista queda {21, 77} y se devuelve true
     * Si lista = {21} lista queda igual y se devuelve false
     * Si lista = {21, 21, 21, 21} lista queda igual y se devuelve false
     * 
     * @return true si se han colocado los segundos máximos
     *          false si no se han colocado los segundos máximos porque no había ninguno
     */
    public boolean segundosMaximosAlPrincipio()
    {
        int segundoM = segundoMaximo();
        int inicio = 0;

        // si no hay segundo, devuelve 'false'
        if(segundoM == -1)
        {
            return false;
        }

        // para no iterar sobre los 'segundos maximos' que ya se han movido
        // al principio de la lista, 'inicio' marca la posicion del primer
        // elemento original
        for (int i = pos - 1; i >= inicio; i--)
        {
            if(lista[i] == segundoM)
            {
                for (int j = i; j > inicio; j--)          // desde posicion 'i' hacia atras hasta 'inicio'
                {                                         // asigno a cada elemento el valor del precedente
                    lista[j] = lista[j - 1];
                }
                lista[inicio] = segundoM;
                i++;                                      // el elemento en posicion 'i' es nuevo,
                                                          // hay que obligar al 'for' a reiterar sobre él.
                inicio++;                                 // al añadir un segundo maximo al principio,
                                                          // la posicion del primer elemento original
                                                          // se desplaza una posicion a la derecha
            }
        }
        return true;
    }

    /**
     * @param numero búsqueda binaria de numero en lista
     * @return devuelve -1 si no se encuentra o la posición en la que aparece
     *  
     * El array original lista no se modifica
     * Para ello crea  previamente una copia
     * de lista y trabaja  con la copia
     *  
     * Usa exclusivamente métodos de la clase Arrays
     */
    public int buscarBinario(int numero)
    {
        int copia[] = Arrays.copyOf(lista, lista.length);
        Arrays.sort(copia);
        int pos = Arrays.binarySearch(copia, numero);

        if(pos < 0)
        {
            pos = -1;
        }
         
        return pos;
    }

    /**
     * 
     * @return xx devuelve un array bidimensional de enteros de tamaño DIMENSION
     * inicializado con valores aleatorios entre 0 y 10 inclusive
     * 
     * Estos valores van a representar el brillo de una zona del espacio
     * 
     */
    public static int[][] crearBrillos()
    {
       int[][] brillos = new int[DIMENSION][DIMENSION];

        for (int f = 0; f < DIMENSION; f++)
        {
            for (int c = 0; c < DIMENSION; c++)
            {
                brillos[f][c] = (int)(Math.random() * 11);
            }
        }
       
        return brillos;
    }

    /**
     * @param  brillos un array bidimensional brillos
     * @return estrellas un nuevo array bidimensional de valores booleanos
     *          de las mismas dimensiones que el array brillos con
     *          valores true en las posiciones donde hay estrellas
     * 
     * Una posición f,c del array brillos es una estrella 
     * si la suma del valor de los brillos de sus cuatro vecinos 
     * (arriba, abajo, derecha e izquierda) es mayor que 30
     * 
     * Nota -  No hay estrellas en los bordes del array brillos
     */
    public static boolean[][] detectarEstrellas(int[][] brillos)
    {
        boolean [][] estrellas = new boolean[DIMENSION][DIMENSION];
        int arriba = 0;
        int abajo = 0;
        int derecha = 0;
        int izquierda = 0;

        for (int f = 1; f < DIMENSION - 1; f++)
        {
            for (int c = 1; c < DIMENSION - 1; c++)
            {
                arriba = brillos[f - 1][c];
                abajo = brillos[f + 1][c];
                derecha = brillos[f][c + 1];
                izquierda = brillos[f][c - 1];
                estrellas[f][c] = (arriba + abajo + derecha + izquierda > 30 );
            }
        }

        return estrellas;
    }

}
