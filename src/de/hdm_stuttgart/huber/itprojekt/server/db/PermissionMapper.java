package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.*;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class PermissionMapper extends DataMapper {

    // Statisches Attribut, welches den Singleton-PermissionMapper enthält
    private static PermissionMapper permissionMapper = null;

    // Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu
    // verhindern)
    protected PermissionMapper() throws ClassNotFoundException, SQLException {
        super();
    }

    // Öffentliche statische Methode, um den Singleton-PermissionMapper zu
    // erhalten
    public static PermissionMapper getPermissionMapper() {

        if (permissionMapper == null) {
            try {
                permissionMapper = new PermissionMapper();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return permissionMapper;
    }

    public Permission getPermissionFor(UserInfo u, Shareable sharedObject) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM notizbuch.permission WHERE beneficiary_id = ? AND ");

        if (sharedObject instanceof Note) {
            sql.append("note_id = ?;");
        } else if (sharedObject instanceof Notebook) {
            sql.append("notebook_id = ?;");
        } else {
            throw new RuntimeException("Das war der falsche Datentyp");
        }

        try {

            PreparedStatement ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, u.getId());
            ps.setInt(2, sharedObject.getId());

            ResultSet rs = ps.executeQuery();

            // Workaround, eventuelle makeFrom-Methode refactoren?
            Vector<Permission> v = makeFromResultSet(rs);

            if (v.size() != 0) {
                return v.firstElement();
            } else {
                return null;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void createPermission(Permission p) {

        String sql = "INSERT INTO notizbuch.permission(author_id, permission_level, type, :targetid, beneficiary_id) VALUES (?, ?, ?, ?, ?)";
        String replaceString;

        switch (p.getSharedObject().getType()) {
            case 'b':
                replaceString = "notebook_id";
                break;
            case 'n':
                replaceString = "note_id";
                break;
            default:
                throw new RuntimeException("Invalid Type for current database configuration!");
        }

        sql = sql.replaceAll(":targetid", replaceString);

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, p.getAuthor().getId());
            ps.setInt(2, p.getLevelAsInt());
            ps.setString(3, Character.toString(p.getSharedObject().getType()));
            ps.setInt(4, p.getSharedObject().getId());
            ps.setInt(5, p.getBeneficiary().getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Vector<Permission> getAllPermissionsFor(Shareable s) {

        Vector<Permission> v = new Vector<>();
        String sql = "SELECT * FROM notizbuch.permission WHERE :targetTable = ?";
        String replaceString;

        switch (s.getType()) {
            case 'b':
                replaceString = "notebook_id";
                break;
            case 'n':
                replaceString = "note_id";
                break;
            default:
                throw new RuntimeException("Invalid Shareable");
        }

        sql = sql.replaceAll(":targetTable", replaceString);

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, s.getId());
            ResultSet rs = ps.executeQuery();

            return makeFromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    public Vector<Permission> makeFromResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {

        Vector<Permission> v = new Vector<>();

        while (rs.next()) {

            Level l;
            switch (rs.getInt("permission_level")) {
                case 10:
                    l = Level.READ;
                    break;
                case 20:
                    l = Level.EDIT;
                    break;
                case 30:
                    l = Level.DELETE;
                    break;
                default:
                    l = Level.NONE;
            }

            Permission p = new Permission(rs.getInt("id"), l);
            UserInfoMapper uim = UserInfoMapper.getUserInfoMapper();
            NoteMapper nm = NoteMapper.getNoteMapper();
            NoteBookMapper nbm = NoteBookMapper.getNoteBookMapper();

            p.setBeneficiary(uim.findById(rs.getInt("beneficiary_id")));

            p.setAuthor(uim.findById(rs.getInt("author_id")));

            Shareable sharedObject;
            char type = rs.getString("type").charAt(0);
            int id;
            switch (type) {
                case 'b':
                    id = rs.getInt("notebook_id");
                    sharedObject = nbm.findById(id);
                    break;
                case 'n':
                    id = rs.getInt("note_id");
                    sharedObject = nm.findById(id);
                    break;
                default:
                    throw new RuntimeException();
            }

            p.setSharedObject(sharedObject);

            v.add(p);

        }

        return v;

    }

    public Vector<Permission> getAllPermissionsCreatedBy(UserInfo u) {

        try {

            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM notizbuch.permission " + "WHERE author_id = ?");
            ps.setInt(1, u.getId());

            return makeFromResultSet(ps.executeQuery());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Vector<Permission> getAllPermissionsFor(UserInfo u) {

        try {

            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM notizbuch.permission " + "WHERE beneficiary_id = ?");
            ps.setInt(1, u.getId());

            return makeFromResultSet(ps.executeQuery());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void savePermission(Permission p) {

        try {

            PreparedStatement ps = connection
                    .prepareStatement("UPDATE notizbuch.permission SET permission_level = ? WHERE id = ?");
            ps.setInt(1, p.getLevelAsInt());
            ps.setInt(2, p.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deletePermission(Permission p) {

        String sql = "DELETE FROM notizbuch.permission WHERE id = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public Vector<Permission> getAllPermissions() {

        Statement s;
        try {

            s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM notizbuch.permission");
            return makeFromResultSet(rs);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}