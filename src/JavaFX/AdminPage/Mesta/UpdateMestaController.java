package JavaFX.AdminPage.Mesta;

import JavaFX.Functions;
import JavaFX.Entity.Mesta;
import JavaFX.connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateMestaController implements Initializable {

    @FXML
    private TextField depoField;
    @FXML
    private TextField kolobezkyField;
    @FXML
    private TextField bicykleField;
    @FXML
    private Button updateButton;
    public int id;
    private String mesto;
    private AdminPageMestaController rodic;
    Alert alert = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void fillFieldsMesto(Mesta mesto) {
        id = mesto.getId();
        depoField.setText(mesto.getDepo());
        kolobezkyField.setText(String.valueOf(mesto.getKolobezky()));
        bicykleField.setText(String.valueOf(mesto.getBicykle()));
    }

    public void updateMesta() {
        if (depoField.getText().equals("")) {
            System.out.println("Nezadane depo");
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si depo");
            alert.setContentText("Zadaj názov depa, ktoré chceš updatovat");
            alert.showAndWait();
        } else if (kolobezkyField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný počet kolobežiek");
            alert.setContentText("Zadaj koľko chceš prideliť kolobežiek updatovanému depu");
            alert.showAndWait();
        } else if (bicykleField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný počet bicyklov");
            alert.setContentText("Zadaj koľko chceš prideliť bicyklov updatoavnému depu");
            alert.showAndWait();
        } else {
            ConnectionClass conn = new ConnectionClass();
            conn.updateDepa(id, depoField.getText(), Integer.parseInt(kolobezkyField.getText()), Integer.parseInt(bicykleField.getText()), mesto);

            Stage stage = (Stage) updateButton.getScene().getWindow();
            rodic.getTablePresov().setItems(Functions.updateTable(mesto));
            stage.close();
        }
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public void setRodic(AdminPageMestaController rodic) {
    this.rodic = rodic;
    }
    public void onClickClose(){
        Stage stage = (Stage) depoField.getScene().getWindow();
        stage.close();
    }


}
