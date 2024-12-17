import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
* La clase ManagerEncryption gestiona el cifrado y descifrado de archivos.
* Proporcionando metodos para cifrar y descifrar .txt, asi como para guardar
* propiedades relacionadas con el proceso de cifrado en un archivo de texto.
* Esta clase utiliza instancias de las clases Cifrar, Descifrar y MyFileHandler
* para realizar sus operaciones.
*/
public class ManagerEncryption {
    private final Cifrar cipher;
    private final Descifrar decrypted;
    private final MyFileHandler fileHandler;

    /**
     * Constructor de la clase ManagerEncryption, inicializa las instancias de Cifrar,
     * Descifrar y MyFileHandler.
     */
    public ManagerEncryption() {
        this.cipher = new Cifrar();
        this.decrypted = new Descifrar();
        this.fileHandler = new MyFileHandler(); // Instancia de tu clase personalizada
    }
    /**
     * Maneja el proceso de cifrado de un archivo (administrando el proceso).
     * Este metodo lee el contenido de un archivo desde la ruta especificada, cifra el texto
     * utilizando un desplazamiento dado y guarda el texto cifrado en un nuevo archivo.
     *
     * @param inputPath La ruta del archivo que se va a cifrar.
     * @param shift El valor de desplazamiento utilizado para el cifrado.
     * @return La ruta del archivo donde se ha guardado el texto cifrado, o null si hubo un error.
     */
    public String manejoDelCifrado(String inputPath, int shift) {
        String text = fileHandler.readFromFile(inputPath); // Llama al método de lectura
        if (text == null) {
            System.err.println("Error: No se pudo leer el texto del archivo.");
            return null;
        }
        String encryptedText = cipher.encrypt(text, shift); // Cifra el texto
        String outputPath = fileHandler.generateOutputPath(inputPath, "eC");
        fileHandler.writeToFile(outputPath, encryptedText); // Escribe el texto cifrado en un archivo
        System.out.println("Texto cifrado guardado en: " + outputPath);
        return outputPath;
    }
    /**
     * Metodo que maneja el proceso de descifrado de un archivo (administrando el proceso)
     * Este metodo lee el contenido de un archivo desde la ruta especificada, cifra el texto
     * utilizando un desplazamiento dado y guarda el texto cifrado en un nuevo archivo.
     *
     * @param inputPath La ruta del archivo que se va a descifrar.
     * @param shift El valor de desplazamiento utilizado para el cifrado.
     */
    public void manejoDelDecifrado(String inputPath, int shift) {
        String text = fileHandler.readFromFile(inputPath); // Llama al método de lectura
        if (text == null) {
            System.err.println("Error: No se pudo descifrar el texto del archivo." + inputPath);
            return;
        }
        String decryptedText = decrypted.decrypt(text, shift); // Descifra el texto
        String outputPath = fileHandler.generateOutputPath(inputPath, "dC");
        fileHandler.writeToFile(outputPath, decryptedText); // Escribe el texto descifrado en un archivo
        System.out.println("Texto descifrado guardado en: " + outputPath);
    }
    /**
     * Procesa el descifrado de datos utilizando un archivo de propiedades.
     * Este metodo lee un archivo de propiedades que contiene la configuracion necesaria
     * para realizar el descifrado, incluyendo la ruta del archivo a descifrar y el
     * desplazamiento utilizado para el descifrado.
     *
     * @param propertiesFilePath La ruta del archivo de propiedades que contiene la configuracion
     *                           necesaria para el descifrado. Este archivo debe incluir
     *                           las claves necesarias para identificar el archivo a descifrar
     *                           y el desplazamiento.
     */
    public void procesarDescifradoDesdePropiedades(String propertiesFilePath) {
        decrypted.decryptFromProperties(propertiesFilePath);
    }
    /**
     * Metodo guardarProperties, guarda propiedades necesarias al cifrar un archivo de texto.
     * Este metodo genera un archivo de propiedades que contiene la ruta del archivo original,
     * la ruta del archivo cifrado y el desplazamiento utilizado en el cifrado. El nombre del
     * archivo de propiedades se basa en un timestamp para asegurar que sea unico.
     *
     * @param inputPath La ruta del archivo original que se esta cifrando.
     * @param shift El valor de desplazamiento utilizado en el cifrado.
     * @param outputPath La ruta donde se guarda el archivo cifrado.
     */
    public void guardarProperties(String inputPath, int shift, String outputPath) {
        String timestamp = new SimpleDateFormat("dd-HH-mm-ss").format(new Date());
        String properFileName = String.format("proper_%s.txt", timestamp);
        Path propFilePath = Paths.get("Properties").resolve(properFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(propFilePath)) {
            writer.write("Original File Path: " + inputPath + "\n");
            writer.write("Encrypted File Path: " + outputPath + "\n");
            writer.write("Desplazamiento: " + shift + "\n");
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }
}