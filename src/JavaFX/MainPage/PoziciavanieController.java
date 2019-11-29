package JavaFX.MainPage;

import JavaFX.Controller;
import JavaFX.Entity.Depo;
import JavaFX.connectivity.ConnectionClass;
import com.jfoenix.controls.JFXTimePicker;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import static JavaFX.MainPage.MainPageController.cisloMesta;

public class PoziciavanieController extends Controller implements Initializable {

    @FXML
    private Label label;
    @FXML
    private JFXTimePicker timeOd;
    @FXML
    private JFXTimePicker timeDo;
    @FXML
    private AnchorPane root;
    @FXML
    private ChoiceBox choiceBoxCas;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Label bicykleDostupne;
    @FXML
    private Label kolobezkyDostupne;
    @FXML
    private ImageView btnBicykel;
    @FXML
    private ImageView btnKolobezka;
    @FXML
    private Button pozicajBtn;
    private List<Depo> depa = new LinkedList<>();
    private String aktualneDepo;
    private Boolean kolobezka = null;
    private int depoId;
    private int kolobezky;
    private int bicykle;
    private String mesto;
    private Long cas;
    private int akoDlho;
    private MainPageController caller;
    Alert alert = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String sql = "SELECT * FROM prešov ";
        switch (cisloMesta) {
            case 0:
                sql = "SELECT * FROM prešov ";
                break;
            case 1:
                sql = "SELECT * FROM kosice";
                break;
            case 2:
                sql = "SELECT *FROM levoca";
                break;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                depa.add(new Depo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] hodnotyChoiceBoxu = new String[depa.size()];
        for (int i = 0; i < depa.size(); i++) {
            hodnotyChoiceBoxu[i] = depa.get(i).getDepo();
        }
        //ziskavanie ktore depo som si vybral v boxe
        choiceBox.getItems().addAll(hodnotyChoiceBoxu);
        choiceBox.setValue(0);
        choiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                    aktualneDepo = newValue.toString();
                    System.out.println(aktualneDepo);

                    for (Depo depo : depa) {
                        if (depo.getDepo().equals(newValue)) {
                            depoId = depo.getId();
                            kolobezky = depo.getKolobezky();
                            bicykle = depo.getBicykle();
                            if (depo.getBicykle() > 0) {
                                bicykleDostupne.setText("Dostupne");
                                bicykleDostupne.setTextFill(Color.web("#74eb15"));
                            } else {
                                bicykleDostupne.setText("Nedostupne");
                                bicykleDostupne.setTextFill(Color.web("#d81313"));
                                btnBicykel.setDisable(true);
                            }
                            if (depo.getKolobezky() > 0) {
                                kolobezkyDostupne.setText("Dostupne");
                                kolobezkyDostupne.setTextFill(Color.web("#74eb15"));
                            } else {
                                kolobezkyDostupne.setText("Nedostupne");
                                kolobezkyDostupne.setTextFill(Color.web("#d81313"));
                                btnKolobezka.setDisable(true);
                            }
                        }
                    }
                    String[] hodnotyCasBoxu = new String[10];
                    hodnotyCasBoxu[0] = "1 min";
                    hodnotyCasBoxu[1] = "30 min";
                    hodnotyCasBoxu[2] = "60 min";
                    hodnotyCasBoxu[3] = "90 min";
                    hodnotyCasBoxu[4] = "2 hod";
                    hodnotyCasBoxu[5] = "4 hod";
                    hodnotyCasBoxu[6] = "6 hod";
                    hodnotyCasBoxu[7] = "8 hod";
                    hodnotyCasBoxu[8] = "10 hod";
                    hodnotyCasBoxu[9] = "12 hod";
                    choiceBoxCas.getItems().clear();
                    choiceBoxCas.getItems().addAll(hodnotyCasBoxu);
                    choiceBoxCas.setValue(choiceBoxCas.getItems().get(0));
                });
        Platform.runLater(() -> {
            label.setText("Užívateľ: "+user.getName());
        });
    }

    public void nastavNaKolobezku() {
        kolobezka = true;
        System.out.println("kolobezka " + kolobezka);
        btnBicykel.setImage(new Image(getClass().getResource("../img/bikeBlack.png").toString()));
        btnKolobezka.setImage(new Image(getClass().getResource("../img/kolobezka.png").toString()));
    }

    public void nastavNaBicykel() {
        kolobezka = false;
        System.out.println("kolobezka " + kolobezka);
        btnBicykel.setImage(new Image(getClass().getResource("../img/bicykel.png").toString()));
        btnKolobezka.setImage(new Image(getClass().getResource("../img/kolobezkaBlack.png").toString()));
    }

    public void onClickPozicaj() {
        switch (cisloMesta) {
            case 0:
                mesto = "prešov";
                break;
            case 1:
                mesto = "kosice";
                break;
            case 2:
                mesto = "levoca";
                break;
        }

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        if (choiceBox.getValue().equals(0)) {
            System.out.println("Nezadane depo");
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezvolené depo");
            alert.setContentText("Zadaj depo, z ktorého si chceš kolobežku albo bicykel požičať");
            alert.showAndWait();
        } else if (kolobezka == null) {
            System.out.println("Nevybral si si ci chces kolobezku alebo bicykel");
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezvolený prostriedok");
            alert.setContentText("Zadaj, či si chceš požičať kolobežku alebo bicykel");
            alert.showAndWait();
        } else if (kolobezky == 0 && bicykle == 0) {
            System.out.println("Niesu dostupne ani kolobezky ani bicykle ");
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Dostupnosť.");
            alert.setContentText("Žaiľbohu na tomto depe niesu dostupné ani kolobežky ani bicykle");
            alert.showAndWait();
        } else if (choiceBoxCas.getValue().equals(0)) {
            System.out.println("Nezadany cas");
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nezadal si na ako dlho ");
            alert.setContentText("Zadaj ako dlho chces mat požičanú kolobežku alebo bicykel");
            alert.showAndWait();
        } else {
            akoDlho = Integer.parseInt(choiceBoxCas.getValue().toString().split(" ")[0]);
            int koeficient = (choiceBoxCas.getValue().toString().split(" ")[1].equals("min")) ? 60 : 3600;
            double hodiny = (akoDlho * koeficient) / 60;
            cas = System.currentTimeMillis() / 1000;
            cas = cas + (akoDlho * koeficient);

            String sql = "INSERT INTO objednavky (depo,kolobezka,cas,hodiny,user_email,mesto)VALUES (?,?,?,?,?,?)";
            PreparedStatement statement;
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1, aktualneDepo);
                statement.setBoolean(2, kolobezka);
                statement.setLong(3, cas);
                statement.setDouble(4, hodiny);
                statement.setString(5, user.getEmail());
                statement.setString(6, mesto);
                int i = statement.executeUpdate();
                if (i == 1) {
                    System.out.println("Data pridane");
                }
            } catch (SQLException e) {
            }
            //vid ternarny operator.. ak kolobezka je true tak sa vykona ze kolobezky -1
            kolobezky = (kolobezka) ? kolobezky - 1 : kolobezky;
            bicykle = (!kolobezka) ? bicykle - 1 : bicykle;

            ConnectionClass conn = new ConnectionClass();
            conn.updateDepa(depoId, aktualneDepo, kolobezky, bicykle, mesto);

            caller.setButtonToReturn();

            Stage stage = (Stage) pozicajBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void setCaller(MainPageController caller) {
        this.caller = caller;
    }
    public void onClickClose(){
        Stage stage = (Stage) pozicajBtn.getScene().getWindow();
        stage.close();
        System.out.println("Ahojadmin   ");
    }
}
