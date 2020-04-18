/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.JpaP;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author alexandre
 */

@Entity

@IdClass(pairId.class)
public class PairXY implements Serializable{
    
    
    @Id
    private String id_pair;
    @Id
    private float x;
    @Id
    private float y;
    
    public PairXY(){}
    
    public PairXY(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    
    public String getId() {
        return id_pair;
    }

    public void setId(String id) {
        this.id_pair= id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }


    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PairXY{" + "x=" + x + ", y=" + y + '}';
    }
    
}
