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
      int minSolicitudes) {
    String sql = "SELECT u.id_usuario AS idUsuario, u.nombre, u.apellido, u.direccion, u.id_tipo AS idTipo " +
        "FROM usuarios u " +
        "INNER JOIN solicitudes_recoleccion sr ON u.id_usuario = sr.id_usuario " +
        "WHERE u.id_tipo = :idTipoParticular " +
        "AND UPPER(u.localidad) = UPPER((SELECT localidad FROM usuarios WHERE id_usuario = :idTienda))" +
        "GROUP BY u.id_usuario " +
        "HAVING COUNT(sr.id_solicitud) >= :minSolicitudes";

    try (Connection con = sql2o.open()) {
      return con.createQuery(sql)
          .addParameter("idTipoParticular", ID_TIPO_PARTICULAR)
          .addParameter("minSolicitudes", minSolicitudes)
          .addParameter("idTienda", idTienda)
          .executeAndFetch(Usuario.class);
    }
  }
}
