/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_200361589;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Davinder Kaur
 */
public class PasswordGenerator {
    /**
     * This will create a random salt consisting of 16 bytes
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom rng = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        rng.nextBytes(salt);
        return salt;
    }
    
    /**
     * This will hash a password with a given salt and return it as a String
     */
    public static String getPW(String pw, byte[] salt)
    {
        String hashedPW = null;
        
        try
        {
            //configure the hashing algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            
            byte[] hashed = md.digest(pw.getBytes());
            
            StringBuilder sb = new StringBuilder();
            
            for (int i=0; i<hashed.length; i++)
            {
                sb.append(Integer.toString((hashed[i] & 0xff)+0x100,16).substring(1));
            }
            
           
            hashedPW = sb.toString();
          

        }
        catch (NoSuchAlgorithmException e)
        {
           System.err.println(e);
        }
        
        return hashedPW;
    }
}
