package JavaFX.MainPage;
import JavaFX.Controller;
import JavaFX.Entity.Mesta;
import JavaFX.Functions;
import JavaFX.NewScene;
import JavaFX.connectivity.ConnectionClass;
import animatefx.animation.*;
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
import javafx.scene.web.WebView;
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
    public ImageView fullScreenButton;
    @FXML
    public Button levocaButton;
    @FXML
    public Button lendButton;
    @FXML
    public ImageView menuButton;
    @FXML
    public Pane sideMenuPane;
    @FXML
    public Pane upPane;
    @FXML
    public TableView<Mesta> tableMain;
    @FXML
    public Label money;
    @FXML
    public ImageView logoImage;
    @FXML
    public Pane imagePane;
    @FXML
    public Pane paneWebView;
    @FXML
    public TableColumn<?, ?> columnDepo;
    @FXML
    public TableColumn<?, ?> columnKolobezky;
    @FXML
    public TableColumn<?, ?> columnBicykle;
    @FXML
    public Label points;
    @FXML
    public Pane pane;
    @FXML
    public WebView webView;
    @FXML
    public ToggleButton toggleButton;
    int podmienkaMenu = 1;
    private ObservableList<Mesta> data;
    private String mesto = "";
    boolean isLending;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    boolean webViewPodmienka=true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            new SlideInLeft(logoImage).play();
            new Pulse(upPane).play();
            menoLabel.setText(user.getName());
            points.setText("" + user.getUserPoints());
            money.setText(String.format("%10.2f", user.getMoney()) + " €");
            String s = "";
            if(user.getEmail().length() > 15){ s += "...";
            emailLabel.setText(user.getEmail().substring(0,15) + s);}
            else{emailLabel.setText(user.getEmail());}
            data = FXCollections.observableArrayList();
            setCellTable();
            onClickPresov();
            webView.getEngine().load("https://www.google.sk/maps/@48.9990164,21.2341902,13.88z");

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
            new ZoomIn(loader.getRoot()).play();

        } else {
            System.out.println("Vrátene");
            Stage stage = (Stage) new Stage();
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./vratenieExtended.fxml"));
            Functions.openNewSceneWithUser(stage, user, loader, "Vrátenie");
            VratenieController vratenieController = loader.getController();
            vratenieController.setCaller(this);
            new ZoomIn(loader.getRoot()).play();
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
        webView.getEngine().load("https://www.google.sk/maps/@48.9990164,21.2341902,13.88z");
        tableMain.setItems(Functions.updateTable(mesto));
        cisloMesta = 0;
        mapImage.setImage(new Image(getClass().getResource("../img/mapa.png").toString()));
        mestoText.setText("Prešov");
        presovButton.setStyle("-fx-background-color:  #0c9c7f;");
        kosiceButton.setStyle("-fx-background-color:   #c3c6cc;");
        levocaButton.setStyle("-fx-background-color:   #c3c6cc;");
        new Pulse(imagePane).play();
        new FadeIn(mestoText).play();
        new Pulse(paneWebView).play();
    }

    public void onClickKosice() {
        data.clear();
        mesto = "kosice";
        webView.getEngine().load("https://www.google.sk/maps/@48.7167933,21.2635638,13.39z");
        tableMain.setItems(Functions.updateTable(mesto));
        cisloMesta = 1;
        mapImage.setImage(new Image(getClass().getResource("../img/mapa-kosice.png").toString()));
        mestoText.setText("Košice");
        kosiceButton.setStyle("-fx-background-color:  #0c9c7f;");
        presovButton.setStyle("-fx-background-color:   #c3c6cc;");
        levocaButton.setStyle("-fx-background-color:   #c3c6cc;");
        new Pulse(imagePane).play();
        new FadeIn(mestoText).play();
        new Pulse(paneWebView).play();
       // imagePane.toFront();
    }

    public void onClickLevoca() {
        data.clear();
        mesto = "levoca";
        webView.getEngine().load("https://www.google.sk/maps/@49.0225687,20.583343,15.17z");
        tableMain.setItems(Functions.updateTable(mesto));
        cisloMesta = 2;
        mapImage.setImage(new Image(getClass().getResource("../img/mapa-levoca.png").toString()));
        mestoText.setText("Levoča");
        levocaButton.setStyle("-fx-background-color:  #0c9c7f;");
        presovButton.setStyle("-fx-background-color:   #c3c6cc;");
        kosiceButton.setStyle("-fx-background-color:   #c3c6cc;");
        new Pulse(imagePane).play();
        new Pulse(paneWebView).play();
        new FadeIn(mestoText).play();


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
            sideMenuPane.toFront();
        if (podmienkaMenu == 1) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(250), sideMenuPane);
            tt.setByX(182);
            tt.setAutoReverse(true);
            tt.play();
            podmienkaMenu--;
            menuButton.setImage(new Image(getClass().getResource("../img/menuBack.png").toString()));
            new Pulse(menuButton).play();
            


        } else {
            TranslateTransition tt = new TranslateTransition(Duration.millis(250), sideMenuPane);
            tt.setByX(-182);
            tt.setAutoReverse(true);
            tt.play();
            podmienkaMenu++;
            menuButton.setImage(new Image(getClass().getResource("../img/menu.png").toString()));
            new Pulse(menuButton).play();
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
        new FadeIn(loader.getRoot()).play();
    }
    public void onClickClose(){
        Stage stage = (Stage) menuButton.getScene().getWindow();
        stage.close();
        System.out.println("Ahojadmin");
    }
    public void onClickMaps(){
        if (toggleButton.isSelected())paneWebView.toFront();
        else imagePane.toFront();

    }
    public void setFullScreenMaps(){
        if (webViewPodmienka) {
            paneWebView.setPrefSize(730, 430);
            webView.setPrefSize(740, 410);
            paneWebView.setLayoutX(10);
            paneWebView.setLayoutY(10);
            new Pulse(paneWebView).play();
            fullScreenButton.setLayoutX(705);
            fullScreenButton.setLayoutY(375);
            webViewPodmienka=false;
            fullScreenButton.setImage(new Image(getClass().getResource("../img/minimize.png").toString()));
        }else {
            paneWebView.setPrefSize(352, 345);
            webView.setPrefSize(341, 336);
            paneWebView.setLayoutX(28);
            paneWebView.setLayoutY(85);
            //new Pulse(paneWebView).play();
            fullScreenButton.setLayoutX(295);
            fullScreenButton.setLayoutY(297);
            webViewPodmienka=true;
            fullScreenButton.setImage(new Image(getClass().getResource("../img/maximize.png").toString()));
        }
    }


}
