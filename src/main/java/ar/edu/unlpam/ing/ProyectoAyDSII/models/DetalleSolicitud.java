package ar.edu.unlpam.ing.ProyectoAyDSII.models;

import java.math.BigInteger;
import lombok.Data;

@Data
public class DetalleSolicitud {
  private Integer cantidad;
  private String descripcion;
  private Integer idResiduo;
  private BigInteger idSolicitud;
}
