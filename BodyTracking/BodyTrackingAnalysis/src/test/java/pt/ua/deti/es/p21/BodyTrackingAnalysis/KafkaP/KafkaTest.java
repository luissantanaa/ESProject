package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

/**
 *
 * @author joao
 */

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.springkafkatest.model.event.UpdatedBrandEvent;
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
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import org.springframework.kafka.test.context.EmbeddedKafka;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP.KafkaProducer;


@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest()
/*@EmbeddedKafka(
    partitions = 1, 
    controlledShutdown = false,
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", 
        "port=9092"
})*/
public class KafkaTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTest.class);

    private static String TOPIC_NAME = "esp21_joints";

    @Autowired
    private KafkaProducer kafkaProducer;

    private KafkaMessageListenerContainer<String, String> container;

    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1,true,TOPIC_NAME);
    
    static EmbeddedKafkaBroker embeddedKafkaBroker = embeddedKafkaRule.getEmbeddedKafka();
    
    //@ClassRule
    //public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TOPIC_NAME);
    
    
    @BeforeClass
    public static void setUpBeforeClass() {
        //System.setProperty("kafka.bootstrapAddress", embeddedKafkaBroker.getBrokersAsString());
        System.setProperty("spring.kafka.bootstrap-servers", embeddedKafkaBroker.getBrokersAsString());
        System.setProperty("spring.cloud.stream.kafka.binder.zkNodes", embeddedKafkaBroker.getZookeeperConnectionString());
    }
    
    
    @Before
    public void setUp() {
        System.out.println("starting setup");
        consumerRecords = new LinkedBlockingQueue<>();

        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("esp21_2", "false", embeddedKafkaBroker);

        DefaultKafkaConsumerFactory<String, String> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        container.setupMessageListener((MessageListener<String, String>) record -> {
            LOGGER.debug("Listened message='{}'", record.toString());
            consumerRecords.add(record);
        });
        container.start();

        //ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        container.stop();
    }

    @Test
    public void it_should_receive_joints() throws InterruptedException, IOException {
        System.out.println("Test of receiving joints starting!");
        String data1 = new String(
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "70.8709,345.8029;70.238,448.2417;140.7686,455.4677;166.7444,452.1386;191.36,449.5869;195.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;271.5313,428.2726;132.7042,338.5558;70.8891,341.9003;70.2916,448.1301;200.7179,445.6667;"
                        + "192.8333;");
        
        //kafkaProducer.sendMessage(data1);

        ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data1);
        System.err.println(json);
        System.err.println(received);
        
        assertThat(received, hasValue(json));

        assertThat(received).has(key(null));
    }
    
    //@Test
    public void it_should_send_alarms() throws InterruptedException, IOException {
        System.out.println("Test of sending alarms starting!");
        String data1 = new String(
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "70.8709,345.8029;70.238,448.2417;140.7686,455.4677;166.7444,452.1386;191.36,449.5869;195.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;271.5313,428.2726;132.7042,338.5558;70.8891,341.9003;70.2916,448.1301;200.7179,445.6667;"
                        + "192.8333;");
        
        kafkaProducer.sendMessage(data1);
    }

}