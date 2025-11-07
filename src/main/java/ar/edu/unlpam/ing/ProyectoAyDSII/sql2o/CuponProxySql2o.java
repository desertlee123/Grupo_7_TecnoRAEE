package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;

@Repository
@Primary
public class CuponProxySql2o implements CuponDAO {
  private final CuponDAO cuponDAOReal;
  private HashMap<Long, CacheCupones> cache;
  private static final long CACHE_TTL_MS = 5 * 60 * 1000; // 5 minutos

  private static final Logger registraLog = LoggerFactory.getLogger(CuponProxySql2o.class);

  public CuponProxySql2o(CuponDAO cuponDAOReal) {
    this.cuponDAOReal = cuponDAOReal;
    this.cache = new HashMap<Long, CacheCupones>();
  }

  @Override
  public ResponseEntity<List<Cupon>> obtenerPorIdUsuario(Long id) {
    CacheCupones cupones = cache.get(id);
    long now = Instant.now().toEpochMilli();

    if (cupones != null && (now - cupones.timestamp) < CACHE_TTL_MS) {
      registraLog.info("[Proxy] Devolviendo cupones desde cache para usuario " + id);
      return ResponseEntity.ok(cupones.cupones);
    }

    ResponseEntity<List<Cupon>> response = cuponDAOReal.obtenerPorIdUsuario(id);

    if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK) {
      cache.put(id, new CacheCupones(response.getBody(), now));
      registraLog.info("[Proxy] Guardando cupones en cache para usuario " + id);
    }

    return response;
  }

  @Override
  public ResponseEntity<Cupon> guardar(Cupon cupon) {
    ResponseEntity<Cupon> response = cuponDAOReal.guardar(cupon);

    if (response.getBody() != null) {
      // invalidar caché de todos los usuarios al insertar un nuevo cupón
      cache.clear();
    }

    return response;
  }
}

class CacheCupones {
  List<Cupon> cupones;
  long timestamp;

  CacheCupones(List<Cupon> cupones, long timestamp) {
    this.cupones = cupones;
    this.timestamp = timestamp;
  }
}
