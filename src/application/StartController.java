package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class StartController implements Initializable{

	@FXML
	private StackPane rootp;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(!Main.isLoaded) {
			loadSplashscreen();
		}
	}
	
	public void loadSplashscreen() {
		try {
			Main.isLoaded = true;
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Splashscreen.fxml"));
			rootp.getChildren().setAll(root);
			
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3),root);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			fadeIn.play();
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3),root);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0.8);
			fadeOut.setCycleCount(1);
			fadeIn.setOnFinished((e)->{
				fadeOut.play();
			});
			
			fadeOut.setOnFinished((e)->{
				try {
					Parent parent = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
					rootp.getChildren().setAll(parent);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
