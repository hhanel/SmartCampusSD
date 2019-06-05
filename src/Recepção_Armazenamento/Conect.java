/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recepção_Armazenamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conect {
    private static final String driverName = "oracle.jdbc.driver.OracleDriver";
    private static final String url="jdbc:oracle:thin:@localhost:1521:xe";
    private static final String username = "";
    private static final String password = "";


    public static Connection criarConexao() throws ClassNotFoundException, SQLException{
        Class.forName(driverName);
        Connection conecta = DriverManager.getConnection(url,username,password);
        
        if(conecta != null){
            System.out.println("Conexão efetuada!!");
            return conecta;
        }
        return null;
        
    }
    
    
}
