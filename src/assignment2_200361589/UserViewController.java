/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_200361589;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

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
    @FXML private ImageView imageView;
    private File imageFile;
    
    //used for the passwords
    @FXML private PasswordField pwField;
    @FXML private PasswordField confirmPwField;
    
    
     /**
     * This method change scene to the Login View without registering
     */
     public void cancelButtonPushed(ActionEvent event) throws IOException
    {
        SceneChanger sc = new SceneChanger();
        
            sc.changeScenes(event, "Login.fxml", "Login");
    }
    
     /**
     * This method will validate that the passwords is greater than 8 digits or words
     */
     public boolean pwChecker(){
       
       //use of lambda expression to ccheck that the password length is greater than 8
       Predicate<String> pwChecker = (passwordArgument) -> passwordArgument.length() > 8;
       return pwChecker.test(pwField.getText());
     }
     
     /**
     * This method will validate that the password field and confirm password field are same
     */
    public boolean validPassword()
    {
        if (pwField.getText().equals(confirmPwField.getText()))
            return true;
        else
            return false;
        }
        
    
    
    /**
     * This method will read from the scene and try to create a new instance of a User.
     * If a user was successfully created, it is updated in the database.
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
             
                    errMsgLabel.setText("");   
                    user.insertIntoDB();    
                

                SceneChanger sc = new SceneChanger();
                sc.changeScenes(event, "UserIdView.fxml", "User Unique Id");
                
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
         errMsgLabel.setText(""); 
         try{
            imageFile = new File("./src/Image/register.jpg");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            
        }
    }    
    
}
