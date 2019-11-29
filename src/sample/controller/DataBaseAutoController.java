package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sample.model.Auto;
import sample.util.Alarm;
import sample.util.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DataBaseAutoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableColumn<String,Auto> tableColumnIdAuto;

    @FXML
    private TableColumn<String,Auto> tableColumnMarka;

    @FXML
    private TableColumn<String,Auto> tableColumnModel;

    @FXML
    private TableColumn<String,Auto> tableColumnColor;

    @FXML
    private TableColumn<String,Auto> tableColumnPower;

    @FXML
    private TableColumn<String,Auto> tableColumnVolumeEngine;

    @FXML
    private TableColumn<String,Auto> tableColumnGosNumber;

    @FXML
    private TableColumn<String,Auto> tableColumnSpeed;

    @FXML
    private TableColumn<String,Auto> tableColumnTypeDrive;

    @FXML
    private TableColumn<String,Auto> tableColumnCityTypeBody;

    @FXML
    private Button buttonRefrashTableAuto;

    @FXML
    private Button buttonEditAuto;

    @FXML
    private TableView<Auto> tableViewAuto;

    ObservableList<Auto> autos ;

    @FXML
    void buttonRefrashTableAutoOnAction() {  //метод обновления таблицы

        tableViewAuto.getItems().clear();
        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;//создание соединения с бд
             PreparedStatement preparedStatement = connection.prepareStatement(DbConnection.SELECT_AUTO) ;) {//Добавления запроса
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {                             //создание экземпляра Автомобиль

                autos.add(new Auto(                              //предача данных в конструктор
                        resultSet.getInt( "id_авто"),
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
                tableViewAuto.setItems(autos);               //добавление экземпляра в таблицу
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

        tableColumnIdAuto.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMarka.setCellValueFactory(new PropertyValueFactory<>("marka"));
        tableColumnModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tableColumnColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        tableColumnPower.setCellValueFactory(new PropertyValueFactory<>("power"));
        tableColumnVolumeEngine.setCellValueFactory(new PropertyValueFactory<>("volume"));
        tableColumnGosNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableColumnSpeed.setCellValueFactory(new PropertyValueFactory<>("speed"));
        tableColumnTypeDrive.setCellValueFactory(new PropertyValueFactory<>("drive"));
        tableColumnCityTypeBody.setCellValueFactory(new PropertyValueFactory<>("body"));

        autos = FXCollections.observableArrayList();

    }
   @FXML
    public void buttonDeleteTableAutoOnAction() {              //удаление автомобиля из таблицы


         if(tableViewAuto.getSelectionModel().isEmpty()){ Alarm.showAlert(Alert.AlertType.ERROR, "Удаление",
                 "Ошибка!", "Выберите автомобиль для удаления");
         }else {

             try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS) ;
                  PreparedStatement preparedStatement = connection.prepareStatement("DELETE from auto where id_авто = ?")) {
                 int auto = tableViewAuto.getSelectionModel().getSelectedItem().getId();
                 preparedStatement.setInt(1, auto);
                 preparedStatement.executeUpdate();
                 Alarm.showAlert(Alert.AlertType.INFORMATION, "Удаление",
                         "Успешно!","Автомобиль удален.");
                 buttonRefrashTableAutoOnAction();
             } catch (SQLException e) {
                 Alarm.showAlert(Alert.AlertType.WARNING, "Удаление",
                         "Ошибка!", "Подробности:" + e + ".");
                 e.printStackTrace();
             }
         }
   }


    @FXML
    void buttonEditAutoOnAction() {        //редактирование данных авто

        Auto auto = tableViewAuto.getSelectionModel().getSelectedItem(); //создается экземпляп класса


        if (tableViewAuto.getSelectionModel().isEmpty() ){
            Alarm.showAlert(Alert.AlertType.ERROR, "Редактирование",
                    "Ошибка!","Выберите автомобиль.");
        }else {
            buttonEditAuto.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/fxml/editAuto.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();

            EditAutoController editAutoController =loader.getController();
            editAutoController.getEditData(auto);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

        }



    }
}
