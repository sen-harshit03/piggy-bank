package com.banking.cards;

import com.banking.cards.dto.CardsContactInfo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = CardsContactInfo.class)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Card Service Documentation",
				description = "This service is responsible for managing the cards",
				contact = @Contact(
						name = "Cards Team",
						url = "https:localhost:9000/swagger-ui/index.html",
						email = "cardsTeam@xyz.com"
				),
				license = @License(name = "Apache 2.0",
						url = "dummy url")
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
