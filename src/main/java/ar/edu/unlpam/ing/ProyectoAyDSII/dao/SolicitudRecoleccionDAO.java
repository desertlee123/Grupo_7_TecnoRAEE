package ar.edu.unlpam.ing.ProyectoAyDSII.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;

public interface SolicitudRecoleccionDAO {
  ResponseEntity<SolicitudRecoleccion> guardar(SolicitudRecoleccion solicitud);

  ResponseEntity<Boolean> actualizarEstado(Long id, int estado);

  ResponseEntity<Boolean> añadirObservaciones(Long id, String observaciones);

  SolicitudRecoleccion encontrarPorId(Long id);

  ResponseEntity<Boolean> setFechaVisita(Long id, LocalDateTime fecha);

  // SOLO PARA PROBAR
  List<SolicitudRecoleccion> obtenerTodas();
}
