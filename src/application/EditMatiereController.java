package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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

public class EditMatiereController implements Initializable{

	String query = "";
	@SuppressWarnings("unused")
	private String idE;
	
	@FXML
	private JFXTextField id;
	@FXML
	private JFXTextField nom;
	@FXML
	private JFXComboBox<String> coefficient;
	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	 /**
     * Obtenir l'information a propos d'un étudiant
     * @param i 
	 * @throws Exception 
     */
    public void getInfoMatiere(String i){
    	this.idE = i;
    	coefficient.getItems().add("");
    	coefficient.getItems().add("1");
    	coefficient.getItems().add("2");
    	coefficient.getItems().add("3");
    	coefficient.getItems().add("4");
    	coefficient.getItems().add("5");
    	
        try{
            query = "SELECT * FROM matieres WHERE id_matiere=?";
            PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
            prepare.setString(1, i);
            ResultSet res = prepare.executeQuery();
            res.first();
            id.setText(res.getString("id_matiere"));
            nom.setText(res.getString("libelle_mat"));
            coefficient.getSelectionModel().select(res.getInt("coef_mat"));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donnée");
        }
    }
    
    @FXML
    private void closeCurrentWindow() {
    	id.getScene().getWindow().hide();
    }
    
    @FXML
    private void btnModifyMatiere() {
        // Modifier Matiere
        try{
            if(!"".equals(nom.getText()) && !"".equals(coefficient.getSelectionModel().getSelectedItem())){
                
                String query = "UPDATE matieres SET libelle_mat=?,coef_mat=? WHERE id_matiere=?";
                 
                PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
                prepare.setString(1, nom.getText());
                prepare.setString(2,coefficient.getSelectionModel().getSelectedItem().toString());
                prepare.setString(3,id.getText());
                prepare.executeUpdate();
                //JOptionPane.showMessageDialog(null,"La matière a été modifié");
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	root.setEffect(blur);
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                //dialogLayout.setHeading(new Label("Login success"));
                dialogLayout.setBody(new Text("La matière a été modifié"));
                dialogLayout.setActions(button);
                dialog.show();
                dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                	root.setEffect(null);
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
            //JOptionPane.showMessageDialog(null, "Erreur de requête");
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
    @SuppressWarnings("unused")
	private void actualiser(){
         nom.setText("");
     }

}
