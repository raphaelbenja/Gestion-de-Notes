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
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
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

public class StudentController implements Initializable{

	private String query;
	double xOffset,yOffset;
	
	@FXML
	private StackPane p;
	
	@FXML
	private JFXTextField txtSearch;
	
	@FXML
	private TableView<Person> tab;
	@FXML
    private TableColumn<Person, String> nomColumn;
    @FXML
    private TableColumn<Person, String> prenomColumn;
    @FXML
    private TableColumn<Person, String> naissanceColumn;
    @FXML
    private TableColumn<Person, String> ageColumn;
    @FXML
    private TableColumn<Person, String> sexeColumn;
    @FXML
    private TableColumn<Person, String> classeColumn;
    @FXML
    private TableColumn<Person, String> id2Column;
    @FXML
	private StackPane rootPane;
	@FXML
	private FlowPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
    
    /*@FXML
    private JFXTextField searchField;
    @FXML
    private JFXButton editButton, deleteButton;*/
	
	/**
     * Obtenir la classe de chaque étudiant
     * @param i
     * @return 
     */
     private ResultSet getClasse(int i){
       ResultSet res = null;
       try {
            String query = "SELECT * FROM classes WHERE id_class=?";
            PreparedStatement state = DevsConnexion.getInstance().prepareStatement(query);
            state.setInt(1, i);
            res = state.executeQuery();
            res.next();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
       
       return res;
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
 		tab.getScene().getWindow().hide();
 	}
     
     @FXML
     public void addAction(ActionEvent event) throws Exception {

         Parent root = FXMLLoader.load(getClass().getResource("fxml/Add_student.fxml"));
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
     
     @SuppressWarnings("unchecked")
	@FXML
     public void searchStudent() {
    	 try { 
             query = "SELECT id_etudiant,nom_etud,prenom_etud,naissance_etud,age_etud,sexe_etud,titre_class FROM etudiants LEFT JOIN classes ON etudiants.classe_etud=classes.id_class WHERE prenom_etud LIKE '%"+txtSearch.getText()+"%' OR nom_etud LIKE '%"
                     +txtSearch.getText()+"%' OR id_etudiant LIKE '"+txtSearch.getText()+"%'";
             Statement st = DevsConnexion.getInstance().createStatement();
             ResultSet res = st.executeQuery(query);

             ResultSetMetaData meta = res.getMetaData();

             for(int i = 1 ; i <= meta.getColumnCount(); i++){
            	 tab.getColumns().setAll(id2Column,nomColumn,prenomColumn,ageColumn,naissanceColumn,sexeColumn,classeColumn);
             }
             
             ObservableList<Person> list = FXCollections.observableArrayList();
              while(res.next()){
            	  
            	String nom = res.getString("nom_etud");
              	String prenom = res.getString("prenom_etud");
              	String naissance = res.getString("naissance_etud");
              	String age = res.getString("age_etud")+" Ans";
              	String sexe = res.getString("sexe_etud");
              	String classe = res.getString("titre_class");
              	String id2 = res.getString("id_etudiant");
              	
              	list.add(new Person(nom,prenom,naissance,age,sexe,classe,id2));   
              }
              
              tab.getItems().setAll(list);

             if (tab.getItems().size() == 0){
                 //JOptionPane.showMessageDialog(null, "Aucun Etudiant");
            	 JFXDialogLayout dialogLayout = new JFXDialogLayout();
                 JFXButton button = new JFXButton("Okay");
                 JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                 button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                 	root.setEffect(blur);
                 	dialog.close();
                 });
                 button.getStyleClass().add("dialog-button");
                 
                 dialogLayout.setHeading(new Label("Information"));
                 dialogLayout.setBody(new Text("Aucun Etudiant trouvée"));
                 dialogLayout.setActions(button);
                 dialog.show();
                 dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                 	root.setEffect(null);
                 	loadStudent();
                 });
             }
             
