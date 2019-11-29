package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.Auto;
import sample.model.Client;
import sample.util.Alarm;
import sample.util.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PolisController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private TableColumn<Integer, Auto> tableColumnIdAuto;

    @FXML
    private TableColumn<String, Auto> tableColumnMarka;

    @FXML
    private TableColumn<String, Auto> tableColumnModel;

    @FXML
    private TableColumn<String, Auto> tableColumnGosNumber;
//--------------------------------------------------------

    @FXML
    private TableView<Client> tableViewClientsPolis;

    @FXML
    private TableColumn<Integer, Client> tableColumnId;

    @FXML
    private TableColumn<String, Client> tableColumnFIO;

    @FXML
    private TableColumn<String, Client> tableColumnDateBirth;

    @FXML
    private TableColumn<String, Client> tableColumnNumberPrav;

    @FXML
    private TableColumn<String, Client> tableColumnGender;

    @FXML
    private Button buttonAddPolis;

    @FXML
    private Button buttonRefrashAutoTable;

    @FXML
    private Button buttonRefrashClientTable;

    @FXML
    private TextField textfieldIdClient;

    @FXML
    private ComboBox<String> comboBoxTypeInsurance;

    ObservableList<String> insurance = FXCollections.observableArrayList("Обязательное");
    @FXML
    private TextField textfieldIdAgent;

    @FXML
    private TextField textfieldIdAuto;

    @FXML
    private TextField textfieldInsurancePayment;

    @FXML
    private TextField textfieldPay;

    @FXML
    private DatePicker textFiledDateIssue;

    @FXML
    private DatePicker textxFiledDateBreakUp;

    @FXML
    private TextField textFieldFilterAuto;

    @FXML
    private Button buttonSearchAuto;

    @FXML
    private Button buttonDataBasePolis;

    @FXML
    private TableView<Auto> tableViewAutoPolis;

    ObservableList<Auto> autos ;
    ObservableList<Client> clients;

    Client client ;

    @FXML
    void initialize() {
         comboBoxTypeInsurance.setItems(insurance);
        autos = FXCollections.observableArrayList();

        clients = FXCollections.observableArrayList();

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id_clienta"));
        tableColumnFIO.setCellValueFactory(new PropertyValueFactory<>("fio_clienta"));
        tableColumnDateBirth.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
        tableColumnNumberPrav.setCellValueFactory(new PropertyValueFactory<>("Number_prav"));
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));


        tableColumnIdAuto.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMarka.setCellValueFactory(new PropertyValueFactory<>("marka"));
        tableColumnModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tableColumnGosNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

        /*
      Инициализация БД в таблице
         */
        tableViewAutoPolis.getItems().clear();

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("select id_авто,marka,model,number_auto from auto") ;) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                autos.add(new Auto(
                        resultSet.getInt( "id_авто"),
                        resultSet.getString("marka"),
                        resultSet.getString("model"),
                        resultSet.getString("number_auto")

                ));
                tableViewAutoPolis.setItems(autos);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();

        } catch ( SQLException e) {
            e.printStackTrace();
        }
            /*
      Инициализация БД в таблице
         */

        FilteredList<Auto> filteredData = new FilteredList<>(autos,b->true);

        textFieldFilterAuto.textProperty().addListener(((observable , oldValue , newValue) ->
                filteredData.setPredicate(auto -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (auto.getNumber().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (auto.getModel().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }else if (String.valueOf(auto.getNumber()).contains(lowerCaseFilter))
                        return true;
                    else
                        return false; // Does not match.
                })));

        SortedList<Auto> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewAutoPolis.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewAutoPolis.setItems(sortedData);

    }


    @FXML
    void buttonDataBasePolisOnAction(ActionEvent event) {
        buttonDataBasePolis.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/dogovorTable.fxml"));
        try {
            loader.load();
        } catch ( IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();

    }

//
//    public void getEditData(Client client) {
//        this.client = client;
//        textfieldIdClient.setText(client.getFio_clienta());
//
//    }
    @FXML
    void buttonAddPolisOnAction(ActionEvent event) {


        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dogovor (dateofissue, breakupdate, id_agent, id_client, insurance_type, insurance_payment, id_auto, pay) VALUES (?,?,?,?,?,?,?,?)")) {

            preparedStatement.setString(1, textFiledDateIssue.getEditor().getText());
            preparedStatement.setString(2, textxFiledDateBreakUp.getEditor().getText());
            preparedStatement.setInt(3, Integer.parseInt(textfieldIdAgent.getText().trim()) );
            preparedStatement.setInt(4, Integer.parseInt(textfieldIdClient.getText().trim()));
            preparedStatement.setString(5, comboBoxTypeInsurance.getValue());
            preparedStatement.setString(6, textfieldInsurancePayment.getText());
            preparedStatement.setInt(7, Integer.parseInt(textfieldIdAuto.getText().trim()) );
            preparedStatement.setString(8, textfieldPay.getText());

            preparedStatement.executeUpdate();

     clearFieldsAuto();
            Alarm.showAlert(Alert.AlertType.INFORMATION, "Оформление",
                    "Успешно!","Полис оформлен.");
        } catch ( SQLException eq) {
            Alarm.showAlert(Alert.AlertType.ERROR, "Оформление",
                    "Ошибка!", "Подробности: " + eq + ".");
        }
        clearFieldsAuto();
    }


    private void clearFieldsAuto() {
        textFiledDateIssue.getEditor().clear();
        textxFiledDateBreakUp.getEditor().clear();
        textfieldIdAgent.clear();
        textfieldIdClient.clear();
        comboBoxTypeInsurance.getItems().clear();
        textfieldInsurancePayment.clear();
        textfieldIdAuto.clear();
        textfieldPay.clear();
    }

    @FXML
    void buttonRefrashAutoTableOnAction(ActionEvent event) {

        tableViewAutoPolis.getItems().clear();

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("select id_авто,marka,model,number_auto from auto") ;) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                autos.add(new Auto(
                        resultSet.getInt( "id_авто"),
                        resultSet.getString("marka"),
                        resultSet.getString("model"),
                        resultSet.getString("number_auto")

                ));
                tableViewAutoPolis.setItems(autos);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();

        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttonRefrashClientTableOnAction(ActionEvent event) {
        tableViewClientsPolis.getItems().clear();

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
             PreparedStatement preparedStatement = connection.prepareStatement("select id_client,fio_client,birth_date,number_prav,gender from client") ;) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                clients.add(new Client(
                        resultSet.getInt( "id_client"),
                        resultSet.getString("fio_client"),
                        resultSet.getString("birth_Date"),
                        resultSet.getString("number_prav"),
                        resultSet.getString("gender")

                ));
                tableViewClientsPolis.setItems(clients);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();

        } catch ( SQLException e) {
            e.printStackTrace();
        }

        client = tableViewClientsPolis.getSelectionModel().getSelectedItem();
    }


}
