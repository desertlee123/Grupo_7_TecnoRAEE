package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import java.math.BigInteger;
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

      BigInteger id = (BigInteger) con.createQuery(sql, true)
          .bind(solicitud)
          .executeUpdate()
          .getKey();

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

  /*
   * @Override
   * public List<SolicitudRecoleccion> obtenerTodas() {
   * String sql = "SELECT * FROM solicitudes_recoleccion";
   * 
   * try (Connection con = sql2o.open()) {
   * List<SolicitudRecoleccion> solicitudes =
   * con.createQuery(sql).executeAndFetch(SolicitudRecoleccion.class);
   * 
   * // cargar detalles de cada solicitud
   * for(SolicitudRecoleccion solicitud : solicitudes) {
   * List<DetalleSolicitud> detalles =
   * detalleDAO.obtenerPorSolicitud(solicitud.getCodigoValidacion());
   * 
   * solicitud.setDetalles(detalles);
   * }
   * 
   * return solicitudes;
   * }
   * }
   */
}
