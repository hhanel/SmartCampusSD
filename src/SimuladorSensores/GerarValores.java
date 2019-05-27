package SimuladorSensores;

import java.util.Random;

public class GerarValores implements Runnable {
	
	private Sensor sensor;
	private int valor;
	
	public GerarValores(Sensor s) {
		this.sensor = s;
		// TODO Auto-generated constructor stub
	}
	
	public int getValor() {
		return valor;
	}
	
	@Override
	public void run() {
		Random gerar = new Random();
				
		while (sensor.getRun()) {
			try {
				
				this.valor = gerar.nextInt((this.sensor.getFimRang() - this.sensor.getIniRange() + 1)) + this.sensor.getIniRange();
				Thread.sleep(sensor.getSleep());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
