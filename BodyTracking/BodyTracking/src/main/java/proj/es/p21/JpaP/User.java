/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.JpaP;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author alexandre
 */

@Entity
public class User implements Serializable{
    
    @Id 
    private String username;
    
    private String password;

    

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
    
    public void setPass(String password){
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));
        this.password = sha256hex;
    }

    public String getPass(){
        return this.password;
    }
    
}
