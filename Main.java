import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private Scene logInScene;
	private Scene salesScene;
	private Scene addNewItem;
	private Scene reportsScene;
	private Scene searchScene;
    public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("POS");
		primaryStage.setFullScreen(true);
		reportsScene = null;
		searchScene = null;
		addNewItem = null;
		salesScene = null;
		logInScene = new LogInScene(primaryStage, salesScene).getScene();
		primaryStage.setScene(logInScene);
		primaryStage.show();
	}
	
	
	
	
}
