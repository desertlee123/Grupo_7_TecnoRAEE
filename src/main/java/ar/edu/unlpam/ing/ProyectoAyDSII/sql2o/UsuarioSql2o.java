package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import java.util.List;
import org.sql2o.Query;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.UsuarioDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Usuario;

@Repository
public class UsuarioSql2o implements UsuarioDAO {
  private final Sql2o sql2o;
  private static final int ID_TIPO_PARTICULAR = 1;

  public UsuarioSql2o(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public List<Usuario> obtenerParticulares(
      Long idTienda,
      int minSolicitudes,
      int limit,
      boolean skipCiudadFilter) {

    String sql = "SELECT u.id_usuario AS idUsuario, u.nombre, u.apellido, u.direccion, u.id_tipo AS idTipo " +
        "FROM usuarios u " +
        "LEFT JOIN (SELECT id_usuario, COUNT(*) AS cnt FROM solicitudes_recoleccion GROUP BY id_usuario) s ON s.id_usuario = u.id_usuario "
        +
        "WHERE u.id_tipo = :idTipoParticular " +
        "  AND s.cnt >= :minSolicitudes " +
        (skipCiudadFilter ? ""
            : "  AND (u.direccion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(direccion,' ',1) FROM usuarios WHERE id_usuario = :idTienda), '%')) ")
        +
        "LIMIT :limit";

    try (Connection con = sql2o.open()) {
      Query query = con.createQuery(sql)
          .addParameter("idTipoParticular", ID_TIPO_PARTICULAR)
          .addParameter("minSolicitudes", minSolicitudes)
          .addParameter("limit", limit);

      // solo agregar idTienda si se usa en el SQL
      if (!skipCiudadFilter) {
        query.addParameter("idTienda", idTienda);
      }

      return query.executeAndFetch(Usuario.class);
    }
  }
}
