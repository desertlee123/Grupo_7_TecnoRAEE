package ar.edu.unlpam.ing.ProyectoAyDSII.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SolicitudRecoleccion {
  private LocalDateTime fechaCreacion;
  private String direccion;
  private LocalDateTime fechaRecoleccion;
  private Integer idUsuario;
  private Integer idPuntoRecoleccion;
  private LocalDateTime fechaVisita;
  private String codigoValidacion;
  private int estado;
  private String observaciones;
  private List<DetalleSolicitud> detalles;
}
