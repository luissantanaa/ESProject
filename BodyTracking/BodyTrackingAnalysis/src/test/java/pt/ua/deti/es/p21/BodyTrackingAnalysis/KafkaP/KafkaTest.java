package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

/**
 *
 * @author joao e guilherme
 */
//import com.springkafkatest.model.event.UpdatedBrandEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasKey;
import static org.springframework.kafka.test.utils.KafkaTestUtils.getSingleRecord;

import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import static org.springframework.kafka.test.utils.KafkaTestUtils.getRecords;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import static pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP.KafkaTest.TOPIC_JOINTS;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest( // tell Spring Boot Kafka auto-config about the embedded kafka endpoints
        //properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        // slice our unit test app context down to just these specific pieces
        //classes = {
        // ...the service to test
        //    KafkaProducer.class,
        // ...use standard Sprint Boot kafka auto-config to give us KafkaTemplate, etc
        //    KafkaTopicConfig.class,
        // ...and our additional test config
        //KafkaProducerTest.TestConfig.class
        //}
        )
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"},
        //topics = { TOPIC_JOINTS, TOPIC_ALARMS }
        topics = {"esp21_joints"}
)
public class KafkaTest {

    @Autowired
    private KafkaListener receiver;
    
    @Autowired
    private KafkaProducer sender;

    public static String TOPIC_JOINTS = "esp21_joints";

    public static String TOPIC_ALARMS = "esp21_alarms";
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //@Autowired
    //public KafkaTemplate<String, String> template;
    //FIXME: everything below here is a fix for the IDE - else @EmbeddedKafka should be enough
    //@Autowired
    //public EmbeddedKafkaRule kafkaEmbedded;
    //@ClassRule
    //public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    //private KafkaMessageListenerContainer<String, String> container;
    //private BlockingQueue<ConsumerRecord<String, String>> records;

    /*@Before
    public void initTest(){
        receiver = new KafkaListener();
        //sender =  new KafkaProducer();
    }*/
 /*@Before
    public void setUp() throws Exception {
        // set up the Kafka consumer properties
        Map<String, Object> consumerProperties
                = KafkaTestUtils.consumerProps("sender", "false",
                        embeddedKafka.getEmbeddedKafka());

        // create a Kafka consumer factory
        DefaultKafkaConsumerFactory<String, String> consumerFactory
                = new DefaultKafkaConsumerFactory<String, String>(
                        consumerProperties);

        // set the topic that needs to be consumed
        ContainerProperties containerProperties
                = new ContainerProperties(TOPIC_JOINTS);

        // create a Kafka MessageListenerContainer
        container = new KafkaMessageListenerContainer<>(consumerFactory,
                containerProperties);

        // create a thread safe queue to store the received message
        records = new LinkedBlockingQueue<>();

        // setup a Kafka message listener
        container
                .setupMessageListener(new MessageListener<String, String>() {
                    @Override
                    public void onMessage(
                            ConsumerRecord<String, String> record) {
                        //OGGER.debug("test-listener received message='{}'", record.toString());
                        records.add(record);
                    }
                });

        // start the container and underlying message listener
        container.start();

        // wait until the container has the required number of assigned partitions
        ContainerTestUtils.waitForAssignment(container,
                embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
    }*/
    @Test
    public void test() throws Exception {
        System.out.println("Test ok!");
    }

    @Test
    public void testSend() throws Exception {
        System.out.println("Test of receiving joints starting!");
        String data1
                = "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                + "70.8709,345.8029;70.238,448.2417;140.7686,455.4677;166.7444,452.1386;72.36,449.5869;72.57,419.4047;"
                + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                + "265.4389,432.1964;72.5313,428.2726;72.7042,338.5558;70.8891,341.9003;70.2916,448.1301;200.7179,445.6667;"
                + "192.8333";

        //template.send(TOPIC_NAME, "Sending with default template");
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/YY - hh:mm:ss");

        String date = sdf.format(date1);

        String stringJson = "{\"username\": \"coelhoguilherme\", \"date_reading\": \"" + date + "\", \"joints\": \"" + data1 + "\"}";

        this.kafkaTemplate.send(TOPIC_JOINTS, "joints", data1);

        final Consumer<String, String> consumer = buildConsumer(
                StringDeserializer.class,
                StringDeserializer.class
        );

        embeddedKafka.consumeFromEmbeddedTopics(consumer, TOPIC_JOINTS);
        
        System.out.println("all records: "+ getRecords(consumer).count());
        
        final ConsumerRecord<String, String> record = getSingleRecord(consumer, TOPIC_JOINTS, 300);

        System.out.println("record: " + record.toString());
        System.out.println("record: " + record.topic());
        System.out.println("record: " + record);
        System.out.println("record finishedd");
        // Use Hamcrest matchers provided by spring-kafka-test
        // https://docs.spring.io/spring-kafka/docs/2.2.4.RELEASE/reference/#hamcrest-matchers

        assertThat(record, hasKey("joints"));
        //assertThat(record, hasValue("coelhoguilherme"));
    }

