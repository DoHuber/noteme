package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Notebook;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.sql.*;
import java.util.Vector;

/**
 * @see NoteMapper
 */
public class NoteBookMapper extends DataMapper {

    private static NoteBookMapper noteBookMapper = null;

    protected NoteBookMapper() throws ClassNotFoundException, SQLException {
        super();
    }

    public static NoteBookMapper getNoteBookMapper() {
        if (noteBookMapper == null) {

            try {

                noteBookMapper = new NoteBookMapper();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return noteBookMapper;
    }

    public Notebook create(Notebook notebook) {

        try {

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO notizbuch.notebook(title, subtitle, creation_date, modification_date, author_id) "
                            + "VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, notebook.getTitle());
            stmt.setString(2, notebook.getSubtitle());

            stmt.setDate(3, notebook.getCreationDate());
            stmt.setDate(4, notebook.getModificationDate());

            stmt.setInt(5, notebook.getOwner().getId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return findById(rs.getLong(1));

            }

        } catch (SQLException | ClassNotFoundException sqlExp) {

            sqlExp.printStackTrace();

        }

        return notebook;

    }

    /**
     * Bestimmtes NoteBook wird anhand der eindeutigen ID gesucht und
     * zur�ckgegeben
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Notebook findById(long id) throws ClassNotFoundException, SQLException {

        Connection connection = DBConnection.getConnection();

        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM notizbuch.notebook WHERE id = ?");
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                return new Notebook(

                        rs.getInt("id"), rs.getString("title"), rs.getString("subtitle"),
                        UserInfoMapper.getUserInfoMapper().findById(rs.getInt("author_id")),
                        rs.getDate("creation_date"), rs.getDate("modification_date"));

            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * NoteBook-Objekt wird wiederholt in die Datenbank geschrieben
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Notebook save(Notebook notebook) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {

            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE notizbuch.notebook SET title=?, subtitle=?, creation_date=?, modification_date=?, author_id=? WHERE id = ?");

            stmt.setString(1, notebook.getTitle());
            stmt.setString(2, notebook.getSubtitle());
            stmt.setDate(3, notebook.getCreationDate());
            stmt.setDate(4, new Date(System.currentTimeMillis()));
            stmt.setInt(5, notebook.getOwner().getId());

            stmt.setInt(6, notebook.getId());

            stmt.executeUpdate();

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }

        return findById(notebook.getId());
    }

    public void delete(Notebook notebook) {

        try {

            PreparedStatement stmt = connection.prepareStatement("DELETE FROM notizbuch.notebook WHERE id = ?");
            stmt.setInt(1, notebook.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

    }

    public Vector<Notebook> getAllNoteBooks() {

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM notizbuch.notebook");

            return makeNoteBooksFromResultSet(rs);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Vector<Notebook> getAllNoteBooksForUserId(int userId) {

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM notizbuch.notebook WHERE author_id = ?");
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            return makeNoteBooksFromResultSet(rs);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Vector<Notebook> getAllNoteBooksSharedWith(UserInfo u) {

        try {

            String sql = "SELECT notebook.id AS id, title, subtitle, creation_date, modification_date, notebook.author_id AS author_id FROM notebook "
                    + "JOIN permission ON notebook.id = permission.notebook_id WHERE beneficiary_id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, u.getId());
            ResultSet rs = ps.executeQuery();

            return makeNoteBooksFromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
            return new Vector<>();
        }

    }

    private Vector<Notebook> makeNoteBooksFromResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {

        Vector<Notebook> v = new Vector<>();

        while (rs.next()) {

            Notebook resultRow = new Notebook(rs.getInt("id"));

            resultRow.setTitle(rs.getString("title"));
            resultRow.setSubtitle(rs.getString("subtitle"));
            resultRow.setCreationDate(rs.getDate("creation_date"));
            resultRow.setModificationDate(rs.getDate("modification_date"));
            resultRow.setOwner(UserInfoMapper.getUserInfoMapper().findById(rs.getInt("author_id")));

            v.add(resultRow);

        }

        return v;
    }

    public Vector<Notebook> getAllNoteBooksSharedBy(UserInfo u) {

        String sql = "SELECT DISTINCT notebook.id AS id FROM notebook JOIN permission ON notebook.id = permission.notebook_id WHERE notebook.author_id = ?";
        Vector<Notebook> v = new Vector<>();

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, u.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                v.add(findById(rs.getInt("id")));
            }

            return v;

        } catch (Exception e) {
            e.printStackTrace();
            return v;
        }

    }

}
