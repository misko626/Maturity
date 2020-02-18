package JavaFX.Registration;

import JavaFX.Functions;
import JavaFX.Entity.User;
import JavaFX.NewScene;
import JavaFX.connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    @FXML
    private Button registerButton;
    @FXML
    private TextField registerNameField;
    @FXML
    private TextField registerLastNameField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private PasswordField registerPasswordField2;
    @FXML
    private TextField registerNumberField;
    @FXML
    private TextField registerEmailField;
    @FXML
    private Label error_name;
    @FXML
    private Label error_lastName;
    @FXML
    private Label error_email;
    @FXML
    private Label error_password;
    @FXML
    private Label error_password1;
    @FXML
    private Label error_number;
    @FXML
    private ImageView closeImage;
    @FXML
    private Label errorLabel;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void registerOnClick(ActionEvent actionEvent) throws SQLException {

        boolean isNameEmpty = JavaFX.validation.TextFieldValidation.isTextFieldNotEmpty(registerNameField, error_name, "*");
        boolean isLastNameEmpty = JavaFX.validation.TextFieldValidation.isTextFieldNotEmpty(registerLastNameField, error_lastName, "*");
        boolean isEmailEmpty = JavaFX.validation.TextFieldValidation.isTextFieldNotEmpty(registerEmailField, error_email, "*");
        boolean isPasswordEmpty = JavaFX.validation.TextFieldValidation.isTextFieldNotEmpty(registerPasswordField, error_password, "*");
        boolean isPasswordEmpty2 = JavaFX.validation.TextFieldValidation.isTextFieldNotEmpty(registerPasswordField2, error_password1, "*");
        boolean isNumberEmpty = JavaFX.validation.TextFieldValidation.isTextFieldNotEmpty(registerNumberField, error_number, "*");
        boolean isEmailWrong = JavaFX.validation.TextFieldValidation.isTextFieldEmail(registerEmailField, errorLabel, "Incorrect email");
        System.out.println(isEmailWrong);
        if (isNameEmpty && isLastNameEmpty && isEmailEmpty && isPasswordEmpty && isPasswordEmpty2 && isNumberEmpty && isEmailWrong) {
            String name = registerNameField.getText();
            String lastName = registerLastNameField.getText();
            String email = registerEmailField.getText();
            String password = registerPasswordField.getText();
            String number = registerNumberField.getText();
            String salt = Functions.generateRandomString(8);
            PreparedStatement statement = null;
            if (password.equals(registerPasswordField2.getText())) {
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String hashPass = Functions.MD5(password,salt);
                String sqlSelect = "SELECT * from USERS WHERE (email = ?)";
                try {
                    preparedStatement = connection.prepareStatement(sqlSelect);
                    preparedStatement.setString(1, email);
                    resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        System.out.println("Pripojene");
                        String sql = "INSERT INTO USERS (first_name,last_name,email,password,phone_number,user_points,money,salt)VALUES (?,?,?,?,?,10,0,?)";
                        try {
                            statement = connection.prepareStatement(sql);
                            statement.setString(1, name);
                            statement.setString(2, lastName);
                            statement.setString(3, email);
                            statement.setString(4, hashPass);
                            statement.setString(5, number);
                            statement.setString(6,salt);
                            int i = statement.executeUpdate();
                            System.out.println(i);
                            if (i == 1) {
                                System.out.println("Data pridane");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            statement.close();
                            System.out.println("Ahoj");
                        }
                        registerLastNameField.clear();
                        registerNameField.clear();
                        registerNumberField.clear();
                        registerPasswordField.clear();
                        registerEmailField.clear();
                        String select = "SELECT * from USERS WHERE (email = ? and password = ?)";
                        try {
                            statement = connection.prepareStatement(select);
                            statement.setString(1, email);
                            statement.setString(2, hashPass);
                            ResultSet resultSet = statement.executeQuery();
                            if (!resultSet.next()) {
                                //loginError.setText("Incorrect email or password");
                            } else {
                                User user = new User(resultSet.getInt(1), name, lastName, email, password, number, resultSet.getInt("user_points"),resultSet.getDouble(8),resultSet.getString(9));

                                Stage stage = (Stage) registerButton.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainPage/mainPageExtended.fxml"));
                                Functions.openNewSceneWithUser(stage, user, loader, "Main Page");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Uzivatel uz existuje");
                        alert.setTitle("Výstraha!");
                        alert.setHeaderText("Užívateľ už existuje");
                        alert.setContentText("Užívateľ s týmto emailom už je u nás registrovaný");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert.setTitle("Výstraha!");
                alert.setHeaderText("Hesla");
                alert.setContentText("Zadané hesla sa nezhodujú");
                alert.showAndWait();
            }
        }
    }
    public void onClickBack() throws IOException {
        /*Stage stage = (Stage) backImage.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../login/login.fxml"));
        Functions.openNewScene(stage, loader, "Login");*/
        NewScene.i.openNewScene2("login/loginExtended.fxml");
        Stage stage = (Stage)registerButton.getScene().getWindow();
        stage.close();
    }
    public void onClickClose(){
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }
}