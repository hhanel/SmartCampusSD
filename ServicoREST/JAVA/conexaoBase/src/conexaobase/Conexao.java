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
public class Conexao {

    public Connection Conexao() {
        try {
            String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=BaseSensores";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            Connection conn = DriverManager.getConnection(connectionUrl, "sa", "rest");
            System.out.println("Conexao com a base estabelecida");
            return conn;
        } catch (SQLException ex) {
            System.out.println("Erro ao tentar se conectar com a base." + ex.getMessage() + ex.getSQLState() + ex.getErrorCode());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("Não foi possivel conectar com a base." + e);
        }
        return null;
    }
}
