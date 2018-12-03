package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ForgotController implements Initializable{

	double x=0,y=0;
	private String query;
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@FXML
	private JFXTextField email;
	@FXML
	private JFXTextField old_password;
	@FXML
	private JFXTextField new_password;
	@FXML
	private JFXTextField confirm_new_password;
	@FXML
	private JFXButton valider;
	@FXML
	private JFXButton change;
	@FXML
	private Hyperlink revenir;
	@FXML
	private AnchorPane root;
	@FXML
	private StackPane rootPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		old_password.setDisable(true);
		new_password.setVisible(false);
		confirm_new_password.setVisible(false);
		change.setVisible(false);
	}
	
	@FXML
	public void verifyEmail() {
		if(!"".equals(email.getText())) {
			query = "select email from utilisateurs WHERE (email ='" +email.getText()
            +"')";
			 try {                
			    Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			    ResultSet res = state.executeQuery(query);
			    
			    if(res.first()){ 
					
					JFXDialogLayout dialogLayout = new JFXDialogLayout();
		            JFXButton button = new JFXButton("Okay");
		            JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
		            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
		            	root.setEffect(blur);
		            	dialog.close();
		            });
		            button.getStyleClass().add("dialog-button");
		            
		            dialogLayout.setHeading(new Label("Information"));
		            dialogLayout.setBody(new Text(res.getString("email")+" a été trouvé"));
		            dialogLayout.setActions(button);
		            dialog.show();
		            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
		            	root.setEffect(null);
		            	old_password.setDisable(false);
						new_password.setVisible(true);
						confirm_new_password.setVisible(true);
						email.setDisable(true);
						change.setVisible(true);
						valider.setVisible(false);
		            });	
				}else {
					JFXDialogLayout dialogLayout = new JFXDialogLayout();
		            JFXButton button = new JFXButton("Okay");
		            JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
		            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
		            	root.setEffect(blur);
		            	dialog.close();
		            });
		            button.getStyleClass().add("dialog-button");
		            
		            dialogLayout.setHeading(new Label("Erreur"));
		            dialogLayout.setBody(new Text("Adresse Email n'existe pas"));
		            dialogLayout.setActions(button);
		            dialog.show();
		            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
		            	root.setEffect(null);
		            	email.setText("");
		            });
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("Okay");
            JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
            	root.setEffect(blur);
            	dialog.close();
            });
            button.getStyleClass().add("dialog-button");
            
            dialogLayout.setHeading(new Label("Champs non remplit"));
            dialogLayout.setBody(new Text("Veuillez entrer votre adresse email s'il vous plait !!!"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
            	root.setEffect(null);
            	email.setText("");
            });
		}
	}
	
	@FXML
	public void verifOldPassword() {
		if(!"".equals(old_password.getText())) {
			query = "select pseudo,password from utilisateurs WHERE (password ='" +old_password.getText()
            +"' AND email ='"+email.getText()+"' )";
			 try {                
			    Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			    ResultSet res = state.executeQuery(query);
			    
			    if(res.first()){ 
					if(!"".equals(new_password.getText()) && !"".equals(confirm_new_password.getText())) {
						if(new_password.getText().equals(confirm_new_password.getText())) {
							changePassword(res.getString("pseudo"));
						}else {
							JFXDialogLayout dialogLayout = new JFXDialogLayout();
				            JFXButton button = new JFXButton("Okay");
				            JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
				            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
				            	root.setEffect(blur);
				            	dialog.close();
				            });
				            button.getStyleClass().add("dialog-button");
				            
				            dialogLayout.setHeading(new Label("Password doesn't match"));
				            dialogLayout.setBody(new Text("Les deux mot de passe ne concordent pas"));
				            dialogLayout.setActions(button);
				            dialog.show();
				            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
				            	root.setEffect(null);
				            	new_password.setText("");
				            	confirm_new_password.setText("");
				            });
						}
					}else {
						JFXDialogLayout dialogLayout = new JFXDialogLayout();
			            JFXButton button = new JFXButton("Okay");
			            JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
			            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
			            	root.setEffect(blur);
			            	dialog.close();
			            });
			            button.getStyleClass().add("dialog-button");
			            
			            dialogLayout.setHeading(new Label("Champs non remplit"));
			            dialogLayout.setBody(new Text("Veuillez entrer votre nouveau mot de passe et sa confirmation s'il vous plait !!!"));
			            dialogLayout.setActions(button);
			            dialog.show();
			            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
			            	root.setEffect(null);
			            	old_password.setText("");
			            	new_password.setText("");
			            	confirm_new_password.setText("");
			            });
					}
				}else {
					JFXDialogLayout dialogLayout = new JFXDialogLayout();
		            JFXButton button = new JFXButton("Okay");
		            JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
		            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
		            	root.setEffect(blur);
		            	dialog.close();
		            });
		            button.getStyleClass().add("dialog-button");
		            
		            dialogLayout.setHeading(new Label("Warning"));
		            dialogLayout.setBody(new Text("Votre ancien mot de passe est incorrect"));
		            dialogLayout.setActions(button);
		            dialog.show();
		            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
		            	root.setEffect(null);
		            	old_password.setText("");
		            	new_password.setText("");
		            	confirm_new_password.setText("");
		            });
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("Okay");
            JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
            	root.setEffect(blur);
            	dialog.close();
            });
            button.getStyleClass().add("dialog-button");
            
            dialogLayout.setHeading(new Label("Warning"));
            dialogLayout.setBody(new Text("Veuillez completez les informations"));
            dialogLayout.setActions(button);
            dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
            	root.setEffect(null);
            });
		}
	}

	/**
	 * Changer le mot de passe
	 * @param string
	 */
	private void changePassword(String pseudo) {
		try {
			query = "update utilisateurs SET password = ? WHERE pseudo=?";
			                 
			PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
			prepare.setString(1, new_password.getText());
			prepare.setString(2,pseudo);
            prepare.executeUpdate();
							
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXButton button = new JFXButton("Okay");
		    JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
		    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
            	root.setEffect(blur);
            	dialog.close();
	       	});
		    
		    button.getStyleClass().add("dialog-button");
					            
		    dialogLayout.setHeading(new Label("Information"));
		    dialogLayout.setBody(new Text("Le mot de passe a été changé avec succès"));
	        dialogLayout.setActions(button);
	        dialog.show();
            dialog.setOnDialogClosed((JFXDialogEvent event1)->{
            	root.setEffect(null);
		       	old_password.setText("");
		       	new_password.setText("");
		       	confirm_new_password.setText("");
		   });
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void revenirLogin() throws Exception {
		Stage load = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
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
		valider.getScene().getWindow().hide();
	}
	
	@FXML
	public void exit() {
		Platform.exit();
	}

}
