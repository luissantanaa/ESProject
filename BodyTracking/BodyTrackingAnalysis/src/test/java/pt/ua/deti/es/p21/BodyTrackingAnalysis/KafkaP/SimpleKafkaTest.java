package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP.KafkaListener;
/*
@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest()
public class SimpleKafkaTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleKafkaTest.class);

    private static String TOPIC_NAME = "UpdatedBrandEvent";

    @Autowired
    private KafkaProducer kafkaMessageProducerService;
    
    @Autowired
    private KafkaListener kafkaListener;

    private KafkaMessageListenerContainer<String, String> container;

    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);

    @Before
    public void setUp() {
        consumerRecords = new LinkedBlockingQueue<>();

        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);

        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
                "sender", "false", embeddedKafka.getEmbeddedKafka());

        DefaultKafkaConsumerFactory<String, String> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
        
        container.setupMessageListener((kafkaListener.consumeJointReadings(jsonO)) record -> {
            LOGGER.debug("Listened message='{}'", record.toString());
            consumerRecords.add(record);
        });
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        container.stop();
    }

    @Test
    public void it_should_send_updated_brand_event() throws InterruptedException, IOException {
        
        String data1 =
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "70.8709,345.8029;70.238,448.2417;140.7686,455.4677;166.7444,452.1386;72.36,449.5869;72.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;72.5313,428.2726;72.7042,338.5558;70.8891,341.9003;70.2916,448.1301;200.7179,445.6667;"
                        + "192.8333";
        
        kafkaMessageProducerService.sendMessage(data1);

        ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString( data1 );

        assertThat(received, hasValue(json));

        assertThat(received).has(key(null));
    }

}*/