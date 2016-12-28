package de.hdm_stuttgart.huber.itprojekt.shared.Report;

import java.io.Serializable;

/**
 * Report, der alle Konten alle Kunden darstellt.
 * Die Klasse tr#gt keine weiteren Attribute- und Methoden-Implementierungen,
 * da alles Notwendige schon in den Superklassen vorliegt. Ihre Existenz ist 
 * dennoch wichtig, um bestimmte Typen von Reports deklarieren und mit ihnen 
 * objektorientiert umgehen zu können.
 * 
 * @author Thies
 */
public class AllNoteBooksOfAllUsers 
	extends CompositeReport 
	implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

}