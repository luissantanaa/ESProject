/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.KafkaP;

import java.io.IOException;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import proj.es.p21.JpaP.JointCollectionRepository;

/**
 *
 * @author alexandre
 */

public class KafkaConsumer {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Producer.class);
    
    
    //automatically send to database
    @Autowired
    JointCollectionRepository jointsRep;
    
     
    @KafkaListener(topics = "", groupId = "") //topico e groupID
    public void consumeJointReadings(String message) throws IOException{
        logger.info(String.format("%s", message));
    }
    
    
    @KafkaListener(topics = "", groupId = "") //topico e groupID
    public void consumeLogs(String message) throws IOException{
        logger.info(String.format("%s", message));
    }
}
