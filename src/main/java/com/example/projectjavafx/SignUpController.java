package com.example.projectjavafx;

import com.example.projectjavafx.DB.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private TextField userName;
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordConfirmField;

    @FXML
    private Button submitButton;
    
    @FXML
    private Button login;


    @FXML
    protected  void login(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("login_form.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(parent,350,300));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    public void signUp(ActionEvent event) throws SQLException {

        Window owner = submitButton.getScene().getWindow();

        if(userName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,owner,"Form Error!","Please enter your name");
            return;
        }

        if (emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (passwordField.getText().isEmpty() || passwordConfirmField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        if(!passwordField.getText().equals(passwordConfirmField.getText())){
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Password and password confirm is not correct");
            return;
        }

        String name = userName.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        DBConnect dbConnect = new DBConnect();

        boolean flag = dbConnect.createAccount(name,email,password);;

        if (!flag) {
            infoBox("Please Check Again!", null, "Failed");
        } else {
            infoBox("Sing Up Successful!", null, "Failed");
        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
