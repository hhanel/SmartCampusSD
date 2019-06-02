package SimuladorSensores;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ComunicadorUdp {

	
    private DatagramSocket socUdp;
    private int porta;
    private String addrUdp;
    
    public ComunicadorUdp(String addrUdp, int porta) throws UnknownHostException, SocketException {
		this.socUdp = new DatagramSocket();
    	this.addrUdp = addrUdp;
		this.porta = porta;
	}
    
    
    public void enviaMsg(String msg) throws IOException {
    	byte buf[] = null;
    	buf = msg.getBytes();
    	DatagramPacket pacote = new DatagramPacket(buf, buf.length, InetAddress.getByName(addrUdp), porta);
    	//DatagramPacket pacote = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), porta);
    	this.socUdp.send(pacote);

    }
    
}
