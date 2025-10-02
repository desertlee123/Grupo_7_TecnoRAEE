package ar.edu.unlpam.ing.ProyectoAyDSII.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;
import ar.edu.unlpam.ing.ProyectoAyDSII.services.ValidacionService;

@RestController
public class ValidacionController {
  private final ValidacionService service;

  public ValidacionController(ValidacionService service) {
    this.service = service;
  }

  @PostMapping("/solicitudes/validar")
  public ResponseEntity<SolicitudRecoleccion> validarSolicitud(@RequestBody SolicitudRecoleccion datos) {
    return service.validarSolicitud(datos);
  }
}
