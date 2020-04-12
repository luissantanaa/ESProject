/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proj.es.BodyTracking;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
/**
 *
 * @author santananas
 */
@Entity
public class user_movement implements Serializable {
    
    @Id private String name;
    @Id private String date;
    List<String> positions = new ArrayList();

    public user_movement(String name, String date){
        this.name = name;
        this.date = date;
    }

     public String getDate(){
        return date;
    }
    
    public void setDate(String date){
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
