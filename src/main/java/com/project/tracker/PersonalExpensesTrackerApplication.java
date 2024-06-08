package com.project.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
		info = @Info(
				title = "Personal Expenses Tracker REST API Documentation",
				description = "The expenses tracker application contains all the data about various categories and expenses for each category.",
				version = "v1.2",
				contact = @Contact(
						name = "Ram",
						email = "jvu@gmail.com",
						url = "https://github.com/jvul"
						),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/jvul/license"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Currently there are no external documents for this project",
				url = "https://github.com/jvul/external-doc.html"
				)
		)
@SpringBootApplication
public class PersonalExpensesTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalExpensesTrackerApplication.class, args);
	}

}
