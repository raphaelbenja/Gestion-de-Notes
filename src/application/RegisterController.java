package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterController implements Initializable{
	
	double x=0,y=0;
	
	@FXML
	private JFXTextField user;
	
	@FXML
	private JFXTextField email;
	
	@FXML
	private JFXPasswordField pass;
	
	@FXML
	private JFXPasswordField passconfirm;
	
	@FXML
	private Button login;
	
	@FXML
	private Hyperlink log;
	@FXML
	private AnchorPane root;
	@FXML
	private StackPane rootPane;
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@FXML
	public void createUser() {
		if(!"".equals(user.getText()) && !"".equals(email.getText()) && !"".equals(pass.getText()) && !"".equals(passconfirm.getText())){
            if(pass.getText().equals(passconfirm.getText())){
                try{
                    String query = "INSERT INTO utilisateurs(pseudo,email,password) VALUES(?,?,?) ";

                    PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
                    prepare.setString(1, user.getText());
                    prepare.setString(2,email.getText());
                    prepare.setString(3, pass.getText());
                    prepare.executeUpdate();
                    //JOptionPane.showMessageDialog(null,"Consulter votre email pour terminer l'inscription");
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("Okay");
                    JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                    	root.setEffect(blur);
                    	dialog.close();
                    });
                    button.getStyleClass().add("dialog-button");
                    
                    dialogLayout.setHeading(new Label("Information"));
                    dialogLayout.setBody(new Text("Consulter votre email pour terminer l'inscription"));
                    dialogLayout.setActions(button);
                    dialog.show();
                    dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                    	root.setEffect(null);
                    	actualiser();
                    });  
                }catch(Exception e){
                    //JOptionPane.showMessageDialog(null, "Erreur de la requête");
                	JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("Okay");
                    JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                    	root.setEffect(blur);
                    	dialog.close();
                    });
                    button.getStyleClass().add("dialog-button");
                    
                    dialogLayout.setHeading(new Label("Danger"));
                    dialogLayout.setBody(new Text("Erreur de la requête SQL"));
                    dialogLayout.setActions(button);
                    dialog.show();
                    dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                    	root.setEffect(null);
                    });
                }
             }else{
                //JOptionPane.showMessageDialog(null, "Les mot de passe ne concordent pas");
            	 JFXDialogLayout dialogLayout = new JFXDialogLayout();
                 JFXButton button = new JFXButton("Okay");
                 JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                 button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                 	root.setEffect(blur);
                 	dialog.close();
                 });
                 button.getStyleClass().add("dialog-button");
                 
                 dialogLayout.setHeading(new Label("Warning"));
                 dialogLayout.setBody(new Text("Les mot de passe ne concordent pas"));
                 dialogLayout.setActions(button);
                 dialog.show();
                 dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                 	root.setEffect(null);
                 });
           }   
        }else{
            //JOptionPane.showMessageDialog(null, "Veuillez remplir les champs");
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
	public void retLogin() throws Exception {
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
		login.getScene().getWindow().hide();
	}
	
	@FXML
	private void exit() {
		Platform.exit();
		System.exit(0);
	}
	
	/**
     * Effacer les champs
     */
    private void actualiser(){
        user.setText("");
        email.setText("");
        pass.setText("");
        passconfirm.setText("");
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
