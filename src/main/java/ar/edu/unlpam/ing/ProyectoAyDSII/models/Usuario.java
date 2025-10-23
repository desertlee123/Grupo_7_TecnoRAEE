package ar.edu.unlpam.ing.ProyectoAyDSII.models;

import lombok.Data;

@Data
public class Usuario {
  private Long idUsuario;
  private String nombre;
  private String apellido;
  private String direccion;
  private Long idTipo;
}
