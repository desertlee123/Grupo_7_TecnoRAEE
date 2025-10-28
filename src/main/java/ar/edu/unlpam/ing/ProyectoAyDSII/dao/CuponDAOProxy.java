package ar.edu.unlpam.ing.ProyectoAyDSII.dao;

import java.util.List;
import org.springframework.http.ResponseEntity;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;

public interface CuponDAOProxy extends CuponDAO {
  ResponseEntity<List<Cupon>> obtenerPorIdUsuarioLazy(Long id, List<Long> idCuponesExistentes);
}
