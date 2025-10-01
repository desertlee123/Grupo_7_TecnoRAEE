package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import java.math.BigInteger;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import ar.edu.unlpam.ing.ProyectoAyDSII.interfaces.DetalleSolicitudDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.interfaces.SolicitudRecoleccionDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.DetalleSolicitud;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.SolicitudRecoleccion;

@Repository
public class SolicitudRecoleccionSql2o implements SolicitudRecoleccionDAO {
  private final DetalleSolicitudDAO detalleDAO;
  private final Sql2o sql2o;

  public SolicitudRecoleccionSql2o(Sql2o sql2o, DetalleSolicitudDAO detalleDAO) {
    this.sql2o = sql2o;
    this.detalleDAO = detalleDAO;
  }

  @Override
  public ResponseEntity<SolicitudRecoleccion> guardar(SolicitudRecoleccion solicitud) {
    try (Connection con = sql2o.open()) {
      String sql = "INSERT INTO solicitudes_recoleccion (fecha_creacion, direccion, id_usuario, id_punto_recoleccion, codigo_validacion, estado, fecha_visita, fecha_recoleccion, observaciones) VALUES (:fechaCreacion, :direccion, :idUsuario, :idPuntoRecoleccion, :codigoValidacion, :estado, :fechaVisita, :fechaRecoleccion, :observaciones)";

      BigInteger key = (BigInteger) con.createQuery(sql, true)
          .bind(solicitud)
          .executeUpdate()
          .getKey();

      Long id = key.longValue();

      // guardar detalles asociados
      if (solicitud.getDetalles() != null) {
        for (DetalleSolicitud detalle : solicitud.getDetalles()) {
          detalle.setIdSolicitud(id);
          detalleDAO.guardar(detalle);
        }
      }

      return ResponseEntity.ok(solicitud);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(null);
    }
  }

  // SOLO PARA PROBAR
  @Override
  public List<SolicitudRecoleccion> obtenerTodas() {
    // No ponemos * porque tiene un problema de mapeo entre id_solicitud y
    // idSolicitud y así con los demás atributos, por eso se usa alias en la
    // consulta
    String sql = "SELECT id_solicitud AS idSolicitud, fecha_creacion AS fechaCreacion, " +
        "direccion, id_usuario AS idUsuario, id_punto_recoleccion AS idPuntoRecoleccion, " +
        "codigo_validacion AS codigoValidacion, estado, fecha_visita AS fechaVisita, " +
        "fecha_recoleccion AS fechaRecoleccion, observaciones " +
        "FROM solicitudes_recoleccion";

    try (Connection con = sql2o.open()) {
      List<SolicitudRecoleccion> solicitudes = con.createQuery(sql).executeAndFetch(SolicitudRecoleccion.class);

      for (SolicitudRecoleccion solicitud : solicitudes) {
        String sqlDetalles = "SELECT id_solicitud AS idSolicitud, id_residuo AS idResiduo, " +
            "cantidad, descripcion " +
            "FROM detalles_solicitud WHERE id_solicitud = :id";
        List<DetalleSolicitud> detalles = con.createQuery(sqlDetalles)
            .addParameter("id", solicitud.getIdSolicitud())
            .executeAndFetch(DetalleSolicitud.class);
        solicitud.setDetalles(detalles);
      }

      return solicitudes;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
