package JavaFX.AdminPage.Users;

import JavaFX.AdminPage.Mesta.AdminPageMestaController;
import JavaFX.Entity.User;
import JavaFX.Functions;
import JavaFX.connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {
    @FXML
    public TextField nameField;
    @FXML
    public TextField surNameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField userPointsField;
    @FXML
    public Button updateButton;

    private int id;
    private AdminPageUsersController rodic;

    Alert alert = new Alert(Alert.AlertType.WARNING);
    String pass = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


    public void fillFieldsUser(User user){

        id=user.getId();
        nameField.setText(user.getName());
        surNameField.setText(user.getSurname());
        emailField.setText(user.getEmail());
        userPointsField.setText(Integer.toString(user.getUserPoints()));
        pass = user.getPassword();

    }

    public void updateUser() {

        if (nameField.getText().equals("")) {

            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si depo");
            alert.setContentText("Zadaj názov depa, ktoré chceš updatovat");

            alert.showAndWait();
        } else if (surNameField.getText().equals("")) {

            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný prostriedok");
            alert.setContentText("Zadaj či chceš kolobežku alebo bicykel");

            alert.showAndWait();

        } else if (emailField.getText().equals("")) {


            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný čas");
            alert.setContentText("Zadaj čas");

            alert.showAndWait();

        }  else if (userPointsField.getText().equals("")) {


            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný mesto");
            alert.setContentText("Zadaj mesto");

            alert.showAndWait();
        } else {

            ConnectionClass conn = new ConnectionClass();
            conn.updateUser(id, nameField.getText(), surNameField.getText(), emailField.getText(),pass, Integer.parseInt(userPointsField.getText()));

            Stage stage = (Stage) updateButton.getScene().getWindow();
            rodic.getTableUsers().setItems(Functions.updateTableUsers());
            stage.close();

        }
    }

    public void setRodic(AdminPageUsersController rodic){

        this.rodic=rodic;
    }
    public void onClickClose(){
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
    }


}
