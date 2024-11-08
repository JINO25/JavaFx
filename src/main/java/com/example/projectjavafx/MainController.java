package com.example.projectjavafx;

import com.example.projectjavafx.DB.DBConnect;
import com.example.projectjavafx.ExportFile.PDFExporter;
import com.example.projectjavafx.Models.Product;
import com.example.projectjavafx.Models.Staticstics;
import com.example.projectjavafx.Models.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AnchorPane menu_form;


    @FXML
    private Button dashboard_btn;

    @FXML
    private Label revenueToday;

    @FXML
    private Label NumberOfCilent;

    @FXML
    private Label orderTotalToday;

    @FXML
    private Label numberOfOrderTotal;

    @FXML
    private LineChart<String, Integer> revenue_chart;

    @FXML
    private BarChart<String, Integer> customer_chart;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button menu_btn;


    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField idProduct;

    @FXML
    private TextField productName;

    @FXML
    private ComboBox type;

    @FXML
    private TextField quantity;

    @FXML
    private TextField price;

    @FXML
    private ImageView img;

    private Image image;

    @FXML
    private TableView<Product> table_view;

    @FXML
    private TableColumn<Product, Integer> table_col_id;
    @FXML
    private TableColumn<Product, String> table_col_name;
    @FXML
    private TableColumn<Product, String> table_col_type;
    @FXML
    private TableColumn<Product, Integer> table_col_stock;
    @FXML
    private TableColumn<Product, Integer> table_col_price;
    @FXML
    private TableColumn<Product,String> table_col_photo;

    @FXML
    private GridPane menu_Grid;

    @FXML
    private TableView<Product> menu_table;

    @FXML
    private TableColumn<Product, Integer> menu_col_price;

    @FXML
    private TableColumn<Product, String> menu_col_pro;

    @FXML
    private TableColumn<Product, Integer> menu_col_quantity;

    @FXML
    private Button menu_delete;

    @FXML
    private TextField searching;

    @FXML
    private Button menu_receipt;

    @FXML
    private TextField menu_receive;

    @FXML
    private Label menu_total;

    @FXML
    private Label change;

    @FXML
    private Button payment;

    @FXML
    private Button statistic_btn;

    @FXML
    private GridPane table_grid;

    @FXML
    private Button booking_table_btn;

    @FXML
    private AnchorPane booking_table_form;

    @FXML AnchorPane statistic_form;

    private ObservableList<Product> cardList = FXCollections.observableArrayList();
    private ObservableList<Table> tableList = FXCollections.observableArrayList();

    private CardController cardController;

    @FXML
    private TextField nameProduct;

    @FXML
    private TableView statistic_table;

    @FXML
    private TableColumn<Product,String> name_col;

    @FXML
    private TableColumn<Product,String> type_col;

    @FXML
    private TableColumn<Product,Integer> totalOrder_col;

    @FXML
    private TableColumn<Product,Integer> totalRevenue_col;

    @FXML
    private TableColumn<Product,String> date_col;

    @FXML
    private DatePicker datePicker;

    @FXML
    private PieChart statistic_chart;

    @FXML
    private Label monthLabel;

    @FXML
    private Label dayLabel;

    @FXML
    private RadioButton day;

    @FXML
    private RadioButton month;

    @FXML
    private RadioButton months;

    @FXML
    private TextField monthField;

    @FXML
    private TextField fromMonth;

    @FXML
    private TextField toMonth;

    @FXML
    private Label from;

    @FXML
    private Label to;

    @FXML
    private Pane monthsPane;

    @FXML
    public void switchForm(ActionEvent event) throws SQLException {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            statistic_form.setVisible(false);
            booking_table_form.setVisible(false);

            showDashBoard();
            showLineChart();
        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            statistic_form.setVisible(false);
            booking_table_form.setVisible(false);

            inventoryTypeList();
            showDataTable();
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            statistic_form.setVisible(false);
            booking_table_form.setVisible(false);

            menuShow();
            showOrder();

        } else if (event.getSource() == statistic_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            statistic_form.setVisible(true);
            booking_table_form.setVisible(false);
        } else if(event.getSource() == booking_table_btn){
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            statistic_form.setVisible(false);
            booking_table_form.setVisible(true);

            tableShow();
        }

    }



