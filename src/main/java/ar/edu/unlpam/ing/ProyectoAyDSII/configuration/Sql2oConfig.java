package ar.edu.unlpam.ing.ProyectoAyDSII.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class Sql2oConfig {
  @Bean
  public Sql2o sql2o() {
    return new Sql2o("jdbc:mysql://localhost:3307/tecno_raee", "root", "");
  }
}
