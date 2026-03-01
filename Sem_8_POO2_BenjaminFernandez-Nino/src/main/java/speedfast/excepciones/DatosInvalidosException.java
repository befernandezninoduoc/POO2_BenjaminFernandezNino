package speedfast.excepciones;

/**
 * Excepción personalizada para manejar errores de validación en la capa de vista o negocio.
 * Se lanza cuando el usuario ingresa datos incompletos o en un formato incorrecto.
 * @author Benjamín Fernández-Niño
 */
    public class DatosInvalidosException extends Exception{

    /**
     * Constructor de la excepción.
     * @param message Mensaje descriptivo del error de validación.
     */
    public DatosInvalidosException(String message) {
        super(message);
    }
}
