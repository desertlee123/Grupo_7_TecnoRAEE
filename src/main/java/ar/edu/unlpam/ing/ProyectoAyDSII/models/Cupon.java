package ar.edu.unlpam.ing.ProyectoAyDSII.models;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Cupon {
    private Long idCupon;
    private Long idTienda;
    private String titulo;
    private String descripcion;
    private String codigo;
    private String condiciones;
    private String webPage;
    private LocalDate fechaInicio;
    private LocalDate fechaExpiracion;
    private int usos;
}
