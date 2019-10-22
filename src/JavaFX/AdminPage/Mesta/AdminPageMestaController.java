package JavaFX.AdminPage.Mesta;

import JavaFX.Functions;
import JavaFX.Entity.Mesta;
import JavaFX.NewScene;
import JavaFX.connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminPageMestaController implements Initializable {


    @FXML
    public TableView<Mesta> tablePresov;
    @FXML
    public TableColumn<?, ?> tableColumnId;
    @FXML
    public TableColumn<?, ?> tableColumnDepo;
    @FXML
    public TableColumn<?, ?> tableColumnKolobezky;
    @FXML
    public TableColumn<?, ?> tableColumnBicykle;
    @FXML
    public Button buttonKosice;
    @FXML
    public Button buttonLevoca;
    @FXML
    public Button buttonPresov;
    @FXML
    public Button buttonUsers;
    @FXML
    public ImageView logoutImage;
    @FXML
    public Label label;
    private ResultSet rs = null;
    private ObservableList<Mesta> data;
    private ConnectionClass connectionClass = new ConnectionClass();

    Connection connection = connectionClass.getConnection();
    PreparedStatement statement = null;

    static String mesto = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        setCellTable();
        onClickButtonPresov();
    }
    public void setCellTable() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnDepo.setCellValueFactory(new PropertyValueFactory<>("depo"));
        tableColumnKolobezky.setCellValueFactory(new PropertyValueFactory<>("kolobezky"));
        tableColumnBicykle.setCellValueFactory(new PropertyValueFactory<>("bicykle"));
    }
    public void onClickButtonUsers(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonKosice.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Users/adminPageUsers.fxml"));
        Functions.openNewScene(stage, loader, "Admin Page");
    }
    public void onClickButtonObjednavky() throws IOException {
        Stage stage = (Stage) buttonKosice.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Objednavky/adminPageObjednavky.fxml"));
        Functions.openNewScene(stage, loader, "Admin Page");

    }

    public void onClickButtonPresov() {
        data.clear();
        mesto = "PREŠOV";


        tablePresov.setItems(Functions.updateTable(mesto));

        buttonPresov.setStyle("-fx-background-color:  #74EB15;");
        buttonKosice.setStyle("-fx-background-color:   #c3c6cc;");
        buttonLevoca.setStyle("-fx-background-color:   #c3c6cc;");
        label.setText("Prešov");

    }

    public void onClickButtonKosice() {
        mesto = "KOSICE";
        data.clear();
        tablePresov.setItems(Functions.updateTable(mesto));
        buttonKosice.setStyle("-fx-background-color:  #74EB15;");
        buttonPresov.setStyle("-fx-background-color:   #c3c6cc;");
        buttonLevoca.setStyle("-fx-background-color:   #c3c6cc;");
        label.setText("Košice");

    }
    public void onClickButtonLevoca() {
        mesto = "LEVOCA";
        data.clear();
        //Naplnenie tabulky udajmi
        tablePresov.setItems(Functions.updateTable(mesto));
        buttonLevoca.setStyle("-fx-background-color:  #74EB15;");
        buttonKosice.setStyle("-fx-background-color:   #c3c6cc;");
        buttonPresov.setStyle("-fx-background-color:   #c3c6cc;");
        label.setText("Levoča");
    }
    public void update() throws IOException {
        System.out.println(tablePresov.getSelectionModel().getSelectedItem().getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./updateMesta.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        UpdateMestaController updateMesto = loader.getController();
        updateMesto.fillFieldsMesto(tablePresov.getSelectionModel().getSelectedItem());
        updateMesto.setMesto(mesto);
        updateMesto.setRodic(this);
        stage.setTitle("Update user");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void delete() {
        Functions.deleteFromTableMesta(mesto, tablePresov.getSelectionModel().getSelectedItem().getId());
        System.out.println("Vymazane depo s id " + tablePresov.getSelectionModel().getSelectedItem().getId());
        tablePresov.setItems(Functions.updateTable(mesto));

    }

    public TableView<Mesta> getTablePresov() {
        return tablePresov;
    }

    public void onClickLogout() throws IOException {
       /* Stage stage = (Stage) logoutImage.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../login/login.fxml"));
        Functions.openNewScene(stage, loader, "Login");*/
        NewScene.i.openNewScene2("login/loginExtended.fxml");
        Stage stage = (Stage)buttonKosice.getScene().getWindow();
        stage.close();
    }

    public void addDepo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./addDepo.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        AddDepoController addDepoController = loader.getController();
        addDepoController.setRodic(this);
        stage.setTitle("Update user");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


}