/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combined;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.sql.*;

/**
 *
 * @author leola
 */
public class Product_Search{
    
    private Scene scene;
    /**
     * @param primaryStage
     * @param menu
     * 
     */
    
    ScrollPane root = new ScrollPane();
    
    public Product_Search(Stage stage,Scene scene1, MenuBar mB) {
        int height = 800;
        int width = 800;
        TableColumn<product, String> name = new TableColumn<product, String>("Name");
        TableColumn<product, String> number = new TableColumn<>("Number");
        TableColumn<product, String> department = new TableColumn<>("Category");
        TableColumn<product, Double> price = new TableColumn<>("Price");
        TableColumn<product, Double> quant = new TableColumn<>("Quantity");
        Button go = new Button("Go");
        TableView<product> table = new TableView();
        TextField searchBar = new TextField();
        ObservableList categories = FXCollections.observableArrayList("ID", "Category", "Name", "Price");
        ChoiceBox<String> cbox = new ChoiceBox<String>(categories);
        
        table.setPlaceholder(new Label("Please enter an item in the search bar above"));
        searchBar.setPromptText("Item");
        searchBar.setMaxHeight(100);
        go.setMaxHeight(100);
        table.setMinWidth(width);
        table.setMinHeight(height);
        cbox.setMinWidth(width/4);
        go.setMinWidth(width/4);
        searchBar.setMinWidth(width/2);
        table.getColumns().setAll(name, number, department, price, quant);
        
        name.setCellValueFactory(new PropertyValueFactory("reciptName"));
        number.setCellValueFactory(new PropertyValueFactory("reciptNumber"));
        department.setCellValueFactory(new PropertyValueFactory("reciptDepartment"));
        price.setCellValueFactory(new PropertyValueFactory("reciptPrice"));
        price.setCellValueFactory(new PropertyValueFactory("reciptQuantity"));
        
        name.setMinWidth(table.getMinWidth()/4);
        number.setMinWidth(table.getMinWidth()/4);
        department.setMinWidth(table.getMinWidth()/4);
        price.setMinWidth(table.getMinWidth()/8);
        quant.setMinWidth(table.getMinWidth()/8);     
        
        HBox search = new HBox(cbox, searchBar, go);
        root.setContent(new VBox(search, table));
        scene = new Scene(root, width, height);
        
        display(stage);
        
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                {try{setTable(cbox.getValue(), searchBar.textProperty(), table);}catch (Exception e1){System.out.print(e1.toString());}}
                buildScene(search, width, height, table, mB);
                display(stage);
            }
        });;
    }
        private void buildScene(HBox search, int width, int height, TableView table, MenuBar mb)
    {

        root.setContent(new VBox(mb, search, table));
        scene = new Scene(root, width, height);
    }
    
    private void display(Stage primaryStage)
    {
        primaryStage.setTitle("Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    
    }
        private void setTable(String cat, StringProperty s, TableView table) throws Exception
    {
        List<product> prodList = new ArrayList<>();
        ResultSet rs = Database.GetProductSet(cat.toLowerCase(), s.getValue());
        while (rs.next()){
            product prod = new product();
            prod.setReciptName(rs.getString("name"));
            prod.setReciptNumber(rs.getString("id"));
            prod.setReciptPrice(rs.getDouble("price"));
            prod.setReciptDepartment(rs.getString("category"));
            prodList.add(prod);
        }
        ObservableList obs;
        obs = FXCollections.observableList(prodList);
        table.setItems(obs);

    }
        
        
        
    }
    public class product{
        private  StringProperty reciptName;
        private  StringProperty reciptNumber;
        private  StringProperty reciptDepartment;
        private  DoubleProperty reciptPrice;
        private  IntegerProperty reciptQuantity;
        public void setReciptName(String value) { reciptName().set(value); }
        public String getReciptNumber() { return reciptNumber().get(); }
        public void setReciptNumber(String value) { reciptNumber().set(value); }
        public String getReciptName() { return reciptName().get(); }
        public StringProperty reciptName() {
            if (reciptName == null) reciptName = new SimpleStringProperty(this, "NAME");
            return reciptName;
        }
        public StringProperty reciptNumber() {
            if (reciptNumber == null) reciptNumber = new SimpleStringProperty(this, "######");
            return reciptNumber;
        }
        public void setReciptDepartment(String value) { reciptDepartment().set(value); }
        public String getReciptDepartment() { return reciptDepartment().get(); }
        public void setReciptPrice(double value) { reciptPrice().set(value); }
        public void setReciptQuantity(int value) { reciptQuantity().set(value); }
        public double getReciptPrice() { return reciptPrice().get(); }
        public int getReciptQuantity() { return reciptQuantity().get(); }
        public StringProperty reciptDepartment() {
            if (reciptDepartment == null) reciptDepartment = new SimpleStringProperty(this, "Dept");
            return reciptDepartment;
        }
        public DoubleProperty reciptPrice() {
            if (reciptPrice == null) reciptPrice = new SimpleDoubleProperty(9.99);
            return reciptPrice;
        }
        public IntegerProperty reciptQuantity() {
            if (reciptQuantity == null) reciptQuantity = new SimpleIntegerProperty(9);
            return reciptQuantity;
        }
        
    }
    
    public Scene getScene(){
        return scene;
    }
    
    
}
