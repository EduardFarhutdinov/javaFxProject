package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import sample.model.Client;
import sample.util.Alarm;
import sample.util.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DataBaseClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableColumn<String, Client> tableColumnId;

    @FXML
    private TableColumn<String, Client> tableColumnFIO;

    @FXML
    private TableColumn<String, Client> tableColumnDateBirth;

    @FXML
    private TableColumn<String, Client> tableColumnNumberPrav;

    @FXML
    private TableColumn<String, Client> tableColumnAutoSkill;

    @FXML
    private TableColumn<String, Client> tableColumnGender;

    @FXML
    private TableColumn<String, Client> tableColumnAdrress;

    @FXML
    private TableColumn<String, Client> tableColumnCallNumber;

    @FXML
    private TableColumn<String, Client> tableColumnCity;

    @FXML
    private TableView<Client> tableViewClients;

    ObservableList<Client> clients;

    @FXML
    private Button buttonRefrashTableClients;
    @FXML
    private Button buttonEditClients;

    @FXML
    void initialize() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id_clienta"));
        tableColumnFIO.setCellValueFactory(new PropertyValueFactory<>("fio_clienta"));
        tableColumnDateBirth.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
        tableColumnNumberPrav.setCellValueFactory(new PropertyValueFactory<>("Number_prav"));
        tableColumnAutoSkill.setCellValueFactory(new PropertyValueFactory<>("auto_skill"));
        tableColumnGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tableColumnAdrress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tableColumnCallNumber.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("City"));


        clients = FXCollections.observableArrayList();

    }

    @FXML
    void buttonEditClientsOnAction(ActionEvent event) {  //изменение данных о клиенте

        Client client = tableViewClients.getSelectionModel().getSelectedItem();  //создание экземпляра клиента

        if (tableViewClients.getSelectionModel().isEmpty()) {
            Alarm.showAlert(Alert.AlertType.ERROR, "Редактирование",
                    "Ошибка!", "Выберите клиента.");
        } else {
            buttonEditClients.getScene().getWindow();       //открывает окно редактирования
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/fxml/editClients.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();

            EditClientController editAutoController = loader.getController();
            editAutoController.getEditData(client);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        }
    }

    @FXML
    void buttonRefrashTableClientsOnAction() {     //обовление таблицы
        tableViewClients.getItems().clear();
        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;//создание соединения с бд
             PreparedStatement preparedStatement = connection.prepareStatement(DbConnection.SELECT_CLIENTS) ;) { //передача запроса
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                clients.add(new Client(         //передача данных в конструктор
                        resultSet.getInt("id_client"),
                        resultSet.getString("fio_client"),
                        resultSet.getString("birth_date"),
                        resultSet.getString("number_prav"),
                        resultSet.getString("auto_skill"),
                        resultSet.getString("gender"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getString("city")
                ));
                tableViewClients.setItems(clients);  //заполнение таблицы
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void buttonDeleteTableClientsOnAction() { //удаление клиента из таблицы
        if (tableViewClients.getSelectionModel().isEmpty()) {   //если в таблицы ничего не выбрано
            Alarm.showAlert(Alert.AlertType.ERROR, "Удаление", //то вылазиет оконо с ошибкой
                    "Ошибка!", "Выберите страхователя для удаления");
        } else {

            try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;//создание соединения
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE from client where id_client = ?")) {//передача запроса
                int client = tableViewClients.getSelectionModel().getSelectedItem().getId_clienta();
                preparedStatement.setInt(1, client);
                preparedStatement.executeUpdate();
                Alarm.showAlert(Alert.AlertType.INFORMATION, "Удаление",
                        "Успешно!", "Клиент удален.");
                buttonRefrashTableClientsOnAction();
            } catch (SQLException e) {
                System.out.println(e);
                Alarm.showAlert(Alert.AlertType.INFORMATION, "Удаление",
                        "Ошибка!", "Подробности:" + e + ".");
//              e.printStackTrace();
            }
        }
    }


}
