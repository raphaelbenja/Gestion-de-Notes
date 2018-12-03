package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AddMatiereController implements Initializable {

	@FXML
	private JFXTextField nom;
	
	@FXML
	private JFXComboBox<String> coefficient;
	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		coefficient.getItems().add("1");
		coefficient.getItems().add("2");
		coefficient.getItems().add("3");
		coefficient.getItems().add("4");
		coefficient.getItems().add("5");
	}
	
	@FXML
	private void addMatiere() {
        // Ajouter une matiere
        try{
            if(nom.getText() != "" && coefficient.getSelectionModel().getSelectedItem() != ""){
                
                String query = "INSERT INTO matieres(libelle_mat,coef_mat) VALUES(?,?) ";

                PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
                prepare.setString(1, nom.getText());
                prepare.setString(2,coefficient.getSelectionModel().getSelectedItem().toString());
                prepare.executeUpdate();
                //JOptionPane.showMessageDialog(null,"La matière a été ajouté");
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	root.setEffect(blur);
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                //dialogLayout.setHeading(new Label("Login success"));
                dialogLayout.setBody(new Text("La matière a été ajouté"));
                dialogLayout.setActions(button);
                dialog.show();
                dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                	root.setEffect(null);
                	actualiser();
                });
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
                dialogLayout.setBody(new Text("Veuillez remplir les champs"));
                dialogLayout.setActions(button);
                dialog.show();
                dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                	root.setEffect(null);
                });
            }
        }catch(Exception e){
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
	
	 /**
     * Reinitialise les champs
     */
    private void actualiser(){
        nom.setText("");
     }
    
    @FXML
    private void closeCurrentWindow() {
    	nom.getScene().getWindow().hide();
    }

}
