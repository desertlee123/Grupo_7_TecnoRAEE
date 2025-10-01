package ar.edu.unlpam.ing.ProyectoAyDSII.services;

// import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ar.edu.unlpam.ing.ProyectoAyDSII.interfaces.SolicitudRecoleccionDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;

@Service
public class SolicitudRecoleccionService {
  private final SolicitudRecoleccionDAO solicitudDAO;

  public SolicitudRecoleccionService(SolicitudRecoleccionDAO solicitudDAO) {
    this.solicitudDAO = solicitudDAO;
  }

  public ResponseEntity<SolicitudRecoleccion> crear(SolicitudRecoleccion solicitud) {

    return solicitudDAO.guardar(solicitud);
  }

  /*
   * public List<SolicitudRecoleccion> obtenerTodas() {
   * return solicitudDAO.obtenerTodas();
   * }
   */
}
