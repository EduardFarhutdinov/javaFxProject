package sample.util;

import javafx.scene.control.Alert;

public class Alarm {
    public static void showAlert(Alert.AlertType alType, String title, String headerText, String contentText) {

        Alert alert = new Alert(alType);
//        alert.initOwner(null);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();



    }

    @Override
    public String toString() {
        return super.toString();
    }
}
