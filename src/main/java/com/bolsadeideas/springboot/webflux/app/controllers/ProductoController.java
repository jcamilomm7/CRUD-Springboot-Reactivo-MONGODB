package com.bolsadeideas.springboot.webflux.app.controllers;

import com.bolsadeideas.springboot.webflux.app.models.Producto;
import com.bolsadeideas.springboot.webflux.app.repository.ProductoRepositories;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class ProductoController {


    @Autowired
    private ProductoRepositories productoRepositories;

    @GetMapping
    public Flux<Producto> listar() {
        Flux<Producto> productos = productoRepositories.findAll();
        return productos;
    }
}
