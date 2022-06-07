package com.bolsadeideas.springboot.webflux.app;

import com.bolsadeideas.springboot.webflux.app.models.Producto;
import com.bolsadeideas.springboot.webflux.app.repository.ProductoRepositories;

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
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ProductoRepositories productoRepositories;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        mongoTemplate.dropCollection("producto").subscribe();

        Flux.just(new Producto("Tv Panasonic Pantalla LCD", 456.89),
                        new Producto("Sony Camara HD Digital", 177.89),
                        new Producto("Hp Notebook", 846.70)
                )
                .flatMap(producto -> {
                    producto.setCreateAt(new Date());
                    return productoRepositories.save(producto);
                })
                .subscribe(producto -> log.info("Insert: " + producto.getId() + producto.getNombre()));
    }
}
