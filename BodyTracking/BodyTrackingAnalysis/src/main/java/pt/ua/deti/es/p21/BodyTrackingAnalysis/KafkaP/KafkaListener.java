/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.*;
/**
 *
 * @author alexandre
 */

@Service
public class KafkaListener {
    
    @Autowired 
    KafkaProducer producer;
    
    @org.springframework.kafka.annotation.KafkaListener(topics = "esp21_joints", groupId = "esp21_2") //topico e groupID
    public void consumeJointReadings(JSONObject jsonO) throws IOException{
        
        
        byte[] array = new byte[30]; // length is bounded by 30
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String[] joints_divided;

        System.out.println(jsonO.toString());
        
        String username = (String) jsonO.get("username");
        String date_reading = (String) jsonO.get("date_reading");
        
        String listJoints =  (String) jsonO.get("joints");
        joints_divided = listJoints.split(",");
        
        if (joints_divided.length > 25){
            int numPeople = (int) Math.ceil(joints_divided.length/25);
            producer.sendMessage(" 0 : " + numPeople);
        }else if(Float.parseFloat(joints_divided[20].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) && Float.parseFloat(joints_divided[23].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) ){
            producer.sendMessage("1");
        }else if (Float.parseFloat(joints_divided[20].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            producer.sendMessage("2");
            System.out.print("ARM UP");
        }else if (Float.parseFloat(joints_divided[23].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            producer.sendMessage("3");
            System.out.print("ARM UP");
        }else if(Float.parseFloat(joints_divided[20].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) &&Float.parseFloat(joints_divided[23].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            producer.sendMessage("4");
        }

    }
    
    
}
