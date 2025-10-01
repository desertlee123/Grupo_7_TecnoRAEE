package ar.edu.unlpam.ing.ProyectoAyDSII.models;

import lombok.Data;

@Data
public class DetalleSolicitud {
  private Integer cantidad;
  private String descripcion;
  private Long idResiduo;
  private Long idSolicitud;
}
