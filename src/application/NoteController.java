package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NoteController implements Initializable{

	private String query;
	double xOffset,yOffset;

	@FXML
	private TableView<Person> table;
	@FXML
	private TableColumn<Person, String> matiereColumn;
	@FXML
	private TableColumn<Person, String> coefficientColumn;
	@FXML
	private TableColumn<Person, String> id2Column;
	@FXML
	private TableColumn<Person, Integer> ageColumn;
	
	@FXML
	private JFXTextField nomEtud;
	
	@FXML
	private JFXTextField moyenne;
	
	@FXML
	private JFXTextField observation;
	@FXML
	private JFXTextField classe;
	
	@FXML
	JFXComboBox<String> idEtud;
	@FXML
	private StackPane rootPane;
	@FXML
	private FlowPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@FXML
	public void returnLogin() throws Exception {
		Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				xOffset = e.getSceneX();
				yOffset = e.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				st.setX(e.getScreenX()-xOffset);
				st.setY(e.getScreenY()-yOffset);
			}
		});
		st.initStyle(StageStyle.UNDECORATED);
		st.setScene(new Scene(root));
		st.show();
		table.getScene().getWindow().hide();
	}
	
	@FXML
	public void pageDashboard() throws Exception {
		Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				xOffset = e.getSceneX();
				yOffset = e.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				st.setX(e.getScreenX()-xOffset);
				st.setY(e.getScreenY()-yOffset);
			}
		});
		st.initStyle(StageStyle.UNDECORATED);
		st.setScene(new Scene(root));
		st.show();
		table.getScene().getWindow().hide();
	}
	
	
	@FXML
	public void showMerited() throws Exception {
		Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Merited.fxml"));
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				xOffset = e.getSceneX();
				yOffset = e.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				st.setX(e.getScreenX()-xOffset);
				st.setY(e.getScreenY()-yOffset);
			}
		});
		st.initModality(Modality.APPLICATION_MODAL);
		st.initStyle(StageStyle.UNDECORATED);
		st.setScene(new Scene(root));
		st.show();
		table.getScene().getWindow().hide();
	}
	
	@FXML
    public void addAction(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("fxml/Add_note.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Student");
        //stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
	
	@FXML
    public void editAction(ActionEvent event) throws Exception {
		
		String matiere = table.getSelectionModel().getSelectedItems().get(0).getMatiere();
		String id = idEtud.getSelectionModel().getSelectedItem();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Edit_note.fxml"));
        Parent root = loader.load();
        EditNoteController controller = (EditNoteController) loader.getController();
        controller.setData(id, matiere);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Student");
        //stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
	
	@FXML
    public void deleteAction(ActionEvent event) throws Exception {
        btnSupprimerNote();
    }
	
	
	/**
     * Get All Student Id
     */
    private void getIdStudent(){
      ResultSet res = null;
      try {
           String query = "SELECT * FROM etudiants";
           PreparedStatement state = DevsConnexion.getInstance().prepareStatement(query);
           //On exécute la requête
           res = state.executeQuery();
           while(res.next()){
               idEtud.getItems().addAll(res.getString("id_etudiant"));
           }
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage());
       }
      
   }
    
    
    /**
     * Obtenir le nom de l'étudiant en question
     */
   private void getNomStudent(){
       query = "SELECT nom_etud,prenom_etud FROM etudiants WHERE id_etudiant=?";
       try {
           PreparedStatement prepare = DevsConnexion.getInstance().prepareStatement(query);
           prepare.setString(1, idEtud.getSelectionModel().getSelectedItem().toString());
           ResultSet res = prepare.executeQuery();
           res.first();
           nomEtud.setText(res.getString("nom_etud")+" "+res.getString("prenom_etud"));
       } catch (SQLException ex) {
    	   Alert e = new Alert(AlertType.ERROR);
    	   e.setHeaderText(ex.toString());
    	   e.show();
       }
   }
   
	
   private void loadColumn() {
		matiereColumn.setCellValueFactory(new PropertyValueFactory<>("Matiere"));
		coefficientColumn.setCellValueFactory(new PropertyValueFactory<>("Coefficient"));
		id2Column.setCellValueFactory(new PropertyValueFactory<>("id2"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
	}
   
   @SuppressWarnings("unchecked")
	public void initTableNote(){
	       
	       query = "SELECT libelle_mat,coef_mat,note_obtenu FROM notes "
	               + "LEFT JOIN etudiants ON id_etudiant=notes.id_etud "
	               + "LEFT JOIN matieres ON id_matiere=notes.id_mat WHERE id_etud='"+idEtud.getSelectionModel().getSelectedItem()+"'";
	           
	       try {
	       //On crée un statement$
	       Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	       
	       //On exécute la requête
	       ResultSet res = state.executeQuery(query);
	       
	       ResultSetMetaData meta = res.getMetaData();
	   	
			// data
	       ObservableList<Person> person = FXCollections.observableArrayList();
	       
	       for(int i = 1 ; i <= meta.getColumnCount(); i++){
				table.getColumns().setAll(matiereColumn,coefficientColumn,id2Column,ageColumn);
	   		}
	    
	       while(res.next()){	
	    	   String nom = res.getString("libelle_mat");
	           int prenom = res.getInt("coef_mat");
	           String naissance = res.getString("note_obtenu");
	           int id2 = res.getInt("coef_mat")*res.getInt("note_obtenu");
	           person.add(new Person(nom,prenom,naissance,id2));
	       }
	       table.getItems().setAll(person);
	       
	       
	       if (table.getItems().size() == 0){
	           moyenne.setText("");
	           observation.setText("");
	           //JOptionPane.showMessageDialog(null, "Aucune note atribuée à cet étudiant");
	           JFXDialogLayout dialogLayout = new JFXDialogLayout();
               JFXButton button = new JFXButton("Okay");
               JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
               button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
               	root.setEffect(blur);
               	dialog.close();
               });
               button.getStyleClass().add("dialog-button");
               
               dialogLayout.setHeading(new Label("Information"));
               dialogLayout.setBody(new Text("Aucune note atribuée à cet étudiant"));
               dialogLayout.setActions(button);
               dialog.show();
               dialog.setOnDialogClosed((JFXDialogEvent event1)->{
               	root.setEffect(null);
               });
	       }else{
	           double total=0;
	           int coefficient=0;
	           double moyenneEtud; 
	           
	           for(int i=0; i < table.getItems().size();i++){
	               //coefficient = coefficient + (int) table.getValueAt(i, 1);
	        	   coefficient = coefficient + (int) table.getItems().get(i).getCoefficient();
	               total = total + (int) table.getItems().get(i).getAge();     
	           }
	           
	           moyenneEtud = total/coefficient;
	           
	           moyenne.setText(moyenneEtud + " / 20");
	       
	           if(moyenneEtud >= 10){
	               observation.setText("Admis");
	           }else if(moyenneEtud < 10 && moyenneEtud >= 7.5 ){
	               observation.setText("Redoublant");
	           } else if(moyenneEtud < 7.5) {
	               observation.setText("Exclus");
	           }else{
	               observation.setText("");
	           }
	       } 
	       
	       res.close();
	       state.close();
	       
	       } catch (SQLException e) {
	       JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	       }  
	}
   
   /**
    * Supprimer un étudiant
    * @param evt 
    */
   private void btnSupprimerNote() {
	   String t = table.getSelectionModel().getSelectedItems().get(0).getMatiere();
       //int i = table.getSelectedRow();
       if(table.getItems().size() != 0){
            if(table.getSelectionModel().getSelectedIndex() != -1){
                 if(JOptionPane.showConfirmDialog(null, "attention!!! êtes-vous sure de"
               + " vouloir supprimer cette note ?","Supprimer note",JOptionPane.YES_NO_OPTION)
               == JOptionPane.OK_OPTION){
                   query = "DELETE FROM notes WHERE id_mat=? AND id_etud=? LIMIT 1";
                   PreparedStatement prepare;
                   try {
                       prepare = DevsConnexion.getInstance().prepareStatement(query);
                       prepare.setString(1, getMatiere(t).getString("id_matiere"));
                       prepare.setString(2, idEtud.getSelectionModel().getSelectedItem().toString());
                       prepare.executeUpdate();
                       //JOptionPane.showMessageDialog(null,"Suppression de note avec success","Suppression",JOptionPane.INFORMATION_MESSAGE);
                       JFXDialogLayout dialogLayout = new JFXDialogLayout();
                       JFXButton button = new JFXButton("Okay");
                       JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                       button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                       	root.setEffect(blur);
                       	dialog.close();
                       });
                       button.getStyleClass().add("dialog-button");
                       
                       //dialogLayout.setHeading(new Label("Success"));
                       dialogLayout.setBody(new Text("Suppression de note avec success"));
                       dialogLayout.setActions(button);
                       dialog.show();
                       dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                       	root.setEffect(null);
                       	initTableNote();
                       });
                   } catch (SQLException ex) {
                       ex.printStackTrace();
                   }
                 }
            }else{
                //JOptionPane.showMessageDialog(null, "Aucune ligne selectionné");
            	JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	root.setEffect(blur);
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                dialogLayout.setHeading(new Label("Warning"));
                dialogLayout.setBody(new Text("Aucune ligne selectionné"));
                dialogLayout.setActions(button);
                dialog.show();
                dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                	root.setEffect(null);
                });
            }
       }else{
           //JOptionPane.showMessageDialog(null, "Aucune donnée");
    	   JFXDialogLayout dialogLayout = new JFXDialogLayout();
           JFXButton button = new JFXButton("Okay");
           JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
           button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
           	root.setEffect(blur);
           	dialog.close();
           });
           button.getStyleClass().add("dialog-button");
           
           dialogLayout.setHeading(new Label("Information"));
           dialogLayout.setBody(new Text("Aucune donnée"));
           dialogLayout.setActions(button);
           dialog.show();
           dialog.setOnDialogClosed((JFXDialogEvent event1)->{
           	root.setEffect(null);
           });
       }
   }
   
   /**
    * Obtenir la matière de l' étudiant
    * @param i
    * @return 
    */
    private ResultSet getMatiere(String i){
      ResultSet res = null;
      try {
           String query = "SELECT id_matiere FROM matieres WHERE libelle_mat=?";
           PreparedStatement state = DevsConnexion.getInstance().prepareStatement(query);
           state.setString(1, i);
           //On exécute la requête
           res = state.executeQuery();
           res.next();
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage());
       }
      
      return res;
   }
   
	@FXML
	private void comboIdItemStateChanged() throws SQLException {
		getNomStudent();
		initTableNote();
		getClasse(getClasseStudent(idEtud.getSelectionModel().getSelectedItem()).getString("classe_etud"));
	}
	
	/**
     * Obtenir la classe de chaque étudiant
     * @param i
     * @return 
     */
     private ResultSet getClasseStudent(String i){
       ResultSet res = null;
       try {
            String query = "SELECT * FROM etudiants WHERE id_etudiant=?";
            PreparedStatement state = DevsConnexion.getInstance().prepareStatement(query);
            state.setString(1, i);
            res = state.executeQuery();
            res.next();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
       
       return res;
    }
     
     /**
      * Obtenir la classe de chaque étudiant
      * @param i
      * @return 
      */
      private void getClasse(String i){
        ResultSet res = null;
        try {
             String query = "SELECT * FROM classes WHERE id_class=?";
             PreparedStatement state = DevsConnexion.getInstance().prepareStatement(query);
             state.setString(1, i);
             res = state.executeQuery();
             res.first();
             classe.setText(res.getString("titre_class"));
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, ex.getMessage());
         }
     }
	
	@FXML
 	private void exit() {
 		Platform.exit();
 		System.exit(0);
 	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getIdStudent();
		loadColumn();
	}
	
	public class Person{
		
		private final SimpleStringProperty matiere;
		private final SimpleIntegerProperty coefficient;
		private final SimpleStringProperty id2;
		private final SimpleIntegerProperty age;
		
		public Person(String matiere, int coefficient, String id2,
				int age) {
			this.matiere = new SimpleStringProperty(matiere);
			this.coefficient = new SimpleIntegerProperty(coefficient);
			this.id2 = new SimpleStringProperty(id2);
			this.age = new SimpleIntegerProperty(age);
		}

		public String getMatiere() {
			return matiere.get();
		}

		public Integer getCoefficient() {
			return coefficient.get();
		}

		public String getId2() {
			return id2.get();
		}

		public Integer getAge() {
			return age.get();
		}
		
	}

}
