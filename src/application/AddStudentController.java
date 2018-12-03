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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AddStudentController implements Initializable{

	@FXML
	private JFXComboBox<String> ComboClasses;
	@FXML
	private JFXComboBox<String> sexe;
	@FXML
	private JFXTextField nom;
	@FXML
	private JFXTextField prenom;
	@FXML
	private JFXDatePicker naissance;
	@FXML
	private JFXSlider age;
	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getAllClasse();
	}
	
	private void getAllClasse(){
		sexe.getItems().add("H");
		sexe.getItems().add("F");
        try{
            String query = "SELECT * FROM classes";
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

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
	
	@SuppressWarnings("unlikely-arg-type")
	@FXML
	private void AddStudent(ActionEvent e) {
		if(!"".equals(nom.getText()) && !"".equals(prenom.getText()) &&  !"0".equals(age.getValue())){
			try{           
                String query = "INSERT INTO etudiants(nom_etud,prenom_etud,naissance_etud,age_etud,sexe_etud,classe_etud) VALUES(?,?,?,?,?,?) ";
                PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
                if(!"0".equals(ComboClasses.getSelectionModel().getSelectedIndex()) && !"0".equals(sexe.getSelectionModel().getSelectedIndex())) {
                	prepare.setString(1, nom.getText());
                    prepare.setString(2,prenom.getText());
                    prepare.setString(3, naissance.getValue().toString());
                    prepare.setDouble(4,age.getValue());
                    prepare.setString(5,sexe.getSelectionModel().getSelectedItem().toString());
                	prepare.setInt(6,ComboClasses.getSelectionModel().getSelectedIndex());
                	prepare.executeUpdate();
                     
                    //JOptionPane.showMessageDialog(null,"L'étudiant a été ajouté");
                	
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("Okay");
                    JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                    	root.setEffect(blur);
                    	dialog.close();
                    });
                    button.getStyleClass().add("dialog-button");
                    
                    //dialogLayout.setHeading(new Label("Login success"));
                    dialogLayout.setBody(new Text("L'étudiant a été ajouté"));
                    dialogLayout.setActions(button);
                    dialog.show();
                    dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                    	root.setEffect(null);
                    	actualiser();
                    });
                }else {
                	//JOptionPane.showMessageDialog(null, "Veuillez selectionné la classe de l'étudiant");
                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    JFXButton button = new JFXButton("Okay");
                    JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                    	root.setEffect(blur);
                    	dialog.close();
                    });
                    button.getStyleClass().add("dialog-button");
                    
                    dialogLayout.setHeading(new Label("Warning"));
                    dialogLayout.setBody(new Text("Veuillez selectionné la classe de l'étudiant"));
                    dialogLayout.setActions(button);
                    dialog.show();
                    dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                    	root.setEffect(null);
                    });
                }}catch(Exception e1){
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
                    dialogLayout.setBody(new Text("Veuillez tous remplir tous les champs"));
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
                dialogLayout.setBody(new Text("Veuillez remplir les champs"));
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
            prenom.setText("");
            naissance.setValue(null);
            age.setValue(0);
            ComboClasses.getSelectionModel().select(14);
     }
    
    @FXML
    private void closeCurrentWindow() {
    	nom.getScene().getWindow().hide();
    }

}
