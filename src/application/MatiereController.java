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
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.mysql.jdbc.ResultSetMetaData;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MatiereController implements Initializable{

	private String query;
	double xOffset,yOffset;
	@FXML
	JFXTreeTableView<Person> table;
	@FXML
	JFXTextField txtSearch;
	@FXML
	private StackPane rootPane;
	@FXML
	private FlowPane root;
	
	BoxBlur blur = new BoxBlur(3,3,3);
	
	@FXML
	public void refreshData() {
		loadMatiere();
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
    public void addAction() throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("fxml/Add_matiere.fxml"));
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
    public void editAction() throws Exception {
		String i = table.getSelectionModel().getSelectedItems().get(0).valueProperty().get().getId();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Edit_matiere.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("Edit_matiere.fxml"));
		Parent root = loader.load();
		EditMatiereController controller = (EditMatiereController) loader.getController();
        controller.getInfoMatiere(i);
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
    public void deleteAction() throws Exception {
		ObservableList<TreeItem<Person>> select;
        select = table.getSelectionModel().getSelectedItems();
       
        //if(table.getItems().size() != 0){
             if(table.getSelectionModel().getSelectedIndex() != -1){
                  if(JOptionPane.showConfirmDialog(null, "attention!!! êtes-vous sure de"
                + " vouloir supprimer cette matière ?","Supprimer la matière",JOptionPane.YES_NO_OPTION)
                == JOptionPane.OK_OPTION){
                    String t = select.get(0).valueProperty().get().getId();
                    query = "DELETE FROM matieres WHERE id_matiere=? LIMIT 1";
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
                    
                    //dialogLayout.setHeading(new Label("Warning"));
                    dialogLayout.setBody(new Text("Suppression avec success"));
                    dialogLayout.setActions(button);
                    dialog.show();
                    dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                    	root.setEffect(null);
                    	loadMatiere();
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
        //}else{
            //JOptionPane.showMessageDialog(null, "Aucune donnée");
        //}
    }
	
	@SuppressWarnings("unchecked")
	public void loadMatiere() {
		@SuppressWarnings({ "rawtypes" })
		JFXTreeTableColumn<Person, String> id = new JFXTreeTableColumn("Numéro");
		id.setPrefWidth(240);
		id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
				return person.getValue().getValue().id;
			}
		});
		
		@SuppressWarnings({ "rawtypes" })
		JFXTreeTableColumn<Person, String> name = new JFXTreeTableColumn("Matière");
		name.setPrefWidth(240);
		name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
				return person.getValue().getValue().libelle;
			}
		});
		
		@SuppressWarnings({ "rawtypes" })
		JFXTreeTableColumn<Person, String> first_name = new JFXTreeTableColumn("Coefficient");
		first_name.setPrefWidth(238);
		first_name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
				return person.getValue().getValue().coefficient;
			}
		});
		
		// data
		ObservableList<Person> person = FXCollections.observableArrayList();	
		
		query = "SELECT * FROM matieres";
		
		try {   
			
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            ResultSet res = state.executeQuery(query);
            ResultSetMetaData meta = (ResultSetMetaData) res.getMetaData();
            
            for(int i = 1 ; i <= meta.getColumnCount(); i++){
    			table.getColumns().setAll(id,name,first_name);
        	}
            
            while(res.next()){	
                person.add(new Person(res.getString("id_matiere"),"                    "+res.getString("libelle_mat"),"                    "+res.getString("coef_mat")));
            }
            
            res.close();
            state.close();
            
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
         } 
		
		final TreeItem<Person> root = new RecursiveTreeItem<Person>(person, RecursiveTreeObject::getChildren);
		
		table.setRoot(root);
		table.setShowRoot(false);
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void searchMatiere() {
		try { 
            query = "SELECT * FROM matieres WHERE id_matiere LIKE '%"+txtSearch.getText()+"%' OR libelle_mat LIKE '%"+txtSearch.getText()+"%'";
            Statement st = DevsConnexion.getInstance().createStatement();
            ResultSet res = st.executeQuery(query);

            ResultSetMetaData meta = (ResultSetMetaData) res.getMetaData();
 
            @SuppressWarnings({ "rawtypes" })
    		JFXTreeTableColumn<Person, String> id = new JFXTreeTableColumn("Numéro");
    		id.setPrefWidth(240);
    		id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {

    			@Override
    			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
    				return person.getValue().getValue().id;
    			}
    		});
    		
    		@SuppressWarnings({ "rawtypes" })
    		JFXTreeTableColumn<Person, String> name = new JFXTreeTableColumn("Matière");
    		name.setPrefWidth(240);
    		name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {
    			
    			@Override
    			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
    				return person.getValue().getValue().libelle;
    			}
    		});
    		
    		@SuppressWarnings({ "rawtypes" })
    		JFXTreeTableColumn<Person, String> first_name = new JFXTreeTableColumn("Coefficient");
    		first_name.setPrefWidth(240);
    		first_name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {
    			
    			@Override
    			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
    				return person.getValue().getValue().coefficient;
    			}
    		});
    		
    		// data
    		ObservableList<Person> person = FXCollections.observableArrayList();

            for(int i = 1 ; i <= meta.getColumnCount(); i++){
            	table.getColumns().setAll(id,name,first_name);
            }
            
            res.last();
            int rowCount = res.getRow();
            res.beforeFirst();
            
             while(res.next()){
            	 person.add(new Person(res.getString("id_matiere"),"                    "+res.getString("libelle_mat"),"                    "+res.getString("coef_mat")));
                 
             }
             
            if (rowCount == 0){
                //JOptionPane.showMessageDialog(null, "Aucune Matière");
            	JFXDialogLayout dialogLayout = new JFXDialogLayout();
                JFXButton button = new JFXButton("Okay");
                JFXDialog dialog = new JFXDialog(rootPane,dialogLayout,JFXDialog.DialogTransition.CENTER);
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->{
                	root.setEffect(blur);
                	dialog.close();
                });
                button.getStyleClass().add("dialog-button");
                
                dialogLayout.setHeading(new Label("Information"));
                dialogLayout.setBody(new Text("Aucune Matière"));
                dialogLayout.setActions(button);
                dialog.show();
                dialog.setOnDialogClosed((JFXDialogEvent event1)->{
                	root.setEffect(null);
                	loadMatiere();
                });
                
            }
            
             table.refresh();
             
             final TreeItem<Person> root = new RecursiveTreeItem<Person>(person, RecursiveTreeObject::getChildren);
     		
     		table.setRoot(root);
     		table.setShowRoot(false);

        }catch (Exception m){
        	JOptionPane.showMessageDialog(null, ""+m.getMessage());
        	System.err.println(m);
        }
		
	}

	@FXML
 	private void exit() {
 		Platform.exit();
 		System.exit(0);
 	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadMatiere();
	}
	
	class Person extends RecursiveTreeObject<Person>{
		
		StringProperty id;
		StringProperty libelle;
		StringProperty coefficient;
		
		public Person(String id,String libelle,String coefficient) {
			this.id = new SimpleStringProperty(id);
			this.libelle = new SimpleStringProperty(libelle);
			this.coefficient = new SimpleStringProperty(coefficient);
		}

		public String getId() {
			return id.get();
		}
	}


}
