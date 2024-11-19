package com.example.projectjavafx;

import com.example.projectjavafx.DB.DBConnect;
import com.example.projectjavafx.Models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    private String idProdduct;

    private Product product;

    private SpinnerValueFactory<Integer> spin;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setData(Product product){
        this.product=product;
        idProdduct = product.getId();
        prod_name.setText(product.getName());
        prod_price.setText(String.valueOf(product.getPrice()));
        String path = "E:\\Spring\\ProjectJavaFX\\src\\main\\resources\\Img\\"+product.getPhoto();
        File file =new File(path);
        Image image = new Image(file.toURI().toString(), 230, 127, false, true);
        prod_imageView.setImage(image);
        price=product.getPrice();
        if(product.getStatus().equals("Hết")){
            prod_addBtn.setText("Hết");
            prod_addBtn.setDisable(true);
        }
    }


    private int qty;
    private int price;
    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    public static boolean check = false;


    @FXML
    void addBtn(ActionEvent event) throws SQLException, IOException {
        Window owner = prod_addBtn.getScene().getWindow();

        qty = prod_spinner.getValue();
        DBConnect dbConnect = new DBConnect();

        if(check==false){
            dbConnect.insertOrderId();
            check=true;
        }

        String mes = dbConnect.orderProduct(idProdduct,qty);

        if(!mes.equals("")){
            showAlert(Alert.AlertType.ERROR,owner,"Error",mes);
        }

        showAlert(Alert.AlertType.INFORMATION,owner,"Thông báo","Thêm thành công!");

        if(mainController != null){
            mainController.showOrder();
        }else{
            System.out.println("Main null");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuantity();
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
