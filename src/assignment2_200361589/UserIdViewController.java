/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_200361589;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;   
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author Davinder Kaur
 */
public class UserIdViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
   @FXML
    private Label userId;
    
     public void logInButtonPushed(ActionEvent event) throws IOException
    {
        SceneChanger sc = new SceneChanger();
        
            sc.changeScenes(event, "login.fxml", "Login");
    }
   
    public void loadUserId() throws SQLException
    {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
             //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/" + "gc200361589", "gc200361589", "RUxpI_An__");
            
            //2. create a statement object
            //statement = conn.createStatement();
            statement = (Statement) conn.createStatement();
            //3. createthe SQL query
            resultSet = statement.executeQuery("SELECT userId FROM user;");
            
            //4. create contact objects from each record
            while(resultSet.next())
            {
                
                userId.setText(Integer.toString(resultSet.getInt("userId")));

            }
           
            
          
        }
        
        catch(Exception e){
            System.err.println(e.getMessage());

        }
        finally{
            if(conn != null)
                conn.close();
            if(statement != null)
                statement.close();
            if(resultSet != null)
                resultSet.close();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       try {
           // TODO
           loadUserId();
       } catch (SQLException ex) {
           Logger.getLogger(UserIdViewController.class.getName()).log(Level.SEVERE, null, ex);
       }
       
    }    
    
}
