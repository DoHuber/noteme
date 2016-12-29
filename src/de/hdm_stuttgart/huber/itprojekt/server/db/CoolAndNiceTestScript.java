package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Testskript zum Ausprobieren ob die DataMapper richtig funktionieren.
 * Nicht für die produktive Applikation gedacht.
 *
 */
public class CoolAndNiceTestScript {


    public static void main(String[] args) throws Throwable {
    	
    	Vector<Note> vector = NoteMapper.getNoteMapper().getAllNotesForUserId(503);
    	System.out.println(Arrays.toString(vector.toArray()));
    	
	
    }

    private static Note createNoteTest() {

        Note n = new Note();
        n.setTitle("Testfall Titel");
        n.setSubtitle("Testfall Untertitel");
        n.setContent("Lorem ipsum sit dolor amet requesiat");
        // SOURCE @TODO

        Date d = new Date(System.currentTimeMillis());
        n.setCreationDate(d);
        n.setDueDate(d);
        n.setModificationDate(d);

        NoteBook nb = new NoteBook(5);
        n.setNoteBook(nb);

        UserInfo nu = new UserInfo(5);
        n.setOwner(nu);

        try {

            return NoteMapper.getNoteMapper().create(n);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    private static Note findNoteByIdTest(int id) {

        try {

            Note n = NoteMapper.getNoteMapper().findById(id);
            System.out.println(n.toString());
            return n;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }

    }

    private static Note saveNoteTest(Note n) {

        n.setTitle("Veränderungstest");
        n.setSubtitle("Veränderungstest");

        try {

            return NoteMapper.getNoteMapper().save(n);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static void deleteNoteTest(Note n) {

        try {
            NoteMapper.getNoteMapper().delete(n);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


}
