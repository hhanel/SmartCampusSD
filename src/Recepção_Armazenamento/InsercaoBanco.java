/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recepção_Armazenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InsercaoBanco {

    public static boolean insere(String dados[]) throws SQLException, ClassNotFoundException {
        Connection c = Conect.criarConexao();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO Sensor (descricao,latitude,longitude,tipo) values (?,?,?,?)";

        try {
            preparedStatement = c.prepareStatement(sql);
            preparedStatement.setString(1, dados[0]);
            preparedStatement.setFloat(2, Float.parseFloat(dados[1]));
            preparedStatement.setFloat(3, Float.parseFloat(dados[2]));
            preparedStatement.setInt(4, Integer.parseInt(dados[3]));

            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

}
