/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KafkaP;

import java.io.IOException;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import rep.LoggerRep;

/**
 *
 * @author alexandre
 */

public class KafkaConsumer {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Producer.class);

    @Autowired
    LoggerRep repLogger;
     
    @KafkaListener(topics = "", groupId = "") //topico e groupID
    public void consume(String message) throws IOException{
        logger.info(String.format("%s", message));
        repLogger.save(new KafkaMessage(message));
    }
}
