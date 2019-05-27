package SimuladorSensores;

public class Sensor {
	
	private long sleep;
	private int iniRange;
	private int fimRang;
	private boolean run;
	private int tipoSensor;
	
	public Sensor() {
		this.run = true;
	}
	
	public Sensor(int sleep, int iniRange, int fimRange){
		this.sleep = sleep;
		this.iniRange = iniRange;
		this.fimRang = fimRange;
	}
	
	public void setAtributos(long sleep, int iniRange, int fimRang, int tipoSensor) {
		this.sleep = sleep;
		this.iniRange = iniRange;
		this.fimRang = fimRang;
		this.tipoSensor = tipoSensor;
	}
	
	public long getSleep() {
		return sleep;
	}
	
	public int getIniRange() {
		return iniRange;
	}
	
	public int getFimRang() {
		return fimRang;
	}

	public void setSleep(long sleep) {
		this.sleep = sleep;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	
	public boolean getRun(){
		return run;
	}

	public void setIniRange(int iniRange) {
		this.iniRange = iniRange;
	}

	public void setFimRang(int fimRang) {
		this.fimRang = fimRang;
	}
	
	public void setTipoSensor(int tipoSensor) {
		this.tipoSensor = tipoSensor;
	}
	
	public int getTipoSensor() {
		return tipoSensor;
	}
}
