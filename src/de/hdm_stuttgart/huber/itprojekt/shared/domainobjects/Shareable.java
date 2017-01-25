package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.io.Serializable;

/**
 * Dieses Interface implementierende Klassen markieren ihre Instanzen damit als für
 * andere Nutzer teilbar.
 */
public interface Shareable extends Serializable {

    /**
     * Gibt eine eindeutige id für das Objekt zurück, welche über alle Instanzen und mehrere Programmlaufzeiten
     * <i>eindeutig</i> sein muss
     *
     * @return eindeutiger Identifikationsnummer
     */
    int getId();

    /**
     * Typ des geteilten Objekts, muss für jede Klasse, die dieses Interface implementiert, eindeutig
     * sein.
     *
     * @return Eindeutige Typenbezeichnung
     */
    char getType();

    /**
     * Gibt die Laufzeit-Freigabe zurück, welche die aktuellen
     * Befugnisse eines Nutzers zu diesem Objekt darstellt.
     *
     * @return <code>Permission</code>, welche Auskunft über Nutzerberechtigungen gibt
     */
    Permission getRuntimePermission();

}
