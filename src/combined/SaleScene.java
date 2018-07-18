package combined;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.*;

/**
 *
 * @author Bryant
 */
//Class to customize sale button screen






public class SaleScene{
    private Scene Sscene;
    private Stage pStage;
    TextArea ledgerT= new TextArea();
    TextField totalF= new TextField();
    TextField stotalF= new TextField();
    TextField taxF= new TextField();
    
    LogInScene l= Combined.logInScene;
    boolean manager=false;
    
    public SaleScene(Stage primaryStage, MenuBar menu) 
    {
        if(l!=null)
        {
            manager=true;//l.managerLoggedOn();
        }
        
        pStage=primaryStage;
        BorderPane SaleS= buildSS(menu);
        Sscene = new Scene(SaleS,1000,700);
        
    }
    
    public void setManager(boolean d)
    {
        manager=d;
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
        
        
        ledgerT.setMaxSize(330, 500);
        ledgerT.setEditable(false);
        ledger.add(ledgerT, 0, 1);
        ledger.setColumnSpan(ledgerT,3);
        
        Label tax= new Label();
        tax.setText("\t \tTax: $");
        tax.setFont(Font.font("Arial", FontWeight.BOLD, 20));;
        ledger.add(tax,0,3);

        ledger.add(taxF,1,3);
        
        Label stotal= new Label();
        stotal.setText("\t \tSubTotal: $");
        stotal.setFont(Font.font("Arial", FontWeight.BOLD, 20));;
        ledger.add(stotal,0,2);
        stotalF.setText("0.00");
        ledger.add(stotalF,1,4);
        
        Label total= new Label();
        total.setText("\t \tTotal: $");
        total.setFont(Font.font("Arial", FontWeight.BOLD, 20));;
        ledger.add(total,0,4);
        
        totalF.setText("0.00");
        ledger.add(totalF, 1, 2);
        Button complete = new Button();
        complete.setText("Complete Sale");
        complete.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        complete.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Redirect to payment screen");
                try{
                    FileWriter fWriter = new FileWriter("tran.txt");
                    BufferedWriter writer = new BufferedWriter(fWriter);
                    String out=ledgerT.getText();
                    String separator=System.getProperty("line.separator");
                    out=out.replace("\n", separator);
                    writer.write(out);
                    writer.write("$"+totalF.getText());
                    writer.close();
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(SaleScene.class.getName()).log(Level.SEVERE, null, ex);
                }

                pStage.setScene(new PaymentScene(pStage,Sscene).getScene());
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
                System.exit(0);
            }
        });
        ledger.add(exit,  0,6);
        

        return ledger;
    }
    
    public GridPane buildButtonS()
    {
        GridPane buttonS = new GridPane();
        buttonS.setHgap(10);
        buttonS.setVgap(10);
        buttonS.setGridLinesVisible(true);
        
        iButton bArr[]=new iButton[25];
        int i=0;
        int j=0;
        int k=0;
        while(i!=25)
        {
            if(j%5==0)
            {
                j=0;
                k++;
            }
            bArr[i]=new iButton();
            buttonS.add(bArr[i].btn,j,k);
            i++;
            j++;
        }
        
        return buttonS;
    }
    
    public BorderPane buildSS(MenuBar main)
    {
        
        
        
        MenuBar mB= main;
        GridPane grid= buildLedger();
        GridPane buttonS= buildButtonS();
        
        BorderPane root = new BorderPane();
        root.setTop(mB);
        root.setCenter(buttonS);
        root.setLeft(grid);
        
        return root;
    }
    
    public Scene getScene() 
    {
        
	return Sscene;
    }


    
    class iButton
    {
        //Private variable
        private String title;
        private double price;
        private int quantity;
        public Button btn;

        //Default constructor
        public iButton()
        {
            title="Default";
            
            
            price=0.00;
            quantity=0;
            btn=new Button();
            btn.setText(title);
            btn.setPrefSize(100, 50);
            btn.setOnMouseClicked(new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event)
                    {
                        MouseButton b=event.getButton();

                        if(b==MouseButton.SECONDARY && manager==true)
                        {
                            JTextField titleF=new JTextField(5);
                            JTextField priceF=new JTextField(5);
                            JTextField quantityF=new JTextField(5);

                            JPanel myPanel=new JPanel();
                            myPanel.add(new JLabel("Product Title:"));
                            myPanel.add(titleF);
                            myPanel.add(Box.createHorizontalStrut(15));
                            myPanel.add(new JLabel("Price $:"));
                            myPanel.add(priceF);
                            myPanel.add(Box.createHorizontalStrut(15));
                            myPanel.add(new JLabel("Quantity:"));
                            myPanel.add(quantityF);

                            int result= JOptionPane.showConfirmDialog(null, myPanel,"Please Enter New Product",JOptionPane.OK_CANCEL_OPTION);
                            if (result==JOptionPane.OK_OPTION)
                            {
                                btn.setText(titleF.getText());
                                title=titleF.getText();
                                price=Double.parseDouble(priceF.getText());
                                quantity=Integer.parseInt(quantityF.getText());
                            }
                        }
                        else
                        {
                            System.out.println("LEDGER");
                            String out=String.format("%s\t$%.2f\n",title,price);
                            double outD=Double.parseDouble(totalF.getText())+price;
                            double taxt=outD*.06;
                            double tot=outD+taxt;
                            ledgerT.setText(ledgerT.getText()+out);
                            String outSD=String.format("%.2f",outD);
                            String taxT=String.format("%.2f", taxt);
                            String totT=String.format("%.2f", tot);
                            taxF.setText(taxT);
                            stotalF.setText(totT);
                            totalF.setText(outSD);
                            
                            
                        }
                    }
                });

        }
        //Constructor given variables
        public iButton(String T,double p, int q)
        {
            title=T;
            price=p;
            quantity=q;

            btn= new Button();
            btn.setText(title);
            btn.setPrefSize(100, 50);
        }
        //Title getter and setter
        public String getTitle()
        {
            return title;
        }
        public void setTitle(String T)
        {
            title=T;
            btn.setText(title);
        }
        //Price getter and setter
        public double getPrice()
        {
            return price;
        }
        public void setPrice(double p)
        {
            price=p;
        }
        //Quantity getter and setter
        public int getQuantity()
        {
            return quantity;
        }
        public void setQuantity(int q)
        {
            quantity=q;
        }


    }
}