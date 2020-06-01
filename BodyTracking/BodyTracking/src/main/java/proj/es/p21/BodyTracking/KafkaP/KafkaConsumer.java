/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.KafkaP;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.hibernate.Hibernate;
import org.json.JSONObject;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import proj.es.p21.BodyTracking.JpaP.JointCollection;
import proj.es.p21.BodyTracking.JpaP.JointCollectionRepository;
import proj.es.p21.BodyTracking.JpaP.PairXY;
import proj.es.p21.BodyTracking.JpaP.PairXYRepository;

/**
 *
 * @author alexandre
 */


@Service
public class KafkaConsumer {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Producer.class);
    
    
    //automatically send to database
    @Autowired
    JointCollectionRepository jointsRep;
    
    
    
    @Autowired
    SimpMessagingTemplate template;    

    @Autowired
    PairXYRepository pairRep;
    
    @KafkaListener(topics = "esp21_joints", groupId = "esp21_1") //topico e groupID
    public void consumeJointReadings(JSONObject jsonO) throws IOException{
        
        
        String generatedString;
        generatedString = String.valueOf((int) new Random().nextInt(50000000));
 

        //System.out.println(jsonO.toString());
        
        String username = (String) jsonO.get("username");
        
        
        String date_reading = (String) jsonO.get("date_reading");
        
        String listJoints =  (String) jsonO.get("joints");
        String[] pos = listJoints.split(",");
        
	HashMap<String,String> toSend = new HashMap<String,String>();	
        toSend.put("username", username);
        toSend.put("JointPos", listJoints);
	    template.convertAndSend("/esp21_topic/movement",toSend);

        JointCollection JC = new JointCollection();
        
        JC.setName(username);
        JC.setDate_reading_day(date_reading.split("-")[0]);
        JC.setDate_reading_time(date_reading.split("-")[1]);
        JC.setId(generatedString);
        
        
        for(String x : pos){
            PairXY tmp_xy = new PairXY();
            //x= x.replace("[", "");
            //x= x.replace("]", "");
            
            String[] xy = x.split(";");
            
            tmp_xy.setX(Float.parseFloat(xy[0]));
            
            tmp_xy.setY(Float.parseFloat(xy[1]));
            
            tmp_xy.setId(generatedString);
            
            pairRep.save(tmp_xy);
        }
        
        
        
        jointsRep.save(JC);
        
        //System.out.println(JC.toString());
        

    }
    
    
    @KafkaListener(topics = "esp21_alarms", groupId = "esp21_1") //topico e groupID
    public void consumeAlarms(JSONObject message) throws IOException{
        System.out.print(message.toString());
        String  messageToSend;
        if(message.get("alarm").equals("NUM_PEOPLE")){
            messageToSend = "Sensor are detecting " + message.get("nump") + " persons!" ;
        }else if(message.get("alarm").equals("BOTH_ARMS")){
            messageToSend = "Both Arms UP!";
        }else if(message.get("alarm").equals("LEFT_ARM")){
            messageToSend="Left Arm UP!";
        }else if(message.get("alarm").equals("RIGHT_ARM")){
            messageToSend="Right Arm UP!";
        }else if(message.get("alarm").equals("HANDSTAND")){
            messageToSend="Handstand";
        }else{
            messageToSend="Cannot discriminate position!";

        }

        HashMap<String,String> toSend = new HashMap<String,String>();	
        toSend.put("alarms", messageToSend);
        template.convertAndSend("/esp21_topic/alarm",toSend);

    }
    
    /*
    
    @KafkaListener(topics = "esp21_logs", groupId = "esp21_1") //topico e groupID
    public void consumeLogs(String message) throws IOException{
        
        System.out.println("LOGS " +  message);
        
    }

    */
}
