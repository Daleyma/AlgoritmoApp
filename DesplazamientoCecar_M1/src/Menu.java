import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Clase que representa un menu interactivo para la encriptacion y desencriptacion de archivos.
 * Permite al usuario seleccionar opciones para encriptar, desencriptar, o desencriptar desde un archivo de propiedades.
 */
public class Menu {
    private final Scanner scanner;
    private final ManagerEncryption managerEncryption;
    private String inputPath;
    private String outputPath;

    /**
     * Constructor de la clase Menu.
     * Inicializa el scaner y el gestor de encriptacion.
     */
    public Menu() {
        this.scanner = new Scanner(System.in);
        this.managerEncryption = new ManagerEncryption();
    }

    /**
     * Muestra el menu de opciones al usuario y gestiona la seleccion de opciones.
     * Permite al usuario encriptar, desencriptar, desencriptar con properties o salir del programa.
     */
    public void showMenu() {
        while ( true ) {
            System.out.println("Seleciona una opcion");
            System.out.println("1. Encriptar");
            System.out.println("2. Desencriptar");
            System.out.println("3. Desencriptar desde Properties");
            System.out.println("4. Salir");

            int optionMenu = readInteger();
            switch (optionMenu) {
                case 1:
                    manejoUsuarioAlCifrar();
                    break;
                case 2:
                    manejoUsuarioAlDescifrar();
                    break;
                case 3:
                    manejoUsuarioAlDecifrarDesdeProperties();
                    break;
                case 4:
                    System.out.println("\n" + "saliendo" );
                    return; //finalizando programa
                default:
                    System.out.println("Opcion no valida");
                    showMenu();
            }
        }
    }

    /**
     * Maneja la logica para encriptar un archivo.
     * <p>
     * Este metodo solicita al usuario que ingrese la ruta del archivo que desea encriptar y el desplazamiento
     * que se utilizara en el proceso de encriptacion. Luego, llama al metodo correspondiente en el gestor de
     * encriptacion para realizar la operacion. Si la encriptacion es exitosa, se guardan las propiedades
     * del archivo encriptado, incluyendo la ruta original, el desplazamiento y la ruta del archivo encriptado.
     * </p>
     * <p>
     * Si ocurre un error durante el proceso de encriptacion, se informa al usuario con un mensaje de error.
     * </p>
     */
    private void manejoUsuarioAlCifrar() {
        System.out.println("Ingrese la ruta del archivo a Encriptar: ");
        inputPath = scanner.nextLine(); // Leer la ruta del archivo
        System.out.println("Ingrese el desplazamiento: ");
        int desplazamiento = readInteger(); // Leer el desplazamiento
        outputPath = managerEncryption.manejoDelCifrado(inputPath, desplazamiento); // Llamar al método de cifrado
        if (outputPath != null) {
            managerEncryption.guardarProperties(inputPath, desplazamiento, outputPath);
        } else {
            System.out.println("Error: No se pudo cifrar el archivo.");
        }
    }

    /**
     * Maneja la logica para desencriptar un archivo.
     * <p>
     * Este metodo solicita al usuario que ingrese la ruta del archivo que desea desencriptar y el desplazamiento
     * que se utilizo para encriptarlo. Luego, llama al metodo correspondiente en el gestor de desencriptacion
     * para realizar la operacion. Si el proceso se completa sin errores, el archivo se desencripta correctamente.
     * </p>
     * <p>
     * Si el archivo no se puede desencriptar, se espera que el gestor de desencriptacion maneje el error
     * y notifique al usuario.
     * </p>
     */
    private void manejoUsuarioAlDescifrar() {
        System.out.println("Ingrese la ruta del archivo a descifrar: ");
        inputPath = scanner.nextLine(); // Leer la ruta del archivo
        System.out.println("Ingrese el desplazamiento: ");
        int desplazamiento = readInteger(); // Leer el desplazamiento
        managerEncryption.manejoDelDecifrado(inputPath, desplazamiento); // Llamar al método de descifrado
    }

    /**
     * Maneja la logica para desencriptar un archivo utilizando un archivo de Properties.
     * <p>
     * Este metodo solicita al usuario que ingrese la ruta del archivo de 'proper' que contiene la informacion
     * necesaria para el proceso de desencriptacion, como la ruta del archivo encriptado y el desplazamiento.
     * Luego, llama al metodo correspondiente en el gestor de desencriptacion para procesar el archivo de propiedades
     * y realizar la desencriptacion.
     * </p>
     */
    private void manejoUsuarioAlDecifrarDesdeProperties()   {
        System.out.println("Ingrese la ruta de 'Propepiedades' a descifrar: ");
        String propertiesPath = scanner.nextLine();
        managerEncryption.procesarDescifradoDesdePropiedades(propertiesPath);
    }

    /**
     * Lee un numero entero del input del usuario.
     * <p>
     * Este metodo lee un numero entero ingresado por el usuario. Si el usuario ingresa un valor no valido,
     * se captura la excepcion y se solicita nuevamente la entrada. Este proceso se repite hasta que se ingresa
     * un numero entero valido.
     * </p>
     *
     * @return el numero entero ingresado por el usuario.
     */
    private int readInteger(){
        while ( true )  {
            try {
                int number = scanner.nextInt(); // Intentar leer un entero
                scanner.nextLine(); // Consumir el salto de línea
                return number; // Retornar el número leído
            }   catch ( InputMismatchException mismatchException)   {
                System.err.println("--- Ingresa un número entero ---");
                scanner.nextLine();
            }
        }
    }
}