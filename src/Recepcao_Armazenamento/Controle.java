/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recepcao_Armazenamento;


import java.net.ServerSocket;
import java.net.Socket;



public class Controle{

    public static void main(String args[]) throws Exception {

        Thread sensor = new UDP();
        sensor.start();
        
        ServerSocket s = null;
        s = new ServerSocket(1983);
        while (true) {
            Socket conexao = s.accept();
            Thread sensor2 = new TCP(conexao);
            sensor2.start();
        }
    }
}
