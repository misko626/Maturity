package JavaFX.AdminPage.Mesta;

import JavaFX.Functions;
import JavaFX.connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import static JavaFX.AdminPage.Mesta.AdminPageMestaController.mesto;

public class AddDepoController implements Initializable {

    @FXML
    private Button addDepoButton;
    @FXML
    private TextField depoField;
    @FXML
    private TextField kolobezkyField;
    @FXML
    private TextField bicykleField;
    private String depo;
    private int kolobezky;
    private int bicykle;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    private AdminPageMestaController rodic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void onClickAdd() {
        if (depoField.getText().equals("")) {
            System.out.println("Nezadane depo");
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si depo");
            alert.setContentText("Zadaj názov depa, ktoré chceš oridať");
            alert.showAndWait();
        } else if (kolobezkyField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný počet kolobežiek");
            alert.setContentText("Zadaj koľko chceš prideliť kolobežiek");
            alert.showAndWait();
        } else if (bicykleField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný počet bicyklov");
            alert.setContentText("Zadaj koľko chceš prideliť bicyklov");
            alert.showAndWait();
        } else {
            depo = depoField.getText();
            kolobezky = Integer.parseInt(kolobezkyField.getText());
            bicykle = Integer.parseInt(bicykleField.getText());

            ConnectionClass connectionClass = new ConnectionClass();
            connectionClass.insertDepo(depo, kolobezky, bicykle, mesto);

            Stage stage = (Stage) addDepoButton.getScene().getWindow();
            rodic.getTablePresov().setItems(Functions.updateTable(mesto));
            stage.close();
        }
    }
    public void setRodic(AdminPageMestaController rodic) {
        this.rodic = rodic;
    }

}
