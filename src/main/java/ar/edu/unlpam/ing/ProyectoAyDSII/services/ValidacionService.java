package ar.edu.unlpam.ing.ProyectoAyDSII.services;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.SolicitudRecoleccionDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.enums.EstadoSolicitud;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;

@Service
public class ValidacionService {
  private final SolicitudRecoleccionDAO solicitudDAO;

  public ValidacionService(SolicitudRecoleccionDAO solicitudDAO) {
    this.solicitudDAO = solicitudDAO;
  }

  public ResponseEntity<SolicitudRecoleccion> validarSolicitud(SolicitudRecoleccion datos) {
    Long idSolicitud = datos.getIdSolicitud();
    int estado = datos.getEstado();
    String observaciones = datos.getObservaciones();

    SolicitudRecoleccion solicitud = solicitudDAO.encontrarPorId(idSolicitud);

    if (solicitud == null) {
      return ResponseEntity.notFound().build();
    }

    if (solicitud.getEstado() == EstadoSolicitud.ACEPTADA.ordinal()
        || solicitud.getEstado() == EstadoSolicitud.RECHAZADA.ordinal()) {
      return ResponseEntity.status(409).body(solicitud);
    }

    ResponseEntity<Boolean> estadoActualizacion = solicitudDAO.actualizarEstado(idSolicitud, estado);

    if (estadoActualizacion.getStatusCode() != HttpStatus.OK) {
      return ResponseEntity.badRequest().build();
    }

    ResponseEntity<Boolean> observacionesActualizacion = solicitudDAO.añadirObservaciones(idSolicitud, observaciones);

    if (observacionesActualizacion.getStatusCode() != HttpStatus.OK) {
      return ResponseEntity.badRequest().build();
    }

    LocalDateTime fechaVisita = LocalDateTime.now();

    ResponseEntity<Boolean> fechaVisitaResponse = solicitudDAO.setFechaVisita(idSolicitud, fechaVisita);

    if (fechaVisitaResponse.getStatusCode() != HttpStatus.OK) {
      return ResponseEntity.badRequest().build();
    }

    solicitud.setEstado(estado);
    solicitud.setObservaciones(observaciones);
    solicitud.setFechaVisita(fechaVisita);

    return ResponseEntity.ok(solicitud);
  }
}
