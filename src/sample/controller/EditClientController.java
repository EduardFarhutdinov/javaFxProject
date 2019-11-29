package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import sample.model.Client;
import sample.util.Alarm;
import sample.util.DbConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditClientController {

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
    private Button buttonEditClient;

    Client client;

    @FXML
    void initialize() {
        buttonEditClient.disableProperty().bind(textFieldFIO.textProperty().isEmpty()
                .or(textFieldAddress.textProperty().isEmpty()
                        .or(textFieldCity.textProperty().isEmpty()
                                .or(textFieldPhoneNumber.textProperty().isEmpty()
                                        .or(textFieldBirthDate.showWeekNumbersProperty().not()
                                                .or(textFieldNumberPrava.textProperty().isEmpty()
                                                        .or(textFieldAutoSkill.textProperty().isEmpty()
                                                        )))))));

    }

    public void getEditData(Client client) {
        this.client = client;
        textFieldFIO.setText(client.getFio_clienta());
        textFieldAddress.setText(client.getAddress());
        textFieldCity.setText(client.getCity());
        textFieldPhoneNumber.setText(client.getPhone());
        textFieldBirthDate.setPromptText(client.getBirth_date());
        textFieldNumberPrava.setText(client.getNumber_prav());
        textFieldAutoSkill.setText(client.getAuto_skill());

        if(client.getGender().equals("Муж")){
            gender.selectToggle(radioButtonMale);
        }else {
            gender.selectToggle(radioButtonFemale);
        }

    }

    @FXML
    void buttonEditOnAction(ActionEvent event) {
       client.setFio_clienta(textFieldFIO.getText().trim());
       client.setAddress(textFieldAddress.getText().trim());
       client.setCity(textFieldCity.getText().trim());
       client.setPhone(textFieldPhoneNumber.getText().trim());
       client.setBirth_date(textFieldBirthDate.getPromptText());
       client.setNumber_prav(textFieldNumberPrava.getText().trim());
       client.setAuto_skill(textFieldAutoSkill.getText().trim());
       client.setGender(getGender());

       editCliet(client);
    }

    private void editCliet(Client client) {
        int id_client = 0;

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_client from client where number_prav=?")) {

            preparedStatement.setString(1, client.getNumber_prav());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id_client = resultSet.getInt("id_client");
            }

        } catch (SQLException q) {
            q.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE client SET fio_client=?,birth_date=?,number_prav=?,auto_skill=?,gender=?,address=?,phone=?,city=?  WHERE id_client=" + id_client)) {
            preparedStatement.setString(1, textFieldFIO.getText());
            preparedStatement.setString(2, textFieldBirthDate.getPromptText());
            preparedStatement.setString(3, textFieldNumberPrava.getText());
            preparedStatement.setString(4, textFieldAutoSkill.getText());
            preparedStatement.setString(5, getGender());
            preparedStatement.setString(6, textFieldAddress.getText());
            preparedStatement.setString(7, textFieldPhoneNumber.getText());
            preparedStatement.setString(8, textFieldCity.getText());

            preparedStatement.executeUpdate();
            Alarm.showAlert(Alert.AlertType.INFORMATION, "Оформление",
                    "Успешно!", "Данные изменены.");
        } catch (SQLException eq) {
            Alarm.showAlert(Alert.AlertType.ERROR, "Оформление",
                    "Ошибка!", "Подробности: " + eq + ".");
        }
        clearFieldsClient();


    }


    void clearFieldsClient() {
        textFieldFIO.clear();
        textFieldBirthDate.getEditor().clear();
        textFieldNumberPrava.clear();
        textFieldAutoSkill.clear();
        textFieldAddress.clear();
        textFieldNumberPrava.clear();
        textFieldCity.clear();
        textFieldPhoneNumber.clear();
    }

    String getGender() {
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
    void onKeyTypedAutoSkill(KeyEvent event) {
        textFieldAutoSkill.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,10}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedCity(KeyEvent event) {
        textFieldCity.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z-.]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedFio(KeyEvent event) {
        textFieldFIO.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- ]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedAddress(KeyEvent event) {
        textFieldAddress.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- .,0-9]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedNumberPrava(KeyEvent event) {
        textFieldNumberPrava.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z- .,0-9]{0,30}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void onKeyTypedPhone(KeyEvent event) {
        textFieldPhoneNumber.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[0-9]{0,15}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }
}
