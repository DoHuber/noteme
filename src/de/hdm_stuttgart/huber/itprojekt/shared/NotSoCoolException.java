package de.hdm_stuttgart.huber.itprojekt.shared;

import java.io.Serializable;

/**
 * Simple Erweiterung von Exception, wurde
 * zu Projektbeginn zu Testzwecken verwendet. Methoden usw. unterscheiden sich nicht relevant von denen von
 * Exception, daher ist im Zweifelsfall die entsprechende Dokumentation zu konsultieren.
 *
 * @see Exception
 * @author Dominik Huber
 */
public class NotSoCoolException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public NotSoCoolException() {

    }

    public NotSoCoolException(String message) {
        super(message);

    }

    public NotSoCoolException(Throwable cause) {
        super(cause);

    }

    public NotSoCoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSoCoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

}
