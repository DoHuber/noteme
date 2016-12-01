package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.io.Serializable;

public abstract class DomainObject implements Serializable {

	/**
	 * Default, hat in dem Falle eclipse selbst generiert
	 */
	private static final long serialVersionUID = 1L;
	protected int id;

	protected DomainObject() {
		// TODO Auto-generated constructor stub
	}

}
