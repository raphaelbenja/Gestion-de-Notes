package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSlider;
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

public class EditStudentController implements Initializable{

	String query;
	
	@FXML
	private JFXTextField id;
	@FXML
	private JFXTextField nom;
	@FXML
	private JFXTextField prenom;
	@FXML
	private JFXDatePicker naissance;
	@FXML
	private JFXSlider age;
	@FXML
	private JFXComboBox<String> sexe;
	@FXML
	private JFXComboBox<String> ComboClasses;
	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	public String idEtud;
	
	public void setData(String idE) {
		this.idEtud = idE;
		getAllClasse();
		getInfoEtudiant(idEtud);
    }
	
	/**
     * Obtenir l'information a propos d'un étudiant
     * @param i 
     */
    public void getInfoEtudiant(String i){
    	sexe.getItems().add("H");
		sexe.getItems().add("F");
        try{
            query = "SELECT * FROM etudiants WHERE id_etudiant=?";
            PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
            prepare.setString(1, i);
            ResultSet res = prepare.executeQuery();
            res.first();
            id.setText(res.getString("id_etudiant"));
            nom.setText(res.getString("nom_etud"));
            prenom.setText(res.getString("prenom_etud"));
            //naissance.setValue(res.getDate("naissance_etud"));
            age.setValue(res.getDouble("age_etud"));
            sexe.getSelectionModel().select(res.getString("sexe_etud"));
            ComboClasses.getSelectionModel().select(res.getInt("classe_etud"));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donnée");
        }
    }
    
    /**
     * Obtenir toutes les niveaux
     */
    private void getAllClasse(){
        try{
            String query = "SELECT * FROM classes";
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //On exécute la requête
            ResultSet res = state.executeQuery(query);
            
            ComboClasses.getItems().add("");
            while(res.next()){
                ComboClasses.getItems().add(res.getString("titre_class"));
            }
            
            ComboClasses.getSelectionModel().select(14);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
   @FXML
    private void btnModifyStudent() {
        // Editer un étudiants 
        try{
            if(nom.getText() != "" && prenom.getText() != "" && naissance.getValue().toString() != ""){
                
                String query = "UPDATE etudiants SET nom_etud=?,prenom_etud=?,naissance_etud=?,age_etud=?,sexe_etud=?,classe_etud=? WHERE id_etudiant=?";
                 
                PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
                prepare.setString(1, nom.getText());
                prepare.setString(2,prenom.getText());
                prepare.setString(3, naissance.getValue().toString());
                prepare.setDouble(4,age.getValue());
                prepare.setString(5,sexe.getSelectionModel().getSelectedItem().toString());
                prepare.setInt(6,ComboClasses.getSelectionModel().getSelectedIndex());
                prepare.setString(7,id.getText());
                prepare.executeUpdate();
                //JOptionPane.showMessageDialog(null,"L'étudiant a été modifié");
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	root.setEffect(blur);
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                //dialogLayout.setHeading(new Label("Login success"));
                dialogLayout.setBody(new Text("L'étudiant a été modifié"));
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
            //JOptionPane.showMessageDialog(null, "Erreur de requête SQL");
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
        prenom.setText("");
        naissance.setValue(null);
        age.setValue(0);
        ComboClasses.getSelectionModel().select(13);
     }
	
	@FXML
    private void closeCurrentWindow() {
    	id.getScene().getWindow().hide();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
