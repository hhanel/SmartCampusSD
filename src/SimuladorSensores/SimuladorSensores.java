package SimuladorSensores;

import java.util.Scanner;

public class SimuladorSensores {
	
	
	
	public static void main(String[] args) {

		
		boolean running = false;
		
		String addrNet = "192.168.56.1";
		int portaTcp = 1983;
		int portaUdp = 1995;
		Sensor sensor = new Sensor(addrNet);
		
		
		Scanner teclado = new Scanner(System.in);
		GerarValoresTCP geraValores = new GerarValoresTCP(sensor);
		GerarValoresUDP geraValoresUdp = new GerarValoresUDP(sensor);
		
		
		System.out.println("------Simulador de Sensores------");
		System.out.println("INFORME O CODIGO DO SENSOR: ");
		String opcao = "FIND";
		
		
		while (!opcao.equals("BYE")) {			
			opcao = teclado.nextLine();
			
			//procurar sensor e setar os parametros
			//sensor.setAtributos(1000, 100, 500, 1,"TCP");
			
			if (opcao.equals("FIND")) {
				System.out.println("Codigo do sensor nao encontrado, digite novamente:");
				
			} else if (opcao.equals("TCP")) {
				if (!running) {
					sensor.setAtributos(1000, 100, 500, 1,"TCP");
					sensor.setPorta(portaTcp);
					Thread gerando = new Thread(geraValores);
					gerando.start();
					running = true;
				}else {
					System.out.println("Rodando....");
				}
					
			}else if (opcao.equals("UDP")) {
				if (!running) {
					sensor.setAtributos(1000, 100, 500, 2,"UDP");
					sensor.setPorta(portaUdp);
					Thread gerando = new Thread(geraValoresUdp);
					gerando.start();
					running = true;
				}else {
				
					System.out.println("Rodando....");

				}
			}else if (opcao.equals("VALOR")){
				System.out.println("valor: " + sensor.getValor() + " Sensor: " + sensor.getIdSensor());

			}else if (opcao.equals("BYE")){
				System.out.println("Encerrando simulador");
				sensor.setRun(false);
				
			}else {
				opcao = "FIND";
			}
					
		}
		
		teclado.close();
		
	}

}
