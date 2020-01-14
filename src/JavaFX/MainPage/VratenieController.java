package JavaFX.MainPage;

import JavaFX.Controller;
import JavaFX.Entity.Depo;
import JavaFX.Entity.Objednavky;
import JavaFX.Functions;
import JavaFX.connectivity.ConnectionClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import static JavaFX.MainPage.MainPageController.cisloMesta;
public class VratenieController extends Controller implements Initializable {

    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Button vratButton;
    @FXML
    private Label textVratenie;
    @FXML
    private Label suma;
    @FXML
    private Label zlavaLabel;
    private boolean kolobezka;
    private Map<String, Integer> predoslyPocet = new HashMap<>();
    private Map<String, Depo> setDepa = new HashMap();
    private String mesto;
    private MainPageController caller;
    private Objednavky data = null;
    private long casVratenia;
    private float dlzkaPozicania;
    private int userPoints;
    double cena = -1;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            userPoints = user.getUserPoints();
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT * FROM objednavky WHERE user_email = ?";
            PreparedStatement statement;
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1, user.getEmail());
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    kolobezka = rs.getBoolean(3);
                    data = new Objednavky(rs.getInt(1), rs.getString(2), rs.getBoolean(3),
                            rs.getLong(4), rs.getInt(5), rs.getString(6), rs.getString(7));
                }
                mesto = data.getMesto();

                String sql1 = "SELECT * FROM " + mesto + "; ";

                ObservableList<Depo> depa = FXCollections.observableArrayList();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        depa.add(new Depo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
                        //zistujem z objednavky co mam pozicane , tertnarny operator
                        predoslyPocet.put(resultSet.getString(2), (kolobezka) ? resultSet.getInt(3) : resultSet.getInt(4));
                        //naplnam set depami
                        setDepa.put(resultSet.getString(2), new Depo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] hodnotyChoiceBoxu = new String[depa.size()];
                for (int i = 0; i < depa.size(); i++) {
                    hodnotyChoiceBoxu[i] = depa.get(i).getDepo();
                    System.out.println(hodnotyChoiceBoxu[i]);
                }
                //ziskavanie ktore depo som si vybral v boxe
                choiceBox.getItems().addAll(hodnotyChoiceBoxu);
                choiceBox.setValue(choiceBox.getItems().get(0));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //praca s userpointami
            casVratenia = (System.currentTimeMillis() / 1000);
            System.out.println(casVratenia);
            //dlzka pozicania kolobezky v minutach
            dlzkaPozicania = data.getHodiny();
            System.out.println(dlzkaPozicania);
            float rozdiel = data.getCas() - casVratenia;
            //zistujem ci mam pripocitat user pointy alebo nie
            userPoints = (rozdiel >= 0) ? userPoints + 1 : userPoints - 1;
            if (userPoints > 40) {
                userPoints = 40;
            }
            if (rozdiel >= 0) {
                // vratil na cas
                textVratenie.setText("Vrátil si včas");
                textVratenie.setTextFill(Color.web("#72b91b"));
                if (userPoints > 10) {
                    int zlava = userPoints - 10;
                    zlavaLabel.setText("Získavaš zľavu " + zlava + "%");
                    cena = ((dlzkaPozicania / (60)) / 100) * (100 - zlava);
                    System.out.println("prvy");
                } else {
                    cena = (dlzkaPozicania / (60));
                }
            } else {
                textVratenie.setText("Nevrátil si včas");
                textVratenie.setTextFill(Color.web("red"));
                cena = Math.abs(dlzkaPozicania / 60 + (rozdiel / 3600 * 2)) + 2;
                System.out.println("druhy " + dlzkaPozicania + "   " + rozdiel);
            }
            suma.setText(String.format("%10.2f", cena) + " €");
            System.out.println(cena);
            System.out.println(userPoints);
        });
    }
    public void onClickVratButton() {

        if (user.getMoney()<cena){
            alert.setTitle("Výstraha!");
            alert.setHeaderText("Nízky finančný stav");
            alert.setContentText("Prejdite k operátorovi depa");
            alert.showAndWait();

        }else {
            //updatovanie userpointov usera
            double money = user.getMoney() - cena;
            user.setMoney(money);
            ConnectionClass connectionClass = new ConnectionClass();
            connectionClass.updateUser(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), userPoints, money);
            //vytvorenie objektu aktualneho depa ...vyberanie depa z mapu podla kluca
            Depo aktualneDepo = setDepa.get(choiceBox.getValue());
            if (kolobezka) {
                aktualneDepo.setKolobezky(aktualneDepo.getKolobezky() + 1);
            } else {
                aktualneDepo.setBicykle(aktualneDepo.getBicykle() + 1);
            }


            connectionClass.updateDepa(aktualneDepo.getId(), aktualneDepo.getDepo(), aktualneDepo.getKolobezky(), aktualneDepo.getBicykle(), mesto);
            Functions.deleteFromTableObjednavky(user.getEmail());


            caller.setButtonToLend();

            Stage stage = (Stage) vratButton.getScene().getWindow();
            stage.close();
            System.out.println("cas vratenia " + casVratenia);
        }
    }

    public void setCaller(MainPageController caller) {
        this.caller = caller;
    }

    public void onClickClose(){
        Stage stage = (Stage) vratButton.getScene().getWindow();
        stage.close();
        System.out.println("Ahojadmin   ");
    }
}
