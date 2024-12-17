/**
 * La clase proporciona metodos para cifrar texto utilizando un desplazamiento en el alfabeto.
 *
 * <p>Esta clase utiliza un objeto de la clase {@link Alphabet} para determinar
 * el conjunto de caracteres que se pueden cifrar. El metodo {@link #encrypt(String, int)}
 * es la interfaz publica que permite cifrar un texto, mientras que {@link #shiftText(String, int)}
 * realiza la logica de cifrado.</p>
 */
public class Cifrar {
    private final Alphabet alphabet;

    /**
     * Constructor de la clase Cifrar. Inicializa una nueva instancia de la clase {@link Alphabet} que se utilizara
     * para el cifrado de texto.</p>
     */
    public Cifrar() {
        this.alphabet = new Alphabet();
    }

    /**
     * <p>Este metodo cifra el texto proporcionado actuando como una interfaz para el cifrado. Llama al metodo
     * {@link #shiftText(String, int)} para realizar el cifrado real del texto.</p>
     *
     * @param text El texto que se desea cifrar.
     * @param desplazamiento El numero de posiciones que se desplazaran los caracteres.
     * @return El texto cifrado al llamar al metodo {@link #shiftText(String, int)}.
     */
    public String encrypt(String text, int desplazamiento) {    return shiftText(text, desplazamiento);}

    /**
     * Desplaza los caracteres del texto segun el desplazamiento especificado.
     *
     * <p>Este metodo itera sobre cada caracter del texto, obtiene su indice en el alfabeto y calcula un nuevo indice
     * basado en el desplazamiento. Si el caracter no esta en el alfabeto, se agrega tal cual al resultado. El nuevo
     * caracter se obtiene utilizando el nuevo indice calculado.</p>
     *
     * <p>Ejemplo: Si el indice es 0 (para 'a') y el desplazamiento es 3, el nuevo indice se calcula como:
     * {@code newIndex} = (0 + 3 + 26) % 26 = 3, que corresponde a 'd'.</p>
     *
     * @param text El texto que se desea cifrar (ej. "abc").
     * @param desplazamiento El numero de posiciones que se desplazaran los caracteres (ej. un desplazamiento de 3
     *                       desplaza 'a' a 'd').
     * @return El texto cifrado resultante (ej. "def" si el texto original era "abc" y el desplazamiento era 3).
     */
    private String shiftText(String text, int desplazamiento) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            int index = alphabet.getIndex(character);   // Obtener el índice del carácter en el alfabeto

            if (index == -1) {             // Si el carácter no está en el alfabeto, se agrega tal cual
                result.append(character);
                continue;
            }

            int newIndex = (index + desplazamiento + alphabet.getAlphabetString().length()) % alphabet.getAlphabetString().length();    // Calcular el nuevo índice con el desplazamiento
            char newChar = alphabet.getCharacter(newIndex, Character.isLowerCase(character));
            result.append(newChar);     // Agregar el nuevo carácter al resultado
        }
        return result.toString();
    }
}

