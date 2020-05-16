package proj.es.p21.BodyTracking.KafkaTestP;

/**
 *
 * @author joao
 */

//import com.springkafkatest.model.event.UpdatedBrandEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@DirtiesContext
@SpringBootTest()


/*@EmbeddedKafka(
    partitions = 1, 
    controlledShutdown = false,
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", 
        "port=9092"
})*/
public class KafkaTest {
    private KafkaConsumerTest ktest;
    
    
    @Before
    public void initTest(){
        ktest = new KafkaConsumerTest();
    }
    
    @Test
    public void alarms_test() throws InterruptedException, IOException {
        System.out.println("Test of alarms starting!");
        
        String result = ktest.consumeAlarms("2");
        
        assertThat(result).isEqualTo("Left Arm UP!");
        
        System.out.println("Test 1 Successful");
    
    }
    
    
    @Test
    public void alarms_test2() throws InterruptedException, IOException {
        System.out.println("Test of alarms starting!");
        
        String result = ktest.consumeAlarms("3");
        
        assertThat(result).isEqualTo("Right Arm UP!");
        
        System.out.println("Test 2 Successful");
    
    }
    
    
    @Test
    public void alarms_test3() throws InterruptedException, IOException {
        System.out.println("Test of alarms starting!");
        
        String result = ktest.consumeAlarms("1");
        
        assertThat(result).isEqualTo("Both Arms UP!");
        
        System.out.println("Test 3 Successful");
    
    }
    
    
    @Test
    public void alarms_test4() throws InterruptedException, IOException {
        System.out.println("Test of alarms starting!");
        
        String result = ktest.consumeAlarms("4");
        
        assertThat(result).isEqualTo("Handstand");
        
        System.out.println("Test 4 Successful");
    
    }
    
    
    
    @Test
    public void alarms_test5() throws InterruptedException, IOException {
        System.out.println("Test of alarms starting!");
        
        String result = ktest.consumeAlarms("0 : 3");
        
        assertThat(result).isEqualTo("Sensor are detecting  3 persons!");
        
        System.out.println("Test 4 Successful");
    
    }
    

}