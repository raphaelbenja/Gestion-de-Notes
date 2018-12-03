package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class AddNoteController implements Initializable{

	@FXML
	private JFXTextField classe;
	@FXML
	private JFXComboBox<String> idEtud;
	@FXML
	private JFXComboBox<String> idMat;
	@FXML
	private JFXTextField matiere;
	@FXML
	private JFXTextField nom;
	@FXML
	private JFXTextField note;
	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getAllIdEtudiant();
		getAllIdMatiere();
	}
	
	private void getAllIdEtudiant(){
		String query = "SELECT * FROM etudiants";
		try {
			Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	         ResultSet res = state.executeQuery(query);
	            
	         while(res.next()){
	            idEtud.getItems().add(res.getString("id_etudiant"));
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    private void getAllIdMatiere(){
        String query = "SELECT * FROM matieres";
        try {
        	Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res = state.executeQuery(query);
                
            while(res.next()){
                idMat.getItems().add(res.getString("id_matiere"));
             }
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    private void getClasse(String idEtud) {
         try{
            String query = "SELECT etudiants.classe_etud,classes.titre_class FROM etudiants LEFT JOIN classes ON etudiants.classe_etud=classes.id_class WHERE id_etudiant='"+idEtud+"'";
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            ResultSet res = state.executeQuery(query);  
            res.first();
            classe.setText(res.getString("titre_class"));
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void getStudent(String id){
        try{
            String query = "SELECT nom_etud,prenom_etud FROM etudiants WHERE id_etudiant='"+id+"'";
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res = state.executeQuery(query);
            
            res.first();
            nom.setText(res.getString("nom_etud")+" "+ res.getString("prenom_etud"));
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     private void getMatiere(String id){
        try{
            String query = "SELECT libelle_mat FROM matieres WHERE id_matiere='"+id+"'";
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet res = state.executeQuery(query);
            
            res.first();
            matiere.setText(res.getString("libelle_mat"));
                       
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erreur de requête SQL");
        }
    }
     
     /**
      * Sauvegarder un étudiant dans la BDD
      * @param evt 
      */
     @FXML
     private void saveStudentMouseClicked() {
    	 // Ajouter un étudiants 
         try{
             if(nom.getText()!= "" && note.getText() != "" &&  matiere.getText() != ""){
                 
                  String query = "INSERT INTO notes(id_mat,id_etud,note_obtenu) VALUES(?,?,?) ";
                 PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
                 prepare.setString(1,idMat.getSelectionModel().getSelectedItem().toString());
                 prepare.setString(2,idEtud.getSelectionModel().getSelectedItem().toString());
                 prepare.setString(3,note.getText());
                 //On exécute la requête
                 prepare.executeUpdate();
                 //JOptionPane.showMessageDialog(null,"La note a été attribué à cet étudiant");
                 JFXDialogLayout dialogLayout = new JFXDialogLayout();
                 JFXButton button = new JFXButton("Okay");
                 JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                 button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                 	root.setEffect(blur);
                 	dialog.close();
                 });
                 button.getStyleClass().add("dialog-button");
                 
                 //dialogLayout.setHeading(new Label("Login success"));
                 dialogLayout.setBody(new Text("La note a été attribué à cet étudiant"));
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
                 dialogLayout.setBody(new Text("Veuillez remplir tous les champs"));
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
             dialogLayout.setBody(new Text("Erreur de requête SQL"));
             dialogLayout.setActions(button);
             dialog.show();
             dialog.setOnDialogClosed((JFXDialogEvent event1)->{
             	root.setEffect(null);
             });
         }
     }

     @FXML
     private void idMatItem() {
         // Liste Matières
         getMatiere(idMat.getSelectionModel().getSelectedItem().toString());
     }
     
     @FXML
     private void idEtudItem() {
    	 // Liste Etudiants
          getStudent(idEtud.getSelectionModel().getSelectedItem().toString());
          getClasse(idEtud.getSelectionModel().getSelectedItem().toString());
     }

     /**
      * Reinitialise les champs
      */
     private void actualiser(){
         note.setText("");
     }
     
     @FXML
     private void closeCurrentWindow() {
     	nom.getScene().getWindow().hide();
     }

}
