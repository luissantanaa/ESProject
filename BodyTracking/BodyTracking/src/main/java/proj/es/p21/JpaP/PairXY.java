/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.JpaP;

/**
 *
 * @author alexandre
 */
public class PairXY {
    private final float x;
    private final float y;
    
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
