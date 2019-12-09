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
    private TextField textfieldIdClient;

    @FXML
    private ComboBox<String> comboBoxTypeInsurance;

    ObservableList<String> insurance;
    @FXML
    private TextField textfieldIdAgent;

    @FXML
    TextField textfieldAutoMarka;
    @FXML
    TextField textfieldAutoModel;
    @FXML
    TextField textfieldAutoNumber;

    @FXML
    private TextField textfieldInsurancePayment;

    @FXML
    private TextField textfieldPay;

    @FXML
    private DatePicker textFiledDateIssue;

    @FXML
    private DatePicker textFiledDateBreakUp;

    @FXML
    private TextField textFieldFilterAuto;

    @FXML
    private TextField textFieldFilterClient;

    @FXML
    private Button buttonDataBasePolis;

    @FXML
    private TableView<Auto> tableViewAutoPolis;

    ObservableList<Auto> autos;
    ObservableList<Client> clients;

    @FXML
    void initialize() {

        if(LoginController.agent.isAccess() != true){
            buttonDataBasePolis.setDisable(true);
        }else {
            buttonDataBasePolis.setDisable(false);
        }

        textfieldIdAgent.setText(LoginController.agent.getFio_agent());

        insurance = FXCollections.observableArrayList("Осаго", "Каско");
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
        tableViewClientsPolis.getItems().clear();

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
//             PreparedStatement preparedStatement = connection.prepareStatement("select id_авто,marka,model,number_auto from auto");
             PreparedStatement preparedStatement = connection.prepareStatement("select id_авто, marka, model, color, power, volume, number_auto, speed, drive, body from auto");
             PreparedStatement preparedStatement1 = connection.prepareStatement("select id_client, fio_client, birth_date, number_prav, auto_skill, gender, address, phone, city from client")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet2 = preparedStatement1.executeQuery();

            while (resultSet.next()) {

                autos.add(new Auto(
                        resultSet.getInt("id_авто"),
                        resultSet.getString("marka"),
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        resultSet.getString("power"),
                        resultSet.getString("volume"),
                        resultSet.getString("number_auto"),
                        resultSet.getString("speed"),
                        resultSet.getString("drive"),
                        resultSet.getString("body")
                ));
                tableViewAutoPolis.setItems(autos);
            }

            while (resultSet2.next()) {
                clients.add(new Client(
                        resultSet2.getInt("id_client"),
                        resultSet2.getString("fio_client"),
                        resultSet2.getString("birth_date"),
                        resultSet2.getString("number_prav"),
                        resultSet2.getString("gender"),
                        resultSet2.getString("address"),
                        resultSet2.getString("phone"),
                        resultSet2.getString("city"),
                        resultSet2.getString("auto_skill")
                ));
                tableViewClientsPolis.setItems(clients);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        FilteredList<Client> filteredDataClient = new FilteredList<>(clients, b -> true);
        textFieldFilterClient.textProperty().addListener((((observable, oldValue, newValue) ->
                filteredDataClient.setPredicate(clt -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (clt.getNumber_prav().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (clt.getFio_clienta().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return false;
                }))));
        SortedList<Client> clientSortedList = new SortedList<>(filteredDataClient);
        clientSortedList.comparatorProperty().bind(tableViewClientsPolis.comparatorProperty());
        tableViewClientsPolis.setItems(clientSortedList);


        FilteredList<Auto> filteredData = new FilteredList<>(autos, b -> true);

        textFieldFilterAuto.textProperty().addListener(((observable, oldValue, newValue) ->
                filteredData.setPredicate(auto -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (auto.getNumber().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (auto.getModel().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (String.valueOf(auto.getNumber()).contains(lowerCaseFilter))
                        return true;
                    else
                        return false; // Does not match.
                })));

        SortedList<Auto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewAutoPolis.comparatorProperty());
        tableViewAutoPolis.setItems(sortedData);

    }

    @FXML
    void buttonDataBasePolisOnAction(ActionEvent event) {
        buttonDataBasePolis.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/dogovorTable.fxml"));
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

    Client client;
    Auto auto;

    @FXML
    void buttonSetValuesFieldsOnAction(ActionEvent event) {
        client = tableViewClientsPolis.getSelectionModel().getSelectedItem();
        auto = tableViewAutoPolis.getSelectionModel().getSelectedItem();
        textfieldIdClient.setText(client.getFio_clienta());
        textfieldAutoMarka.setText(auto.getMarka());
        textfieldAutoModel.setText(auto.getModel());
        textfieldAutoNumber.setText(auto.getNumber());
    }

    int initIdAgent() {

        String nameAgent = textfieldIdAgent.getText();
        String query2 = "Select id_agent from main_office.public.agent where fio_agent='"+nameAgent+"'";
        int agentId =0;
        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
             PreparedStatement preparedStatement1 = connection.prepareStatement(query2)
        ) {
            ResultSet resultSetAgentId = preparedStatement1.executeQuery();
            while (resultSetAgentId.next()) {
                agentId = resultSetAgentId.getInt(1);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return agentId;
    }

    @FXML
    void buttonAddPolisOnAction(ActionEvent event) {
        int agentId = 0;
        String nameAgent = textfieldIdAgent.getText();
//        String query2 = "Select id_agent from main_office.public.agent where fio_agent='"+LoginController.agent.getFio_agent()+"'";
        String query2 = "Select id_agent from main_office.public.agent where fio_agent=?";
        String query = "INSERT INTO dogovor (dateofissue, breakupdate, id_agent, id_client, insurance_type, insurance_payment, id_auto, pay,branch) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             PreparedStatement preparedStatement1 = connection.prepareStatement(query2)

        ) {
            preparedStatement1.setString(1,LoginController.agent.getFio_agent());
            ResultSet resultSetAgentId = preparedStatement1.executeQuery();
            if (resultSetAgentId.next()) {
                agentId = resultSetAgentId.getInt(1);
            }

            preparedStatement.setString(1, textFiledDateIssue.getEditor().getText());
            preparedStatement.setString(2, textFiledDateBreakUp.getEditor().getText());
            preparedStatement.setInt(3, agentId);
            preparedStatement.setInt(4, client.getId_clienta());
            preparedStatement.setString(5, comboBoxTypeInsurance.getValue());
            preparedStatement.setInt(6, Integer.parseInt(textfieldInsurancePayment.getText()));
            preparedStatement.setInt(7, auto.getId());
            preparedStatement.setInt(8, Integer.parseInt(textfieldPay.getText()));
            preparedStatement.setString(9, "H");

            preparedStatement.executeUpdate();

            clearFieldsAuto();
            Alarm.showAlert(Alert.AlertType.INFORMATION, "Оформление",
                    "Успешно!", "Полис оформлен.");
        } catch (SQLException eq) {
            Alarm.showAlert(Alert.AlertType.ERROR, "Оформление",
                    "Ошибка!", "Подробности: " + eq + ".");
        }
    }


    private void clearFieldsAuto() {
        textFiledDateIssue.getEditor().clear();
        textFiledDateBreakUp.getEditor().clear();
        textfieldIdAgent.clear();
        textfieldIdClient.clear();
        textfieldInsurancePayment.clear();
        textfieldAutoModel.clear();
        textfieldAutoMarka.clear();
        textfieldAutoNumber.clear();
        textfieldPay.clear();
    }

}
