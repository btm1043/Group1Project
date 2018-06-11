import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
public class LogInScene {
	private static final int defaultUser = 132451;
	private static final int defaultPIN = 1111;
	private static String logInPrompt = "Please enter your log-in number.";
	private static String pinPrompt = "Please enter your PIN.";
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double screenWidth = screen.getWidth();
	private double screenHeight = screen.getHeight();
	private int user;
	private int pin;
	private Scene loginScene;
	private GridPane pad;

	public LogInScene(Stage stage, Scene scene) {
		pad = makeLogOnPad(stage, scene);
		loginScene = new Scene(pad);
	}
	
	GridPane makeLogOnPad(Stage stage, Scene scene) {
		GridPane numPad = new GridPane();
		DropShadow shadow = new DropShadow();
		numPad.setAlignment(Pos.BOTTOM_CENTER);
		String[] keyText = {
				"1", "2", "3",
				"4", "5", "6",
				"7", "8", "9",
				"Clear", "0", "Enter"
		};
		Label bigTitle = new Label();
		bigTitle.setFont(Font.font ("Verdana", 100));
		bigTitle.setText("Log In");
		GridPane.setHalignment(bigTitle, HPos.CENTER);
		GridPane.setColumnSpan(bigTitle, 3);
		numPad.add(bigTitle, 0,  0);
		TextField input = new TextField();
		input.setEffect(shadow);
		input.setAlignment(Pos.CENTER);
		input.setPromptText("Please enter your log-in number.");
		input.setMinHeight(screenHeight/16);
		GridPane.setMargin(input, new Insets(screenHeight/16,screenWidth/18,screenHeight/16,screenWidth/18));
		numPad.add(input, 0, 1);
		GridPane.setColumnSpan(input, 3);
		for (int i = 0; i < keyText.length; i++) {
			Button b = new Button(keyText[i]);
			GridPane.setMargin(b, new Insets(10,10,10,10));
			b.setEffect(shadow);
			b.setMinWidth(screenWidth/9);
			b.setMinHeight(screenHeight/8);
			b.getStyleClass().add("num-button");
			if (b.getText().equals("Enter")) 
				b.setStyle("-fx-background-color: #008000; ");
			else if (b.getText().length() > 1)
				b.setStyle("-fx-background-color: #FF0000; ");
			
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					String buttonText = b.getText();
					String fieldText = input.getText();
					if (buttonText.length() == 1) {
						fieldText += buttonText;
						input.setText(fieldText);
					} else if (buttonText.equals("Clear")) {
						fieldText = "";
						input.setText("");
					} else if (input.getText().length() > 0) {
						if(input.getPromptText().equals(logInPrompt)) {
							try {
								user = Integer.parseInt(fieldText);
								input.setPromptText(pinPrompt);
							} catch (NumberFormatException err) {
								promptError(stage, "Wrong Format", "Please only enter integers.");
							}
							
						} else {
							try {
								pin = Integer.parseInt(fieldText);
								boolean success = logIn();
								if (!success) {
									promptError(stage, "Login ID does not match with PIN!", "Please re-enter your login ID and PIN.");
									input.setPromptText(logInPrompt);
								} else {
									stage.setScene(scene);
									stage.show();
								}
							} catch (NumberFormatException err) {
								promptError(stage, "Wrong Format", "Please only enter integer.");
							}
							
							
						}
						fieldText = "";
						input.setText("");
						
					}
					
				}
			});
			numPad.add(b, i%3, (int) Math.ceil(i/3) + 2);
		}
		return numPad;
	}

	public void promptError(Stage stage, String header, String content) {
		Alert failedLogIn = new Alert(AlertType.ERROR);
		failedLogIn.initOwner(stage);
		failedLogIn.setHeaderText(header);
		failedLogIn.setContentText(content);
		failedLogIn.showAndWait();
	}
	
	public GridPane getLogOnPad() {
		return pad;
	}
	
	public boolean logIn() {
		return (user == defaultUser && pin == defaultPIN) || lookUp();
	}
	
	public boolean lookUp() {
		return false;
	}
	
	public Scene getScene() {
		return loginScene;
	}
}
