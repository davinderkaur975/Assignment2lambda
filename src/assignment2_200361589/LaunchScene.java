/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_200361589;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Davinder Kaur
 */  
public class LaunchScene extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
     @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        
        Scene scene = new Scene(root);
       
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
