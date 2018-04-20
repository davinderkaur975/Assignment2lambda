/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_200361589;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Davinder Kaur
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField userIdTextField;
    @FXML private PasswordField pwField;
    @FXML private Label errMsgLabel;
    
    /**
     * This method change scene to registration View
     */
    public void registerButtonPushed(ActionEvent event) throws IOException
    {
        SceneChanger sc = new SceneChanger();
        
            sc.changeScenes(event, "UserView.fxml", "Register");
    }
    
    /**
     * This method login the user and takes the user to another view
     * @param event
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    
      public void loginButtonPushed(ActionEvent event) throws IOException, NoSuchAlgorithmException
    {
        //query the database with the userID provided, get the salt
        //and encrypted password stored in the database
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        
        //use of lambda expression to convert the string
        Function<String, Integer> converter = (stringToConvert) -> Integer.parseInt(stringToConvert);
        int userNum = converter.apply(userIdTextField.getText());
        
        
        try{
            //1.  connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/" + "gc200361589", "gc200361589", "RUxpI_An__");
            
            //2.  create a query string with ? used instead of the values given by the user
            String sql = "SELECT * FROM user WHERE userId = ?";
            
            //3.  prepare the statement
            ps = conn.prepareStatement(sql);
            
            //4.  bind the volunteerID to the ?
            ps.setInt(1, userNum);
            
            //5. execute the query
            resultSet = ps.executeQuery();
            
            //6.  extract the password and salt from the resultSet
            String dbPassword=null;
            byte[] salt = null;
            
            User user = null;
            
            while (resultSet.next())
            {
                dbPassword = resultSet.getString("password");
                
                Blob blob = resultSet.getBlob("salt");
                
                //convert into a byte array
                int blobLength = (int) blob.length();
                salt = blob.getBytes(1, blobLength);
                
                user = new User(resultSet.getString("userName"),
                                resultSet.getString("password"));
                
                user.setUserId(resultSet.getInt("userId"));
                
                 
            }
            
            //convert the password given by the user into an encryted password
            //using the salt from the database
            String userPW = PasswordGenerator.getPW(pwField.getText(), salt);
            
            SceneChanger sc = new SceneChanger();
            
            
            //if the passwords match - change to the VolunteerTableView
            if (userPW.equals(dbPassword))
                sc.changeScenes(event, "CarTableView.fxml", "All Cars");
            else
                //if the do not match, update the error message
                errMsgLabel.setText("The userID and password do not match");
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        
    }
      
   
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
