package ar.edu.unlpam.ing.ProyectoAyDSII.dao;

import java.util.List;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Usuario;

public interface UsuarioDAO {
  public List<Usuario> obtenerParticulares(Long idTienda, int minSolicitudes);
}