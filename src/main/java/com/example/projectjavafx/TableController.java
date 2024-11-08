package com.example.projectjavafx;

import com.example.projectjavafx.DB.DBConnect;
import com.example.projectjavafx.Models.Product;
import com.example.projectjavafx.Models.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableController {
    @FXML
    private Button bookTable;

    @FXML
    private Button cancelTable;

    @FXML
    private Label nameTable;

    @FXML
    private Label statusTable;

    private Table table;

    private int idTable;


    public void setData(Table table){
        this.table=table;
        idTable = table.getId();
        nameTable.setText(String.valueOf("Bàn Số "+table.getStt()));
        if(table.getStatus()){
            bookTable.setOpacity(0.2);
            bookTable.setDisable(true);
            statusTable.setText("Đã Đặt");
        }
    }

    @FXML
    public void BookingTable(ActionEvent event) throws SQLException {
        DBConnect dbConnect = new DBConnect();
        dbConnect.bookingTable(idTable,true);
        bookTable.setOpacity(0.2);
        bookTable.setDisable(true);
        statusTable.setText("Đã Đặt");
    }

    @FXML
    public void CancelTable(ActionEvent event) throws SQLException {
        DBConnect dbConnect = new DBConnect();
        dbConnect.bookingTable(idTable,false);
        bookTable.setDisable(false);
        bookTable.setOpacity(1);
        statusTable.setText("Trống");
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}
