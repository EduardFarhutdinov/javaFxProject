package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import sample.util.Alarm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/main_office";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label fieldSuccesoOrFalseLog;

    @FXML
    private Button buttonEnterLogin;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox checkBoxShowPassword;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonEnterLogin.disableProperty().bind(loginField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));
    }


    @FXML
    private void buttonSetOnActionLogin(ActionEvent event) {

        if (loginField.getText().equals(" ") || passwordField.getText().equals(" ")) {
            Alarm.showAlert(Alert.AlertType.ERROR, "Авторизация",
                    "Ошибка!", "Пожалуйста, заполните поля.");
        } else {
            try {
                String query = "select * from agent where login = ? and password = ?";//SQl запрос для проверки логина и пароля
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);//Создание соединения с БД

                PreparedStatement preparedStatement = connection.prepareStatement(query);//Выполнение запроса

                connection.setAutoCommit(false);


                preparedStatement.setString(1, loginField.getText().trim());
                preparedStatement.setString(2, passwordField.getText().trim());


                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) { //Проверка наличия логина и пароля в БД
                    System.out.println("Succeseful");
                    buttonEnterLogin.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/fxml/client.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.showAndWait();
                } else {
//                fieldSuccesoOrFalseLog.setText("Login fail");
                    Alarm.showAlert(Alert.AlertType.ERROR, "Авторизация",
                            "Ошибка!", "Неверный логин или пароль пользователя.");
                    loginField.clear();
                    passwordField.clear();
                }

                preparedStatement.close();
                resultSet.close();

            } catch (Exception el) {
                fieldSuccesoOrFalseLog.setText("SQL eror");
                System.err.println(el);
            }


        }
    }

    @FXML
    void passwordFieldOnActon(InputMethodEvent event) {

    }

    public void checkBoxShowPasswordOnAction(ActionEvent actionEvent) {

        if (checkBoxShowPassword.isSelected()) {
            passwordField.setVisible(true);

            passwordField.isVisible();
        }

    }


}
