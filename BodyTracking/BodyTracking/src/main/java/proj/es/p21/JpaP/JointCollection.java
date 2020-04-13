/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.JpaP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

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
    private String date_reading;
    List<PairXY> positions = new ArrayList();

    public JointCollection(){}
    
    
    public JointCollection(String name, String date){
        this.name = name;
        this.date_reading = date;
    }

     public String getDate(){
        return date_reading;
    }
    
    public void setDate(String date){
        this.date_reading = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setPositions(List<PairXY> positions){
        this.positions = positions;
    }
    
    public List<PairXY> getPositions(){
        return this.positions;
    }

    @Override
    public int compareTo(JointCollection t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
