/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.JpaP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.transaction.Transactional;

/**
 *
 * @author alexandre and santananas
 */

@Entity


@IdClass(JointId.class)
public class JointCollection implements Comparable<JointCollection>, Serializable{
    
    @Id 
    private String name;
    @Id 
    private String date_reading_day;
    @Id 
    private String date_reading_time;
    @Id
    private String id;
    
    
    public JointCollection(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_reading_day() {
        return date_reading_day;
    }

    public void setDate_reading_day(String date_reading_day) {
        this.date_reading_day = date_reading_day;
    }

    public String getDate_reading_time() {
        return date_reading_time;
    }

    public void setDate_reading_time(String date_reading_time) {
        this.date_reading_time = date_reading_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JointCollection{" + "name=" + name + ", date_reading_day=" + date_reading_day + ", date_reading_time=" + date_reading_time + ", id=" + id + '}';
    }

   
    
    

    @Override
    public int compareTo(JointCollection t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
