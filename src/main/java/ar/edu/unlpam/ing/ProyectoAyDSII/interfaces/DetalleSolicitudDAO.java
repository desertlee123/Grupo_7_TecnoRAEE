package ar.edu.unlpam.ing.ProyectoAyDSII.interfaces;

import org.springframework.http.ResponseEntity;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.DetalleSolicitud;

public interface DetalleSolicitudDAO {
  ResponseEntity<DetalleSolicitud> guardar(DetalleSolicitud detalle);
}
