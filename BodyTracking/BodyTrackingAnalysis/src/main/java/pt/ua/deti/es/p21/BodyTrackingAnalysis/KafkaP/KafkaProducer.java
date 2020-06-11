/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexandre
 */


@Service
public class KafkaProducer {
 
    
    private static final String TOPIC = "esp21_joints";

    @Autowired
    private KafkaTemplate<String, JSONObject> kafkaTemplate;
    
    public void sendMessage(JSONObject message) {
        //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        //Date date = new Date(System.currentTimeMillis());
        this.kafkaTemplate.send(TOPIC, "joints", message);
    }
    
}
