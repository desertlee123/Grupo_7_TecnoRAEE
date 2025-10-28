package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponDAOProxy;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;

@Repository
@Primary
public class CuponProxySql2o implements CuponDAOProxy {
  private final CuponDAO cuponDAOReal;

  private static final Logger registraLog = LoggerFactory.getLogger(CuponProxySql2o.class);

  public CuponProxySql2o(CuponDAO cuponDAOReal) {
    this.cuponDAOReal = cuponDAOReal;
  }

  @Override
  public ResponseEntity<List<Cupon>> obtenerPorIdUsuario(Long id) {
    return cuponDAOReal.obtenerPorIdUsuario(id);
  }

  @Override
  public ResponseEntity<Cupon> guardar(Cupon cupon) {
    return cuponDAOReal.guardar(cupon);
  }

  @Override
  public ResponseEntity<List<Cupon>> obtenerPorIdUsuarioLazy(Long id, List<Long> idCuponesExistentes) {
    ResponseEntity<List<Cupon>> response = obtenerPorIdUsuario(id);

    if (response.getStatusCode() == HttpStatus.OK) {
      List<Cupon> cupones = response.getBody();
      cupones.removeIf(cupon -> idCuponesExistentes.contains(cupon.getIdCupon()));
      registraLog.info("[Proxy] Devolviendo cupones");
      return ResponseEntity.ok(cupones);
    } else {
      registraLog.info("[] Devolviendo cupones");
      return response;
    }
  }
}
