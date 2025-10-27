package ar.edu.unlpam.ing.ProyectoAyDSII.models;

import java.util.List;

public record CuponResponse(Long idCupon, List<Long> usuariosDestinatarios) {
}
