package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

public class LoginController implements Initializable{
	private String query;
	double x=0,y=0;
	
	@FXML
	private JFXTextField user;
	
	@FXML
	private JFXPasswordField pass;
	
	@FXML
	private Button login;
	
	@FXML
	private AnchorPane root;
	@FXML
	private StackPane rootPane;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@FXML
	public void btn_exitClick() {
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	public void LogUser() throws IOException {
		if(!"".equals(user.getText()) && !"".equals(pass.getText())){         
			query = "Select * from utilisateurs WHERE (pseudo ='" +user.getText()
                    +"' OR email='"+user.getText()+"') and password='"+pass.getText()+"'";
         try {                
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res = state.executeQuery(query);
            
            if(res.first()){ 
            	BoxBlur blur = new BoxBlur(3,3,3);
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                dialogLayout.setHeading(new Label("Login success"));
                dialogLayout.setBody(new Text("Vous êtes connecté "+res.getString("pseudo")));
                dialogLayout.setActions(button);
                dialog.show();
                dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                	String title = "Login success";
        	        String message = null;
					try {
						message = "Welcome to the Application "+res.getString("pseudo").toUpperCase();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        	        Image img = new Image("file:C:\\Users\\Kurosaki\\eclipse-workspace\\Devs\\src\\application\\images\\Circled.png");
        	        TrayNotification tray = new TrayNotification();
        	        tray.setTitle(title);
        	        tray.setMessage(message);
        	        //tray.setNotificationType(notification);
        	        //tray.setRectangleFill(Paint.valueOf("#2A9A84"));
        	        tray.setAnimationType(AnimationType.FADE);
        	        tray.setImage(img);
        	        tray.showAndDismiss(Duration.seconds(2));
                	root.setEffect(blur);
                	try {
	                	//new MyLoaader(res.getString("pseudo")).start();
	                	
	                	user.setText("");
	                    pass.setText("");
	                    Stage load = new Stage();
	                    Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
	                   
	                    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
	
	            			@Override
	            			public void handle(MouseEvent e) {
	            				x = e.getSceneX();
	            				y = e.getSceneY();
	            			}
	            		});
	            		
	            		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	
	            			@Override
	            			public void handle(MouseEvent e) {
	            				load.setX(e.getScreenX()-x);
	            				load.setY(e.getScreenY()-y);
	            			}
	            		});
	        			load.initStyle(StageStyle.UNDECORATED);
	        			load.setScene(new Scene(root));
	        			load.show();
	        			login.getScene().getWindow().hide();
                	}catch(Exception e) {
                		e.printStackTrace();
                	}
                });
                root.setEffect(null);
            }else{
                //JOptionPane.showMessageDialog(null,"Pseudo / Mot de passe Incorrect","ERREUR",JOptionPane.ERROR_MESSAGE);
            	/*Alert alert = new Alert(AlertType.WARNING);
            	alert.setHeaderText(null);
            	alert.setContentText("Pseudo / Mot de passe Incorrect");
            	alert.show();*/
            	JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	root.setEffect(blur);
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                dialogLayout.setHeading(new Label("Warning"));
                dialogLayout.setBody(new Text("Pseudo / Mot de passe Incorrect"));
                dialogLayout.setActions(button);
                dialog.show();
                dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                	root.setEffect(null);
                	pass.setText("");
                });	
            }
                                    
            //res.close();
            //state.close();

          } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
          }   
     }else {
    	 //JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
    	/*Alert alert = new Alert(AlertType.ERROR);
     	alert.setHeaderText("Required");
     	alert.setContentText("Veuillez remplir tous les champs");
     	alert.showAndWait();*/
    	 JFXDialogLayout dialogLayout = new JFXDialogLayout();
         JFXButton button = new JFXButton("Okay");
         JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
         button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
         	root.setEffect(blur);
         	dialog.close();
         });
         button.getStyleClass().add("dialog-button");
         
         dialogLayout.setHeading(new Label("Warning"));
         dialogLayout.setBody(new Text("Veuillez remplir tous les champs"));
         dialogLayout.setActions(button);
         dialog.show();
         dialog.setOnDialogClosed((JFXDialogEvent event1)->{
         	root.setEffect(null);
         });
     }
	}
	
	@FXML
	public void createUser() throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Register.fxml"));
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				x = e.getSceneX();
				y = e.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				primaryStage.setX(e.getScreenX()-x);
				primaryStage.setY(e.getScreenY()-y);
			}
		});
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		login.getScene().getWindow().hide();
	}
	
	@FXML
	public void forgotPassword() throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Forgot.fxml"));
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				x = e.getSceneX();
				y = e.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				primaryStage.setX(e.getScreenX()-x);
				primaryStage.setY(e.getScreenY()-y);
			}
		});
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		login.getScene().getWindow().hide();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

	class MyLoaader extends Thread{
		
		private String pseudo;
		
		public MyLoaader(String user) {
			this.pseudo = user;
		}
		
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Image img = new Image("file:C:\\Users\\Kurosaki\\eclipse-workspace\\Devs\\src\\application\\images\\Circled.png");
        	Notifications notif = Notifications.create()
        			.title("             Login success")
        			.text("Welcome to the Application \n ---------- "+this.pseudo.toUpperCase()+" ----------")
        			.hideAfter(Duration.seconds(3))
        			.position(Pos.BOTTOM_RIGHT)
        			.darkStyle()
        			.graphic(new ImageView(img))
        			.onAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent e) {
							System.out.print("Notification has been Clicked");
						}
					});
			/*String title = "Congratulations sir";
	        String message = "You've successfully created your first Tray Notification";
	        NotificationType notification = NotificationType.SUCCESS;
	        
	        TrayNotification tray = new TrayNotification();
	        tray.setTitle(title);
	        tray.setMessage(message);
	        tray.setNotificationType(notification);
	        tray.showAndWait();*/
        	Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					notif.show();
				}
			});	
		}
	}
}
