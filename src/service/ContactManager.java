package service;

import model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {

    // ── Database settings ─────────────────────────────
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    // Load from config file
    static {
        String url      = "jdbc:mysql://localhost/telephone_directory";
        String user     = "root";
        String password = "";

        try (java.io.InputStream input =
                ContactManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input != null) {
                java.util.Properties prop = new java.util.Properties();
                prop.load(input);
                url      = prop.getProperty("db.url");
                user     = prop.getProperty("db.user");
                password = prop.getProperty("db.password");
            }

        } catch (java.io.IOException e) {
            System.err.println("Config error: " + e.getMessage());
        }

        URL      = url;
        USER     = user;
        PASSWORD = password;
    }

    // ── Constructor ───────────────────────────────────
    public ContactManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = getConnection();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    // ── Get connection ────────────────────────────────
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ── Add contact ───────────────────────────────────
    public boolean addContact(String name, String phone) {
        if (searchContact(name) != null) {
            return false;
        }

        String sql = "INSERT INTO contacts (name, phone) " +
                "VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ps.setString(2, phone.trim());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Add error: " + e.getMessage());
            return false;
        }
    }

    // ── Search contact ────────────────────────────────
    public Contact searchContact(String name) {
        String sql = "SELECT * FROM contacts " +
                "WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Contact(
                        rs.getString("name"),
                        rs.getString("phone")
                );
            }

        } catch (SQLException e) {
            System.err.println("Search error: " + e.getMessage());
        }
        return null;
    }

    // ── Delete contact ────────────────────────────────
    public boolean deleteContact(String name) {
        if (searchContact(name) == null) {
            return false;
        }

        String sql = "DELETE FROM contacts " +
                "WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    // ── Update contact ────────────────────────────────
    public boolean updateContact(String name, String newPhone) {
        if (searchContact(name) == null) {
            return false;
        }

        String sql = "UPDATE contacts " +
                "SET phone = ? " +
                "WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPhone.trim());
            ps.setString(2, name.trim());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Update error: " + e.getMessage());
            return false;
        }
    }

    // ── Get all contacts ──────────────────────────────
    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();

        String sql = "SELECT * FROM contacts ORDER BY name";
        try (Connection conn = getConnection();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Contact(
                        rs.getString("name"),
                        rs.getString("phone")
                ));
            }

        } catch (SQLException e) {
            System.err.println("GetAll error: " + e.getMessage());
        }
        return list;
    }
}