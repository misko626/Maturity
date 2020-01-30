package JavaFX.AdminPage.Users;

//import JavaFX.TableLists.UsersList;

import JavaFX.Functions;
import JavaFX.Entity.User;
import JavaFX.NewScene;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageUsersController implements Initializable {

    @FXML
    public TableView<User> tableUsers;
    @FXML
    public TableColumn<?, ?> tableColumnId;
    @FXML
    public TableColumn<?, ?> tableColumnName;
    @FXML
    public TableColumn<?, ?> tableColumnSurname;
    @FXML
    public TableColumn<?, ?> tableColumnEmail;
    @FXML
    public TableColumn<?, ?> tableColumnPassword;
    @FXML
    public TableColumn<?, ?> tableColumnNumber;
    @FXML
    public TableColumn<?, ?> tableColumnUserPoints;
    @FXML
    public TableColumn<?, ?> tableColumnMoney;
    @FXML
    public Button presovButton;
    @FXML
    public Button buttonKosice;
    @FXML
    public Button pouzivateliaButton;
    @FXML
    public ImageView closeButton;
    @FXML
    public ImageView logoutImage;
    @FXML
    public Pane sideMenuPane;
    @FXML
    public Pane upPane;

    int p =0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            new Pulse(closeButton).play();
            new Pulse(tableUsers).play();

            p=1;
        if (p==1) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(700), sideMenuPane);
            tt.setByX(162);
            tt.setAutoReverse(true);
            tt.play();

        }


        pouzivateliaButton.setStyle("-fx-background-color:  #1ABC9C;");
        setCellTable();
        //naplnenie tabulky udajmi
        tableUsers.setItems(Functions.updateTableUsers());
        });
    }

    public void setCellTable() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableColumnUserPoints.setCellValueFactory(new PropertyValueFactory<>("userPoints"));
        tableColumnMoney.setCellValueFactory(new PropertyValueFactory<>("money"));
    }

    public void onClickButtonPresov(ActionEvent event) throws IOException {
        Stage stage = (Stage) presovButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Mesta/adminPageMestaExtended.fxml"));
        Functions.openNewScene(stage, loader, "Admin page");
    }

    public void onClickButtonObjednavky() throws IOException {
        Stage stage = (Stage) buttonKosice.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Objednavky/adminPageObjednavkyExtended.fxml"));
        Functions.openNewScene(stage, loader, "Admin Page");
    }

    public void update() throws IOException {
        System.out.println(tableUsers.getSelectionModel().getSelectedItem().getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./updateUserExtended.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        UpdateUserController updateUser = loader.getController();
        updateUser.fillFieldsUser(tableUsers.getSelectionModel().getSelectedItem());
        updateUser.setRodic(this);
        stage.setTitle("Update user");
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void delete() {
        Functions.deleteFromTableUsers(tableUsers.getSelectionModel().getSelectedItem().getId());
        System.out.println("Vymazane depo s id " + tableUsers.getSelectionModel().getSelectedItem().getId());
        tableUsers.setItems(Functions.updateTableUsers());

    }

    public TableView<User> getTableUsers() {
        return tableUsers;
    }

    public void onClickLogout() throws IOException {
        NewScene.i.openNewScene2("login/loginExtended.fxml");
        Stage stage = (Stage)tableUsers.getScene().getWindow();
        stage.close();
    }

    public void onClickClose(){
        Stage stage = (Stage) tableUsers.getScene().getWindow();
        stage.close();
    }
}
