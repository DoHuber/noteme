package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteBook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.NoteUser;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Testskript zum Ausprobieren ob die DataMapper richtig funktionieren.
 * Nicht für die produktive Applikation gedacht.
 *
 */
public class CoolAndNiceTestScript {


    public static void main(String[] args) {

        Note n1 = createNoteTest();             // PASS
        Note n2 = saveNoteTest(n1);             // PASS
        findNoteByIdTest(n2.getId());           // PASS

        Note n3 = new Note(349);
        deleteNoteTest(n3);                     // PASS


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

        NoteUser nu = new NoteUser(5);
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
