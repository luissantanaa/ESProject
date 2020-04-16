/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.JpaP;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author alexandre
 */

@Entity
public class PairXY implements Serializable{
    @Id
    private float x;
    @Id
    private float y;
    
    public PairXY(){}
    
    public PairXY(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    public float getX(){
        return this.x;
    }
    
    public float getY(){
        return this.y;
    }
}
