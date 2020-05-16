/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.KafkaTestP;
import java.io.IOException;
/**
 *
 * @author alexandre
 */


public class KafkaConsumerTest {
    
    
    public String consumeAlarms(String message) throws IOException{
        
        System.out.println("ALARMS " +  message);
        String  messageToSend;
        if(message.contains(":")){
            messageToSend = "Sensor are detecting " + message.split(":")[1] + " persons!" ;
        }else if(message.equals("1")){
            messageToSend = "Both Arms UP!";
        }else if(message.equals("2")){
            messageToSend="Left Arm UP!";
        }else if(message.equals("3")){
            messageToSend="Right Arm UP!";
        }else if(message.equals("4")){
            messageToSend="Handstand";
        }else{
            messageToSend="Cannot discriminate position!";

        }
        
        return messageToSend;
        
    }
}
