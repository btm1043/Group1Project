package combined;

import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.CheckBox;
import java.lang.Object;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.print.Printer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollBar;
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
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.IntegerSyntax;
import javax.print.SimpleDoc;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.print.PrinterJob;


public class receiptGenerator {
    private Scene receiptGenerator;
    String inputbuild = ""; 

public receiptGenerator(Stage stage, Scene scene) {     
    
		BorderPane pad = viewReceipt(stage, scene);
		receiptGenerator = new Scene(pad);
                stage.setResizable(false);
    }
        
BorderPane viewReceipt(Stage stage, Scene scene) {
    
    File file = new File("tran.txt"); 
           
  	    try (BufferedReader br = new BufferedReader(new FileReader(file))){
	    	Scanner inFile = new Scanner (file);
	    	
    
	        while (inFile.hasNextLine()) {
String line = inFile.nextLine();
                            inputbuild += line + "\n";
	        }
	    	
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	    }
	

    BorderPane pane = new BorderPane();
    pane.setPadding(new Insets(20, 20, 20, 20));
    
                GridPane heading = new GridPane();
                pane.setTop(heading);
                
                
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
                pane.setLeft(grid);
                
                GridPane options = new GridPane();
                pane.setRight(options);
                options.setPadding(new Insets(25,25,25,25));
                options.setVgap(10);
                Button back = new Button();
                back.setText("< Back to Home Screen");
                
                back.setOnAction(new EventHandler<ActionEvent>() {
               
                public void handle(ActionEvent e) {
                    stage.setScene(scene);
                    stage.show();
                }
                });
                                
                Label Title = new Label();
		Title.setFont(Font.font ("Verdana", 60));
		Title.setText("View/Print Receipt");
		GridPane.setHalignment(Title, HPos.LEFT);
		GridPane.setColumnSpan(Title, 3);
                

                Group g = new Group();
                inputbuild.substring( 4, inputbuild.length());
                TextArea preview = new TextArea(inputbuild);
                preview.setMaxSize(350, 600);
               preview.setPrefSize(350, 600);
               preview.setManaged(true);

                preview.setStyle("-fx-background-color: white; -fx-padding: 10;");
                grid.setStyle("-fx-border: 2px solid; -fx-border-color: red;");
                g.getChildren().add(preview);
                
          
                
                Label NumCopies = new Label();
                NumCopies.setText("No. of Copies");
                NumCopies.setFont(Font.font ("Verdana", 20));
                HBox nCopies = new HBox(30);
             
                
                TextField num = new TextField();
                num.setPromptText("1");
                num.setMaxWidth(70);
                nCopies.getChildren().addAll(NumCopies, num);
                
                CheckBox Dupecopy = new CheckBox("Print separate Customer/Merchant Copies");
                
                /*   THE PRINTERS */
          
            StringBuilder PrinterNames = new StringBuilder();
        
            ObservableSet<Printer> printers = Printer.getAllPrinters();
                    
            for(Printer printer : printers){

                        PrinterNames.append(printer.getName()+ "\n");
              
            } 

                final ComboBox networkPrinter = new ComboBox();
                
                Button Print = new Button();
                if (PrinterNames  == null){
                    networkPrinter.setPromptText("---NO NETWORK PRINTERS FOUND---");
                    Print.setDisable(true);
                }else{
                    networkPrinter.setPromptText("---Select A Network Printer---");
                    
                    String[] printerlist =  PrinterNames.toString().split("\\n");
                    //printerlist.substring(0, (printerlist.length()-3));
                    
                    networkPrinter.getItems().addAll(
                            printerlist
                    );
                
                }

                heading.add(back, 0, 0);
                heading.add(Title, 0, 1);
                grid.add(g, 0, 0);
                options.add(nCopies, 1, 1);
                options.add(Dupecopy, 1, 2);
                options.add(networkPrinter, 1, 3);
                options.add(Print, 1, 4);
                Print.setText("Print");
                
                    Print.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            String numInput = num.getText();
                            int numCopies = 0;
                            
                            if (num.getText().length() != 0){ //if there is input, check that it is int
                           
                               try {
                                    numCopies = Integer.parseInt(numInput);
                               }catch (NumberFormatException err) {
                                    showErrorDialog();
                                }
                            }   
                              if (numCopies == 0 ){ //if no input, then default to 1 copy
                                   numCopies = 1;
                               }
                            
                               if(Dupecopy.isSelected() == true) {
                                   numCopies *= 2;
                               }
                               
                        String printto = networkPrinter.getValue().toString();       
                        showPrintingDialog(printto, numCopies);
                       
                        
                         Printer theCHOSEN_ONE = null;
                
                         for(Printer printer : printers){
                            if(printer.getName().matches(printto)){
                            theCHOSEN_ONE = printer;
                            }
                        }
                        PrinterJob job = PrinterJob.createPrinterJob(); 
                       
                            job.setPrinter(theCHOSEN_ONE);
                            //job.Copies(numCopies);
                            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
                            aset.add(new Copies(numCopies));
                       
                        
                         if (job != null){
                             for (int i = 0; i < numCopies; i++){
                                job.printPage(preview);
                                
                             }
                        }else{
                             showPrintErrorDialog();
                         }
                         job.endJob();
                         
                    }
                    });
                
                
                
                return pane;
                            
 }

	public Scene getScene() {
		return receiptGenerator;
	}
  
        private void showPrintingDialog(String printer, int numCopies) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Printing Receipt");
            alert.setHeaderText("Printing " + numCopies + " copies to " + printer);
            alert.setContentText("Saved to Database");
            alert.show();
        }
        
        private void showErrorDialog() {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Number of Copies Not Valid");
            alert.setHeaderText("Number of Copies is Not a Valid Number");
            alert.setContentText("Please re-enter the number of copies to print");
            alert.showAndWait();
            
        }
        
         private void showPrintErrorDialog() {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Printing Failed");
            alert.setHeaderText("Print could not be completed. Check network.");
            alert.showAndWait();
        }
  

}

