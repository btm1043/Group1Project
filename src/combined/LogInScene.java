package combined;

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
	private static final String logInPrompt = "Please enter your log-in number.";
	private static final String pinPrompt = "Please enter your PIN.";
	private Rectangle2D screen;
	private double screenWidth;
	private double screenHeight;
	private int user;
	private int pin;
	private Scene loginScene;


	/**
	 * Constructs the scene.
	 * 
	 * @param stage
	 * @param scene The main screen to transition to have successful log in.
	 */
	public LogInScene(Stage stage, Scene scene) {
		screen = Screen.getPrimary().getVisualBounds();
		screenWidth = screen.getWidth();
		screenHeight = screen.getHeight();
		GridPane pad = makeLogOnPad(stage, scene);
		loginScene = new Scene(pad);
	}
	

	/**
	 * Creates the logon pad for the logon scene.
	 * 
	 * @param stage
	 * @param scene Transition scene after successful log in.
	 * 
	 * @return Returns logon pad Gridpane.
	 */
	private GridPane makeLogOnPad(Stage stage, Scene scene) {
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
					if (buttonText.length() == 1) { // Number button
						fieldText += buttonText;
						input.setText(fieldText);
					} else if (buttonText.equals("Clear")) {
						fieldText = "";
						input.setText("");
						input.requestFocus();
					} else if (input.getText().length() > 0) { // Enter button
						if(input.getPromptText().equals(logInPrompt)) {
							try {
								user = Integer.parseInt(fieldText);
								input.setPromptText(pinPrompt);
								input.requestFocus();
							} catch (NumberFormatException err) {
								promptError(stage, "Wrong Format", "Please only enter integers.");
							}
						} else {
							try {
								pin = Integer.parseInt(fieldText);
								boolean success = lookUp();
								if (!success) {
									promptError(stage, "Login ID does not match with PIN!", "Please re-enter your login ID and PIN.");
									input.setPromptText(logInPrompt);
									input.requestFocus();
								} else {
									input.setPromptText(logInPrompt);
									input.requestFocus();
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
	

	/**
	 * @return true if user/pin exists in DB or is default login. False otherwise
	 */
	private boolean lookUp() {
		if (user == defaultUser && pin == defaultPIN)
			return true;
		
		return Database.checkUser(user);
	}
	
	/**
	 * Small helper method to prompt error when user enters invalid data.
	 * 
	 * @param stage
	 * @param header Name of the error dialogue box
	 * @param content Error message
	 */
	private void promptError(Stage stage, String header, String content) {
		Alert failedLogIn = new Alert(AlertType.ERROR);
		failedLogIn.initOwner(stage);
		failedLogIn.setHeaderText(header);
		failedLogIn.setContentText(content);
		failedLogIn.showAndWait();
	}
	
	/**
	 * @return returns the loginscene. 
	 */
	public Scene getScene() {
		return loginScene;
	}
}