//    DashBoard-------------------------------
        public void showDashBoard() throws SQLException {
            DBConnect connect = new DBConnect();
            List<Integer> list;
            list=connect.getTotalToday();
            revenueToday.setText(String.valueOf(list.get(0))+"đ");
            NumberOfCilent.setText(String.valueOf(list.get(1)));

            int numberOfOrderToday = connect.getNumberOfOrderToday();
            numberOfOrderTotal.setText(String.valueOf(numberOfOrderToday));
        }

        public void showLineChart() throws SQLException{
            revenue_chart.getData().clear();
            DBConnect connect = new DBConnect();
            ObservableList<XYChart.Data<String,Integer>> list = connect.getDataForLineChart();
            XYChart.Series<String, Integer> series = new XYChart.Series<>(list);
            series.setName("doanh thu theo ngày");
            revenue_chart.getData().add(series);
            revenue_chart.setTitle("Doanh Thu");
        }

        public void showBarChart() throws SQLException{
            customer_chart.getData().clear();
            DBConnect dbConnect = new DBConnect();
            ObservableList<XYChart.Data<String,Integer>> list =dbConnect.getDataForBarChart();
            XYChart.Series<String, Integer> series = new XYChart.Series<>(list);
            series.setName("Số khách theo ngày");
            customer_chart.getData().add(series);
            customer_chart.setTitle("Khách Hàng");
        }




