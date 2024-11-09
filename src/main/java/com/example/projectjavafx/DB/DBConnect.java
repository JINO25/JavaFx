package com.example.projectjavafx.DB;
import com.example.projectjavafx.MainController;
import com.example.projectjavafx.Models.Invoice;
import com.example.projectjavafx.Models.Product;
import com.example.projectjavafx.Models.Staticstics;
import com.example.projectjavafx.Models.Table;
import com.example.projectjavafx.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {
    private String userName = "root";
    private String pwd ="0834023573Dat@@";
    private String url ="jdbc:mysql://localhost:3306/ProjectJavaFX";
    private Connection connection;

    public DBConnect() throws SQLException {
        this.connect();
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url,userName,pwd);
    }

    public boolean createAccount(String name,String email, String password) throws SQLException {
        String SQL = "INSERT INTO user (name,email, pwd) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            int rs = pstmt.executeUpdate();
            System.out.println("Result: " + (rs > 0 ? "Success" : "Failed"));
            connection.close();
            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkAccount(String email, String password) throws SQLException {
        String sql = "select * from user where email=? and pwd=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1,email);
        stm.setString(2,password);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            data.id=rs.getInt("id");
            data.userName=rs.getString("name");
            System.out.println(rs.getString("name"));
            System.out.println("Login successful for: " + email);
            return true;
        } else {
            System.out.println("Login failed for: " + email);
            return false;
        }
    }

    public ObservableList<String> showTypeList() throws SQLException {
        String sql = "SELECT * FROM type_product";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        ObservableList<String> list = FXCollections.observableArrayList();
        while (rs.next()) {
            list.add(rs.getString("typeName"));
        }
        connection.close();
        return list;
    }


    public ObservableList<Product> listData() throws SQLException {
        ObservableList<Product> list= FXCollections.observableArrayList();
        String sql="select p.productId, p.productName, p.price, p.photo, t.typeName from product as p join type_product as t on p.typeID = t.typeID;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            Product product = new Product(
                    rs.getString("productId"),
                    rs.getString("productName"),
                    rs.getString("typeName"),
                    rs.getInt("price"),
                    rs.getString("photo")
            );
            list.add(product);
        }
        connection.close();
        return list;
    }

    public int addProduct(String nameProduct, String typeProduct, String priceProduct, String photoProduct) throws SQLException {
        int type=0;
        if(typeProduct.equals("Food")){
            type=1;
        }else if(typeProduct.equals("Drink")){
            type=2;
        }

        String sql ="insert into product(productName, price, photo, typeID) values (?,?,?,?)";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1,nameProduct);
        preparedStatement.setInt(2,Integer.parseInt(priceProduct));
        preparedStatement.setString(3,photoProduct);
        preparedStatement.setInt(4,type);
        int row = preparedStatement.executeUpdate();
        connection.close();
        return row;
    }

    public int updateProduct(String id,String nameProduct, String typeProduct, String priceProduct, String photoProduct) throws SQLException {
        int type=0;
        if(typeProduct.equals("Food")){
            type=1;
        }else if(typeProduct.equals("Drink")){
            type=2;
        }
        String sql ="update product set productName=?, typeID=?, price=?, photo=? where productID =?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1,nameProduct);
        preparedStatement.setInt(2,type);
        preparedStatement.setInt(3,Integer.parseInt(priceProduct));
        preparedStatement.setString(4,photoProduct);
        preparedStatement.setInt(5,Integer.parseInt(id));
        int row = preparedStatement.executeUpdate();
        connection.close();
        return row;
    }

    public void deleteProduct(String id) throws SQLException {
        String sql ="Delete from product where productID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,Integer.parseInt(id));
        preparedStatement.executeUpdate();
        connection.close();
    }

    public ObservableList<Product> getDataProduct() throws SQLException {
        ObservableList<Product> list=FXCollections.observableArrayList();

        String sql="select p.productId, p.productName, p.price, p.photo, t.typeName from product as p join type_product as t on p.typeID = t.typeID;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Product product = new Product(
                    rs.getString("productId"),
                    rs.getString("productName"),
                    rs.getString("typeName"),
                    rs.getInt("price"),
                    rs.getString("photo")
            );
            list.add(product);
        }
        connection.close();
        return list;
    }

    public ObservableList<Product> getDataFromSearching(String name) throws SQLException {
        ObservableList<Product> list=FXCollections.observableArrayList();

        String sql="select p.productId, p.productName, p.price, p.photo, t.typeName from product as p join type_product as t on p.typeID = t.typeID where p.productName like ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"%"+name+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Product product = new Product(
                    rs.getString("productId"),
                    rs.getString("productName"),
                    rs.getString("typeName"),
                    rs.getInt("price"),
                    rs.getString("photo")
            );
            list.add(product);
        }
        connection.close();
        return list;
    }

    public static int orderId = 0;
    public void insertOrderId() throws SQLException {
        int row=0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dateTimeFormatter.format(now);
        String sqlOrder = "INSERT INTO `Order` (OrderDate) VALUES (?)";

        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, date);

        row = preparedStatement.executeUpdate();

        // Retrieve the generated OrderID
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            orderId = generatedKeys.getInt(1);
        }
    }

    public String orderProduct(String id, int quantity) throws SQLException {
        int row=0;

        String sqlOrderDetail = "INSERT INTO Order_Detail (OrderID, ProductID, Quantity) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlOrderDetail);
        preparedStatement.setInt(1, orderId);
        preparedStatement.setInt(2, Integer.parseInt(id));
        preparedStatement.setInt(3, quantity);

        row = preparedStatement.executeUpdate();

        if(row<=0){
            return "Lỗi, vui lòng thử lại sau!";
        }

        connection.close();
        return "";
    }

    public ObservableList<Product> getOrder() throws SQLException {
        ObservableList<Product> list=FXCollections.observableArrayList();

        if(orderId==0){
            return null;
        }

        String sql= "select od.productId, p.productName, od.quantity, p.price from order_detail as od join product as p on od.productId = p.productId where orderID=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,orderId);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Product product = new Product(
                    rs.getString("productId"),
                    rs.getString("productName"),
                    rs.getInt("Quantity"),
                    rs.getInt("price")
            );
            list.add(product);
        }
        connection.close();
        return list;
    }

    public void deleteOrder(String id, int quantity) throws SQLException{
        String sql;
        PreparedStatement preparedStatement;
        System.out.println(id+" "+quantity+" "+orderId);
            sql="delete from order_detail where orderID=? and ProductId=? and Quantity=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,orderId);
            preparedStatement.setInt(2,Integer.parseInt(id));
            preparedStatement.setInt(3,quantity);
            preparedStatement.executeUpdate();
            connection.close();
    }

    public void deleteAllOrder() throws SQLException{
        String sql;
        PreparedStatement preparedStatement;
        sql="delete from order_detail where orderID=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,orderId);
        preparedStatement.executeUpdate();
        connection.close();
    }

    public int paymentOrder(int total) throws SQLException{

        if(orderId == 0){
            return 0;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dateTimeFormatter.format(now);

        String sql="insert into bill(total, orderID, billDate, userID) values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,total);
        preparedStatement.setInt(2,orderId);
        preparedStatement.setString(3,date);
        preparedStatement.setInt(4,data.id);
        int row = preparedStatement.executeUpdate();
//        orderId=0;
        connection.close();
        return row;

    }

    public List<Invoice> getInvoice(int idOrder) throws SQLException {
        List<Invoice> list = new ArrayList<>();
        String sql="select p.productName, o.quantity, p.price, b.total, b.billDate from bill as b \n" +
                "join order_detail as o on b.orderID=o.orderID\n" +
                "join product as p on p.productID = o.productID\n" +
                "where b.orderID =?";

        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,orderId);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Invoice invoice = new Invoice(
                    rs.getString("productName"),
                    rs.getInt("quantity"),
                    rs.getInt("price"),
                    rs.getInt("total"),
                    rs.getString("billDate")
            );
            list.add(invoice);
        }
        return list;
    }


    public ObservableList<Table> getAllTable() throws SQLException {
        ObservableList<Table> list = FXCollections.observableArrayList();
        String sql ="select * from `table`";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            Table table = new Table(
                    rs.getInt("tableID"),
                    rs.getInt("stt"),
                    rs.getBoolean("status")
            );
            list.add(table);
        }

        return list;
    }

    public void bookingTable(int id, boolean status) throws SQLException {
        String sql="update `table` set status=? where tableID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1,status);
        preparedStatement.setInt(2,id);
        preparedStatement.executeUpdate();
        connection.close();
    }

    public List getTotalToday() throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = dateTimeFormatter.format(localDateTime);
        List<Integer> list = new ArrayList<>();
        int total=0;
        int numberOfCustomers=0;

        String sql="SELECT sum(total) as totalToday, count(billID) as numberOfCumtomers FROM projectjavafx.bill\n" +
                "where billDate=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,date);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            numberOfCustomers=rs.getInt(1);
            total=rs.getInt(2);
            list.add(numberOfCustomers);
            list.add(total);
        }
        return list;
    }

    public int getNumberOfOrderToday() throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = dateTimeFormatter.format(localDateTime);
        int total=0;

        String sql="SELECT count(orderDetailID) FROM order_detail\n" +
                "join `order` as o on o.OrderID = order_detail.orderID\n" +
                "where o.OrderDate=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,date);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            total=rs.getInt(1);
        }
        return total;
    }

    public int getTotalNumberOfOrderToday() throws SQLException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = dateTimeFormatter.format(localDateTime);
        int total=0;

        String sql="SELECT sum(quantity) FROM order_detail\n" +
                "                join `order` as o on o.OrderID = order_detail.orderID\n" +
                "                where o.OrderDate=?\n" +
                "                group by o.OrderDate;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,date);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            total=rs.getInt(1);
        }
        connection.close();
        return total;
    }
    public ObservableList<XYChart.Data<String,Integer>> getDataForLineChart() throws SQLException {
        ObservableList<XYChart.Data<String,Integer>> list = FXCollections.observableArrayList();

        String sql="SELECT billDate,Sum(total) FROM projectjavafx.bill\n" +
                "group by billDate;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            var data = new XYChart.Data<>(rs.getString(1), rs.getInt(2));
            list.add(data);
        }
        return list;
    }

    public ObservableList<XYChart.Data<String,Integer>> getDataForBarChart() throws SQLException {
        ObservableList<XYChart.Data<String,Integer>> list = FXCollections.observableArrayList();

        String sql="SELECT billDate, count(billID) FROM projectjavafx.bill\n" +
                "group by billDate;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            var data = new XYChart.Data<>(rs.getString(1), rs.getInt(2));
            list.add(data);
        }
        return list;
    }

    public ObservableList<Staticstics> getDataFromDay(String date,String name) throws SQLException {
        ObservableList<Staticstics> list=FXCollections.observableArrayList();

        String sql;
        PreparedStatement preparedStatement;
        ResultSet rs;
        if(!name.isEmpty() && !date.isEmpty()){
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where p.productName like ? and b.billDate =?\n" +
                    "group by  p.productID, p.productName, o.quantity;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+name+"%");
            preparedStatement.setString(2,date);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }else if(name.isEmpty()){
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where b.billDate =?\n" +
                    "group by  p.productID, p.productName, o.quantity;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,date);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }else{
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where p.productName like ?\n" +
                    "group by  p.productID, p.productName, o.quantity, b.billDate;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+name+"%");
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }

    }

    public ObservableList<Staticstics> getDataFromMonth(String date,String name) throws SQLException {
        ObservableList<Staticstics> list=FXCollections.observableArrayList();

        String sql;
        PreparedStatement preparedStatement;
        ResultSet rs;
        if(!name.isEmpty() && !date.isEmpty()){
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where p.productName like ? and month(b.billDate) =?\n" +
                    "group by  p.productID, p.productName, o.quantity, b.billDate;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+name+"%");
            preparedStatement.setString(2,date);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }else if(name.isEmpty()){
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where month(b.billDate) =?\n" +
                    "group by  p.productID, p.productName, o.quantity, b.billDate;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,date);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }else{
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where p.productName like ?\n" +
                    "group by  p.productID, p.productName, o.quantity, b.billDate;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+name+"%");
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }

    }

    public ObservableList<Staticstics> getDataFromMonths(String fromDate,String toDate,String name) throws SQLException {
        ObservableList<Staticstics> list=FXCollections.observableArrayList();

        String sql;
        PreparedStatement preparedStatement;
        ResultSet rs;
        if(!name.isEmpty() && !fromDate.isEmpty() && !toDate.isEmpty()){
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where p.productName like ? and month(b.billDate) >= ? and month(b.billDate) <= ? \n" +
                    "group by  p.productID, p.productName ,o.quantity, b.billDate;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+name+"%");
            preparedStatement.setString(2,fromDate);
            preparedStatement.setString(3,toDate);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }else if(name.isEmpty()){
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where month(b.billDate) >= ? and month(b.billDate) <= ? \n" +
                    "group by  p.productID, p.productName ,o.quantity, b.billDate;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,fromDate);
            preparedStatement.setString(2,toDate);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }else{
            sql="select p.productID,p.productName,t.typeName, o.quantity as numberOfOrder, (p.price * o.quantity) as totalRevenue ,b.billDate from product p\n" +
                    "join type_product t on p.typeID=t.typeID\n" +
                    "join order_detail o on o.productID = p.productID\n" +
                    "join bill b on b.orderID = o.orderID\n" +
                    "where p.productName like ?\n" +
                    "group by  p.productID, p.productName ,o.quantity, b.billDate;";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+name+"%");
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Staticstics staticstics = new Staticstics(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getString("typeName"),
                        rs.getInt("numberOfOrder"),
                        rs.getInt("totalRevenue"),
                        rs.getString("billDate")
                );
                list.add(staticstics);
            }
            return list;
        }

    }

    public ObservableList<PieChart.Data> getDataforPieChart(String date, String name) throws SQLException {
        ObservableList<PieChart.Data> list=FXCollections.observableArrayList();
        String sql="select t.typeName,count(t.typeName) as quantity from product p\n" +
                "join type_product t on p.typeID=t.typeID\n" +
                "join order_detail o on o.productID = p.productID\n" +
                "join bill b on b.orderID = o.orderID\n" +
                "where b.billDate =? or p.productName=?\n" +
                "group by t.typeName;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,date);
        preparedStatement.setString(2,name);
        ResultSet rs= preparedStatement.executeQuery();

        while(rs.next()){
            var data= new PieChart.Data(rs.getString(1),rs.getInt(2));
            list.add(data);
        }
        connection.close();
        return list;
    }

    public ObservableList<PieChart.Data> getDataforPieChartNotDate(String name) throws SQLException {
        ObservableList<PieChart.Data> list=FXCollections.observableArrayList();
        String sql="select t.typeName,count(t.typeName) as quantity from product p\n" +
                "join type_product t on p.typeID=t.typeID\n" +
                "join order_detail o on o.productID = p.productID\n" +
                "join bill b on b.orderID = o.orderID\n" +
                "where p.productName like ?\n" +
                "group by t.typeName;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"%"+name+"%");
        ResultSet rs= preparedStatement.executeQuery();

        while(rs.next()){
            var data= new PieChart.Data(rs.getString(1),rs.getInt(2));
            list.add(data);
        }
        connection.close();
        return list;
    }


}
