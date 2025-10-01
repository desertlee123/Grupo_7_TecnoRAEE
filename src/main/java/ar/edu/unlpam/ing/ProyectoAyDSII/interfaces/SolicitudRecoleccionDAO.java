package ar.edu.unlpam.ing.ProyectoAyDSII.interfaces;

// import java.util.List;

import org.springframework.http.ResponseEntity;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;

public interface SolicitudRecoleccionDAO {
  ResponseEntity<SolicitudRecoleccion> guardar(SolicitudRecoleccion solicitud);

  // List<SolicitudRecoleccion> obtenerTodas();
}
