import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * <p>La clase {@code MyFileHandler} proporciona metodos para manejar la
 * creacion de directorios, escritura y lectura de contenido y generacion de rutas de salida para archivos procesados.</p>
 * <p>La {@code outputDirectory} es la carpeta de salida y {@code properDirectory} es la carpeta de propiedades, ambos
 * almacenaran los archivos generados.</p>
 */
public class MyFileHandler {
    private final String outputDirectory;
    private final String properDirectory;

    /**
     * <p>Constructor de la clase {@code MyFileHandler}.
     * Inicializa los directorios de salida y de propiedades, creando
     * las carpetas si no existen.</p>
     * <p>La carpeta Files guardara los archivos de salida y La carpeta Properties guardara los proper generados.</p>
     */
    public MyFileHandler() {
        this.outputDirectory = "Files";
        createOutputDirectory();                // Crear la carpeta si no existe
        this.properDirectory = "Properties";
        createProperDirectory();                // Crear la carpeta de propiedades si no existe
    }

    /**
     * El metodo {@code createOutputDirectory} Crea el directorio File de salida si no existe.
     */
    private void createOutputDirectory() {
        File dir = new File(outputDirectory);
        if (!dir.exists())  dir.mkdirs();   // Crear la carpeta si no existe
    }

    /**
     * El metodo {@code createProperDirectory} Crea el directorio Properties de datos si no existe.
     */
    private void createProperDirectory() {
        File dir = new File(properDirectory);
        if (!dir.exists()) dir.mkdirs();    // Crear la carpeta si no existe
    }

    /**
     * Metodo que escribe el contenido en un archivo especifico {@code fileName}.
     *
     * @param fileName El nombre del archivo donde se escribira el contenido.
     * @param content El contenido que se escribira en el archivo.
     */
    public void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);                              // Escribe en el contenido proporcionado
            writer.write(System.lineSeparator());               // Salto de línea después del contenido
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());   // Captura la excepción
        }
    }

    /**
     * El metodo {@code readFromFile} se usa para leer el contenido de un archivo especifico {@code fileName}.
     * <p>Este metodo utiliza {@code StringBuilder} para construir el contenido del archivo, ya que evita la creacion de
     * multiples objetos de cadena.</p>
     * <p>Se emplea {@code BufferedReader} para leer lineas completas de manera eficiente.</p>
     * <p>La variable {@code line} almacenara cada linea leida hasta que {@code readLine() != null} indique que no haya
     * mas lineas.</p>
     * <p>Con esta instruccion {@code content.append(line)} se agrega cada linea leida al contenedor y
     * {@code append(System.lineSeparator())} proporciona un salto de linea en caso de leer multiples lineas.
     *
     * @param fileName El nombre del archivo que se leera.
     * @return El contenido del archivo como una cadena eliminando el ultimo salto de linea, o {@code null} si ocurre
     * un error.
     */
    public String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();        // Construir el contenido
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {  // Leemos
            String line;
            while ((line = reader.readLine()) != null) {    // Lee cada línea del archivo
                content.append(line).append(System.lineSeparator());   // Agrega la línea leída y un salto de línea.
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
        return content.toString().trim();     // Convierte el contenido a una cadena y quita el salto de línea
    }

    /**
     * El metodo generateOutputPath recibe dos parametros para generar un ruta de salida para un archivo basado en un
     * sufijo y un timestamp.
     * <p>Este metodo crea un identificador unico basado en la fecha y hora actual, ademas de construir la ruta de
     * salida diferenciandola al proporcionarle el sufijo y el timestamp, evitando que se sobreescriba en el archivo.
     * El nombre del archivo resultante tendra la forma: {outputDirectory}/{suffix}_{timestamp}.txt.</p>
     * @param inputPath Ruta del archivo de entrada (Lo queria para el nombre pero lo hace extenso asi que no se utiliza)
     * @param suffix Un sufijo que se anadira al nombre del archivo de salida para diferenciar archivos cifrados y
     *               desencriptados.
     * @return La ruta de salida generada como una cadena.
     */
    public String generateOutputPath(String inputPath, String suffix){
        String timestamp = new SimpleDateFormat("dd-HH-mm-ss").format(new Date());
        return String.format("%s%s%s_%s.txt", outputDirectory, File.separator, suffix, timestamp);
    }
}