package ar.edu.unlpam.ing.ProyectoAyDSII.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;
import ar.edu.unlpam.ing.ProyectoAyDSII.services.SolicitudRecoleccionService;

@RestController
public class SolicitudRecoleccionController {
  private final SolicitudRecoleccionService service;

  public SolicitudRecoleccionController(SolicitudRecoleccionService service) {
    this.service = service;
  }

  @PostMapping("solicitudes/crear")
  public ResponseEntity<SolicitudRecoleccion> crear(@RequestBody SolicitudRecoleccion solicitud) {
    return service.crear(solicitud);
  }

  // SOLO PARA PROBAR
  @GetMapping("solicitudes")
  public List<SolicitudRecoleccion> obtenerTodas() {
    return service.obtenerTodas();
  }
}
