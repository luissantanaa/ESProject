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
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP.UserReading;
import pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP.UsersReadingsRepository;
//import pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP.UserReading;
//import pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP.UsersReadingsRepository;


/**
 *
 * @author alexandre
 */

@Service
public class KafkaListener {
    
    
    private static final Logger logger = LogManager.getLogger(KafkaListener.class);

    
    
    @Autowired 
    KafkaProducer producer;
    
    
    @Autowired
    UsersReadingsRepository usersReadingRep;
    
    
    @org.springframework.kafka.annotation.KafkaListener(topics = "esp21_joints", groupId = "esp21_2") //topico e groupID
    public void consumeJointReadings(JSONObject jsonO) throws IOException, JSONException{
        
        
        List<UserReading> list_users_reads = usersReadingRep.findAll();
        
        
        JSONObject elk_OBJ;

        
        byte[] array = new byte[30]; // length is bounded by 30
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String[] joints_divided;

        System.out.println(jsonO.toString());
        
        String username = (String) jsonO.get("username");
        String date_reading = (String) jsonO.get("date_reading");
        
        String listJoints =  (String) jsonO.get("joints");
        joints_divided = listJoints.split(",");
        
        
        if (!usersReadingRep.existsById(username)){
            
            UserReading userRead = new UserReading();
            userRead.setUsername_reading(username);
            userRead.setId_username_reading(list_users_reads.size());
            usersReadingRep.save(userRead);
        }
        
        
        int message=0;
        elk_OBJ = new JSONObject();
        if (joints_divided.length > 25){
            int numPeople = (int) Math.ceil(joints_divided.length/25);
            producer.sendMessage(" 0 : " + numPeople);
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) && Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) ){
            producer.sendMessage("1");
            logger.debug("BOTH ARMS UP");
        }else if (Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            producer.sendMessage("2");
            System.out.print("ARM UP");
            logger.debug("LEFT ARM UP");
        }else if (Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            producer.sendMessage("3");
            logger.debug("RIGHT ARM UP");
            System.out.print("ARM UP");
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) &&Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            producer.sendMessage("4");
            logger.debug("HANDSTAND");
        }
        
        
    }
    /*
    @org.springframework.kafka.annotation.KafkaListener(topics = "esp21_joints", groupId = "esp21_2") //topico e groupID
    public JSONObject consumeJointReadingsJson(JSONObject jsonO) throws IOException{
        return jsonO;
    }
    */
    
}
