package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.sql.Date;

public interface DateFilterable {
	
	public enum DateType {
		
		CREATION_DATE, MODIFICATION_DATE, DUE_DATE;
		
	}
	
	public Date getDate(DateType type);
	public String toString();

}
