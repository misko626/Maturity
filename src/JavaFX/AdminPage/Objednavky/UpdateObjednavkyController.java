package JavaFX.AdminPage.Objednavky;

import JavaFX.AdminPage.Users.AdminPageUsersController;
import JavaFX.Entity.Objednavky;
import JavaFX.Entity.User;
import JavaFX.Functions;
import JavaFX.connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateObjednavkyController implements Initializable {

    @FXML
    private TextField depoField;
    @FXML
    private TextField kolobezkaField;
    @FXML
    private TextField casField;
    @FXML
    private TextField hodinyField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField mestoField;
    @FXML
    private Button updateButton;
    private int id;
    private boolean objednavka;
    private long cas;
    private int hodiny;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    private AdminPageObjednavkyController rodic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void fillFieldsObjednavky(Objednavky objednavky){
        objednavka=objednavky.getKolobezka();
        id=objednavky.getId();
        depoField.setText(objednavky.getDepo());
        kolobezkaField.setText((objednavky.getKolobezka())?"true":"false");
        casField.setText(Long.toString(objednavky.getCas()));
        hodinyField.setText(Integer.toString(objednavky.getHodiny()));
        emailField.setText(objednavky.getUser_name());
        mestoField.setText(objednavky.getMesto());
    }

    public void updateObjednavky() {
        if (depoField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si depo");
            alert.setContentText("Zadaj názov depa, ktoré chceš updatovat");
            alert.showAndWait();
        } else if (kolobezkaField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný prostriedok");
            alert.setContentText("Zadaj či chceš kolobežku alebo bicykel");
            alert.showAndWait();
        } else if (casField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný čas");
            alert.setContentText("Zadaj čas");
            alert.showAndWait();
        }else if (hodinyField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný čas");
            alert.setContentText("Zadaj čas");
            alert.showAndWait();
        }else if (emailField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný email");
            alert.setContentText("Zadaj email");
            alert.showAndWait();
        }
        else if (mestoField.getText().equals("")) {
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadaný mesto");
            alert.setContentText("Zadaj mesto");
            alert.showAndWait();
        }else {
            String kolobezka;
            Boolean bKolobezka = objednavka;

            ConnectionClass conn = new ConnectionClass();

            //pretypovanie stringu precitaneho z textfieldu do booleanu
            kolobezka = kolobezkaField.getText();
            if (kolobezka.toLowerCase().equals("true")) {
                bKolobezka = true;
            } else if (kolobezka.toLowerCase().equals("false")) {
                bKolobezka = false;
            }
            cas = Long.parseLong(casField.getText());
            hodiny = Integer.parseInt(hodinyField.getText());

            conn.updateObjednavky(id, depoField.getText(), bKolobezka, cas, hodiny, emailField.getText(), mestoField.getText());

            Stage stage = (Stage) updateButton.getScene().getWindow();
            rodic.getTableObjednavky().setItems(Functions.updateTableObjednavky());
            stage.close();
        }
    }
    public void setRodic(AdminPageObjednavkyController rodic){
        this.rodic=rodic;
    }
}
