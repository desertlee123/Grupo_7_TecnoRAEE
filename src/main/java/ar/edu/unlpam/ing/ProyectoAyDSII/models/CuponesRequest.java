package ar.edu.unlpam.ing.ProyectoAyDSII.models;

import java.util.List;

public record CuponesRequest(Long idUsuario, List<Long> idCuponesExistentes) {

}
