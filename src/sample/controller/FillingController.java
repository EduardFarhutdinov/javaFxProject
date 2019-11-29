package sample.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FillingController implements Serializable {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/main_office";
    private static final String USER = "postgres";
    private static final String PASS = "root";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TextField textFieldFioClient;

    @FXML
    private DatePicker datePickerClient;

    @FXML
    private TextField textFiledNumberVodUdst;

    @FXML
    private TextField textFieldSkillAuto;

    @FXML
    private Label labelAccessOrFail;
    @FXML
    private TextField textFieldFioClientId;


    @FXML
    private TextField textFieldMarkaAuto;


    @FXML
    private TextField textFieldModelAuto;

    @FXML
    private TextField textFieldColorAuto;

    @FXML
    private TextField textFieldPowerAuto;

    @FXML
    private TextField textFieldVengine;

    @FXML
    private Button buttonAddClientAndAuto;

    @FXML
    private CheckBox checkBoxMale;

    @FXML
    private CheckBox checkBoxFemale;

    @FXML
    private Button buttonAddClient;

    @FXML
    private RadioButton radioButtonFemale;
    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton radioButtonMale;
    @FXML
    private Button buttonMakePolice;

    @FXML
    void buttonAddAutoSetOnAction(ActionEvent event) {

    }
    String getGender(){
        String gender = null;

        if(radioButtonMale.isSelected()){
            gender = "Муж";
        }
        if ((radioButtonFemale.isSelected())){
            gender = "Жен";
        }
        return gender;
//      if (checkBoxMale.isSelected()){
//           gender = "Муж";
//      }else {
//          gender = "Жен";
//      }

    }
    @FXML
    void setOnKeyTypedFileName() {
        textFieldFioClient.setTextFormatter(new TextFormatter<String>(change -> {
            if (!(change.getControlNewText().matches("[а-яА-Я,a-zA-Z-.]{0,10}"))) {
                return null;
            } else {
                return change;
            }
        }));
    }

    @FXML
    void buttonAddClientSetOnAction() throws NullPointerException, SQLException {

        //language=SQL
        String query = "INSERT INTO client (fio_client,birth_date,number_prav,auto_skill,gender) values (?,?,?,?,?)";

        try(Connection connection = DriverManager.getConnection(DB_URL,USER,PASS) ;
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            System.out.println("Opened database successfully!!!!!!!");

            preparedStatement.setString(1,textFieldFioClient.getText());
            preparedStatement.setString(2, datePickerClient.getEditor().getText());
            preparedStatement.setInt(3, Integer.parseInt(textFiledNumberVodUdst.getText()));
            preparedStatement.setInt(4, Integer.parseInt(textFieldSkillAuto.getText()));
            preparedStatement.setString(5, getGender());

            preparedStatement.executeUpdate();



        } catch ( SQLException el) {
            labelAccessOrFail.setText("SQL eror");
            System.err.println(el);
        }

    }



    @FXML
    void buttonMakePoliceSetOnAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

        buttonAddClient.disableProperty().bind(textFieldFioClient.textProperty().isEmpty().
                or(textFiledNumberVodUdst.textProperty().isEmpty()).
                        or(textFieldSkillAuto.textProperty().isEmpty()));

    }
}
