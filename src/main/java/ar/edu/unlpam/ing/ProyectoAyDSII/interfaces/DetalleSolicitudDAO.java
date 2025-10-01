package ar.edu.unlpam.ing.ProyectoAyDSII.interfaces;

// import java.util.List;

import org.springframework.http.ResponseEntity;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.DetalleSolicitud;

public interface DetalleSolicitudDAO {
  ResponseEntity<DetalleSolicitud> guardar(DetalleSolicitud detalle);

  // List<DetalleSolicitud> obtenerPorSolicitud(String codigoValidacion);
}
