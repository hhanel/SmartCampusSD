/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recepção_Armazenamento;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;




class TCP extends Thread { //THREAD PARA CONTROLAR SENSOR TCP

    private Socket soc;

    public TCP(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {
        String msg = "";
        Scanner respostaCliente = null;
        try {
            respostaCliente = new Scanner(soc.getInputStream());
        } catch (IOException ex) {
            System.out.println("Alguma coisa" + ex.getMessage());
        }

        while (respostaCliente.hasNextLine()) {
            msg = respostaCliente.nextLine();
            
            System.out.println("TCP: " + this.getName() + " "+ msg);
            /*MongoConexao mongoConexao = new MongoConexao();
            mongoConexao.inserirDocumentoNaColecaoDados(msg);*/
        }
        System.out.println("encerrou: " + this.getName() + " " + soc.getPort());
        try {
            soc.close();
        } catch (IOException ex) {
            System.out.println("fudeu");
        }

    }
}
