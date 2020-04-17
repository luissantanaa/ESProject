/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.KafkaP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import proj.es.p21.BodyTracking.JpaP.JointCollection;
import proj.es.p21.BodyTracking.JpaP.JointCollectionRepository;
import proj.es.p21.BodyTracking.JpaP.PairXY;

/**
 *
 * @author alexandre
 */

public class KafkaConsumer {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Producer.class);
    
    
    //automatically send to database
    @Autowired
    JointCollectionRepository jointsRep;
    
     
    @KafkaListener(topics = "joints", groupId = "") //topico e groupID
    public void consumeJointReadings(JSONObject jsonO) throws IOException{
        
        
        
        String username = (String) jsonO.get("username");
        String date_reading = (String) jsonO.get("date_reading");
        List<PairXY> jointC = (ArrayList) jsonO.get("joints");
        
        JointCollection JC = new JointCollection();
        
        JC.setName(username);
        JC.setDate_reading_day(date_reading.split("-")[0]);
        JC.setDate_reading_time(date_reading.split("-")[1]);
        JC.setPositions(jointC);
        jointsRep.save(JC);

    }
    
    
    @KafkaListener(topics = "", groupId = "") //topico e groupID
    public void consumeLogs(String message) throws IOException{
        logger.info(String.format("%s", message));
    }
}
