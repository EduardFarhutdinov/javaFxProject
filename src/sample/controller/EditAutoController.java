package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import sample.model.Auto;
import sample.util.Alarm;
import sample.util.DbConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditAutoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    private Button buttonEditAuto;

    public Auto auto;

    @FXML
    void initialize() {
        comboBoxTypeDrive.setItems(typeDrive);
        comboBoxTypeBody.setItems(typeBody);


        buttonEditAuto.disableProperty().bind(textFieldMarkaAuto.textProperty().isEmpty().
                or(textFieldModelAuto.textProperty().isEmpty()
                        .or(textFieldColorAuto.textProperty().isEmpty()
                                .or(textFieldPowerAuto.textProperty().isEmpty()
                                        .or(textFieldVolumeEngine.textProperty().isEmpty()
                                                .or(textFieldGosNumber.textProperty().isEmpty()
                                                        .or(textFieldMaxSpeed.textProperty().isEmpty()
                                                        )))))));

    }


    public void getEditData(Auto auto) {

        this.auto = auto;
        textFieldMarkaAuto.setText(auto.getMarka());
        textFieldModelAuto.setText(auto.getModel());
        textFieldColorAuto.setText(auto.getColor());
        textFieldPowerAuto.setText(auto.getPower());
        textFieldVolumeEngine.setText(auto.getVolume());
        textFieldGosNumber.setText(auto.getNumber());
        textFieldMaxSpeed.setText(auto.getSpeed());
        comboBoxTypeDrive.setValue(auto.getDrive());
        comboBoxTypeBody.setValue(auto.getBody());
    }

    @FXML
    void buttonEditAutoOnAction() {


        auto.setMarka(textFieldMarkaAuto.getText().trim());
        auto.setModel(textFieldModelAuto.getText().trim());
        auto.setColor(textFieldColorAuto.getText().trim());
        auto.setPower(textFieldPowerAuto.getText().trim());
        auto.setVolume(textFieldVolumeEngine.getText().trim());
        auto.setNumber(textFieldGosNumber.getText().trim());
        auto.setSpeed(textFieldMaxSpeed.getText().trim());
        auto.setDrive(comboBoxTypeDrive.getPromptText());
        auto.setBody(comboBoxTypeBody.getPromptText());

        editAuto(auto);
    }

    public void editAuto(Auto auto) {
        int id_auto = 0;

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_авто from auto where number_auto=?")) {

            preparedStatement.setString(1, auto.getNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id_auto = resultSet.getInt("id_авто");
            }

        } catch (SQLException q) {
            q.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE auto SET marka =? ,model=?,color=?,power=?,volume=?,number_auto=?,speed=?,drive=?,body=? WHERE id_авто=" + id_auto)) {
            preparedStatement.setString(1, textFieldMarkaAuto.getText());
            preparedStatement.setString(2, textFieldModelAuto.getText());
            preparedStatement.setString(3, textFieldColorAuto.getText());
            preparedStatement.setString(4, textFieldPowerAuto.getText());
            preparedStatement.setString(5, textFieldVolumeEngine.getText());
            preparedStatement.setString(6, textFieldGosNumber.getText());
            preparedStatement.setString(7, textFieldMaxSpeed.getText());
            preparedStatement.setString(8, comboBoxTypeDrive.getValue());
            preparedStatement.setString(9, comboBoxTypeBody.getValue());

            preparedStatement.executeUpdate();
            Alarm.showAlert(Alert.AlertType.INFORMATION, "Оформление",
                    "Успешно!", "Автомобиль добавлен.");
        } catch (SQLException eq) {
            Alarm.showAlert(Alert.AlertType.ERROR, "Оформление",
                    "Ошибка!", "Подробности: " + eq + ".");
        }
        clearFieldsAuto();


    }

    private void clearFieldsAuto() {
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
    void onKeyTypedColorAuto(KeyEvent event) {
        textFieldColorAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- ]{0,15}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedGosNumber(KeyEvent event) {
        textFieldGosNumber.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- ,0-9]{0,15}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedMarkaAuto(KeyEvent event) {
        textFieldMarkaAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z-,]{0,20}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedMaxSpeed(KeyEvent event) {
        textFieldMaxSpeed.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,5}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedModelAuto(KeyEvent event) {
        textFieldModelAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z-]{0,20}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedPowerAuto(KeyEvent event) {
        textFieldPowerAuto.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,4}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }


    @FXML
    void onKeyTypedVolumeEngine(KeyEvent event) {
        textFieldVolumeEngine.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,10}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

}