             tab.refresh();
             
         }catch (Exception m){
        	JOptionPane.showMessageDialog(null, ""+m.getMessage());
         	System.err.println(m);}
     }
     
     @FXML
     public void editAction(ActionEvent event) throws Exception {
    	 String idE = tab.getSelectionModel().getSelectedItems().get(0).getId2();
    	 //ObservableList<Person> id = tab.getSelectionModel().getSelectedItems();
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Edit_student.fxml"));
    	 Parent root = loader.load();
    	 EditStudentController controller = (EditStudentController) loader.getController();
    	 controller.setData(idE);
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
         tab.getSelectionModel().clearSelection();
     }
     
     @FXML
     public void deleteAction(ActionEvent event) throws Exception {

    	 ObservableList<Person> select;
         select = tab.getSelectionModel().getSelectedItems();
        
         if(tab.getItems().size() != 0){
              if(tab.getSelectionModel().getSelectedIndex() != -1){
                   if(JOptionPane.showConfirmDialog(null, "attention!!! êtes-vous sure de"
                 + " vouloir supprimer cet étudiant ?","Supprimer étudiant",JOptionPane.YES_NO_OPTION)
                 == JOptionPane.OK_OPTION){
                     String t = select.get(0).getId2();
                     query = "DELETE FROM etudiants WHERE id_etudiant=? LIMIT 1";
                     PreparedStatement prepare;
                     prepare = DevsConnexion.getInstance().prepareStatement(query);
                     prepare.setString(1, t);
                     prepare.executeUpdate();
                     //JOptionPane.showMessageDialog(null,"Suppression avec success","Suppression",JOptionPane.INFORMATION_MESSAGE);
                     JFXDialogLayout dialogLayout = new JFXDialogLayout();
                     JFXButton button = new JFXButton("Okay");
                     JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                     button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                     	root.setEffect(blur);
                     	dialog.close();
                     });
                     button.getStyleClass().add("dialog-button");
                     
                     //dialogLayout.setHeading(new Label("Login success"));
                     dialogLayout.setBody(new Text("Suppression avec success"));
                     dialogLayout.setActions(button);
                     dialog.show();
                     dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                     	root.setEffect(null);
                     	loadStudent();
                     });              
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
             
             dialogLayout.setHeading(new Label("Warning"));
             dialogLayout.setBody(new Text("Aucune donnée"));
             dialogLayout.setActions(button);
             dialog.show();
             dialog.setOnDialogClosed((JFXDialogEvent event1)->{
             	root.setEffect(null);
             });
         }
     }
	
    @FXML
 	private void exit() {
 		Platform.exit();
 		System.exit(0);
 	}
    
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
		tab.getScene().getWindow().hide();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 loadColumn();
		 loadStudent();
	}
	
	@SuppressWarnings("unchecked")
	public void loadStudent() {
		query = "SELECT * FROM etudiants";
		ObservableList<Person> list = FXCollections.observableArrayList();
		try {
            
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = state.executeQuery(query);
            ResultSetMetaData meta = (ResultSetMetaData) res.getMetaData();
            
            for(int i = 1 ; i <= meta.getColumnCount(); i++){
    			tab.getColumns().setAll(id2Column,nomColumn,prenomColumn,ageColumn,naissanceColumn,sexeColumn,classeColumn);
            }
            
            while(res.next()){	
            	
            	String nom = res.getString("nom_etud");
            	String prenom = res.getString("prenom_etud");
            	String naissance = res.getString("naissance_etud");
            	String age = res.getString("age_etud")+" Ans";
            	String sexe = res.getString("sexe_etud");
            	String classe = getClasse(res.getInt("classe_etud")).getString("titre_class");
            	String id2 = res.getString("id_etudiant");
            	
            	list.add(new Person(nom,prenom,naissance,age,sexe,classe,id2));     
            }
            res.close();
            state.close();
            
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
         } 
		tab.getItems().setAll(list);
	}
	
	@FXML
	public void refreshData() {
		loadStudent();
	}

	private void loadColumn() {
		 nomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		 prenomColumn.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	     naissanceColumn.setCellValueFactory(new PropertyValueFactory<>("Naissance"));
	     ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
	     sexeColumn.setCellValueFactory(new PropertyValueFactory<>("Sexe"));
	     classeColumn.setCellValueFactory(new PropertyValueFactory<>("Classe"));
	     id2Column.setCellValueFactory(new PropertyValueFactory<>("id2"));
	}

	public class Person{
		
		private final SimpleStringProperty nom;
		private final SimpleStringProperty prenom;
		private final SimpleStringProperty naissance;
		private final SimpleStringProperty age;
		private final SimpleStringProperty sexe;
		private final SimpleStringProperty classe;
		private final SimpleStringProperty id2;
		
		public Person(String nom,String prenom,String naissance,String age,String sexe,String classe,String id2) {
			this.nom = new SimpleStringProperty(nom);
			this.prenom = new SimpleStringProperty(prenom);
			this.naissance = new SimpleStringProperty(naissance);
			this.age = new SimpleStringProperty(age);
			this.sexe = new SimpleStringProperty(sexe);
			this.classe = new SimpleStringProperty(classe);
			this.id2 = new SimpleStringProperty(id2);
		}

		public String getNom() {
			return nom.get();
		}

		public String getPrenom() {
			return prenom.get();
		}
		
		public String getNaissance() {
			return naissance.get();
		}

		public String getAge() {
			return age.get();
		}

		public String getSexe() {
			return sexe.get();
		}

		public String getClasse() {
			return classe.get();
		}
		
		public String getId2() {
			return id2.get();
		}
	}

}
