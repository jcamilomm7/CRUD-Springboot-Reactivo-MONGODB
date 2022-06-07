package com.springboot.webflux.apirest.app.controllers;

import com.springboot.webflux.apirest.app.models.Producto;
import com.springboot.webflux.apirest.app.repository.ProductoRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {


    @Autowired
    private ProductoRepositories productoRepositories;


    @GetMapping
    public Flux<Producto> listar() {
        Flux<Producto> productos = productoRepositories.findAll();
        return productos;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> ver(@PathVariable String id) {
        return productoRepositories.findById(id).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                ;
    }


    @PostMapping
    public Mono<ResponseEntity<Producto>> crear(@RequestBody Producto producto) {

        if (producto.getCreateAt() == null) {
            producto.setCreateAt(new Date());
        }

        return productoRepositories.save(producto).map(p -> ResponseEntity
                .created(URI.create("/api/prodcutos/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p)
        );

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> actualizar(@RequestBody Producto producto, @PathVariable String id) {
        return productoRepositories.findById(id).flatMap(p -> {
                    p.setNombre(producto.getNombre());
                    p.setPrecio(producto.getPrecio());

                    return productoRepositories.save(p);
                }).map(p -> ResponseEntity.created(URI.create("/api/productos/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                ;
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
        return productoRepositories.findById(id).flatMap(p -> {
            return productoRepositories.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
