package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;

import java.sql.*;
import java.util.Vector;

public class NoteMapper extends DataMapper {

    // Statisches Attribut, welches den Singleton-NoteMapper enthält.
    private static NoteMapper noteMapper = null;

    // Nichtöffentlicher Konstruktor, um "unauthorisiertes" Instanziieren dieser Klasse zu verhindern.
    protected NoteMapper() throws ClassNotFoundException, SQLException {

    }

    // Öffentliche Methode um den Singleton-NoteMapper zu erhalten
    public static NoteMapper getNoteMapper() throws ClassNotFoundException, SQLException {

        if (noteMapper == null) {

            noteMapper = new NoteMapper();

        }

        return noteMapper;
    }

    public Note create(Note note) throws ClassNotFoundException, SQLException {

        try {

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO notizbuch.note" +
                    "(title, subtitle, content, note_source, creation_date, due_date, modification_date, notebook_id, author_id) VALUES (?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getSubtitle());
            stmt.setString(3, note.getContent());
            stmt.setString(4, "https://Platzhalter");

            stmt.setDate(5, note.getCreationDate());
            stmt.setDate(6, note.getDueDate());
            stmt.setDate(7, note.getModificationDate());

            stmt.setInt(8, note.getNoteBook().getId());
            stmt.setInt(9, note.getOwner().getId());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return findById(rs.getInt(1));

            }

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();

        }
        return note;

    }

    public Note findById(int id) throws ClassNotFoundException, SQLException {

        if (isObjectLoaded(id, Note.class)) {
            return (Note) loadedObjects.get(id);
        }

            try {

                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM notizbuch.note WHERE id = ?");
                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {

                    Note note = new Note(rs.getInt("id"),
                            rs.getString("content"),
                            rs.getString("title"),
                            rs.getString("subtitle"),
                            UserInfoMapper.getUserInfoMapper().findById(rs.getInt("author_id")),
                            NoteBookMapper.getNoteBookMapper().findById(rs.getInt("notebook_id")),
                            rs.getDate("creation_date"),
                            rs.getDate("due_date"),
                            rs.getDate("modification_date"));

                    loadedObjects.put(rs.getInt("id"), note);

                    return note;
                }

            } catch (SQLException sqlExp) {
                sqlExp.printStackTrace();
                return null;
            }

        return null;
    }

    public Note save(Note note) throws ClassNotFoundException, SQLException {

        try {

            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE notizbuch.note SET title=?, subtitle=?, content=?, note_source=?, creation_date=?, due_date=?, modification_date=?, notebook_id=?, author_id=? WHERE id = ?");

            // Alle String-Inhalte
            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getSubtitle());
            stmt.setString(3, note.getContent());
            stmt.setString(4, "PLATZHALTER SORUCE"); // TODO

            // Daten
            stmt.setDate(5, note.getCreationDate());
            stmt.setDate(6, note.getDueDate());
            stmt.setDate(7, new Date(System.currentTimeMillis()));

            // IDs
            stmt.setInt(8, note.getNoteBook().getId());
            stmt.setInt(9, note.getOwner().getId());

            // Id der zu speichernden Notiz
            stmt.setInt(10, note.getId());

            stmt.executeUpdate();


        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }

        return findById(note.getId());
    }

    public void delete(Note note) throws ClassNotFoundException, SQLException {

        try {

            PreparedStatement stmt = connection.prepareStatement("DELETE FROM notizbuch.note WHERE id = ?");
            stmt.setInt(1, note.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

    }


    public Vector<Note> getAllNotes() throws ClassNotFoundException, SQLException {

        Vector<Note> v = new Vector<>();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM notizbuch.note");
            UserInfoMapper noteUserMapper = UserInfoMapper.getUserInfoMapper();
            NoteBookMapper noteBookMapper = NoteBookMapper.getNoteBookMapper();

            while (rs.next()) {

                Note note = new Note(rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("title"),
                        rs.getString("subtitle"),
                        noteUserMapper.findById(rs.getInt("author_id")),
                        noteBookMapper.findById(rs.getInt("notebook_id")),
                        rs.getDate("creation_date"),
                        rs.getDate("modification_date"),
                        rs.getDate("due_date"));

                v.add(note);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;

    }
    
    public Vector<Note> getAllNotesForUserId(int userId) {

        Vector<Note> v = new Vector<>();

        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM notizbuch.note WHERE author_id = ?");
            stmt.setInt(1, userId);
            
            ResultSet rs = stmt.executeQuery();
            
            UserInfoMapper noteUserMapper = UserInfoMapper.getUserInfoMapper();
            NoteBookMapper noteBookMapper = NoteBookMapper.getNoteBookMapper();

            while (rs.next()) {

                Note note = new Note(rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("title"),
                        rs.getString("subtitle"),
                        noteUserMapper.findById(userId),
                        noteBookMapper.findById(rs.getInt("notebook_id")),
                        rs.getDate("creation_date"),
                        rs.getDate("modification_date"),
                        rs.getDate("due_date"));

                v.add(note);

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return v;

    }

}