//    inventory --------------------------------

    public void inventoryTypeList() throws SQLException {
        DBConnect dbConnect = new DBConnect();
        ObservableList<String> list = dbConnect.showTypeList();
        type.setItems(list);
    }

    public void showDataTable() throws SQLException {
        DBConnect dbConnect = new DBConnect();
        ObservableList<Product> products = dbConnect.listData();
        table_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table_col_photo.setCellValueFactory(new PropertyValueFactory<>("photo"));
        table_view.setItems(products);
    }

    public void selectDataTableView(){
        ObservableList<Product> products;
        Product product = table_view.getSelectionModel().getSelectedItem();
        int index = table_view.getSelectionModel().getSelectedIndex();
        idProduct.setText(product.getId());
        productName.setText(product.getName());
        int stock = product.getStock();
        quantity.setText(String.valueOf(stock));
        price.setText(String.valueOf(product.getPrice()));
        String path = "E:\\Spring\\ProjectJavaFX\\src\\main\\resources\\Img\\"+product.getPhoto();
        File file =new File(path);
        Image image = new Image(file.toURI().toString(), 120, 127, false, true);
        img.setImage(image);

    }

    @FXML
    public void inventory_clear(ActionEvent event) {
        idProduct.setText("");
        productName.setText("");
        type.getSelectionModel().clearSelection();
        quantity.setText("");
        price.setText("");
        data.photo="";
        img.setImage(null);
    }

    @FXML
    public void add_btn() throws IOException, SQLException {
        Window owner = addButton.getScene().getWindow();
        String name = productName.getText();
        String typeProduct = type.getValue().toString();
        String quantities = quantity.getText();
        String priceProduct = price.getText();

        if(name.isEmpty() || typeProduct.isEmpty() || quantities.isEmpty() || priceProduct.isEmpty() || data.photo.isEmpty()){
            showAlert(Alert.AlertType.ERROR,owner,"Form Error!",
                    "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        System.out.println(data.photo);
        saveImg(data.photo);

        DBConnect dbConnect = new DBConnect();
        int row = dbConnect.addProduct(name,typeProduct,quantities,priceProduct,data.photo);
        if (row>0) {
            infoBox("Thêm sản phẩm thành công!", null, "Success");
            productName.setText("");
            type.getSelectionModel().clearSelection();
            quantity.setText("");
            price.setText("");
        } else {
            infoBox("Vui lòng kiểm tra lại", null, "Failed");
        }

    }

    @FXML
    public void update_btn() throws SQLException, IOException {
        Window owner = updateButton.getScene().getWindow();
        String id = idProduct.getText();
        String name = productName.getText();
        String typeProduct = type.getValue().toString();
        String quantities = quantity.getText();
        String priceProduct = price.getText();
//        ObservableList<Product> products;
        Product product = table_view.getSelectionModel().getSelectedItem();
        String photo = product.getPhoto();

        if(name.isEmpty() || typeProduct.isEmpty() || quantities.isEmpty() || priceProduct.isEmpty()){
            showAlert(Alert.AlertType.ERROR,owner,"Form Error!",
                    "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        saveImg(photo);

        DBConnect dbConnect = new DBConnect();
        int row = dbConnect.updateProduct(id,name,typeProduct,quantities,priceProduct,photo);
        if (row>0) {
            infoBox("Cập nhật thành công!", null, "Success");
            productName.setText("");
            type.getSelectionModel().clearSelection();
            quantity.setText("");
            price.setText("");
        } else {
            infoBox("Vui lòng kiểm tra lại", null, "Failed");
        }
    }

    @FXML
    public void delete_btn() throws SQLException, IOException {
        Window owner = deleteButton.getScene().getWindow();
        String id = idProduct.getText();

        Product product = table_view.getSelectionModel().getSelectedItem();
        String photo = product.getPhoto();

        DBConnect dbConnect = new DBConnect();
        dbConnect.deleteProduct(id);
        infoBox("Xoá thành công",null,"Success");
    }



    private void saveImg(String name) throws IOException {
        String path = "E:\\Spring\\ProjectJavaFX\\src\\main\\resources\\Img";
        File file =new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        if(data.path != null && !data.path.isEmpty()){
            File sourceFile = null;
            sourceFile = new File(data.path);
            File destination = new File(path+"\\"+name);
            if(!destination.exists()){
                Files.copy(sourceFile.toPath(), destination.toPath());
            }else{
                System.out.println("Hinh da co");
            }
        }
    }

    @FXML
    public void import_btn(){
        // Lấy cửa sổ hiện tại từ một node bất kỳ (ví dụ từ img hoặc root node)
        Stage stage = (Stage) img.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Resource File", "*.png", "*.jpg"));
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String photo = file.getAbsolutePath();
            data.path=photo;
            int inFirstIndex = photo.lastIndexOf("\\");
            data.photo = photo.substring(inFirstIndex+1,photo.length());
            Image image = new Image(file.toURI().toString(), 120, 127, false, true);
            img.setImage(image);
            System.out.println(data.photo);
        }
    }

    /* ------------------------Menu--------------------- */

    public ObservableList<Product> menuGetData() throws SQLException {

        DBConnect dbConnect=new DBConnect();
        ObservableList<Product> list = dbConnect.getDataProduct();
        return list;
    }

    public ObservableList<Product> menuGetDataForSearching(String name) throws SQLException {

        DBConnect dbConnect=new DBConnect();
        ObservableList<Product> list = dbConnect.getDataFromSearching(name);
        return list;
    }

    public void menuShow() throws SQLException {
        cardList.clear();
        cardList.addAll(menuGetData());
        int row = 0;
        int column = 0;

        menu_Grid.getChildren().clear();
        menu_Grid.getRowConstraints().clear();
        menu_Grid.getColumnConstraints().clear();

        for (int i = 0; i < cardList.size(); i++) {

            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("/com/example/projectjavafx/card.fxml"));
                AnchorPane pane = load.load();
                cardController=load.getController();
                cardController.setData(cardList.get(i));
                if (column == 2) {
                    column = 0;
                    row += 1;
                }

                menu_Grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuShow(String name) throws SQLException {
        cardList.clear();
        cardList.addAll(menuGetDataForSearching(name));
        int row = 0;
        int column = 0;

        menu_Grid.getChildren().clear();
        menu_Grid.getRowConstraints().clear();
        menu_Grid.getColumnConstraints().clear();

        for (int i = 0; i < cardList.size(); i++) {

            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("/com/example/projectjavafx/card.fxml"));
                AnchorPane pane = load.load();
                cardController=load.getController();
                cardController.setData(cardList.get(i));
                if (column == 2) {
                    column = 0;
                    row += 1;
                }

                menu_Grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void search(ActionEvent actionEvent) throws SQLException {

        searching.setOnKeyPressed(e->{
            String search = searching.getText();
            if(!search.isEmpty() && e.getCode()==KeyCode.ENTER){
                try {
                    menuShow(search);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (search.isEmpty()) {
                try {
                    menuShow();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    public void showOrder() throws SQLException {
        DBConnect dbConnect = new DBConnect();
        ObservableList<Product> list = dbConnect.getOrder();
        if(list != null){
            menu_col_pro.setCellValueFactory(new PropertyValueFactory<>("name"));
            menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
            menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            menu_table.setItems(list);
            menuDisplayTotal();
        }else{
            menu_table.setItems(null);
        }
    }

    public void menuDisplayTotal() throws SQLException {
        int total=0;
        DBConnect dbConnect = new DBConnect();
        ObservableList<Product> list = dbConnect.getOrder();

        for(Product p : list){
            total += p.getPrice() * p.getStock();
        }

        menu_total.setPrefSize(100,36);
        menu_total.setText(String.valueOf(total)+" VNĐ");


    }

    public void menuDisplayChange(){
        String receive=menu_receive.getText();
        String menuTotal = menu_total.getText();

        String numeric = menuTotal.substring(0,menuTotal.lastIndexOf(" "));
        int total = Integer.parseInt(numeric);
        int receiverNumeric= Integer.parseInt(receive);
        int changeForCustomer =receiverNumeric-total;
        change.setText(String.valueOf(changeForCustomer)+" VNĐ");
    }

    @FXML
    public void menuDelete() throws SQLException {
        Window owner = menu_delete.getScene().getWindow();
        Product product = menu_table.getSelectionModel().getSelectedItem();
        DBConnect dbConnect = new DBConnect();

        if (product!=null) {
            dbConnect.deleteOrder(product.getId(), product.getStock());
            showOrder();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác Nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có muốn xoá hết các order không?");
            alert.initOwner(owner);

            Optional<ButtonType> rs = alert.showAndWait();
            if(rs.isPresent() && rs.get() == ButtonType.OK){
                dbConnect.deleteAllOrder();
                showOrder();
            }
        }

    }


    public void checkPaymentClick() throws IOException, SQLException {
        Window owner = payment.getScene().getWindow();
        DBConnect dbConnect = new DBConnect();
        if(menu_table == null){
            showAlert(Alert.AlertType.ERROR, owner, "Error","Vui lòng order để thanh toán");
        }else{
            String menuTotal = menu_total.getText();
            String numeric = menuTotal.substring(0,menuTotal.lastIndexOf(" "));
            int total = Integer.parseInt(numeric);
            System.out.println(total);
            int rs = dbConnect.paymentOrder(total);
            if(rs>0){
                infoBox("Thanh Toán thành công.", null, "Success");
                menu_table.setItems(null);
                CardController.check=false;
                menu_receive.setText("");
                menu_total.setText("0.0");
                change.setText("0.0");
            }
        }
    }

    public void printInvoice() throws SQLException {
        Stage stage = new Stage();
        PDFExporter pdfExporter = new PDFExporter();
        pdfExporter.exportPDF(stage);

    }



//    ------------------- Booking table
public ObservableList<Table> tableGetData() throws SQLException {

    DBConnect dbConnect=new DBConnect();
    ObservableList<Table> list = dbConnect.getAllTable();
    return list;
}

    public void tableShow() throws SQLException {
        tableList.clear();
        tableList.addAll(tableGetData());
        int row = 0;
        int column = 0;

        table_grid.getChildren().clear();
        table_grid.getRowConstraints().clear();
        table_grid.getColumnConstraints().clear();

        for (int i = 0; i < tableList.size(); i++) {

            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("/com/example/projectjavafx/table.fxml"));
                AnchorPane pane = load.load();
                TableController tableController = load.getController();
                tableController.setData(tableList.get(i));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                table_grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


//    Statistics-------------------------------------------------
    public void showChoices() {

        ToggleGroup tg = new ToggleGroup();
        day.setToggleGroup(tg);
        month.setToggleGroup(tg);
        months.setToggleGroup(tg);

        if (day.isSelected()) {
            dayLabel.setVisible(true);
            datePicker.setVisible(true);

            monthLabel.setVisible(false);
            monthField.setVisible(false);
            monthsPane.setVisible(false);

            month.setSelected(false);
            months.setSelected(false);
        } else if (month.isSelected()) {
            monthLabel.setVisible(true);
            monthField.setVisible(true);

            dayLabel.setVisible(false);
            datePicker.setVisible(false);
            monthsPane.setVisible(false);

            day.setSelected(false);
            months.setSelected(false);
        } else if (months.isSelected()) {
            monthsPane.setVisible(true);

            dayLabel.setVisible(false);
            datePicker.setVisible(false);
            monthLabel.setVisible(false);
            monthField.setVisible(false);

            day.setSelected(false);
            month.setSelected(false);
        }
    }


    public void showStatisticsData() throws SQLException {
        LocalDate date =datePicker.getValue();
        String name = nameProduct.getText();
        String monthFieldText = monthField.getText();
        String from = fromMonth.getText();
        String to = toMonth.getText();
        DBConnect dbConnect= new DBConnect();
        ObservableList<Staticstics> list = null;

        if(day.isSelected()){
            if(date == null) {
                list = dbConnect.getDataFromDay("",name);
            }else {
                list = dbConnect.getDataFromDay(date.toString(),name);
            }
        } else if (month.isSelected()) {
                list = dbConnect.getDataFromMonth(monthFieldText,name);
        }else if(months.isSelected()){
            list = dbConnect.getDataFromMonths(from,to,name);
        }


        ObservableList<PieChart.Data> pieCharts = FXCollections.observableArrayList();
        if(list != null){
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            totalOrder_col.setCellValueFactory(new PropertyValueFactory<>("totalBill"));
            totalRevenue_col.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
            date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            statistic_table.setItems(list);

            if(date == null) {
                pieCharts = dbConnect.getDataforPieChartNotDate(name);
                showPercentagePieChart(pieCharts);
            }else{
                pieCharts = dbConnect.getDataforPieChart(date.toString(),name);
                showPercentagePieChart(pieCharts);
            }

//            statistic_chart.setData(pieCharts);
            statistic_chart.setTitle("Tỉ lệ (%) 2 loại sản phẩm");
//            showPercentagePieChart(pieCharts);
        }
    }

    public void showPercentagePieChart(ObservableList<PieChart.Data> pieCharts){
        statistic_chart.setData(pieCharts);

        // Calculate the total value of all slices
        double total = pieCharts.stream().mapToDouble(PieChart.Data::getPieValue).sum();

        // Update each slice's label with percentage
        for (PieChart.Data data : pieCharts) {
            double percentage = (data.getPieValue() / total) * 100;
            data.setName(String.format("%s (%.1f%%)", data.getName(), percentage));
        }
    }

    // Alert ----------------------------------------------------
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboard_form.setVisible(true);

        try {
            showDashBoard();
            showLineChart();
            showBarChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
