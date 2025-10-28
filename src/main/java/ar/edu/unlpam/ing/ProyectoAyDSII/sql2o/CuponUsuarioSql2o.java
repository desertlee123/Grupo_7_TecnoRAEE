package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponUsuarioDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.CuponUsuario;
import org.sql2o.Sql2o;
import org.sql2o.Connection;

@Repository
public class CuponUsuarioSql2o implements CuponUsuarioDAO {
  private final Sql2o sql2o;

  public CuponUsuarioSql2o(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public ResponseEntity<CuponUsuario> guardar(CuponUsuario cuponUsuario) {
    String sql = QueryBuilder.generateQueryInsert(cuponUsuario, this.getTableName());

    try (Connection con = sql2o.beginTransaction()) {
      con.createQuery(sql)
          .bind(cuponUsuario)
          .executeUpdate();
      con.commit();

      return ResponseEntity.ok(cuponUsuario);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(null);
    }
  }

  private String getTableName() {
    return "cupones_usuarios";
  }
}
