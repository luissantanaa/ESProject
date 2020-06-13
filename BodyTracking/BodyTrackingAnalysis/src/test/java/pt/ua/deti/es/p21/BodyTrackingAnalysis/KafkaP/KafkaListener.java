package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP.*;
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
import org.springframework.kafka.core.KafkaTemplate;
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
    
    
    static final Logger logger = LogManager.getLogger(KafkaListener.class);
    
    
    @Autowired
    private KafkaTemplate<String,String> ktemplate;
    
    @Autowired 
    KafkaProducer producer;
    
    
    @Autowired
    UsersReadingsRepository usersReadingRep;
    
    
    //@org.springframework.kafka.annotation.KafkaListener(topics = "esp21_joints", groupId = "esp21_2") //topico e groupID
    public int consumeJointReadings(String message) throws IOException, JSONException{
        
        
        List<UserReading> list_users_reads = usersReadingRep.findAll();
        
        
        
        JSONObject jsonO = new JSONObject(message);

        JSONObject elk_OBJ;

        
        byte[] array = new byte[30]; // length is bounded by 30
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String[] joints_divided;

        System.out.println("Listener reading");
        
        String username = (String) jsonO.get("username");
        String date_reading = (String) jsonO.get("date_reading");
        
        String listJoints =  (String) jsonO.get("joints");
        if("".equals(listJoints))
            return 0;
        
        joints_divided = listJoints.split(",");
        
        
        
        if (!usersReadingRep.existsById(username)){
            
            UserReading userRead = new UserReading();
            userRead.setUsername_reading(username);
            userRead.setId_username_reading(list_users_reads.size());
            usersReadingRep.save(userRead);
        }
        
        
        elk_OBJ = new JSONObject();
        
        System.out.println("Alarm detection");
        if (joints_divided.length > 25){
            int numPeople = (int) Math.ceil(joints_divided.length/25);
            
            elk_OBJ.put("alarm", "NUM_PEOPLE");
            
            elk_OBJ.put("nump", numPeople);
            logger.info(elk_OBJ.toString());
        
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) && Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) ){
            elk_OBJ.put("alarm", "BOTH_ARMS");
            logger.info(elk_OBJ.toString());
        
        }else if (Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            
            elk_OBJ.put("alarm", "LEFT_ARM");
            
            
            logger.info(elk_OBJ.toString());
        
        }else if (Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            
            elk_OBJ.put("alarm", "RIGHT_ARM");
            logger.info(elk_OBJ.toString());
        
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) &&Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            elk_OBJ.put("alarm", "HANDSTAND");
            logger.info(elk_OBJ.toString());
        
        }
        
        
        
        return 1;
    }
    
    //@org.springframework.kafka.annotation.KafkaListener(topics = "esp21_joints", groupId = "esp21_3") //topico e groupID
    public int consumeJointReadingsT(String message) throws IOException, JSONException{
        
        
        List<UserReading> list_users_reads = usersReadingRep.findAll();
        
        
        
        JSONObject jsonO = new JSONObject(message);

        JSONObject elk_OBJ;

        
        byte[] array = new byte[30]; // length is bounded by 30
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String[] joints_divided;

        System.out.println("Listener reading");
        
        String username = (String) jsonO.get("username");
        String date_reading = (String) jsonO.get("date_reading");
        
        String listJoints =  (String) jsonO.get("joints");
        if("".equals(listJoints))
            return 0;
        
        joints_divided = listJoints.split(",");
        
        
        
        if (!usersReadingRep.existsById(username)){
            
            UserReading userRead = new UserReading();
            userRead.setUsername_reading(username);
            userRead.setId_username_reading(list_users_reads.size());
            usersReadingRep.save(userRead);
        }
        
        
        elk_OBJ = new JSONObject();
        if (joints_divided.length > 25){
            int numPeople = (int) Math.ceil(joints_divided.length/25);
            
            elk_OBJ.put("alarm", "NUM_PEOPLE");
            
            elk_OBJ.put("nump", numPeople);
        
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) && Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1]) ){
            elk_OBJ.put("alarm", "BOTH_ARMS");
            
        }else if (Float.parseFloat(joints_divided[6].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            
            elk_OBJ.put("alarm", "LEFT_ARM");
            
            
        }else if (Float.parseFloat(joints_divided[10].split(";")[1]) < Float.parseFloat(joints_divided[3].split(";")[1])){
            
            elk_OBJ.put("alarm", "RIGHT_ARM");
            
        
        }else if(Float.parseFloat(joints_divided[6].split(";")[1]) > Float.parseFloat(joints_divided[3].split(";")[1]) &&Float.parseFloat(joints_divided[10].split(";")[1]) > Float.parseFloat(joints_divided[3].split(";")[1])){
            elk_OBJ.put("alarm", "HANDSTAND");
        
        }
        
        
        ktemplate.send("esp21_alarms",elk_OBJ.toString());
        
        
        //return elk_OBJ.toString();
        return 1;
    }
}