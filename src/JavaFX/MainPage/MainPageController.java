package JavaFX.MainPage;

import JavaFX.Controller;
import JavaFX.Entity.Mesta;
import JavaFX.Functions;
import JavaFX.NewScene;
import JavaFX.connectivity.ConnectionClass;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MainPageController extends Controller implements Initializable {

    @FXML
    public Label menoLabel;
    @FXML
    public ImageView logoutImage;
    @FXML
    public ImageView mapImage;
    @FXML
    public Label mestoText;
    @FXML
    public Label emailLabel;
    @FXML
    public Button presovButton;
    @FXML
    public Button kosiceButton;
    @FXML
    public Button levocaButton;
    @FXML
    public Button lendButton;
    @FXML
    public ImageView menuButton;
    @FXML
    public Pane sideMenuPane;
    @FXML
    public TableView<Mesta> tableMain;
    @FXML
    public TableColumn<?, ?> columnDepo;
    @FXML
    public TableColumn<?, ?> columnKolobezky;
    @FXML
    public TableColumn<?, ?> columnBicykle;
    @FXML
    public Label points;
    int podmienkaMenu = 1;
    private ObservableList<Mesta> data;
    private String mesto = "";
    boolean isLending;
    Alert alert = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            menoLabel.setText(user.getName());
            points.setText("" + user.getUserPoints());
            String s = "";
            if(user.getEmail().length() > 15){ s += "...";
            emailLabel.setText(user.getEmail().substring(0,15) + s);}
            else{emailLabel.setText(user.getEmail());}
            data = FXCollections.observableArrayList();
            setCellTable();
            onClickPresov();

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String sql = "SELECT * from objednavky WHERE (user_email = ? )";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getEmail());
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println();
                if (!resultSet.next()) {
                    isLending = true;
                    lendButton.setStyle("-fx-background-color:   #1ABC9C;");
                    lendButton.setText("Požičaj");
                    System.out.println("green");
                } else {
                    setButtonToReturn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setCellTable() {
        columnDepo.setCellValueFactory(new PropertyValueFactory<>("depo"));
        columnKolobezky.setCellValueFactory(new PropertyValueFactory<>("kolobezky"));
        columnBicykle.setCellValueFactory(new PropertyValueFactory<>("bicykle"));
    }

    public void onClickPoziciavanie() throws IOException {
        if (isLending) {
            Stage stage = (Stage) new Stage();
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./poziciavanieExtended.fxml"));
            Functions.openNewSceneWithUser(stage, user, loader, "Požičiavanie");
            PoziciavanieController poziciavanieController = loader.getController();
            poziciavanieController.setCaller(this);
        } else {
            System.out.println("Vrátene");
            Stage stage = (Stage) new Stage();
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./vratenieExtended.fxml"));
            Functions.openNewSceneWithUser(stage, user, loader, "Vrátenie");
            VratenieController vratenieController = loader.getController();
            vratenieController.setCaller(this);
        }
    }

    public void onClickLogout() throws IOException {

        NewScene.i.openNewScene2("login/loginExtended.fxml");
        Stage stage = (Stage)kosiceButton.getScene().getWindow();
        stage.close();
    }

    static int cisloMesta = 0;

    public void onClickPresov() {
        data.clear();
        mesto = "PREŠOV";
        tableMain.setItems(Functions.updateTable(mesto));
        cisloMesta = 0;
        mapImage.setImage(new Image(getClass().getResource("../img/mapa.png").toString()));
        mestoText.setText("Prešov");
        presovButton.setStyle("-fx-background-color:  #0c9c7f;");
        kosiceButton.setStyle("-fx-background-color:   #c3c6cc;");
        levocaButton.setStyle("-fx-background-color:   #c3c6cc;");
    }

    public void onClickKosice() {
        data.clear();
        mesto = "kosice";
        tableMain.setItems(Functions.updateTable(mesto));
        cisloMesta = 1;
        mapImage.setImage(new Image(getClass().getResource("../img/mapa-kosice.png").toString()));
        mestoText.setText("Košice");
        kosiceButton.setStyle("-fx-background-color:  #0c9c7f;");
        presovButton.setStyle("-fx-background-color:   #c3c6cc;");
        levocaButton.setStyle("-fx-background-color:   #c3c6cc;");
    }


    public void onClickLevoca() {
        data.clear();
        mesto = "levoca";
        tableMain.setItems(Functions.updateTable(mesto));
        cisloMesta = 2;
        mapImage.setImage(new Image(getClass().getResource("../img/mapa-levoca.png").toString()));
        mestoText.setText("Levoča");
        levocaButton.setStyle("-fx-background-color:  #0c9c7f;");
        presovButton.setStyle("-fx-background-color:   #c3c6cc;");
        kosiceButton.setStyle("-fx-background-color:   #c3c6cc;");
    }

    public void setButtonToReturn() {
        isLending = false;
        lendButton.setStyle("-fx-background-color:  red;");
        lendButton.setText("Vráť");
    }

    public void setButtonToLend() {
        isLending = true;
        lendButton.setStyle("-fx-background-color:  #1ABC9C;");
        lendButton.setText("Požičať");
    }

    public void onClickMenu() {
        if (podmienkaMenu == 1) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(250), sideMenuPane);
            tt.setByX(182);
            tt.setAutoReverse(true);
            tt.play();
            podmienkaMenu--;
            menuButton.setImage(new Image(getClass().getResource("../img/menuBack.png").toString()));


        } else {
            TranslateTransition tt = new TranslateTransition(Duration.millis(250), sideMenuPane);
            tt.setByX(-182);
            tt.setAutoReverse(true);
            tt.play();
            podmienkaMenu++;
            menuButton.setImage(new Image(getClass().getResource("../img/menu.png").toString()));
        }
    }

    public void onClickSettings() throws IOException {
        Stage stage = (Stage) new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./userSettingsExtended.fxml"));

        Functions.openNewSceneWithUser(stage, user, loader, "Nastavenia");
        UserSettingsController userSettingsController=loader.getController();
        userSettingsController.setRodic(this);
    }
    public void onClickClose(){
        Stage stage = (Stage) menuButton.getScene().getWindow();
        stage.close();
        System.out.println("Ahojadmin   ");
    }


}
