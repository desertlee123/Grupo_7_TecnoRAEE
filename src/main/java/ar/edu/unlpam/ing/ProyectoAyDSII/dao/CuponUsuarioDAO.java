package ar.edu.unlpam.ing.ProyectoAyDSII.dao;

import org.springframework.http.ResponseEntity;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.CuponUsuario;

public interface CuponUsuarioDAO {
  ResponseEntity<CuponUsuario> guardar(CuponUsuario cuponUsuario);
}
