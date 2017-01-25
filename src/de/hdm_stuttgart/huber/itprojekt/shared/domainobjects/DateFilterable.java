package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.sql.Date;

/**
 * Klassen, welche dieses Interface implementieren, geben damit an,
 * nach Datum filterbar zu sein. Dazu muss mit einem der Werte des unteren Enums
 * das entsprechende Datum zurückgebbar sein.
 */
public interface DateFilterable {

    /**
     * Gibt ein <code>java.sql.Date</code>-Datum zurück, welches dem entsprechenden vom <code>DateType</code>-
     * Wert angegebenen Datum entspricht.
     *
     * @param type Typ das zurückgegebenen Datums
     * @return Das entsprechende Datum
     */
    Date getDate(DateType type);

    /**
     * @see Object
     * @return String-Repräsentation des Objekts
     */
    @Override
	String toString();

    /**
     * Enthält mögliche Datumstypen.
     */
    enum DateType {

        CREATION_DATE, MODIFICATION_DATE, DUE_DATE

    }

}
