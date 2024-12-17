import java.util.HashMap;
import java.util.Map;

/**
 * La clase {@code Alphabet} representa un alfabeto combinado que incluye letras
 * de varios idiomas, caracteres acentuados, numeros y simbolos.
 *
 * <p>Esta clase proporciona metodos para obtener el indice de un caracter,
 * obtener un caracter basado en su indice, verificar si un caracter es una letra
 * y obtener la cadena del alfabeto.</p>
 */
public class Alphabet {
    private final String alphabetString = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"
            + "abcdefghijklmnñopqrstuvwxyz"
            + "àâäçéèêëîïôùûü"
            + "0123456789"
            + ".,;:!?\"'()[]{}<>-+/=@#$%^&*~«|»¿"
            + " ";

    private final Map<Character, Integer> indexMap;

    /**
     * Constructor de la clase {@code Alphabet}.
     *
     * <p>Este constructor inicializa el mapa de indices {@code indexMap}, que
     * asocia cada caracter del alfabeto con su indice de posicion correspondiente en la
     * cadena {@code alphabetString}.</p>
     * <pre>EJEMPLO: Si alphabetString tiene el caracter 'A' en la posicion 0,
     * se ejecuta indexMap.put('A', 0);.
     * </pre>
     * <p>Esto permite un acceso rapido a los indices de los caracteres.</p>
     */
    public Alphabet() {
        this.indexMap = new HashMap<>(); //inicializamos Hm(k,v)
        for (int i = 0; i < alphabetString.length(); i++) {
            indexMap.put(alphabetString.charAt(i), i);
        }
    }

    /**
     * Obtiene el indice de un caracter en el alfabeto.
     *
     * <p>Este metodo busca el caracter proporcionado en el mapa de indices y
     * devuelve su posicion en {@code alphabetString}. Si el caracter no esta
     * presente, se devuelve -1.</p>
     *
     * @param character El caracter del cual se desea obtener el indice.
     * @return El indice del caracter en el alfabeto, o -1 si el caracter no esta presente.
     */
    public int getIndex(char character) {
        return indexMap.getOrDefault(character, -1);
    }

    /**
     * Obtiene el caracter correspondiente a un indice dado.
     *
     * <p>Este metodo permite recuperar un caracter de {@code alphabetString} basado
     * en su indice. Ademas, permite especificar si se desea el caracter en minuscula
     * o en mayuscula.</p>
     * @param index El indice del caracter en el alfabeto.
     * @param isLowerCase {@code true} si se desea el caracter en minuscula, {@code false} para mayuscula.
     * @return El caracter correspondiente al indice.
     * @throws IndexOutOfBoundsException Si el indice esta fuera de los limites del alfabeto.
     */
    public char getCharacter(int index, boolean isLowerCase) {
        if (index < 0 || index >= alphabetString.length()) {
            throw new IndexOutOfBoundsException("Indice fuera de los límites del alfabeto.");
        }
        char character = alphabetString.charAt(index);  // Obtiene el carácter en el índice especificado.
        return isLowerCase ?
                Character.toLowerCase(character) : character;
    }

    /**
     * Verifica si un caracter es una letra del alfabeto.
     *
     * @param character El caracter a verificar.
     * @return {@code true} si el caracter es una letra, {@code false} en caso contrario.
     */
    public boolean isLetter(char character) {
        return indexMap.containsKey(character);
    }

    /**
     * Obtiene la cadena que representa el alfabeto.
     *
     * <p>Este metodo devuelve la cadena completa que contiene todos los caracteres
     * del alfabeto, incluyendo letras, numeros y simbolos.</p>
     * @return La cadena del alfabeto.
     */
    public String getAlphabetString() {
        return alphabetString;
    }
}