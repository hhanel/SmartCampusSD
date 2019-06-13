package conexaobase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.*;

public class UDP extends Thread {

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

            String msg = new String(buf, 0, buf.length).trim();

            System.out.println("UDP: " + this.getName() + " " + msg);

            String[] arrayDados = msg.split(";");

            String sensor = arrayDados[0], valor = arrayDados[1];

            // Abre conexão com a base
            Conexao con = new Conexao();
            Connection conn = con.Conexao();

            // Insere os dados na base
            InsertDados in = new InsertDados();
            in.Insert(conn, sensor, valor);

            soc.close();
        }
    }
}
