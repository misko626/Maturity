package JavaFX.login;

import JavaFX.Functions;
import JavaFX.Entity.User;
import JavaFX.NewScene;
import JavaFX.connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Button loginButton;
    @FXML
    public TextField loginEmailField;
    @FXML
    public TextField loginPasswordField;
    @FXML
    public Button joinUs;
    @FXML
    public Label loginError;
    Alert alert = new Alert(Alert.AlertType.WARNING);

    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String adminMeno = "admin";
    String adminHeslo = "admin";

    public void onClickLogin(ActionEvent envent) throws IOException {

        if (loginEmailField.getText().equals(adminMeno) && loginPasswordField.getText().equals(adminHeslo)) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AdminPage/Users/adminPageUsers.fxml"));
            Functions.openNewScene(stage, loader, "Admin page");
        } else {
            String emailLogin = loginEmailField.getText().toString();
            String passwordLogin = loginPasswordField.getText().toString();
            String sql = "SELECT * from USERS WHERE (email = ? and password = ?)";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, emailLogin);
                preparedStatement.setString(2, passwordLogin);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    loginError.setText("Nesprávne Meno alebo Heslo");
                } else {
                    User user = new User(resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));

                    if (resultSet.getInt(7) == 0) {
                        alert.setTitle("Výstraha!");
                        alert.setHeaderText("Nezodpovedný užívateľ");
                        alert.setContentText("Váš účet bol zablokovaný!!!\nKontaktujte administrátora.");
                        alert.showAndWait();
                    } else {
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainPage/mainPageExtended.fxml"));
                        Functions.openNewSceneWithUser(stage, user, loader, "Main page");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onClickJoinUs(ActionEvent event) throws IOException {
       /* Stage stage = (Stage) joinUs.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Registration/registrationExtended.fxml"));
        Functions.openNewScene(stage, loader, "Registrácia");*/
        NewScene.i.openNewScene2("Registration/registrationExtended.fxml");
        Stage stage = (Stage)joinUs.getScene().getWindow();
        stage.close();
    }
    public void onClickClose(){
        Stage stage = (Stage) joinUs.getScene().getWindow();
        stage.close();
    }
}
