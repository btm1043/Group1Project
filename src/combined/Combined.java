/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combined;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Bryant
 */
public class Combined extends Application 
{

    public static LogInScene logInScene=null;
    public SaleScene salesScene;
    public reportsScene reportsScene;
    public Product_Search searchScene;
    public MenuBar mainMenu;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("POS");
	primaryStage.setMaximized(true);
        salesScene = new SaleScene(primaryStage, buildMenubar(primaryStage));
        reportsScene = new reportsScene(primaryStage,salesScene.getScene(), buildMenubar(primaryStage));
        searchScene = new Product_Search(primaryStage,salesScene.getScene(), buildMenubar(primaryStage));
	
        logInScene = new LogInScene(primaryStage, salesScene.getScene());
	primaryStage.setScene(logInScene.getScene());
	primaryStage.show();
                
    }

    public MenuBar buildMenubar(Stage primaryStage)
    {
        //Generating Menu Bar
        MenuBar mB=new MenuBar();
        Menu mF= new Menu("Mode");
        MenuItem cM=new MenuItem("Cashier");
        cM.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(salesScene.getScene());
                
            }
        });
        MenuItem mM=new MenuItem("Manager");
        mM.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                logInScene.requestManager(salesScene.getScene());
                primaryStage.setScene(logInScene.getScene());
                
            }
        });
        mF.getItems().addAll(cM,mM);
        
        Menu mR= new Menu("Reports");
        MenuItem eodR=new MenuItem("End of Day Report");
        eodR.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(reportsScene.getScene());
            }
        });
        MenuItem wR=new MenuItem("Weekly Report");
        MenuItem sA=new MenuItem("Sales Analysis");
        MenuItem iR=new MenuItem("Inventory Report");
        mR.getItems().addAll(eodR,wR,sA,iR);
        
        Menu mRe= new Menu("Receipts");
        MenuItem rR=new MenuItem("Reprint Last Receipt");
        MenuItem sR=new MenuItem("Search Receipt");
        mRe.getItems().addAll(rR,sR);
        
        Menu mI= new Menu("Inventory");
        MenuItem iI=new MenuItem("Inventory");
        iI.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(searchScene.getScene());
            }
        });
        MenuItem eI=new MenuItem("Edit Inventory");
        mI.getItems().addAll(iI,eI);
        
        mB.getMenus().addAll(mF,mR,mRe,mI);
        
        return mB;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
