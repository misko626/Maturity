package JavaFX.connectivity;

import java.sql.*;

/**
 * authored MICHAL
 */

public class ConnectionClass {
    public Connection connection;
    public Connection getConnection() {
        String dbName = "lendit";
        String userName = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lendit?serverTimezone=UTC", userName, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void updateUser(Integer id, String name, String surname, String emailemail, String password, int userpoints, double money) {
        getConnection();
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?,user_points = ?,money = ? where user_id= ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, emailemail);
            pstmt.setString(4, password);
            pstmt.setInt(5, userpoints);
            pstmt.setDouble(6,money);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserForSettings(Integer id, String name, String surname, String email, String number) {
        getConnection();
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?,phone_number = ?where user_id= ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, email);
            pstmt.setString(4, number);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDepa(Integer id, String depo, Integer kolobezky, Integer bicykle, String tabulka) {
        getConnection();
        String sql = "UPDATE " + tabulka + " SET depo_name = ?, kolobezky = ?, bicykle = ? where depo__id= ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, depo);
            pstmt.setInt(2, kolobezky);
            pstmt.setInt(3, bicykle);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateObjednavky(Integer id, String depo, Boolean kolobezka, Long cas, int hodiny, String user_name, String mesto) {
        getConnection();
        String sql = "UPDATE objednavky SET depo = ?, kolobezka = ?, cas = ?, hodiny = ?, user_email=?, mesto=? where id_objednavky= ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, depo);
            pstmt.setBoolean(2, kolobezka);
            pstmt.setLong(3, cas);
            pstmt.setInt(4, hodiny);
            pstmt.setString(5, user_name);
            pstmt.setString(6, mesto);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDepo(String depo, int kolobezky, int bicykle, String tabulka) {
        getConnection();
        System.out.println("ahoj");
        String sql = "INSERT INTO " + tabulka + " (depo_name,kolobezky,bicykle) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, depo);
            pstmt.setInt(2, kolobezky);
            pstmt.setInt(3, bicykle);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
