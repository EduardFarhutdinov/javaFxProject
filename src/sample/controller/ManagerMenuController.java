package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerMenuController {


    @FXML
    private Button buttonForBranchTwoOffice;

    @FXML
    private Button buttonForBranchOneOffice;

    @FXML
    private Button buttonForMainOffice;

    @FXML
    private Button buttonRedirectApp;

    public void buttonForMainOfficeOnAction() {
        buttonForMainOffice.getScene().getWindow();        //Открывается окно с таблицей из клиентов
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/mainOffice.fxml"));
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

    public void buttonForBranchOneOfficeOnAction(ActionEvent event) {
        buttonForBranchOneOffice.getScene().getWindow();        //Открывается окно с таблицей из клиентов
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/branchOne.fxml"));
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

    public void buttonForBranchTwoOfficeOnAction(ActionEvent event) {
        buttonForBranchTwoOffice.getScene().getWindow();        //Открывается окно с таблицей из клиентов
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fxml/branchTwo.fxml"));
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

    public void buttonRedirectAppOnAction(ActionEvent event) {
        buttonRedirectApp.getScene().getWindow();
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
    }
}
