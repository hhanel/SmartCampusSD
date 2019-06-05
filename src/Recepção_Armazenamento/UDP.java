/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recepção_Armazenamento;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;


class UDP extends Thread { //THREAD PARA CONTROLAR SENSOR UDP

    public UDP() {

    }

    @Override
    public void run() {
        byte buf[] = new byte[100];
        DatagramSocket soc = null;
        DatagramPacket pct = null;
        
        while (true) {
            try {
                soc = new DatagramSocket(1995);
                pct = new DatagramPacket(buf, buf.length);
                soc.receive(pct);
            } catch (SocketException ex8) {
                System.out.println("Sensor saiu");
            } catch (IOException ex8) {
                System.out.println("Sensor saiu");
            }
            
            String msg = new String(pct.getData());
            
            /*MongoConexao mongoConexao = new MongoConexao();
            mongoConexao.inserirDocumentoNaColecaoDados(msg);*/
            System.out.println("UDP: " + msg +" " + this.getName());
            soc.close();
        }

    }
}
