package pt.ua.deti.es.p21.BodyTrackingAnalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP"})
@EntityScan({"pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP"})

public class BodyTrackingAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BodyTrackingAnalysisApplication.class, args);
	}

}
