package combined;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Bryant
 */
public class SaleScene{
    private Scene Sscene;
    private Stage pStage;
    private MenuBar mainmenu;
    
    
    public SaleScene(Stage primaryStage, MenuBar menu) 
    {
        pStage=primaryStage;
        mainmenu=menu;
        BorderPane SaleS= buildSS(menu);
        Sscene = new Scene(SaleS,1000,700);
    }
    
    
    
    public GridPane buildLedger()
    {
        GridPane ledger = new GridPane();
        ledger.setHgap(10);
        ledger.setVgap(10);
        ledger.setGridLinesVisible(true);
        
        Text category = new Text("Items");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ledger.add(category, 0, 0); 
        
        TextArea ledgerT= new TextArea();
        ledgerT.setMaxSize(330, 500);
        ledgerT.setEditable(false);
        ledger.add(ledgerT, 0, 1);
        ledger.setColumnSpan(ledgerT,3);
        
        Label total= new Label();
        total.setText("\t \tTotal: $");
        total.setFont(Font.font("Arial", FontWeight.BOLD, 20));;
        ledger.add(total,0,2);
        
        TextField totalF= new TextField();
        ledger.add(totalF, 1, 2);
        
        Button complete = new Button();
        complete.setText("Complete Sale");
        complete.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        complete.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Redirect to payment screen");
            }
        });
        ledger.add(complete,  0,3);
        //ledger.setColumnSpan(complete,3);
        
        Button exit = new Button();
        exit.setText("Exit");
        exit.setAlignment(Pos.CENTER);
        exit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        exit.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Close Program");
            }
        });
        ledger.add(exit,  0,4);
        

        return ledger;
    }
    
    public GridPane buildButtonS()
    {
        GridPane buttonS = new GridPane();
        buttonS.setHgap(10);
        buttonS.setVgap(10);
        buttonS.setGridLinesVisible(true);
        
        Button btn = new Button(); btn.setPrefSize(100, 50);
        btn.setText("Say \n'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        Button btn1= new Button();
        btn1.setPrefSize(100, 50);
        Button btn2 = new Button();
        btn2.setPrefSize(100, 50);
        Button btn3 = new Button();
        btn3.setPrefSize(100, 50);
        Button btn4 = new Button(); btn4.setPrefSize(100, 50);
        Button btn5 = new Button(); btn5.setPrefSize(100, 50);
        Button btn6 = new Button(); btn6.setPrefSize(100, 50);
        Button btn7 = new Button(); btn7.setPrefSize(100, 50);
        Button btn8 = new Button(); btn8.setPrefSize(100, 50);
        Button btn9 = new Button(); btn9.setPrefSize(100, 50);
        Button btn10 = new Button(); btn10.setPrefSize(100, 50);
        Button btn11 = new Button(); btn11.setPrefSize(100, 50);
        Button btn12 = new Button(); btn12.setPrefSize(100, 50);
        Button btn13 = new Button(); btn13.setPrefSize(100, 50);
        Button btn14 = new Button(); btn14.setPrefSize(100, 50);
        Button btn15 = new Button(); btn15.setPrefSize(100, 50);
        Button btn16 = new Button(); btn16.setPrefSize(100, 50);
        Button btn17 = new Button(); btn17.setPrefSize(100, 50);
        Button btn18 = new Button(); btn18.setPrefSize(100, 50);
        Button btn19 = new Button(); btn19.setPrefSize(100, 50);
        Button btn20 = new Button(); btn20.setPrefSize(100, 50);
        Button btn21 = new Button(); btn21.setPrefSize(100, 50);
        Button btn22 = new Button(); btn22.setPrefSize(100, 50);
        Button btn23 = new Button(); btn23.setPrefSize(100, 50);
        Button btn24 = new Button(); btn24.setPrefSize(100, 50);
        
        buttonS.add(btn, 0, 0);
        buttonS.add(btn1, 0, 1);
        buttonS.add(btn2, 0, 2);
        buttonS.add(btn3, 0, 3);
        buttonS.add(btn4, 0, 4);
        buttonS.add(btn5, 1, 0);
        buttonS.add(btn6, 1, 1);
        buttonS.add(btn7, 1, 2);
        buttonS.add(btn8, 1, 3);
        buttonS.add(btn9, 1, 4);
        buttonS.add(btn10, 2, 0);
        buttonS.add(btn11, 2, 1);
        buttonS.add(btn12, 2, 2);
        buttonS.add(btn13, 2, 3);
        buttonS.add(btn14, 2, 4);
        buttonS.add(btn15, 3, 0);
        buttonS.add(btn16, 3, 1);
        buttonS.add(btn17, 3, 2);
        buttonS.add(btn18, 3, 3);
        buttonS.add(btn19, 3, 4);
        buttonS.add(btn20, 4, 0);
        buttonS.add(btn21, 4, 1);
        buttonS.add(btn22, 4, 2);
        buttonS.add(btn23, 4, 3);
        buttonS.add(btn24, 4, 4);
        
        
        
        
        return buttonS;
    }
    
    public BorderPane buildSS(MenuBar main)
    {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                
            }
        });
        
        
        
        MenuBar mB= main;
        GridPane grid= buildLedger();
        GridPane buttonS= buildButtonS();
        
        BorderPane root = new BorderPane();
        root.setBottom(btn);
        root.setTop(mB);
        root.setCenter(buttonS);
        root.setLeft(grid);
        
        return root;
    }
    
    public Scene getScene() 
    {
        
	return Sscene;
    }

}
