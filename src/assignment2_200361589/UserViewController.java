/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_200361589;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
public class UserViewController implements Initializable {

    
    /**
     * Initializes the controller class.
     */
    private User user;
    @FXML private TextField userNameTextField;
    @FXML private Label errMsgLabel;
    @FXML private Label headerLabel;
    
    //used for the passwords
    @FXML private PasswordField pwField;
    @FXML private PasswordField confirmPwField;
    
     public void cancelButtonPushed(ActionEvent event) throws IOException
    {
        SceneChanger sc = new SceneChanger();
        
            sc.changeScenes(event, "Login.fxml", "Login");
    }
    
     /**
     * This method will validate that the passwords match
     * 
     */
     public boolean pwChecker(){
       Predicate<String> pwChecker = (passwordArgument) -> passwordArgument.length() > 8;
       return pwChecker.test(pwField.getText());
     }
     
    public boolean validPassword()
    {
        if (pwField.getText().equals(confirmPwField.getText()))
            return true;
        else
            return false;
        }
        
    
    
    /**
     * This method will read from the scene and try to create a new instance of a Volunteer.
     * If a volunteer was successfully created, it is updated in the database.
     */
    public void saveButtonPushed(ActionEvent event) throws NoSuchAlgorithmException, SQLException
    {
        if (validPassword())
        {
            if(pwChecker() == true)
            {
            try
            {
                user = new User(userNameTextField.getText(), 
                                        pwField.getText());
             
                    errMsgLabel.setText("");    //do not show errors if creating Volunteer was successful
                    user.insertIntoDB();    
                

                SceneChanger sc = new SceneChanger();
                sc.changeScenes(event, "UserIdView.fxml", "All Volunteers");
                
            }
            catch (Exception e)
            {
                errMsgLabel.setText(e.getMessage());
            }
            }
            else{
                errMsgLabel.setText("Password must be greater than 8");
            }
        }
        
        else{
            errMsgLabel.setText("Password and confirm password must be similar");
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
