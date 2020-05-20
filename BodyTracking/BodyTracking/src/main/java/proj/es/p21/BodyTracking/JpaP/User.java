/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.JpaP;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author alexandre
 */

@Entity
public class User implements Serializable{
    
    
    
    private String id;
    
    @Id
    private String username;
    
    private String password;

    

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
    
    public void setPassword(String password){
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
             byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String sha256hex = new String(Hex.encode(hash));
            this.password = sha256hex;
        } catch (NoSuchAlgorithmException ex) {
        }
       
    }

    public String getPassword(){
        return this.password;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    
    
    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", id=" + id + '}';
    }
    
}
