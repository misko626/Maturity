package JavaFX.AdminPage.Mesta;

import JavaFX.Functions;
import JavaFX.Entity.Mesta;
import JavaFX.NewScene;
import JavaFX.connectivity.ConnectionClass;
import animatefx.animation.Pulse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

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
    public Button buttonObjednavky;
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
    double xOffset, yOffset;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Users/adminPageUsersExtended.fxml"));
        Functions.openNewScene(stage, loader, "Admin Page");
    }
    public void onClickButtonObjednavky() throws IOException {
        Stage stage = (Stage) buttonKosice.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Objednavky/adminPageObjednavkyExtended.fxml"));
        Functions.openNewScene(stage, loader, "Admin Page");

    }

    public void onClickButtonPresov() {
        new Pulse(tablePresov).play();
        data.clear();
        mesto = "PREŠOV";


        tablePresov.setItems(Functions.updateTable(mesto));

        buttonPresov.setStyle("-fx-background-color:  #1ABC9C;");
        buttonKosice.setStyle("-fx-background-color:    #f2f2f2; -fx-border-color: black; -fx-border-width: 0 0 1px 0");
        buttonLevoca.setStyle("-fx-background-color:    #f2f2f2; -fx-border-color: black; -fx-border-width: 0 0 1px 0");
        label.setText("Prešov");

    }

    public void onClickButtonKosice() {
        new Pulse(tablePresov).play();
        mesto = "KOSICE";
        data.clear();
        tablePresov.setItems(Functions.updateTable(mesto));
        buttonKosice.setStyle("-fx-background-color:  #1ABC9C;");
        buttonPresov.setStyle("-fx-background-color:    #f2f2f2; -fx-border-color: black; -fx-border-width: 0 0 1px 0");
        buttonLevoca.setStyle("-fx-background-color:    #f2f2f2; -fx-border-color: black; -fx-border-width: 0 0 1px 0");
        label.setText("Košice");

    }
    public void onClickButtonLevoca() {
        new Pulse(tablePresov).play();
        mesto = "LEVOCA";
        data.clear();
        //Naplnenie tabulky udajmi
        tablePresov.setItems(Functions.updateTable(mesto));
       buttonLevoca.setStyle("-fx-background-color:  #1ABC9C;");
        buttonKosice.setStyle("-fx-background-color:    #f2f2f2; -fx-border-color: black; -fx-border-width: 0 0 1px 0");
        buttonPresov.setStyle("-fx-background-color:    #f2f2f2; -fx-border-color: black; -fx-border-width: 0 0 1px 0");
        label.setText("Levoča");
    }
    public void update() throws IOException {
        System.out.println(tablePresov.getSelectionModel().getSelectedItem().getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./updateMestaExtended.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        UpdateMestaController updateMesto = loader.getController();
        updateMesto.fillFieldsMesto(tablePresov.getSelectionModel().getSelectedItem());
        updateMesto.setMesto(mesto);
        updateMesto.setRodic(this);
        stage.setTitle("Update user");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        movement(root,stage);
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
        NewScene.i.openNewScene2("login/loginExtended.fxml");
        Stage stage = (Stage)buttonKosice.getScene().getWindow();
        stage.close();
    }

    public void addDepo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./addDepoExtended.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        AddDepoController addDepoController = loader.getController();
        addDepoController.setRodic(this);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        movement(root,stage);

    }

    private void movement(Parent root, Stage stage){
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void onClickClose(){
        Stage stage = (Stage) tablePresov.getScene().getWindow();
        stage.close();
    }


}