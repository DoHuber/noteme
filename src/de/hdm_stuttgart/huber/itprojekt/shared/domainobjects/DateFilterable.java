package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.sql.Date;

public interface DateFilterable {

    Date getDate(DateType type);

    @Override
	String toString();

    enum DateType {

        CREATION_DATE, MODIFICATION_DATE, DUE_DATE

    }

}
