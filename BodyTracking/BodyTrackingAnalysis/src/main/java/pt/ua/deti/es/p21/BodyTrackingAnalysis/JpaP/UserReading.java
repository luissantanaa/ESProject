/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author alexandre
 */

@Entity
public class UserReading implements Serializable{
    
    @Id
    private String username_reading;
    
    
    private int id_username_reading;

    public String getUsername_reading() {
        return username_reading;
    }

    public void setUsername_reading(String username_reading) {
        this.username_reading = username_reading;
    }

    public int getId_username_reading() {
        return id_username_reading;
    }

    public void setId_username_reading(int id_username_reading) {
        this.id_username_reading = id_username_reading;
    }
    

    
    
    @Override
    public String toString() {
        return "User{" + "username_reading=" + username_reading + '}';
    }

    
}
