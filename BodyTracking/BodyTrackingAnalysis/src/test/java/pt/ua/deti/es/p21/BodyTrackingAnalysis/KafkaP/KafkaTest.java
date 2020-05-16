package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

/**
 *
 * @author joao
 */

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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP.KafkaProducer;


@RunWith(SpringRunner.class)
//@DirtiesContext
@SpringBootTest()


public class KafkaTest {
    private KafkaListenerTest ktest;
    
    
    @Before
    public void initTest(){
        ktest = new KafkaListenerTest();
    }
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    }
    

}