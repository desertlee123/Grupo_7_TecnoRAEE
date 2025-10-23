package ar.edu.unlpam.ing.ProyectoAyDSII.services;

import org.springframework.stereotype.Service;

@Service
public class CuponService {
    private final CuponDAO cuponDAO;

    public CuponService(CuponDAO cuponDAO) {
        this.cuponDAO = cuponDAO;
    }

    public ResponseEntity<Set<Cupon>> obtenerPorIdUsuario(Long id) {
        return cuponDAO.obtenerPorIdUsuario(id);
    }
}
