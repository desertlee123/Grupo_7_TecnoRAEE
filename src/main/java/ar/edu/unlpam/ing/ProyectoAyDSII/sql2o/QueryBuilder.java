package ar.edu.unlpam.ing.ProyectoAyDSII.sql2o;

import java.lang.reflect.Field;

public class QueryBuilder {
  public static String generateQueryInsert(Object object, String tableName) {
    Field[] fields = object.getClass().getDeclaredFields();

    String queryColumnsInsert = "";
    String queryColumnsValues = "";

    for (int i = 0; i < fields.length; i++) {
      try {
        fields[i].setAccessible(true);
        queryColumnsInsert += fields[i].getName() + ",";
        queryColumnsValues += ":" + (String) fields[i].getName() + ",";
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    queryColumnsInsert = queryColumnsInsert.substring(0, queryColumnsInsert.length() - 1);
    queryColumnsValues = queryColumnsValues.substring(0, queryColumnsValues.length() - 1);

    String sql = "INSERT INTO " + tableName + " (" + queryColumnsInsert + ") VALUES (" + queryColumnsValues
        + ")";

    return sql;
  }
}
