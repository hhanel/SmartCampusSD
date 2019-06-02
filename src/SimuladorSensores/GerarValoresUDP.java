package SimuladorSensores;

import java.util.Random;

public class GerarValoresUDP implements Runnable {
	
	private Sensor sensor;
	
	public GerarValoresUDP(Sensor s) {
		this.sensor = s;
	}
	
	
	@Override
	public void run() {
		
		
		try {
			
			ComunicadorUdp comunicadorUdp = new ComunicadorUdp(sensor.getAddrIp(),sensor.getPorta());
			Random gerar = new Random();	
			while (sensor.getRun()) {
				
				sensor.setValor(gerar.nextInt((this.sensor.getFimRang() - this.sensor.getIniRange() + 1)) + this.sensor.getIniRange());
				Thread.sleep(sensor.getSleep());
				
                comunicadorUdp.enviaMsg(sensor.getValor());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

}
