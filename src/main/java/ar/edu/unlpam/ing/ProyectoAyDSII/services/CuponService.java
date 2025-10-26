package ar.edu.unlpam.ing.ProyectoAyDSII.services;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.CuponUsuarioDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.dao.UsuarioDAO;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Cupon;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.CuponUsuario;
import ar.edu.unlpam.ing.ProyectoAyDSII.models.Usuario;

@Service
public class CuponService {
    private final CuponDAO cuponDAO;
    private final UsuarioDAO usuarioDAO;
    private final CuponUsuarioDAO cuponUsuarioDAO;

    public CuponService(
            CuponDAO cuponDAO,
            UsuarioDAO usuarioDAO,
            CuponUsuarioDAO cuponUsuarioDAO) {
        this.cuponDAO = cuponDAO;
        this.usuarioDAO = usuarioDAO;
        this.cuponUsuarioDAO = cuponUsuarioDAO;
    }

    public ResponseEntity<List<Cupon>> obtenerPorIdUsuario(Long id) {
        return cuponDAO.obtenerPorIdUsuario(id);
    }

    public ResponseEntity<HashMap<String, Object>> registrarCupon(Cupon cupon) {
        // 1) calcular destinatarios
        List<Long> destinatarios = calcularUsuariosDestinatarios(cupon);

        // 2) persistir cupón -> obtener id
        ResponseEntity<Cupon> cuponGuardadoResponse = cuponDAO.guardar(cupon);

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

        // 4) construir respuesta JSON final manualmente
        HashMap<String, Object> response = new HashMap<>();
        response.put("idCupon", cuponGuardado.getIdCupon());
        response.put("titulo", cuponGuardado.getTitulo());
        response.put("descripcion", cuponGuardado.getDescripcion());
        response.put("codigo", cuponGuardado.getCodigo());
        response.put("fechaInicio", cuponGuardado.getFechaInicio());
        response.put("fechaExpiracion", cuponGuardado.getFechaExpiracion());
        response.put("condiciones", cuponGuardado.getCondiciones());
        response.put("webPage", cuponGuardado.getWebPage());
        response.put("usos", cuponGuardado.getUsos());
        response.put("idTienda", cuponGuardado.getIdTienda());
        response.put("usuariosDestinatarios", destinatarios);

        return ResponseEntity.ok(response);
    }

    private List<Long> calcularUsuariosDestinatarios(Cupon cupon) {
        // parámetros del negocio (moverlos a config)
        Long idTienda = cupon.getIdTienda();
        int minSolicitudes = 1;
        int limit = cupon.getUsos();
        boolean skipCiudadFilter = true; // para arrancar y evitar problemas de texto en direccion

        List<Usuario> usuarios = usuarioDAO.obtenerParticulares(
                idTienda,
                minSolicitudes,
                limit,
                skipCiudadFilter);

        return usuarios.stream().map(Usuario::getIdUsuario).collect(Collectors.toList());
    }

    private ResponseEntity<CuponUsuario> registrarAsignacion(Cupon cuponGuardado, Long idUsuario) {
        CuponUsuario cuponUsuario = new CuponUsuario();
        cuponUsuario.setIdCupon(cuponGuardado.getIdCupon());
        cuponUsuario.setIdUsuario(idUsuario);
        return cuponUsuarioDAO.guardar(cuponUsuario);
    }
}
