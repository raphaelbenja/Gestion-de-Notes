package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXProgressBar;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Loading implements Initializable,Runnable{

	@FXML
    private JFXProgressBar progress;
    
    @FXML
    private Text loading;
    
    @FXML
    private AnchorPane anch;
    
    @FXML
    private Pane pane;
    
    @FXML
    private ImageView img;
    
    public static boolean alive = false;
	
	int s=0;
    Thread th1;
    Stage load = new Stage();
    
    public Loading() throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
	      load.initStyle(StageStyle.UNDECORATED);
	      load.setScene(new Scene(root));
    	th1 = new Thread((Runnable)this);
    }
    
	@Override
	public void run() {
		 s=s+1;
	       try{
	          for(double i=0;i<1.0;i+=0.01){
	              double v = progress.getProgress();
	              double m = 1.0;
	              if(v<m){
	                   progress.setProgress((progress.getProgress())+0.01);
	              }else{
	                i=1;
	              }
	              Thread.sleep(50);
	          }
	        }catch(Exception e){
	            e.getMessage();
	        }
	       anch.getScene().getWindow().hide();
	}
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 FadeTransition fadeIn = new FadeTransition(Duration.seconds(3));
		 	fadeIn.setNode(anch);
			fadeIn.setFromValue(0.5);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			fadeIn.play();
		th1.start();
		//
		load.show();
	}

}
