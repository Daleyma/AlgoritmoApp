import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * La clase {@code Descifrar} proporciona metodos para desencriptar texto utilizando un desplazamiento
 * especifico. Esta clase utiliza un alfabeto definido por la clase {@link Alphabet} y maneja la
 * lectura y escritura de archivos a traves de la clase {@link MyFileHandler}.
 * <p>
 * La desencriptacion se realiza mediante un desplazamiento negativo, que se aplica a cada caracter
 * del texto encriptado. La clase tambien permite la desencriptacion de archivos a partir de un archivo
 * de propiedades que contiene la informacion necesaria para el proceso.
 * </p>
 */
public class Descifrar {
    private final Alphabet alphabet;
    private final MyFileHandler fileHandler;

    /**
     * Constructor de la clase {@code Descifrar}.
     * <p>
     * Inicializa una nueva instancia de {@code Descifrar} creando un objeto de {@link Alphabet}
     * y un objeto de {@link MyFileHandler} para manejar operaciones de archivo.
     * </p>
     */
    public Descifrar() {
        this.alphabet = new Alphabet();
        this.fileHandler = new MyFileHandler();
    }

    /**
     * Desencripta un texto dado utilizando un desplazamiento especifico.
     * <p>
     * Este metodo invoca {@link #shiftText(String, int)} con el desplazamiento negativo para
     * realizar la desencriptacion.
     * </p>
     *
     * @param text el texto encriptado que se desea desencriptar.
     * @param desplazamiento el numero de posiciones que se debe desplazar cada caracter.
     * @return el texto desencriptado.
     */
    public String decrypt(String text, int desplazamiento) {
        return shiftText(text, -desplazamiento);
    }

    /**
     * Desplaza los caracteres del texto segun el desplazamiento especificado.
     *
     * <p>Este metodo itera sobre cada caracter del texto, obtiene su indice en el
     * alfabeto y calcula un nuevo indice basado en el desplazamiento. Si el caracter
     * no esta en el alfabeto, se agrega tal cual al resultado. El nuevo caracter se
     * obtiene del alfabeto utilizando el nuevo indice calculado.</p>
     *
     * <p>El {@code StringBuilder result} almacena los caracteres resultantes del texto
     * desencriptado. Para poder iterar por los caracteres, el texto es convertido a un arreglo
     * de caracteres utilizando {@code text.toCharArray()}. Se obtiene el indice del
     * caracter actual en el alfabeto; si el indice es {@code -1}, se agrega el caracter
     * original al resultado sin cambios.</p>
     *
     * <p>Para calcular el nuevo indice desplazado se realiza la siguiente operacion:
     * Ejemplo: Si {@code index} es 0 (para 'a'), {@code desplazamiento} es 3, y el
     * alfabeto tiene 26 letras, se calcula {@code newIndex} de la siguiente manera:
     * {@code newIndex} = (0 + (-3) + 26) % 26 = 23, que corresponde a 'x'. Finalmente,
     * se obtiene el nuevo caracter, sin importar si es letra o simbolo, utilizando
     * {@code alphabet.getCharacter(newIndex, Character.isLowerCase(character))}.</p>
     *
     * @param text El texto que se desea desencriptar. Por ejemplo, "def".
     * @param desplazamiento El numero de posiciones que se desplazaran los caracteres.
     *                       Por ejemplo, un desplazamiento de 3 desplazara 'd' a 'a'.
     * @return El texto desencriptado resultante. Por ejemplo, si el texto original era "def"
     *         y el desplazamiento era 3, el resultado sera "abc".
     */
    private String shiftText(String text, int desplazamiento) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (alphabet.isLetter(character)) {
                int index = alphabet.getIndex(character);
                int newIndex = (index + desplazamiento + alphabet.getAlphabetString().length()) % alphabet.getAlphabetString().length(); // Asegurarse de que el índice sea positivo
                result.append(alphabet.getCharacter(newIndex, Character.isLowerCase(character)));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    /**
     * Desencripta un archivo utilizando la informacion de un archivo de propiedades.\n
     *
     * <p>Este metodo lee el contenido del archivo de propiedades para obtener la ruta del archivo
     * encriptado y el desplazamiento. Luego, desencripta el contenido del archivo encriptado
     * y guarda el texto desencriptado en un nuevo archivo.</p>
     *
     * <p>Por ejemplo, si el archivo de propiedades contiene:</p>
     * <pre>
     * Original File Path: original.txt
     * Encrypted File Path: encrypted.txt
     * Desplazamiento: 3
     * </pre>
     * <p>Y el archivo {@code encrypted.txt} contiene el texto "def", el metodo desencriptara
     * el texto utilizando un desplazamiento de 3, resultando en "abc", que se guardara en un nuevo archivo
     * llamado "original.txt".</p>
     *
     * @param propertiesFilePath La ruta del archivo de propiedades que contiene la informacion
     *                           necesaria para la desencriptacion.
     */
    public void decryptFromProperties(String propertiesFilePath) {
        String propertiesContent = fileHandler.readFromFile(propertiesFilePath);    //leer archivo de propiedades
        if (propertiesContent == null) {
            System.err.println("Error: No se pudo leer el archivo de propiedades.");
            return;
        }

        String originalFilePath = null;
        String filePathEncriptado = null;
        int desplazamiento = 0;

        String[] lineas = propertiesContent.split(System.lineSeparator()); // Procesar el contenido del archivo de propiedades
        for (String linea : lineas) {
            if (linea.startsWith("Original File Path: ")) {
                originalFilePath = linea.substring("Original File Path: ".length());
            } else if (linea.startsWith("Encrypted File Path: ")) {
                filePathEncriptado = linea.substring("Encrypted File Path: ".length());
            } else if (linea.startsWith("Desplazamiento: ")) {
                desplazamiento = Integer.parseInt(linea.substring("Desplazamiento: ".length()));
            }
        }

        String textoEncriptado = fileHandler.readFromFile(filePathEncriptado);         // Leer el contenido del archivo encriptado
        if (textoEncriptado == null) {
            System.err.println("Error: No se pudo leer el archivo encriptado.");
            return;
        }

        String decryptedText = decrypt(textoEncriptado, desplazamiento);

        // Guardar el texto descifrado
        String outputPath = fileHandler.generateOutputPath(filePathEncriptado, "DFPp");
        fileHandler.writeToFile(outputPath, decryptedText);
        System.out.println("Texto descifrado guardado en: " + outputPath);
    }
}