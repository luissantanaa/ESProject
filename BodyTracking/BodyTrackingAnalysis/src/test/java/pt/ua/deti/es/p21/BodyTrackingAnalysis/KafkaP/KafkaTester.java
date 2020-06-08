/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP.KafkaProducer;

//import org.hamcrest.CoreMatchers.equalTo;
//import org.hamcrest.MatcherAssert.assertThat;


/**
 *
 * @author joao
 */

//@RunWith(SpringRunner.class)
//@DirtiesContext
@SpringBootTest()
@EmbeddedKafka//(controlledShutdown = true, topics = "esp21_joints")
public class KafkaTester {
    
    @Autowired
    private KafkaListener receiver;

    @Autowired
    private KafkaProducer sender;

    private static String TOPIC_NAME = "esp21_joints";
    
    @Autowired
    public KafkaTemplate<String, String> template;

    //FIXME: everything below here is a fix for the IDE - else @EmbeddedKafka should be enough
    @Autowired
    public EmbeddedKafkaRule kafkaEmbedded;

    @ClassRule
    public EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);

    //BlockingQueue<ConsumerRecord<String, String>> records;

    //KafkaMessageListenerContainer<String, String> container;
    
    /*@Before
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "false", embeddedKafka.getEmbeddedKafka()));
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer());
        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
    }

    @After
    void tearDown() {
        container.stop();
    }*/
    
    @Before
    public void initTest(){
        receiver = new KafkaListener();
        sender = new KafkaProducer();
    }
    
    @Test
    public void testReceive() throws Exception {
        System.out.println("Test of receiving joints starting!");
        String data1 =
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "70.8709,345.8029;70.238,448.2417;140.7686,455.4677;166.7444,452.1386;72.36,449.5869;72.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;72.5313,428.2726;72.7042,338.5558;70.8891,341.9003;70.2916,448.1301;200.7179,445.6667;"
                        + "192.8333";
        
        template.send(TOPIC_NAME, "Sending with default template");
        sender.sendMessage(data1);
        
        JSONObject json = new JSONObject(data1);

        receiver.consumeJointReadings(json);
        
        assertThat(receiver).isEqualTo(1);
        //System.out.println("Test 1 Successful\n");
    }
    /*
    @Test
    public void testSend() throws Exception {
        sender.send(topic, "Sending with own controller");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount(), equalTo(0L));
    }*/
}
