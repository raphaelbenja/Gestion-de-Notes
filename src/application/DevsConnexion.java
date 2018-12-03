package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DevsConnexion {
	 //URL de connexion
    private String url = "jdbc:mysql://localhost:3306/projet_2018";
    //Nom du user
    private String user = "root";
    //Mot de passe de l'utilisateur
    private String passwd = "root";
    //Objet Connection
    private static Connection connect;

    //Constructeur privé
    private DevsConnexion(){
      try {
        connect = DriverManager.getConnection(url, user, passwd);
      } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "ERREUR DE LA CONNEXION AU SERVEUR DE LA BASE DE DONNEE", "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
      }
    }

    //Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
     public static Connection getInstance(){
      if(connect == null){
        new DevsConnexion();
      }
      return connect;   
    }   
}
