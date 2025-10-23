package ar.edu.unlpam.ing.ProyectoAyDSII.controllers;

import org.springframework.web.bind.annotation.RestController;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;
import ar.edu.unlpam.ing.ProyectoAyDSII.services.CuponService;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CuponController {
    private final CuponService service;

    public CuponController(CuponService service) {
        this.service = service;
    }

    @GetMapping("cupones/obtenerPorIdUsuario")
    public ResponseEntity<List<Cupon>> obtenerPorIdUsuario(@RequestParam Long id) {
        return service.obtenerPorIdUsuario(id);
    }

    @PostMapping("cupones/registrarCupon")
    public ResponseEntity<HashMap<String, Object>> registrarCupon(@RequestBody Cupon datos) {
        return service.registrarCupon(datos);
    }
}
