package in.codingstreams.etuserauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class EtUserAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtUserAuthServiceApplication.class, args);
	}

}
