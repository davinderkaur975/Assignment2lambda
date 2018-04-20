/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_200361589;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Davinder Kaur
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private byte[] salt;

    public User(String userName, String password) throws NoSuchAlgorithmException {
        setUserName(userName);
        salt = PasswordGenerator.getSalt();
        this.password = PasswordGenerator.getPW(password, salt);
    }

  
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }
    
     /**
     * This method will write the instance of the user into the database
     */
    public void insertIntoDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try
        {
            //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://aws.computerstudi.es:3306/" + "gc200361589", "gc200361589", "RUxpI_An__");
            
            //2. Create a String that holds the query with ? as user inputs
            String sql = "INSERT INTO user (userName, password, salt)"
                    + "VALUES (?,?,?)";
                    
            //3. prepare the query
            preparedStatement = conn.prepareStatement(sql);
                   
            //4. Bind the values to the parameters
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setBlob(3, new javax.sql.rowset.serial.SerialBlob(salt));

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (preparedStatement != null)
                preparedStatement.close();
            
            if (conn != null)
                conn.close();
        }
    }
}
