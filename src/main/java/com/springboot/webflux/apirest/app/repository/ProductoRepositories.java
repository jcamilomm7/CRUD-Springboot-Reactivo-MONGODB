package com.springboot.webflux.apirest.app.repository;

import com.springboot.webflux.apirest.app.models.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositories extends ReactiveMongoRepository<Producto, String> {

}
