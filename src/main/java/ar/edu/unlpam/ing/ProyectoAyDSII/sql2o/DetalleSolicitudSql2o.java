package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import ar.edu.unlpam.ing.ProyectoAyDSII.interfaces.DetalleSolicitudDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.DetalleSolicitud;

@Repository
public class DetalleSolicitudSql2o implements DetalleSolicitudDAO {
  private final Sql2o sql2o;

  public DetalleSolicitudSql2o(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public ResponseEntity<DetalleSolicitud> guardar(DetalleSolicitud detalle) {
    String sql = "INSERT INTO detalles_solicitud (id_solicitud, id_residuo, cantidad, descripcion) VALUES (:idSolicitud, :idResiduo, :cantidad, :descripcion)";

    try (Connection con = sql2o.open()) {
      con.createQuery(sql)
          .bind(detalle)
          .executeUpdate();

      return ResponseEntity.ok(detalle);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(null);
    }
  }
}
