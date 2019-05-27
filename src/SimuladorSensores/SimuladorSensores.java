package SimuladorSensores;

import java.util.Scanner;

public class SimuladorSensores {
	
	
	
	public static void main(String[] args) {

		boolean running = false;
		Sensor sensor = new Sensor();
		Scanner teclado = new Scanner(System.in);
		GerarValores geraValores = new GerarValores(sensor);
		
		
		System.out.println("------Simulador de Sensores------");
		System.out.println("1:Condições Climaticas - 2:Qualidade do ar - 9:Sair");
		System.out.println("INFORME O TIPO DO SENSOR: ");
		String opcao = "";
		
		while (!opcao.equals("9")) {
			opcao = teclado.nextLine();
			if (opcao.equals("1")) {
				if (!running) {
					sensor.setAtributos(1000, 100, 500, 1);
					Thread gerando = new Thread(geraValores);
					gerando.start();
					running = true;
				}else {
					System.out.println("Rodando....");
				}
					
			}else if (opcao.equals("2")) {
				if (!running) {
					sensor.setAtributos(1000, 100, 500, 2);
					Thread gerando = new Thread(geraValores);
					gerando.start();
					running = true;
				}else {
					System.out.println("Rodando....");
				}
			}else if (opcao.equals("3")){
				System.out.println("valor: " + geraValores.getValor() + " Sensor do tipo: " + sensor.getTipoSensor());
			}
			else if (opcao.equals("4")){
				sensor.setSleep(500);
			}else if (opcao.equals("5")){
				sensor.setSleep(2000);
			}else if (opcao.equals("9")){
				System.out.println("Encerrando simulador");
				sensor.setRun(false);
			}else {
				System.out.println("Tipo incorreto!");
			}
					
		}
		
		teclado.close();
		
	}

}
