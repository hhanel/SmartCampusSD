/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexaobase;

import java.sql.*;

/**
 *
 * @author Leonardo Maier da Silva
 */
public class InsertDados {

    public void Insert(Connection conn, String sensor, String valor) {
        try {
            Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery("SELECT COD_SENSOR FROM SENSOR;");

            Boolean bool = true;
            while (rs.next()) {
                String codSensorBase = rs.getString("COD_SENSOR");
                if (codSensorBase.equals(sensor)) {
                    bool = false;
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO DADOS (COD_SENSOR, SEQ_DADOS, DATAHORA, VALOR)\n"
                            + "OUTPUT INSERTED.SEQ_DADOS VALUES(?, NEXT VALUE FOR SEQ_DADOS, CURRENT_TIMESTAMP, ?);");
                    stmt.setString(1, sensor);
                    stmt.setString(2, valor);
                    stmt.executeQuery();
                    System.out.println("Dados inseridos");
                }
            }
            if (bool) {
                System.out.println("Sensor não encontrado na base.");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir dados na base. " + ex.getMessage());
        }
    }
}
