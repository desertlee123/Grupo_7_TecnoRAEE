package ar.edu.unlpam.ing.ProyectoAyDSII.interfaces;

public interface CuponDAO {
    ResponseEntity<Set<Cupon>> obtenerPorIdUsuario(Long id);
}
