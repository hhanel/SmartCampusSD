package SimuladorSensores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class GerarValoresUDP implements Runnable {
	
	private Sensor sensor;
	private int valor;
	
	public GerarValoresUDP(Sensor s) {
		this.sensor = s;
	}
	
	public int getValor() {
		return valor;
	}
	
	@Override
	public void run() {
		
		
		try {
			
			ComunicadorUdp comunicadorUdp = new ComunicadorUdp(sensor.getAddrIp(),sensor.getPorta());
			Random gerar = new Random();
			SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy';'hh:mm:ss", new Locale("pt", "br"));
				
			while (sensor.getRun()) {
				
				this.valor = gerar.nextInt((this.sensor.getFimRang() - this.sensor.getIniRange() + 1)) + this.sensor.getIniRange();
				sensor.setValor(valor);
				Thread.sleep(sensor.getSleep());
				
				String msg = this.sensor.getIdSensor() + ";"+valor +";"+ dataFormat.format(new Date());
				
                comunicadorUdp.enviaMsg(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

}
