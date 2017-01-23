package de.hdm_stuttgart.huber.itprojekt.server;

public class InvalidLoginStatusException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6623416840322170431L;


    public InvalidLoginStatusException() {
        super("Kein User eingeloggt. Funktion an falscher Stelle verwendet?");
        // TODO Auto-generated constructor stub
    }

    public InvalidLoginStatusException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public InvalidLoginStatusException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public InvalidLoginStatusException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
