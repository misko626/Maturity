package JavaFX.AdminPage.Objednavky;

import JavaFX.Entity.Objednavky;
import JavaFX.Functions;
import JavaFX.NewScene;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageObjednavkyController implements Initializable {

    @FXML
    private ImageView logoutImage;
    @FXML
    private Button buttonUsers;
    @FXML
    private Button objednavkyButton;
    @FXML
    public TableView<Objednavky> tableObjednavky;
    @FXML
    public TableColumn<?, ?> tableColumnId;
    @FXML
    public TableColumn<?, ?> tableColumnDepo;
    @FXML
    public TableColumn<?, ?> tableColumnKolobezka;
    @FXML
    public TableColumn<?, ?> tableColumnEmail;
    @FXML
    public TableColumn<?, ?> tableColumnCasOd;
    @FXML
    public TableColumn<?, ?> tableColumnHodiny;
    @FXML
    public TableColumn<?, ?> tableColumnMesto;

    double xOffset,yOffset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            objednavkyButton.setStyle("-fx-background-color:  #1ABC9C;");
            setCellTable();
            tableObjednavky.setItems(Functions.updateTableObjednavky());
        });
    }

    public void setCellTable() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnDepo.setCellValueFactory(new PropertyValueFactory<>("depo"));
        tableColumnKolobezka.setCellValueFactory(new PropertyValueFactory<>("kolobezka"));
        tableColumnCasOd.setCellValueFactory(new PropertyValueFactory<>("cas"));
        tableColumnHodiny.setCellValueFactory(new PropertyValueFactory<>("hodiny"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        tableColumnMesto.setCellValueFactory(new PropertyValueFactory<>("mesto"));
    }

    public void onClickPouzivateliaButton() throws IOException {
        Stage stage = (Stage) tableObjednavky.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Users/adminPageUsersExtended.fxml"));
        Functions.openNewScene(stage, loader, "Admin Page");
    }

    public void onClickButtonPresov() throws IOException {

        Stage stage = (Stage) tableObjednavky.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Mesta/adminPageMestaExtended.fxml"));
        Functions.openNewScene(stage, loader, "Admin page");
    }

    public void onClickLogout() throws IOException {
        NewScene.i.openNewScene2("login/loginExtended.fxml");
        Stage stage = (Stage)tableObjednavky.getScene().getWindow();
        stage.close();
    }

    public TableView<Objednavky> getTableObjednavky() {
        return tableObjednavky;
    }

    public void update() throws IOException {
        System.out.println(tableObjednavky.getSelectionModel().getSelectedItem().getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./updateObjednavkyExtended.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);

        UpdateObjednavkyController updateObjednavkyController = loader.getController();
        updateObjednavkyController.fillFieldsObjednavky(tableObjednavky.getSelectionModel().getSelectedItem());
        updateObjednavkyController.setRodic(this);
        stage.setTitle("Update user");
        stage.setResizable(false);
        stage.setScene(scene);
        movement(root,stage);
        stage.show();
    }

    public void delete() {
        Functions.deleteFromTableObjednavky(tableObjednavky.getSelectionModel().getSelectedItem().getId());
        System.out.println("Vymazane depo s id " + tableObjednavky.getSelectionModel().getSelectedItem().getId());
        tableObjednavky.setItems(Functions.updateTableObjednavky());

    }
    public void onClickClose(){
        Stage stage = (Stage) tableObjednavky.getScene().getWindow();
        stage.close();
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
}
