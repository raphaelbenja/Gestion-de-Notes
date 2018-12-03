package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXComboBox;
import com.mysql.jdbc.ResultSetMetaData;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MeritedController implements Initializable{

	String query = "";
	double xOffset,yOffset;
	
	@FXML
	private PieChart pie;
	
	@FXML
	private JFXComboBox<String> classe;
	@FXML
	private TableView<Person> table;
	@FXML
	private TableColumn<Person, String> matiereColumn;
	@FXML
	private TableColumn<Person, String> coefficientColumn;
	@FXML
	private TableColumn<Person, String> id2Column;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 getClasseName();
		 loadColumn();
	}
	
	private void loadColumn() {
		matiereColumn.setCellValueFactory(new PropertyValueFactory<>("Matiere"));
		coefficientColumn.setCellValueFactory(new PropertyValueFactory<>("Coefficient"));
		id2Column.setCellValueFactory(new PropertyValueFactory<>("id2"));
	}
	
	/**
     * Obtenir la coefficient du matière
     * @param idMat
     * @return 
     */
    public ResultSet getCoefficientMat(int idMat){
        query = "SELECT coef_mat FROM matieres WHERE id_matiere='"+idMat+"'";
        ResultSet res = null;
        try {
            Statement state = DevsConnexion.getInstance().createStatement();
            res =  state.executeQuery(query);
            res.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion");
        }
        return res;
    }
    
    /**
     * Obtenir les Notes par ordre de merite
     * @param idStudent
     * @return 
     */
    private double getNoteStudent(int idStudent){
        
        double total = 0;
        double totalFin = 0;
        int coefficient = 0;
        double moyenneEtud = 0;
        
        query = "SELECT * FROM notes WHERE id_etud='"+idStudent+"'";
        try {
            Statement state = DevsConnexion.getInstance().createStatement();
            ResultSet res = state.executeQuery(query);
            while (res.next()) {
                total = res.getInt("note_obtenu") * (getCoefficientMat(res.getInt("id_mat")).getInt("coef_mat"));
                totalFin = totalFin + total;
                coefficient = coefficient + getCoefficientMat(res.getInt("id_mat")).getInt("coef_mat");
            }
            moyenneEtud = totalFin / coefficient;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion");
        }
        
        return moyenneEtud;
    }
    
    @SuppressWarnings("unchecked")
	@FXML
	private void loadNoteMerited() {
		
		/*@SuppressWarnings({ "rawtypes" })
		JFXTreeTableColumn<Person, String> id = new JFXTreeTableColumn("Numéro");
		id.setPrefWidth(240);
		id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
				return person.getValue().getValue().id;
			}
		});
		
		@SuppressWarnings({ "rawtypes" })
		JFXTreeTableColumn<Person, String> name = new JFXTreeTableColumn("Nom");
		name.setPrefWidth(240);
		name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
				return person.getValue().getValue().libelle;
			}
		});
		
		@SuppressWarnings({ "rawtypes" })
		JFXTreeTableColumn<Person, String> first_name = new JFXTreeTableColumn("Moyenne");
		first_name.setPrefWidth(240);
		first_name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Person,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Person, String> person) {
				return person.getValue().getValue().coefficient;
			}
		});*/
		
		// data
		ObservableList<Person> person = FXCollections.observableArrayList();
		
		
		 query = "SELECT * FROM etudiants WHERE classe_etud='"+(classe.getSelectionModel().getSelectedIndex()+1)+"'";
	        try {
	            Statement state = DevsConnexion.getInstance().createStatement();
	            ResultSet res = state.executeQuery(query);
	            ResultSetMetaData meta = (ResultSetMetaData) res.getMetaData();
	            
	            for(int i = 1 ; i <= meta.getColumnCount(); i++){
	            	table.getColumns().setAll(matiereColumn,id2Column,coefficientColumn);
	        	}
	            
	          /*Petite manipulation pour obtenir le nombre de lignes
	            res.last();
	            int row = res.getRow();
	          //On revient au départ
	            res.beforeFirst();*/
	            
	            while(res.next()){
	                double moyenneEt = getNoteStudent(res.getInt("id_etudiant"));
	                
	                if(moyenneEt >= 0){
	                    moyenneEt = getNoteStudent(res.getInt("id_etudiant"));
	                }else{
	                    moyenneEt = 0;
	                }
	                String id = "E00"+res.getInt("id_etudiant");
	                String nom = res.getString("nom_etud")+" "+res.getString("prenom_etud");
	                double moyennet = moyenneEt;
	                
	                person.add(new Person(id,nom,moyennet));
	            }
	            
	            table.getItems().setAll(person);
	            
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	        }
	        
	     // build tree
			/*final TreeItem<Person> root = new RecursiveTreeItem<Person>(person, RecursiveTreeObject::getChildren);
			
			table.setRoot(root);
			table.setShowRoot(false);*/
	}
	
   
   /**
    * Get All Classe name
    */
   private void getClasseName(){
     ResultSet res;
     try {
          query = "SELECT * FROM classes";
          PreparedStatement state = DevsConnexion.getInstance().prepareStatement(query);
          res = state.executeQuery();
          while(res.next()){
              classe.getItems().add(res.getString("titre_class"));
          }
          classe.getSelectionModel().select(13);
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage());
      }
     
  }
   
   @FXML
   private void closeCurrentWindow() {
   	table.getScene().getWindow().hide();
   }
   
   @FXML
   private void printResult() {
	   /*MessageFormat header = new MessageFormat("Notes des Etudiants "+classe.getSelectionModel().getSelectedItem());
       try{
    	   Connection con = DriverManager.getConnection("jdbc:mysql://localhost/projet_2018","root","root");
    	   JasperReport JAsp=JasperCompileManager.compileReport("C:\\Users\\Kurosaki\\eclipse-workspace\\Devs\\src\\report1.jrxml");
           JasperPrint jp = JasperFillManager.fillReport(JAsp, null, con);
           JasperViewer.viewReport(jp,false);
           //table.print(jfxtr.PrintMode.FIT_WIDTH,header,footer);
       }catch(Exception e){
           JOptionPane.showInternalMessageDialog(null,"Erreur d'impression"+e);
       }*/
	   
       Printer printer = Printer.getDefaultPrinter();
       PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
       PrinterJob job = PrinterJob.createPrinterJob();
       if (job != null) {
           //boolean successPrintDialog = job.showPrintDialog(primaryStage.getOwner());
           //if(successPrintDialog){
    	   	   table.setScaleX(0.60);
    	   	   table.setScaleY(0.60);
    	   	   table.setTranslateX(-220);
    	   	   table.setTranslateY(-70);
               boolean success = job.printPage(pageLayout,table);
               if (success) {
                   job.endJob();
               }
               table.setTranslateX(0);
               table.setTranslateY(0);               
               table.setScaleX(1.0);
               table.setScaleY(1.0);
           //}
       }
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
	public void pageMerited() throws Exception {
		Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Note.fxml"));
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
   public void loadChart() {
	   ObservableList<PieChart.Data> details = FXCollections.observableArrayList();
	   double note;        
	   int admis =0,redouble=0,exclu = 0;
	   for(int i=0;i<table.getItems().size();i++){
           note = table.getItems().get(i).getCoefficient();
           if(note >= 10){
        	   admis = 0 + i;
           }else if(note < 10 && note >= 7.5){
        	   redouble = 0 + i;
           }else if(note < 7.5){
        	   exclu = 0 + i;
           }
       }
	   
	   details.addAll(new PieChart.Data("ADMIS", admis-1));
	   details.addAll(new PieChart.Data("REDOUBLANTS", redouble-1));
	   details.addAll(new PieChart.Data("EXCLUS", exclu-1));
	   
		pie.setTitle("STATS FOR ALL STUDENT "+classe.getSelectionModel().getSelectedItem());
		pie.setLegendSide(Side.BOTTOM);
		pie.setLabelsVisible(true);
		pie.setStartAngle(90);
		//pie.setClockwise(false);
		pie.getData().setAll(details);		
   }
   
   public class Person{
	   
	   	private final SimpleStringProperty matiere;
		private final SimpleStringProperty id2;
		private final SimpleDoubleProperty coefficient;
		
		public Person(String matiere, String id2, double coefficient) {
			this.matiere = new SimpleStringProperty(matiere);
			this.id2 = new SimpleStringProperty(id2);
			this.coefficient = new SimpleDoubleProperty(coefficient);
		}

		public String getMatiere() {
			return matiere.get();
		}

		public String getId2() {
			return id2.get();
		}
		
		public double getCoefficient() {
			return coefficient.get();
		}
	}

}
