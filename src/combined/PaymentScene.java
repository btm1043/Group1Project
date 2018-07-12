/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combined;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
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
public class PaymentScene 
{
    private Stage pStage;
    private Scene pScene;
    Scene og;
    
    TextArea ledgerT= new TextArea();
    TextField totalF= new TextField();
    TextField totalsF= new TextField();
    TextField totaltF= new TextField();
    
    
    public PaymentScene(Stage primaryStage,Scene s) 
    {
        pStage=primaryStage;
        og=s;
        BorderPane p= buildPscene(primaryStage,s);
        pScene = new Scene(p,1000,700);
    }
    
    public GridPane buildLedger()
    {
        GridPane ledger = new GridPane();
        ledger.setHgap(10);
        ledger.setVgap(10);
        ledger.setGridLinesVisible(true);
        
        File file = new File("tran.txt");
 
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PaymentScene.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        String st;
        try {
            while ((st = br.readLine()) != null)
            {
                System.out.println(st);
                if(st.startsWith("$"))
                {
                    String rep=st.substring(1);
                    System.out.println(rep);
                    double outD=Double.parseDouble(rep);
                    double taxt=outD*.06;
                    double tot=outD+taxt;
                    String t= String.format("%.2f", outD);
                    String taxT=String.format("%.2f", taxt);
                    String totT=String.format("%.2f", tot);
                    totalsF.setText(t);
                    totaltF.setText(taxT);
                    totalF.setText(totT);
                    
                } 
                else
                {
                    ledgerT.setText(ledgerT.getText()+st+"\n");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PaymentScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Text category = new Text("Items");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ledger.add(category, 0, 0); 
        
        
        ledgerT.setMaxSize(330, 500);
        ledgerT.setEditable(false);
        ledger.add(ledgerT, 0, 1);
        ledger.setColumnSpan(ledgerT,3);
        
        Label stotal= new Label();
        stotal.setText("\t \t \t \tSubtotal: $");
        stotal.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        ledger.add(stotal,0,2);
        
        ledger.add(totalsF, 1, 2);
        
        ledger.add(totalF, 1, 4);
        Button complete = new Button();
        complete.setText("Complete Sale");
        complete.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        Label taxtotal= new Label();
        taxtotal.setText("\t \t \t \t \tTax: $");
        taxtotal.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        ledger.add(taxtotal,0,3);
        
        ledger.add(totaltF, 1,3);
        
        Label total= new Label();
        total.setText("\t\t\tTotal: $");
        total.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ledger.add(total,0,4);
        
        
        complete.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Redirect to Sales screen");
                File dir = new File("C:\\Users\\Bryant\\Documents\\NetBeansProjects\\Combined\\Reciepts\\test.txt");
                pStage.setScene(og);
            }
        });
        ledger.add(complete,  0,5);
        //ledger.setColumnSpan(complete,3);
        
        Button exit = new Button();
        exit.setText("Exit");
        exit.setAlignment(Pos.CENTER);
        exit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        exit.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Close Program");
                pStage.setScene(og);
                //pStage.show();
            }
        });
        ledger.add(exit,  0,6);
        

        return ledger;
    }
    
    private GridPane makeLogOnPad() {
		GridPane numPad = new GridPane();
		DropShadow shadow = new DropShadow();
		//numPad.setAlignment(Pos.BOTTOM_CENTER);
		String[] keyText = {
			"1", "2", "3",
			"4", "5", "6",
			"7", "8", "9",
			".", "0", "Enter"
		};
		TextField input = new TextField();
		input.setEffect(shadow);
		input.setAlignment(Pos.CENTER);
		input.setPromptText("Enter amount tendered");
		//input.setMinHeight(screenHeight/16);
		//GridPane.setMargin(input, new Insets(screenHeight/16,screenWidth/18,screenHeight/16,screenWidth/18));
		numPad.add(input, 0, 0);
		GridPane.setColumnSpan(input, 3);
		for (int i = 0; i < keyText.length; i++) 
                {
                    Button b = new Button(keyText[i]);
                    GridPane.setMargin(b, new Insets(10,10,10,10));
                    b.setEffect(shadow);
                    b.setMinWidth(100);
                    b.setMinHeight(100);
                    b.getStyleClass().add("num-button");
                    if (b.getText().equals("Enter")) 
                            b.setStyle("-fx-background-color: #008000; ");
                    else if (b.getText().length() > 1)
                            b.setStyle("-fx-background-color: #FF0000; ");

                    numPad.add(b, i%3, (int) Math.ceil(i/3) + 2);
		}
		return numPad;
	}
    
    public BorderPane buildPscene(Stage stage, Scene scene)
    {
        BorderPane pS= new BorderPane();
        GridPane ledger=buildLedger();
        GridPane numpad=makeLogOnPad();
        
        pS.setLeft(ledger);
        pS.setCenter(numpad);
        
        return pS;
    }

        public Scene getScene() 
    {
        
	return pScene;
    }
    
}
