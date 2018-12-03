package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
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

public class EditNoteController implements Initializable{

	String query = "";
	
	@FXML
	private JFXTextField classe;
	@FXML
	private JFXTextField idEtud;
	@FXML
	private JFXTextField idMat;
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
		
	}
	
	public void setData(String idEtudiant,String matiere) {
		getInfoEtudiant(idEtudiant);
        getInfoMatiere(matiere);
        getClasse(idEtudiant);
	}
	
	/**
     * Obtenir l'information a propos d'un étudiant
     * @param id 
     */
    public void getInfoEtudiant(String id){
        try{
            query = "SELECT * FROM etudiants WHERE id_etudiant=?";
            PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
            prepare.setString(1, id);
            ResultSet res = prepare.executeQuery();
            res.first();
            idEtud.setText(res.getString("id_etudiant"));
            nom.setText(res.getString("nom_etud")+" "+res.getString("prenom_etud"));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donnée");
        }
    }
    
    /**
     * Obtenir l'information a propos d'un étudiant 
     * @param nom
     */
    public void getInfoMatiere(String nom){
        try{
            query = "SELECT * FROM matieres WHERE libelle_mat=?";
            PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
            prepare.setString(1, nom);
            ResultSet res = prepare.executeQuery();
            res.first();
            idMat.setText(res.getString("id_matiere"));
            matiere.setText(res.getString("libelle_mat"));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donnée");
        }
    }
    
    /**
     * Obtenir la classe d'étudiant
     * @param idEtud 
     */
    private void getClasse(String idEtud) {
         try{
            query = "SELECT etudiants.classe_etud,classes.titre_class FROM etudiants LEFT JOIN classes ON etudiants.classe_etud=classes.id_class WHERE id_etudiant='"+idEtud+"'";
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            //On exécute la requête
            ResultSet res = state.executeQuery(query);  
            res.first();
            classe.setText(res.getString("titre_class"));
             getNote(idMat.getText(), this.idEtud.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
	
    /**
     * Obtenir l'information a propos d'un étudiant 
     * @param mat
     * @param etud
     */
    public void getNote(String mat,String etud){
        try{
            query = "SELECT * FROM notes WHERE id_mat=? AND id_etud=?";
            PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
            prepare.setString(1, mat);
            prepare.setString(2, etud);
            ResultSet res = prepare.executeQuery();
            res.first();
            note.setText(res.getString("note_obtenu"));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donnée");
        }
    }
    
    @FXML
    private void closeCurrentWindow() {
    	nom.getScene().getWindow().hide();
    }
    
	@FXML
	public void editNoteStudent() {
		// Editer une note d'un étudiant
        try{
            if(!"".equals(note.getText())){
                
                query = "UPDATE notes SET note_obtenu=? WHERE id_mat=? AND id_etud=?";
                 
                PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
                prepare.setString(1, note.getText());
                prepare.setString(2,idMat.getText());
                prepare.setString(3, idEtud.getText());
                
                prepare.executeUpdate();
                //JOptionPane.showMessageDialog(null,"La note a été modifié");
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	root.setEffect(blur);
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                //dialogLayout.setHeading(new Label("Login success"));
                dialogLayout.setBody(new Text("La note a été modifié"));
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
            dialogLayout.setBody(new Text("Erruer de requête SQL"));
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
        note.setText("");
     }

}
