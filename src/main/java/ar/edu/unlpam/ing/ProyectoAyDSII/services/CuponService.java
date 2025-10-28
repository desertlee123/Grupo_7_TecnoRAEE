package ar.edu.unlpam.ing.ProyectoAyDSII.services;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponDAOProxy;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponUsuarioDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.UsuarioDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.CuponResponse;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.CuponUsuario;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Usuario;

@Service
public class CuponService {
    private final CuponDAOProxy cuponDAOProxy;
    private final CuponUsuarioDAO cuponUsuarioDAO;
    private final UsuarioDAO usuarioDAO;

    public CuponService(
            CuponDAOProxy cuponDAOProxy,
            CuponUsuarioDAO cuponUsuarioDAO,
            UsuarioDAO usuarioDAO) {
        this.cuponDAOProxy = cuponDAOProxy;
        this.cuponUsuarioDAO = cuponUsuarioDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public ResponseEntity<List<Cupon>> obtenerPorIdUsuario(Long id, List<Long> idCuponesExistentes) {
        return cuponDAOProxy.obtenerPorIdUsuarioLazy(id, idCuponesExistentes);
    }

    public ResponseEntity<CuponResponse> registrarCupon(Cupon cupon) {
        // 1) calcular destinatarios
        List<Long> destinatarios = calcularUsuariosDestinatarios(cupon);

        // 2) persistir cupón -> obtener id
        ResponseEntity<Cupon> cuponGuardadoResponse = cuponDAOProxy.guardar(cupon);

        if (cuponGuardadoResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.internalServerError().build();
        }

        Cupon cuponGuardado = cuponGuardadoResponse.getBody();

        // 3) registrar asignaciones
        for (Long idUsuario : destinatarios) {
            ResponseEntity<CuponUsuario> cuponUsuarioGuardadoResponse = registrarAsignacion(cuponGuardado, idUsuario);
            if (cuponUsuarioGuardadoResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.internalServerError().build();
            }
        }

        // 4) mandar response final
        return ResponseEntity.ok(new CuponResponse(cuponGuardado.getIdCupon(), destinatarios));
    }

    private List<Long> calcularUsuariosDestinatarios(Cupon cupon) {
        // parámetros del negocio
        Long idTienda = cupon.getIdTienda();
        int minSolicitudes = 1;

        List<Usuario> usuarios = usuarioDAO.obtenerParticulares(
                idTienda,
                minSolicitudes);

        return usuarios.stream().map(Usuario::getIdUsuario).collect(Collectors.toList());
    }

    private ResponseEntity<CuponUsuario> registrarAsignacion(Cupon cuponGuardado, Long idUsuario) {
        CuponUsuario cuponUsuario = new CuponUsuario();
        cuponUsuario.setIdCupon(cuponGuardado.getIdCupon());
        cuponUsuario.setIdUsuario(idUsuario);
        return cuponUsuarioDAO.guardar(cuponUsuario);
    }
}
