package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class DemoApplication {

	@Bean
	RouterFunction<?> routes(PersonRepository personRepository) {
		return route(GET("/people"), req -> ok().body(personRepository.findAll(), Person.class));
	}

	@Bean
	ApplicationRunner run(PersonRepository repository) {
		return args ->
				repository
						.deleteAll()
						.thenMany(
								Flux.just("A", "B", "C", "D")
										.map(x -> new Person(null, x))
										.flatMap(repository::save))
						.thenMany(repository.findAll())
						.subscribe(System.out::println);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
class Person {

	@Id
	private String id;

	private String name;
}