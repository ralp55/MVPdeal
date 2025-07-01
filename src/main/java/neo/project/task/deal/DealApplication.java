package neo.project.task.deal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "neo.project.task.deal.Repository")
@EntityScan(basePackages = "neo.project.task.deal.DTO")
public class DealApplication {
	public static void main(String[] args) {
		SpringApplication.run(DealApplication.class, args);
	}
}
