/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.JpaP;

import java.io.Serializable;

/**
 *
 * @author alexandre
 */
public class pairId implements Serializable{
    
    private float x;
    private float y;
    private String id_pair;
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
     
    
}
