package combined;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.CheckBox;

public class reportsScene { 
        private Scene reportsScene;

public reportsScene(Stage stage, Scene scene, MenuBar mB) {       
		GridPane pad = TransactionList(stage, scene);
		BorderPane main = new BorderPane();
		main.setTop(mB);
		main.setCenter(pad);;
		reportsScene = new Scene(main);
    
    }

	         GridPane TransactionList(Stage stage, Scene scene) {
               
		            GridPane grid = new GridPane();
                grid.setPadding(new Insets(25,25,25,25));
                grid.setVgap(20);
                grid.setHgap(20);
		            grid.setAlignment(Pos.TOP_LEFT);
               	
                Button back = new Button();
                back.setText("< Back to Home Screen");
                back.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    stage.setScene(scene);
                    stage.show();
                }
                });
                
                Label Title = new Label();
		            Title.setFont(Font.font ("Verdana", 60));
		            Title.setText("History and Reports");
		            GridPane.setHalignment(Title, HPos.LEFT);
		            GridPane.setColumnSpan(Title, 3);
                
                //Custom Start/End Dates
                Label Start = new Label();
                Label End = new Label();
                Start.setText("Start Date: ");
                End.setText("End Date: ");
                DatePicker StartDate = new DatePicker();
                DatePicker EndDate = new DatePicker();
                HBox customDates = new HBox(20);
                customDates.getChildren().addAll(Start, StartDate, End, EndDate);
                
                //Radio Buttons for automatic date specifications 
                ToggleGroup radiogroup = new ToggleGroup();
                RadioButton Today = new RadioButton("Today");
                RadioButton Week = new RadioButton("1 Week");
                RadioButton Month = new RadioButton("1 Month");
                RadioButton Year = new RadioButton("1 Year");
                Today.setToggleGroup(radiogroup);
                Week.setToggleGroup(radiogroup);
                Month.setToggleGroup(radiogroup);
                Year.setToggleGroup(radiogroup);
                HBox radioDates = new HBox(70);
                radioDates.getChildren().addAll(Today, Week, Month, Year);
                
                Button Search = new Button();
                Search.setText("Search");
                //Search.setStyle("-fx-font: 18 arial;");
                
                TableView table = new TableView();
                //table.setEditable(true);
                
                TableColumn tDate = new TableColumn("Date");
                TableColumn tTime = new TableColumn("Time");
                TableColumn tReceipt = new TableColumn("Receipt/PO #");
                TableColumn tTotal = new TableColumn("Total");
                table.getColumns().addAll(tDate, tTime, tReceipt, tTotal);
                
                tDate.setResizable(true);
                tTime.setResizable(true);
                tReceipt.setResizable(true);
                tTotal.setResizable(true);
                
                tDate.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
                tTime.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
                tReceipt.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
                tTotal.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
                tDate.setSortType(TableColumn.SortType.DESCENDING);
                tTime.setSortType(TableColumn.SortType.DESCENDING);
                tReceipt.setSortType(TableColumn.SortType.ASCENDING);
                tTotal.setSortType(TableColumn.SortType.DESCENDING);
                
                Label Filters = new Label();
                Filters.setText("Apply Sorting Filters");
                Filters.setFont(Font.font ("Verdana", 20));
                
                CheckBox Cash = new CheckBox("Cash");
                CheckBox Card = new CheckBox("Credit/Debit Cards (all)");
                CheckBox StoreCredit = new CheckBox("Store Credit/Gift Card");
                HBox paymentType = new HBox(70);
                paymentType.getChildren().addAll(Cash, Card, StoreCredit);
                
                CheckBox Visa = new CheckBox("Visa");
                CheckBox MC = new CheckBox("MasterCard");
                CheckBox AmEx = new CheckBox("American Express");
                CheckBox Disc = new CheckBox("Discover");
                HBox cardType = new HBox(70);
                cardType.getChildren().addAll(Visa, MC, AmEx, Disc);
                
                Label keyword = new Label("Receipts with Keyword:");
                TextField keywordSearch = new TextField();
                keywordSearch.setPromptText("search");
                HBox keySearch = new HBox();
                keySearch.getChildren().addAll(keyword, keywordSearch);
                keySearch.setSpacing(10);
                
                Label totals = new Label("Receipts with totals from $");
                TextField minTotal = new TextField ();
                minTotal.setPromptText("minimum");
                minTotal.setMaxWidth(70);
                TextField maxTotal = new TextField ();
                maxTotal.setPromptText("maximum");
                maxTotal.setMaxWidth(70);
                Label to = new Label("to $");
                HBox rangeTotals = new HBox(10);
                rangeTotals.getChildren().addAll(totals, minTotal, to, maxTotal);
         
                Button Filter = new Button();
                Filter.setText("Filter Results");
                
                grid.add(back, 0, 0);
                grid.add(Title, 0, 1);
                grid.add(customDates, 0, 2);
                grid.add(radioDates, 0, 3);
                grid.add(Search, 0, 4);
                grid.add(table, 0, 5);
                grid.add(Filters, 0, 6);
		            grid.add(paymentType, 0, 7);
                grid.add(cardType, 0, 8);
                grid.add(keySearch, 0, 9);
                grid.add(rangeTotals, 0, 10);
                grid.add(Filter, 0, 11);
                
                
                        if (StartDate.getValue() != null   &&  EndDate.getValue() != null){  //if both start date and end date are chosen
                            //get results from db
                            //else do nothing 
                  
                        }
	            return grid; 
          }

	public Scene getScene() {
		return reportsScene;
	}
  
  
}
