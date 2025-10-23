package ar.edu.unlpam.ing.ProyectoAyDSII.dao;

import java.util.List;
import org.springframework.http.ResponseEntity;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;

public interface CuponDAO {
    ResponseEntity<List<Cupon>> obtenerPorIdUsuario(Long id);

    ResponseEntity<Cupon> guardar(Cupon cupon);
}
