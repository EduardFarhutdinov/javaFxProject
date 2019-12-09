package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.util.Alarm;
import sample.util.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldFIO;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldCity;

    @FXML
    private TextField textFieldPhoneNumber;

    @FXML
    private DatePicker textFieldBirthDate;

    @FXML
    private TextField textFieldNumberPrava;

    @FXML
    private TextField textFieldAutoSkill;

    @FXML
    private RadioButton radioButtonMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton radioButtonFemale;

    @FXML
    private Button buttonAddClient;
    @FXML
    private Button buttonPolis;
    //_________________________________________________________
    @FXML
    private TextField textFieldMarkaAuto;

    @FXML
    private TextField textFieldModelAuto;

    @FXML
    private TextField textFieldColorAuto;

    @FXML
    private TextField textFieldPowerAuto;

    @FXML
    private TextField textFieldVolumeEngine;

    @FXML
    private TextField textFieldGosNumber;

    @FXML
    private TextField textFieldMaxSpeed;

    @FXML
    private ComboBox<String> comboBoxTypeDrive;

    @FXML
    private ComboBox<String> comboBoxTypeBody;

    ObservableList<String> typeBody = FXCollections.observableArrayList("Седан", "Хэтчбек",
            "Универсал", "Кабриолет", "Внедорожник", "Кроссовер", "Родстер", "Фургон", "Минивэн", "Пикап");
    ObservableList<String> typeDrive = FXCollections.observableArrayList("Передний", "Задний", "Полный");

    ObservableList<String> clients = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> comboBoxClient;


    @FXML
    private TextField textFieldVolumeEngine11;


    @FXML
    private Button buttonAddAuto;


    @FXML
    private Button buttonDataBaseClient;

    @FXML
    private Button buttonDataBaseAuto;

    @FXML
    private Button buttonCreatePolis;

    @FXML
    void buttonCreatePolisOnAction(ActionEvent event) {
    }


    @FXML
    void buttonDataBaseAutoOnAction(ActionEvent event) { //Переключение сцены
        buttonDataBaseAuto.getScene().getWindow();       //Открывается оконо с таблицей из автомобилей
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/tableAuto.fxml"));
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
    }

    @FXML
    void comboBoxClientOnAction(ActionEvent event) {
        comboBoxClient.getItems().clear();

        String query = "select fio_client  from client";
        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comboBoxClient.getItems().addAll(resultSet.getString("fio_client"));

            }
            connection.close();
            preparedStatement.close();
            resultSet.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        comboBoxTypeDrive.setItems(typeDrive);
        comboBoxTypeBody.setItems(typeBody);


        buttonAddClient.disableProperty().bind(textFieldFIO.textProperty().isEmpty()
                .or(textFieldAddress.textProperty().isEmpty()
                        .or(textFieldCity.textProperty().isEmpty()
                                .or(textFieldPhoneNumber.textProperty().isEmpty()
                                        .or(textFieldBirthDate.showWeekNumbersProperty().not()
                                                .or(textFieldNumberPrava.textProperty().isEmpty()
                                                        .or(textFieldAutoSkill.textProperty().isEmpty()
                                                        )))))));

        buttonAddAuto.disableProperty().bind(textFieldMarkaAuto.textProperty().isEmpty().
                or(textFieldModelAuto.textProperty().isEmpty()
                        .or(textFieldColorAuto.textProperty().isEmpty()
                                .or(textFieldPowerAuto.textProperty().isEmpty()
                                        .or(textFieldVolumeEngine.textProperty().isEmpty()
                                                .or(textFieldGosNumber.textProperty().isEmpty()
                                                        .or(textFieldMaxSpeed.textProperty().isEmpty()
                                                        )))))));
    }


    //___________________________________________________________
    @FXML
    void buttonAddAutoOnAction(ActionEvent event) {               //добавление автомобиля
        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;//создание соединения с бд
             PreparedStatement preparedStatement = connection.prepareStatement(DbConnection.INSERT_AUTO)) { //передача запроса

            preparedStatement.setString(1, textFieldMarkaAuto.getText()); //взятие данных с полей
            preparedStatement.setString(2, textFieldModelAuto.getText());//взятие данных с полей
            preparedStatement.setString(3, textFieldColorAuto.getText());//взятие данных с полей
            preparedStatement.setString(4, textFieldPowerAuto.getText());//взятие данных с полей
            preparedStatement.setString(5, textFieldVolumeEngine.getText());//взятие данных с полей
            preparedStatement.setString(6, textFieldGosNumber.getText());//взятие данных с полей
            preparedStatement.setString(7, textFieldMaxSpeed.getText());//взятие данных с полей
            preparedStatement.setString(8, comboBoxTypeDrive.getValue());//взятие данных с полей
            preparedStatement.setString(9, comboBoxTypeBody.getValue());//взятие данных с полей

            preparedStatement.executeUpdate();
            Alarm.showAlert(Alert.AlertType.INFORMATION, "Оформление",
                    "Успешно!", "Автомобиль добавлен.");
        } catch (SQLException eq) {
            System.out.println(eq);
            Alarm.showAlert(Alert.AlertType.ERROR, "Оформление",
                    "Ошибка!", "Подробности: " + eq + ".");
        }
        clearFieldsAuto();                          //отчистка полей
    }

    private void clearFieldsAuto() {             //отчистка полей
        textFieldMarkaAuto.clear();
        textFieldModelAuto.clear();
        textFieldColorAuto.clear();
        textFieldPowerAuto.clear();
        textFieldVolumeEngine.clear();
        textFieldGosNumber.clear();
        textFieldMaxSpeed.clear();
        comboBoxTypeDrive.setValue(null);
        comboBoxTypeBody.setValue(null);
    }

    @FXML
    void onKeyTypedColorAuto(KeyEvent event) {     //ограничение на ввод
        textFieldColorAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- ]{0,15}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedGosNumber(KeyEvent event) {        //ограничение на ввод
        textFieldGosNumber.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- ,0-9]{0,15}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedMarkaAuto(KeyEvent event) {        //ограничение на ввод
        textFieldMarkaAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z-,]{0,20}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedMaxSpeed(KeyEvent event) {            //ограничение на ввод
        textFieldMaxSpeed.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,5}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedModelAuto(KeyEvent event) {        //ограничение на ввод
        textFieldModelAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z-]{0,20}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedPowerAuto(KeyEvent event) {         //ограничение на ввод
        textFieldPowerAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,4}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedVolumeEngine(KeyEvent event) {     //ограничение на ввод
        textFieldVolumeEngine.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9, .]{0,10}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }


    //_________________________________________________________________
    @FXML
    void buttonAddClientOnAction(ActionEvent event) throws SQLException {    //добавление клиента

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;//Создание соединения с БД
             PreparedStatement preparedStatement = connection.prepareStatement(DbConnection.INSERT_CLIENT)) {//Выполнения SQL запроса

            preparedStatement.setString(1, textFieldFIO.getText());                     //Взятие данных с полей
            preparedStatement.setString(2, textFieldBirthDate.getEditor().getText());//Взятие данных с полей
            preparedStatement.setString(3, textFieldNumberPrava.getText());//Взятие данных с полей
            preparedStatement.setString(4, textFieldAutoSkill.getText());//Взятие данных с полей
            preparedStatement.setString(5, getGender());//Взятие данных с полей
            preparedStatement.setString(6, textFieldAddress.getText());//Взятие данных с полей
            preparedStatement.setString(7, textFieldPhoneNumber.getText());//Взятие данных с полей
            preparedStatement.setString(8, textFieldCity.getText());//Взятие данных с полей

            preparedStatement.executeUpdate();             //добавление данных в БД

            Alarm.showAlert(Alert.AlertType.INFORMATION, "Оформление",
                    "Успешно!","Клиент добавлен.");
        } catch (SQLException eq) {
            System.out.println(eq);
            Alarm.showAlert(Alert.AlertType.WARNING, "Оформление",
                    "Ошибка!", "Подробности: " + eq + ".");
        }
        clearFieldsClient();//метод очищение полей после добавления

    }


    void clearFieldsClient() {   //очистка полей для клиента
        textFieldFIO.clear();
        textFieldBirthDate.getEditor().clear();
        textFieldNumberPrava.clear();
        textFieldAutoSkill.clear();
        textFieldAddress.clear();
        textFieldNumberPrava.clear();
        textFieldCity.clear();
        textFieldPhoneNumber.clear();
    }

    String getGender() {       //метод возвращающий пол клиента
        String gender = null;

        if (radioButtonMale.isSelected()) {
            gender = "Муж";
        }
        if ((radioButtonFemale.isSelected())) {
            gender = "Жен";
        }
        return gender;
    }

    @FXML
    void onKeyTypedAutoSkill(KeyEvent event) {     //ограничение на ввод
        textFieldAutoSkill.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,10}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedCity(KeyEvent event) {//ограничение на ввод
        textFieldCity.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z-.]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedFio(KeyEvent event) {//ограничение на ввод
        textFieldFIO.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- ]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedAddress(KeyEvent event) {//ограничение на ввод
        textFieldAddress.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- .,0-9]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedNumberPrava(KeyEvent event) {//ограничение на ввод
        textFieldNumberPrava.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- .,0-9]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedPhone(KeyEvent event) {//ограничение на ввод
        textFieldPhoneNumber.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,15}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void buttonPolisOnAction(ActionEvent actionEvent) {//Переключение сцены.
        buttonPolis.getScene().getWindow();                   //Открывается окно оформления полиса
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/createPolis.fxml"));
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
    }

    @FXML
    void buttonDataBaseClientOnAction(ActionEvent event) {   //Переключение сцены
        buttonDataBaseClient.getScene().getWindow();        //Открывается окно с таблицей из клиентов
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/tableClients.fxml"));
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

    }
}
