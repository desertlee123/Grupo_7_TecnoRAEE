package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import java.math.BigInteger;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Usuario;

@Repository
public class CuponSql2o implements CuponDAO {
  private final Sql2o sql2o;

  public CuponSql2o(Sql2o sql2o) {
    this.sql2o = sql2o;
  }

  @Override
  public ResponseEntity<List<Cupon>> obtenerPorIdUsuario(Long id) {
    String sql = "SELECT * FROM cupones WHERE idCupon IN (SELECT idCupon FROM cupones_usuarios WHERE idUsuario = :id)";
    String sql2 = "SELECT id_usuario as idUsuario, nombre, apellido, direccion, id_tipo as idTipo FROM usuarios WHERE id_usuario = :id";

    try (Connection con = sql2o.open()) {
      Usuario usuario = con.createQuery(sql2)
          .addParameter("id", id)
          .executeAndFetchFirst(Usuario.class);

      if (usuario == null) {
        return ResponseEntity.notFound().build();
      }

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(null);
    }

    try (Connection con = sql2o.open()) {
      List<Cupon> cupones = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetch(Cupon.class);
      return ResponseEntity.ok(cupones);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(null);
    }
  }

  @Override
  public ResponseEntity<Cupon> guardar(Cupon cupon) {
    String sql = "INSERT INTO cupones (titulo, descripcion, codigo, fechaInicio, fechaExpiracion, condiciones, webPage, usos, idTienda) VALUES (:titulo, :descripcion, :codigo, :fechaInicio, :fechaExpiracion, :condiciones, :webPage, :usos, :idTienda)";

    try (Connection con = sql2o.open()) {
      BigInteger key = (BigInteger) con.createQuery(sql, true)
          .bind(cupon)
          .executeUpdate()
          .getKey();

      Long id = key.longValue();
      cupon.setIdCupon(id);

      return ResponseEntity.ok(cupon);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body(null);
    }
  }
}
