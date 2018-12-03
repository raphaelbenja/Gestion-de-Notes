package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController implements Initializable{

	double xOffset=0,yOffset=0;
	String query;
	
	@FXML
	private Pane p;
	@FXML
	private Pane logout;
	
	@FXML
	private Pane p1;
	@FXML
	private Pane p2;
	@FXML
	private Pane p3;
	@FXML
	private Pane p4;
	@FXML
	private Pane p5;
	@FXML
	private Pane p6;
	
	@FXML
	private Text totalStudent;
	@FXML
	private Text totalMatiere;
	@FXML
	private Text totalNote;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTotalStudent();
		loadTotalMatiere();
		loadTotalNote();
	}
	
	private void loadTotalStudent() {
		query = "SELECT * FROM etudiants";
		try {
            
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = state.executeQuery(query);
            res.last();
            int rowCount = res.getRow();
            totalStudent.setText(""+rowCount);
            res.beforeFirst();
            res.close();
            state.close();
            
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
         }
	}
	
	private void loadTotalMatiere() {
		query = "SELECT * FROM matieres";
		try {        
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = state.executeQuery(query);
            res.last();
            int rowCount = res.getRow();
            totalMatiere.setText(""+rowCount);
            res.beforeFirst();
            res.close();
            state.close();
            
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
         }
	}
	
	private void loadTotalNote() {
		query = "SELECT * FROM notes";
		try {        
            Statement state = DevsConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = state.executeQuery(query);
            res.last();
            int rowCount = res.getRow();
            totalNote.setText(""+rowCount);
            res.beforeFirst();
            res.close();
            state.close();
            
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
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
		p.getScene().getWindow().hide();
	}
	
	/**
     * Navigation
     * @param pane 
     */
    /*private void setColor(Pane pane)
    {
        pane.setBackground(new Color(41,57,80));
    }*/
    
    /**
     * Navigation
     * @param pane 
     */
    /*private void resetColor(Pane [] pane)
    {
        for(int i=0;i<pane.length;i++){
           //pane[i].setBackground(new Color(40,43,48));
           
        } 
    }*/
    
    @FXML
    private void pageStudent() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Student.fxml"));
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
		p.getScene().getWindow().hide();
    }
	
    @FXML
    private void pageMatiere() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Matiere.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageNote() throws Exception {
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageProfessor() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Professor.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageDepartment() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Department.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageClasse() throws Exception {
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageProfile() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Profile.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageProject() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Project.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageAbout() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/About.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageContact() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Contact.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageTeam() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Team.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
    @FXML
    private void pageSponsorship() throws Exception {
    	Stage st = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Sponsorship.fxml"));
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
		p.getScene().getWindow().hide();
    }
    
	@FXML
	private void exit() {
		Platform.exit();
		System.exit(0);
	}
}