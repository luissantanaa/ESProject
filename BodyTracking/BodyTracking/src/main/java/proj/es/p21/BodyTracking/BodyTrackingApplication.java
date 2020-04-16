package proj.es.p21.BodyTracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"proj.es.p21.BodyTracking.JpaP"})
@EntityScan({"proj.es.p21.BodyTracking.KafkaP","proj.es.p21.BodyTracking.JpaP"})
public class BodyTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BodyTrackingApplication.class, args);
	}

}
