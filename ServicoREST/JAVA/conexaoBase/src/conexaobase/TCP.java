package conexaobase;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.sql.*;

public class TCP extends Thread {

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

            System.out.println("TCP: " + this.getName() + " " + msg);

            String[] arrayDados = msg.split(";");

            String sensor = arrayDados[0], valor = arrayDados[1];

            // Abre conexão com a base
            Conexao con = new Conexao();
            Connection conn = con.Conexao();

            // Insere os dados na base
            InsertDados in = new InsertDados();
            in.Insert(conn, sensor, valor);

        }
        System.out.println("encerrou: " + this.getName() + " " + soc.getPort());
        try {
            soc.close();
        } catch (IOException ex) {
            System.out.println("fudeu");
        }
    }
}
