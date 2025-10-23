package ar.edu.unlpam.ing.ProyectoAyDSII.services;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.SolicitudRecoleccionDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;

@Service
public class SolicitudRecoleccionService {
  private final SolicitudRecoleccionDAO solicitudDAO;

  public SolicitudRecoleccionService(SolicitudRecoleccionDAO solicitudDAO) {
    this.solicitudDAO = solicitudDAO;
  }

  public ResponseEntity<SolicitudRecoleccion> crear(SolicitudRecoleccion solicitud) {
    ResponseEntity<SolicitudRecoleccion> solicitudResponse = solicitudDAO.guardar(solicitud);

    if (solicitudResponse.getStatusCode() != HttpStatus.OK) {
      return ResponseEntity.internalServerError().body(null);
    }

    return solicitudResponse;
  }

  // SOLO PARA PROBAR
  public List<SolicitudRecoleccion> obtenerTodas() {
    return solicitudDAO.obtenerTodas();
  }
}
