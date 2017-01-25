package de.hdm_stuttgart.huber.itprojekt.server.db;

import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.sql.*;
import java.util.Vector;

/**
 * @see NoteMapper
 */
public class UserInfoMapper extends DataMapper {

    // Statisches Attribut, welches den Singleton-NoteUserMapper enthält
    private static UserInfoMapper userInfoMapper = null;

    // Konstruktor (protected, um unauthorisiertes Instanziieren der Klasse zu
    // verhindern)
    protected UserInfoMapper() throws ClassNotFoundException, SQLException {

    }

    // Öffentliche statische Methode, um den Singleton-NoteUserMapper zu
    // erhalten
    public static UserInfoMapper getUserInfoMapper() {
        if (userInfoMapper == null) {

            try {
                userInfoMapper = new UserInfoMapper();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e.toString());
            }

        }

        return userInfoMapper;
    }

    // create Methode
    // Use register
    @Deprecated
    public UserInfo create(UserInfo noteUser) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO userinfo(FirstName, UserName, SurName, Email, GoogleId) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, noteUser.getFirstName());
            stmt.setString(2, noteUser.getNickname());
            stmt.setString(3, noteUser.getSurName());
            stmt.setString(4, noteUser.getEmailAddress());
            stmt.setString(5, noteUser.getGoogleId());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return findById(rs.getInt(1));
            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
        }
        return noteUser;
    }

    // FINDBYID
    public UserInfo findById(int id) throws ClassNotFoundException, SQLException {

        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM notizbuch.userinfo WHERE id = ?");
            stmt.setInt(1, id);

            // Ergebnis holen
            ResultSet results = stmt.executeQuery();
            if (results.next()) {

                UserInfo noteUser = new UserInfo(results.getInt("id"), results.getString("firstname"),
                        results.getString("username"), results.getString("lastname"), results.getString("email"),
                        results.getString("google_id"));

                return noteUser;

            }

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            return null;
        }

        return null;
    }

    // save-Methode: NoteUser-Objekt wird wiederholt in die DB geschrieben
    public UserInfo save(UserInfo userToSave) throws ClassNotFoundException, SQLException {

        try {

            String sql = "UPDATE notizbuch.userinfo SET username = ?, firstname = ?, lastname = ?, email = ? WHERE google_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, userToSave.getNickname());
            stmt.setString(2, userToSave.getFirstName());
            stmt.setString(3, userToSave.getSurName());
            stmt.setString(4, userToSave.getEmailAddress());
            stmt.setString(5, userToSave.getGoogleId());

            stmt.executeUpdate();

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }

        return findById(userToSave.getId());

    }

    public void delete(UserInfo noteUser) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM notizbuch.userinfo WHERE id = ?");
            stmt.setLong(1, noteUser.getId());
            stmt.executeUpdate();

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public Vector<UserInfo> getAllUserInfos() {

        Vector<UserInfo> result = new Vector<>();

        try {
            // wieso kein prepared Statement mehr?
            // PreparedStatement stmt = con.prepareStatement("SELECT NoteUserId
            // FROM NoteUser");
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM notizbuch.userinfo");

            while (results.next()) {
                UserInfo noteUser = new UserInfo(results.getInt("id"), results.getString("firstname"),
                        results.getString("username"), results.getString("lastname"), results.getString("email"),
                        results.getString("google_id"));
                result.add(noteUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Mapper: found " + Integer.toString(result.size()) + " Users");

        return result;
    }

    public boolean isUserRegistered(String email) {

        try {

            PreparedStatement ps = connection
                    .prepareStatement("SELECT EXISTS(SELECT * FROM notizbuch.userinfo WHERE email = ?) AS does_exist");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getBoolean("does_exist");

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new RuntimeException(e.toString());

        }

        return false;
    }

    /**
     * Registriert einen User mit seiner GoogleId in der Datenbank Diese
     * Funktion registriert nur username, email und GoogleId um Chaos zu
     * vermeiden Später wird dem User nach der Registrierung angeboten, den Rest
     * auch gleich zu setzen
     *
     * @param u
     */
    public void registerUser(UserInfo u) {

        try {

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO notizbuch.userinfo(username, email, google_id)" + " VALUES (?, ?, ?)");

            ps.setString(1, u.getNickname());
            ps.setString(2, u.getEmailAddress());
            ps.setString(3, u.getGoogleId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }

    }

    public UserInfo findUserByGoogleId(String googleId) {

        try {

            PreparedStatement ps = connection.prepareCall("SELECT id FROM notizbuch.userinfo WHERE google_id = ?");
            ps.setString(1, googleId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return findById(rs.getInt("id"));

            } else {

                throw new IllegalArgumentException("Logikfehler: Kein User gefunden");

            }

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public UserInfo findByEmailAdress(String email) {

        try {

            PreparedStatement ps = connection.prepareCall("SELECT id FROM notizbuch.userinfo WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return findById(rs.getInt("id"));

            } else {

                return null;

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Vector<String> getAllEmailAdresses() {

        Vector<String> v = new Vector<>();

        try {

            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT email FROM notizbuch.userinfo");

            while (rs.next()) {
                v.add(rs.getString("email"));
            }

            return v;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;
    }

}
