package com.bolsadeideas.springboot.webflux.app.repository;

import com.bolsadeideas.springboot.webflux.app.models.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepositories extends ReactiveMongoRepository<Producto, String> {

}
