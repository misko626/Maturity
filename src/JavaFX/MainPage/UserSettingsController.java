package JavaFX.MainPage;


import JavaFX.Controller;
import JavaFX.Functions;
import JavaFX.connectivity.ConnectionClass;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class UserSettingsController extends Controller implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField numberField;
    @FXML
    private PasswordField oldPassField;
    @FXML
    private PasswordField newPassField;
    @FXML
    private PasswordField newPassField2;
    @FXML
    private Button updateButton;
    @FXML
    private Button updatePassButton;
    private String name, surname, email, number;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private MainPageController rodic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            label.setText("Používateľ: "+user.getName());
            setFieldsText();
        });
    }

    public void setFieldsText() {
        nameField.setText(user.getName());
        surnameField.setText(user.getSurname());
        emailField.setText(user.getEmail());
        numberField.setText(user.getNumber());
    }
    public void onClickUpdate() throws IOException {
        name = nameField.getText();
        surname = surnameField.getText();
        email = emailField.getText();
        number = numberField.getText();
        if (name.equals(user.getName()) && surname.equals(user.getSurname()) && email.equals(user.getEmail()) && number.equals(user.getNumber())) {
            alert.setTitle("Info");
            alert.setHeaderText("Žiadna zmena");
            alert.setContentText("Nezmenil si žiadne údaje");
            alert.showAndWait();
        } else if (nameField.getText().equals("")) {
            alert.setTitle("Info");
            alert.setHeaderText("Nezadal si depo");
            alert.setContentText("Zadaj názov depa, ktoré chceš updatovat");
            alert.showAndWait();
        } else if (surnameField.getText().equals("")) {
            alert.setTitle("Info");
            alert.setHeaderText("Nezadaný prostriedok");
            alert.setContentText("Zadaj či chceš kolobežku alebo bicykel");
            alert.showAndWait();
        } else if (emailField.getText().equals("")) {
            alert.setTitle("Info");
            alert.setHeaderText("Nezadaný čas");
            alert.setContentText("Zadaj čas");
            alert.showAndWait();
        } else {

            ConnectionClass conn = new ConnectionClass();
            conn.updateUserForSettings(user.getId(), nameField.getText(), surnameField.getText(), emailField.getText(), numberField.getText());

            alert.setTitle("Výstraha!");
            alert.setHeaderText("Zmenené dáta o uživateľovi");
            alert.setContentText("Pre načítanie zmien sa nanovo prihláste");
            alert.showAndWait();

            Stage stage = (Stage) surnameField.getScene().getWindow();
            rodic.onClickLogout();
            stage.close();

            nameField.clear();
            surnameField.clear();
            emailField.clear();
            numberField.clear();

        }
    }
    public void onClickUpdatePass() throws IOException {
        if (oldPassField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si stare heslo");
            alert.setContentText("Pre zmenu hesla je potrebné zadať staré heslo");
            alert.showAndWait();
        } else if (newPassField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si nové heslo");
            alert.setContentText("Pre zmenu hesla je potrebné zadať nové heslo");
            alert.showAndWait();
        } else if (newPassField2.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si znova nové heslo");
            alert.setContentText("Pre zmenu hesla je potrebné zadať znova nové heslo");
            alert.showAndWait();
        } else {
            if (user.getPassword().equals(oldPassField.getText())) {
                if (newPassField.getText().equals(newPassField2.getText())) {

                    ConnectionClass conn = new ConnectionClass();
                    conn.updateUser(user.getId(), user.getName(), user.getSurname(), user.getEmail(), newPassField2.getText(), user.getUserPoints());

                    alert.setTitle("Výstraha!");
                    alert.setHeaderText("Zmenené");
                    alert.setContentText("Zmenené heslo užívateľa");
                    alert.showAndWait();
                } else {
                    alert.setTitle("Výstraha!");
                    alert.setHeaderText("Nezhodné hesla");
                    alert.setContentText("Nové hesla sa nezhodujú");
                    alert.showAndWait();
                }
            } else {
                alert.setTitle("Výstraha!");
                alert.setHeaderText("Nezhodné hesla");
                alert.setContentText("Zadané staré heslo sa nezhodujé s terajším heslom");
                alert.showAndWait();
            }
        }
    }

    public void setRodic(MainPageController rodic) {
        this.rodic = rodic;
    }
    public void onClickClose(){
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();

    }
}
