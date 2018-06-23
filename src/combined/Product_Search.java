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
import javafx.stage.Stage;

/**
 *
 * @author leola
 */
public class Product_Search{
    
    private Scene scene;
    /**
     * @param primaryStage
     * @param menu
     */
    public Product_Search(Stage stage,Scene scene1) {
        int height = 800;
        int width = 600;
        TableColumn<product, String> name = new TableColumn<product, String>("Name");
        TableColumn<product, String> number = new TableColumn<product, String>("Number");
        TableColumn<product, String> department = new TableColumn<product, String>("Department");
        TableColumn<product, Double> price = new TableColumn<product, Double>("Price");
        TableView<product> table = new TableView();
        TextField searchBar = new TextField();
        searchBar.setMaxHeight(100);
        table.getColumns().setAll(name, number, department, price);
        
        product prod1 = new product();
        product prod2 = new product();
        prod2.setReciptDepartment("food");
        prod2.setReciptName("Edible stuff");
        prod2.setReciptNumber("976530");
        prod2.setReciptPrice(123.45);
        prod1.setReciptDepartment("InedibleStuff");
        prod1.setReciptName("WindWaker");
        prod1.setReciptNumber("2345");
        prod1.setReciptPrice(8.8);
        table.setMaxHeight(height);
        table.setMaxWidth(width);
        table.setMinHeight(height);
        table.setMinWidth(width); 
        searchBar.setMinWidth(width);
        
        name.setCellValueFactory(new PropertyValueFactory("reciptName"));
        number.setCellValueFactory(new PropertyValueFactory("reciptNumber"));
        department.setCellValueFactory(new PropertyValueFactory("reciptDepartment"));
        price.setCellValueFactory(new PropertyValueFactory("reciptPrice"));
        name.setMinWidth(table.getMinWidth()/3);
        number.setMinWidth(table.getMinWidth()/6);
        department.setMinWidth(table.getMinWidth()/3);
        price.setMinWidth(table.getMinWidth()/6);
        
        List<product> prodList = new ArrayList<product>();
        prodList.add(prod1);
        prodList.add(prod2);
        ObservableList obs;
        obs = FXCollections.observableList(prodList);
        
        table.setItems(obs);
        
        Button back = new Button();
        back.setText("< Back to Home Screen");
        back.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            stage.setScene(scene1);
            stage.show();
        }
        });
        
        ScrollPane root = new ScrollPane();
        root.setContent(new VBox(searchBar, table,back));
        scene = new Scene(root, width, height);
        
        
    }
    public class product{
        private  StringProperty reciptName;
        private  StringProperty reciptNumber;
        private  StringProperty reciptDepartment;
        private  DoubleProperty reciptPrice;
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
        public double getReciptPrice() { return reciptPrice().get(); }
        public StringProperty reciptDepartment() {
            if (reciptDepartment == null) reciptDepartment = new SimpleStringProperty(this, "Dept");
            return reciptDepartment;
        }
        public DoubleProperty reciptPrice() {
            if (reciptPrice == null) reciptPrice = new SimpleDoubleProperty(9.99);
            return reciptPrice;
        }
        
    }
    
    public Scene getScene(){
        return scene;
    }
    
    
}
