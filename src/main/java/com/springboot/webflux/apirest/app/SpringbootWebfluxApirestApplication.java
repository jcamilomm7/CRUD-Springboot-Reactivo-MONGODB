package com.springboot.webflux.apirest.app;

import com.springboot.webflux.apirest.app.models.Producto;
import com.springboot.webflux.apirest.app.repository.ProductoRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringbootWebfluxApirestApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringbootWebfluxApirestApplication.class);

	@Autowired
	private ProductoRepositories productoRepositories;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebfluxApirestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("productos").subscribe();

		Flux.just(new Producto("Tv Panasonic Pantalla LCD", 456.89),
						new Producto("Panasonic Nevera 12", 177.89),
						new Producto("Hp Notebook", 846.70)
				)
				.flatMap(producto -> {
					producto.setCreateAt(new Date());
					return productoRepositories.save(producto);
				})
				.subscribe(producto -> log.info("Insert: " + producto.getId() + producto.getNombre()));

	}
}
