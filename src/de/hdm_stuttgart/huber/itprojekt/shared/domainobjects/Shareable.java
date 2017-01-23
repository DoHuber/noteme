package de.hdm_stuttgart.huber.itprojekt.shared.domainobjects;

import java.io.Serializable;

public interface Shareable extends Serializable {

    int getId();

    char getType();

    Permission getRuntimePermission();

}