    private <K, V> Consumer<K, V> buildConsumer(Class<? extends Deserializer> keyDeserializer,
            Class<? extends Deserializer> valueDeserializer) {
        // Use the procedure documented at https://docs.spring.io/spring-kafka/docs/2.2.4.RELEASE/reference/#embedded-kafka-annotation

        final Map<String, Object> consumerProps = KafkaTestUtils
                .consumerProps("testSend", "true", embeddedKafka);
        // Since we're pre-sending the messages to test for, we need to read from start of topic
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // We need to match the ser/deser used in expected application config
        consumerProps
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer.getName());
        consumerProps
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer.getName());

        final DefaultKafkaConsumerFactory<K, V> consumerFactory
                = new DefaultKafkaConsumerFactory<>(consumerProps);
        return consumerFactory.createConsumer();
    }

    /*@After
    public void tearDown() {
        // stop the container
        container.stop();
    }*/

 /*
    //@Test
    public void both_arms_up_joints() throws InterruptedException, IOException { // BOTH ARMS UP
        System.out.println("Test of receiving joints starting!");
        String data1 =
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "70.8709,345.8029;70.238,448.2417;140.7686,455.4677;166.7444,452.1386;72.36,449.5869;72.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;72.5313,428.2726;72.7042,338.5558;70.8891,341.9003;70.2916,448.1301;200.7179,445.6667;"
                        + "192.8333";
        
        int result = ktest.consumeJointReadings(data1);
        
        assertThat(result).isEqualTo(1);
        
        System.out.println("Test 1 Successful\n");
    
    }
    
    //@Test
    public void left_arm_up_joints() throws InterruptedException, IOException { // LEFT ARM UP
        System.out.println("Test of receiving joints starting!");
        String data1 =
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "70.8709,345.8029;70.238,448.2417;140.7686,455.4677;166.7444,452.1386;191.36,449.5869;195.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;271.5313,428.2726;132.7042,338.5558;70.8891,341.9003;70.2916,448.1301;200.7179,445.6667;"
                        + "192.8333";
        
        int result = ktest.consumeJointReadings(data1);
        
        assertThat(result).isEqualTo(2);
        
        System.out.println("Test 2 Successful\n");
    
    }
    
    //@Test
    public void right_arm_up_joints() throws InterruptedException, IOException { // RIGHT ARM UP
        System.out.println("Test of receiving joints starting!");
        String data1 =
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "185.8709,345.8029;185.238,448.2417;140.7686,455.4677;166.7444,452.1386;72.36,449.5869;72.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;72.5313,428.2726;72.7042,338.5558;184.8891,341.9003;183.2916,448.1301;200.7179,445.6667;"
                        + "192.8333";
        
        int result = ktest.consumeJointReadings(data1);
        
        assertThat(result).isEqualTo(3);
        
        System.out.println("Test 3 Successful\n");
    
    }
    
    //@Test
    public void two_persons_joints() throws InterruptedException, IOException { // TWO PERSONS
        System.out.println("Test of receiving joints starting!");
        String data1 =
                "428.3214;193.7077,428.3398;158.8254,428.2409;124.0125,433.2831;109.9034,410.2422;110.9774,379.26;90.5726,355.4487;"
                        + "185.8709,345.8029;185.238,448.2417;140.7686,455.4677;166.7444,452.1386;180.36,449.5869;180.57,419.4047;"
                        + "193.4234,416.1849;229.2507,415.9737;264.5146,419.9604;273.6193,437.2123;193.9518,431.0868;229.4249,427.6477;"
                        + "265.4389,432.1964;178.5313,428.2726;178.7042,338.5558;184.8891,341.9003;183.2916,448.1301;200.7179,445.6667;"
                        + "192.8333";
        String data2 = 
                "228.3214;193.7077,228.3398;158.8254,228.2409;124.0125,233.2831;109.9034,210.2422;110.9774,179.26;90.5726,155.4487;"
                        + "185.8709,145.8029;185.238,248.2417;140.7686,255.4677;166.7444,252.1386;180.36,249.5869;180.57,219.4047;"
                        + "193.4234,216.1849;229.2507,215.9737;264.5146,219.9604;273.6193,237.2123;193.9518,231.0868;229.4249,227.6477;"
                        + "265.4389,232.1964;178.5313,228.2726;178.7042,138.5558;184.8891,141.9003;183.2916,248.1301;200.7179,245.6667;"
                        + "192.8333";
        
        int result = ktest.consumeJointReadings(data1+data2);
        
        assertThat(result).isEqualTo(0);
        
        System.out.println("Test 4 Successful\n");
    
    }
    
    //@Test
    public void handstand_joints() throws InterruptedException, IOException { // Handstand
        System.out.println("Test of receiving joints starting!");
        String data1 =
                "428.3214;107.7077,428.3398;142.8254,428.2409;176.0125,433.2831;191.9034,410.2422;190.9774,379.26;210.5726,355.4487;"
                        + "230.8709,345.8029;230.238,448.2417;160.7686,455.4677;134.7444,452.1386;228.36,449.5869;228.57,419.4047;"
                        + "107.4234,416.1849;71.2507,415.9737;36.5146,419.9604;27.6193,437.2123;107.9518,431.0868;71.4249,427.6477;"
                        + "35.4389,432.1964;228.5313,428.2726;228.7042,338.5558;230.8891,341.9003;230.2916,448.1301;100.7179,445.6667;"
                        + "108.8333";
        
        int result = ktest.consumeJointReadings(data1);
        
        assertThat(result).isEqualTo(4);
        
        System.out.println("Test 5 Successful\n");
    
    }*/
}
