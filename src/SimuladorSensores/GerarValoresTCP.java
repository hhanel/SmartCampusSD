package SimuladorSensores;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

public class GerarValoresTCP implements Runnable {
	
	private Sensor sensor;
	private int valor;
	
	public GerarValoresTCP(Sensor s) {
		this.sensor = s;
	}
	
	public int getValor() {
		return valor;
	}
	
	@Override
	public void run() {
		
		try {
			Random gerar = new Random();
			Socket socket = new Socket(sensor.getAddrIp(), sensor.getPorta());
				
			while (sensor.getRun()) {
				
				sensor.setValor(gerar.nextInt((this.sensor.getFimRang() - this.sensor.getIniRange() + 1)) + this.sensor.getIniRange());
				Thread.sleep(sensor.getSleep());
					
				PrintStream saida = new PrintStream (socket.getOutputStream());
                saida.println(sensor.getValor());
                
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

}
