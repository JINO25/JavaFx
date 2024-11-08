package com.example.projectjavafx;
import com.example.projectjavafx.DB.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

        @FXML
        private TextField emailIdField;

        @FXML
        private PasswordField passwordField;

        @FXML
        private Button submitButton;

        @FXML
        public void login(ActionEvent event) throws SQLException, IOException {

            Window owner = submitButton.getScene().getWindow();

            System.out.println(emailIdField.getText());
            System.out.println(passwordField.getText());

            if (emailIdField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "Please enter your email id");
                return;
            }
            if (passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                        "Please enter a password");
                return;
            }

            String emailId = emailIdField.getText();
            String password = passwordField.getText();

            DBConnect dbConnect = new DBConnect();
            boolean flag = dbConnect.checkAccount(emailId,password);
//            boolean flag = true;

            if (!flag) {
                infoBox("Please enter correct Email and Password", null, "Failed");
            } else {
                infoBox("Login Successful!", null, "Success");
                FXMLLoader root = new FXMLLoader(getClass().getResource("main.fxml"));
                Scene scene = new Scene(root.load());
                Stage stage = new Stage();
                stage.setTitle("Main");
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        }

        @FXML
        public void signUp(ActionEvent event) throws IOException {
//            Parent parent = FXMLLoader.load(getClass().getResource("signUp_form.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("Sign Up");
//            stage.setScene(new Scene(parent,350,300));
//            stage.show();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signUp_form.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }

        public static void infoBox(String infoMessage, String headerText, String title) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText(infoMessage);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.showAndWait();
        }

        private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initOwner(owner);
            alert.show();
        }
}
