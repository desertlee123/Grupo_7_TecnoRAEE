package ar.edu.unlpam.ing.ProyectoAyDSII.controllers;

import org.springframework.web.bind.annotation.RestController;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;
import ar.edu.unlpam.ing.ProyectoAyDSII.services.CuponService;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CuponController {
    private static final Logger registraLog = LoggerFactory.getLogger(CuponController.class);
    private final CuponService service;

    public CuponController(CuponService service) {
        this.service = service;
    }

    @GetMapping("cupones/obtenerPorIdUsuario")
    public ResponseEntity<List<Cupon>> obtenerPorIdUsuario(@RequestParam Long id) {
        ResponseEntity<List<Cupon>> response = service.obtenerPorIdUsuario(id); 

        if (response.getStatusCode() == HttpStatus.OK){
            registraLog.debug(
                "{} cupones obtenidos para el usuario con id {} ",
                response.getBody().size(),
                id
            );

            registraLog.info("Cupones obtenidos con exito para el usuario con id {}", id);
        } else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
            registraLog.info("Cupones no obtenidos por fallas en el servidor para el usuario con id {}", id);
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND){
            registraLog.info("No se encontraron cupones para el usuario con id {}", id);
        }

        return response;
    }

    @PostMapping("cupones/registrarCupon")
    public ResponseEntity<HashMap<String, Object>> registrarCupon(@RequestBody Cupon datos) {
        
        ResponseEntity<HashMap<String, Object>> response = service.registrarCupon(datos);

        if (response.getStatusCode() == HttpStatus.OK){
            registraLog.info("Cupone registrado exitosamente con id {}", response.getBody().get("idCupon"));

            registraLog.debug("Cupon enlasado a usuarios {}", response.getBody().get("usuariosDestinatarios"));
        } else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
            registraLog.info("Falla al registrar el cupon");
        }

        return response;

    }
}
