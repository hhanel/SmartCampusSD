package SimuladorSensores;

import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
			SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy';'hh:mm:ss", new Locale("pt", "br"));
				
			while (sensor.getRun()) {
				
				this.valor = gerar.nextInt((this.sensor.getFimRang() - this.sensor.getIniRange() + 1)) + this.sensor.getIniRange();
				sensor.setValor(valor);
				Thread.sleep(sensor.getSleep());
				
				String msg = this.sensor.getIdSensor() + ";"+valor +";"+ dataFormat.format(new Date());
				
				PrintStream saida = new PrintStream (socket.getOutputStream());
                saida.println(msg);
                
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

}
