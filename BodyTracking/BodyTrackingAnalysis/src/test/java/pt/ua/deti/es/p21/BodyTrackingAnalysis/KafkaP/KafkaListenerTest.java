/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

import java.io.IOException;
import org.springframework.stereotype.Service;
import java.lang.*;


/**
 *
 * @author alexandre
 */

public class KafkaListenerTest {

    public int consumeJointReadings(String messageIn) throws IOException{
        
    
        
        String[] joints_divided = messageIn.split(",");
        
        
        
        
        int message=0;
        if (joints_divided.length > 25){
            int numPeople = (int) Math.ceil(joints_divided.length/25);
            
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) && Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) ){
            
            message=1;
        }else if (Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            System.out.println("LEFT ARM UP");
            message=2;
            
        }else if (Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            message=3;
            
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) &&Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            message=4;
        }
        
        
        return message;
    }
    
    
}