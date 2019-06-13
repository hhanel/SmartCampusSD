package conexaobase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Controle {

    public static void main(String[] args) throws IOException {

        Thread sensor = new UDP();
        sensor.start();
        try {
            ServerSocket s = null;
            s = new ServerSocket(1983);
            while (true) {
                Socket conexao = s.accept();
                Thread sensor2 = new TCP(conexao);
                sensor2.start();

            }
            //s.close();
        } catch (Exception e) {
        }
    }
}
