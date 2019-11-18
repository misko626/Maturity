package JavaFX;

import JavaFX.Entity.Mesta;
import JavaFX.Entity.Objednavky;
import JavaFX.Entity.User;
import JavaFX.connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Functions {

    private static ConnectionClass connectionClass = new ConnectionClass();
    private static Connection connection = connectionClass.getConnection();
    private static PreparedStatement statement = null;
    private static ResultSet rs = null;

    public static void openNewSceneWithUser(Stage oldStage, User user, FXMLLoader loader, String title) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Controller controller = loader.getController();
        controller.setUser(user);
        oldStage.setTitle(title);
        oldStage.setScene(scene);
        oldStage.show();
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                oldStage.setX(event.getScreenX() - xOffset[0]);
                oldStage.setY(event.getScreenY() - yOffset[0]);
            }
        });
    }

    public static void openNewScene(Stage oldStage, FXMLLoader loader, String title) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        oldStage.setTitle(title);
        oldStage.setScene(scene);
        oldStage.show();
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                oldStage.setX(event.getScreenX() - xOffset[0]);
                oldStage.setY(event.getScreenY() - yOffset[0]);
            }
        });
    }




    public static ObservableList<Mesta> updateTable(String mesto) {
        //list na zobrazovanie v tabulkach
        ObservableList<Mesta> data = FXCollections.observableArrayList();
        try {
            statement = connection.prepareStatement("Select * from " + mesto + " ;");
            rs = statement.executeQuery();
            while (rs.next()) {
                data.add(new Mesta(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<User> updateTableUsers() {
        ObservableList<User> data = FXCollections.observableArrayList();
        try {
            statement = connection.prepareStatement("Select * from   USERS ;");
            rs = statement.executeQuery();
            while (rs.next()) {
                data.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7)));
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<Objednavky> updateTableObjednavky() {
        ObservableList<Objednavky> data = FXCollections.observableArrayList();
        try {
            statement = connection.prepareStatement("Select * from   objednavky ;");
            rs = statement.executeQuery();
            while (rs.next()) {

                data.add(new Objednavky(rs.getInt(1), rs.getString(2), rs.getBoolean(3),
                        rs.getLong(4), rs.getInt(5), rs.getString(6), rs.getString(7)));
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void deleteFromTableMesta(String mesto, int id) {
        try {
            statement = connection.prepareStatement("DELETE from " + mesto + " where depo__id = " + id + ";");
            statement.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromTableObjednavky(int id) {
        try {
            statement = connection.prepareStatement("DELETE from objednavky where id_objednavky = " + id + ";");
            statement.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromTableUsers(int id) {
        try {
            statement = connection.prepareStatement("DELETE from users where user_id = " + id + ";");
            statement.executeUpdate();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromTableObjednavky(String email) {
        try {
            statement = connection.prepareStatement("DELETE from objednavky where user_email='" + email + "';");
            statement.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }




}
