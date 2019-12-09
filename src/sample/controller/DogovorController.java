package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.ls.LSOutput;
import sample.model.Dogovor1;
import sample.util.Alarm;
import sample.util.DbConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DogovorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Dogovor1, Integer> id_dogovor;

    @FXML
    private TableColumn<Dogovor1, String> fio_client;

    @FXML
    private TableColumn<Dogovor1, String> daye_issue;

    @FXML
    private TableColumn<Dogovor1, String> date_break;

    @FXML
    private TableColumn<Dogovor1, String> type_insurance;

    @FXML
    private TableColumn<Dogovor1, String> model;

    @FXML
    private TableColumn<Dogovor1, String> marka;

    @FXML
    private TableColumn<Dogovor1, String> payment;

    @FXML
    private TableColumn<Dogovor1, String> pay;

    @FXML
    private TableColumn<Dogovor1, String> fio_agent;
    @FXML
    private TableColumn<Dogovor1, Integer> auto;

    @FXML
    private Button buttonLoadDogovor;
    @FXML
    private Button buttonEditDogovor;
    @FXML
    private Button buttonOtchet;

    @FXML
    private Button buttonDeleteDogovor;

    ObservableList<Dogovor1> dogovors = FXCollections.observableArrayList();

    @FXML
    private TableView<Dogovor1> tableViewDogovor;


    @FXML
    void initialize() {
        id_dogovor.setCellValueFactory(new PropertyValueFactory<>("id"));
        daye_issue.setCellValueFactory(new PropertyValueFactory<>("date_issue"));
        date_break.setCellValueFactory(new PropertyValueFactory<>("date_brake"));
        fio_agent.setCellValueFactory(new PropertyValueFactory<>("id_agent"));
        fio_client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        type_insurance.setCellValueFactory(new PropertyValueFactory<>("type_insurance"));
        payment.setCellValueFactory(new PropertyValueFactory<>("insurance_payment"));
        auto.setCellValueFactory(new PropertyValueFactory<>("id_auto"));
        pay.setCellValueFactory(new PropertyValueFactory<>("pay"));

//       marka.setCellValueFactory(new PropertyValueFactory<>("marka"));
//        model.setCellValueFactory(new PropertyValueFactory<>("model"));
//        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
//        pay.setCellValueFactory(new PropertyValueFactory<>("pay"));
//        fio_agent.setCellValueFactory(new PropertyValueFactory<>("fio_agent"));


    }


    @FXML
    public void buttonOtchetOnAction(ActionEvent event) {
        String query = "select sum(insurance_payment) from main_office.public.dogovor where branch='H'";
        int sum= 0;
        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                sum = resultSet.getInt(1);
            }
            Alarm.showAlert(Alert.AlertType.INFORMATION, "Отчет",
                    "Главный офис", "Сумма составляет: " + sum + "руб.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @FXML
    void buttonDeleteDogovorOnAction(ActionEvent event) {
        if (tableViewDogovor.getSelectionModel().isEmpty()) {
            Alarm.showAlert(Alert.AlertType.ERROR, "Удаление",
                    "Ошибка!", "Выберите договор.");
        } else {
            try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE from dogovor where id_договора = ?")) {
                int client = tableViewDogovor.getSelectionModel().getSelectedItem().getId();
                preparedStatement.setInt(1, client);
                preparedStatement.executeUpdate();

                Alarm.showAlert(Alert.AlertType.INFORMATION, "Удаление",
                        "Успешно!", "Договор рассторгнут.");


                buttonLoadDogovorOnAction();
            } catch (SQLException e) {
                Alarm.showAlert(Alert.AlertType.ERROR, "Удаление",
                        "Ошибка!", "Подробности:" + e + ".");
//              e.printStackTrace();
            }
        }
    }

    @FXML
    void buttonEditDogovorOnAction(ActionEvent event) {

    }

    @FXML
    void buttonLoadDogovorOnAction() {
        tableViewDogovor.getItems().clear();
        String query = "select id_договора" +
                ",dateofissue," +
                "breakupdate," +
                "id_agent," +
                "id_client," +
                "insurance_type," +
                "insurance_payment," +
                "id_auto," +
                "pay" +
                " from main_office.public.dogovor where branch='H'";


        int id_agent = 0;
        String fioAgnt = "";

        int id_client = 0;
        String fioClient = "";

        int id_auto = 0;
        String markaAuto = "";

        try (Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id_agent = resultSet.getInt("id_agent");
                ResultSet rs = resultSet(id_agent);
                if (rs.next()) {
                    fioAgnt = rs.getString("fio_agent");
                }

                id_client = resultSet.getInt("id_client");
                ResultSet rs1 = resultSet1(id_client);
                if (rs1.next()) {
                    fioClient = rs1.getString("fio_client");
                }

                id_auto = resultSet.getInt("id_auto");
                ResultSet rs2 = resultSet2(id_auto);
                if (rs2.next()) {
                    markaAuto = rs2.getString("marka");
                }
                dogovors.add(new Dogovor1(
                        resultSet.getInt("id_договора"),
                        resultSet.getString("dateofissue"),
                        resultSet.getString("breakupdate"),
                        fioAgnt,
                        fioClient,
                        resultSet.getString("insurance_type"),
                        resultSet.getInt("insurance_payment"),
                        markaAuto,
                        resultSet.getInt("pay")
                ));
                tableViewDogovor.setItems(dogovors);
            }
            preparedStatement.close();
            resultSet.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet resultSet(int id) {
        ResultSet resultSet1 = null;
        String query1 = "SELECT fio_agent from agent where id_agent = ?";
        try {
            Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, id);
            resultSet1 = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet1;
    }

    public ResultSet resultSet1(int id) {
        ResultSet resultSet1 = null;
        String query1 = "SELECT fio_client from client where id_client = ?";
        try {
            Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, id);
            resultSet1 = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet1;
    }

    public ResultSet resultSet2(int id) {
        ResultSet resultSet1 = null;
        String query1 = "SELECT marka from auto where id_авто = ?";
        try {
            Connection connection = DriverManager.getConnection(DbConnection.DB_URL, DbConnection.DB_USER, DbConnection.DB_PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, id);
            resultSet1 = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet1;
    }

}

