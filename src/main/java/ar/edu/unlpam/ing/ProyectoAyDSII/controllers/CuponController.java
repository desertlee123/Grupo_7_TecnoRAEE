package ar.edu.unlpam.ing.ProyectoAyDSII.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CuponController {
    private final CuponService service;
    public CuponController(CuponService service) {
        this.service = service;
    }

    @GetMapping("cupones/obtenerPorIdUsuario")
    public ResponseEntity<Set<Cupon>> getMethodName(@RequestParam Long id) {
        return service.obtenerPorIdUsuario(id);
    }
    
}
