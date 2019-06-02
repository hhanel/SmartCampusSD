package SimuladorSensores;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sensor {
	
	private String tipo;
	private long sleep;
	private int iniRange;
	private int fimRang;
	private boolean run;
	private int idSensor;
	private String addrIp;
	private int porta;
	private int valor;
	private Date time;
	private SimpleDateFormat dataFormat;
	
	
	/**
	 * Deve-se informar ip para iniciar um novo sensor
	 * 
	 * @param addrIp
	 * 
	 */
	
	public Sensor(String addrIp) {
		this.run = true;
		this.addrIp = addrIp;
		dataFormat = new SimpleDateFormat("dd/MM/yyyy';'HH:mm:ss", new Locale("pt", "br"));
	}
	
	
	public Sensor(Socket s) {
		this.run = true;
	}
	
	public Sensor(int sleep, int iniRange, int fimRange){
		this.sleep = sleep;
		this.iniRange = iniRange;
		this.fimRang = fimRange;
	}
	
	/**
	 * Configurar atributos para o sensor
	 * 
	 * 
	 * @param sleep - Tempo de geração
	 * @param iniRange - valor entre
	 * @param fimRang - valor final
	 * @param idSensor - codigo do sensor
	 * @param tipo - tipo do sensor
	 */
	
	public void setAtributos(long sleep, int iniRange, int fimRang, int idSensor, String tipo) {
		this.sleep = sleep;
		this.iniRange = iniRange;
		this.fimRang = fimRang;
		this.idSensor = idSensor;
		this.tipo = tipo;
		
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
	
	public String getAddrIp(){
		return addrIp;
	}
	
	public int getPorta() {
		return porta;
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
	
	public void setIdSensor(int tipoSensor) {
		this.idSensor = tipoSensor;
	}
	
	public int getIdSensor() {
		return idSensor;
	}
	
	public void setPorta(int porta){
		this.porta = porta;
	}
	
	public String getValor() {
		return (idSensor + ";"+ valor +";"+ dataFormat.format(time)).trim();
	}
	
	public void setValor(int valor) {
		this.valor = valor;
		time = new Date();
	}
}
