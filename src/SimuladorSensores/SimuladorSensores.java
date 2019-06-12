package SimuladorSensores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;


public class SimuladorSensores {

	public static void main(String[] args) {

		
		boolean running = false;
		
		String addrNet = "192.168.137.252"; //Endereço de IP do concentrador
		String portaWS = "5000";
		int portaTcp = 1983;
		int portaUdp = 1995;
		String opcao = "FIND";
		int sleep = 1000;
		int ini = 100;
		int fim = 500;
		int id = 0;
		Scanner teclado = new Scanner(System.in);
		

		System.out.println("------Simulador de Sensores------");
		System.out.println("INFORME O CODIGO DO SENSOR: ");
		
		opcao = teclado.nextLine();
		opcao = opcao.toUpperCase();
		
		
		try {
			URL url = new URL("http://"+addrNet+":"+portaWS+"/sensortype/"+opcao);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			System.out.println("enviando requisção");
			if (conn.getResponseCode() != 200) {
				teclado.close();
				throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
				
			}
        
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            String json = "";
            while ((output = br.readLine()) != null) {
                json = json + output;
            }
            conn.disconnect();
            
            JSONObject obj = new JSONObject(json);
            
            try {
            	sleep = Integer.parseInt(obj.getString("minimo"));
            	ini = Integer.parseInt(obj.getString("minimo"));
            	fim = Integer.parseInt(obj.getString("maximo"));
            	id = Integer.parseInt(obj.getString("id"));
            	opcao = obj.getString("comunica");
            }catch (Exception e) {
            	id = 0;
            	System.out.println("sensor nao encontrado");
            	opcao = "BYE";
            }

            

			
		}catch (Exception e) {
			System.out.println("Exception" + e);
			opcao = "BYE";
		}
		
		Sensor sensor = new Sensor(addrNet);
		
//		System.out.println("------Simulador de Sensores------");
//		System.out.println("INFORME O CODIGO DO SENSOR: ");
//		
//		opcao = teclado.nextLine();
//		opcao = opcao.toUpperCase();
			
		
		GerarValoresTCP geraValores = new GerarValoresTCP(sensor);
		GerarValoresUDP geraValoresUdp = new GerarValoresUDP(sensor);
		
		
		while (!opcao.equals("BYE")) {							
			if (opcao.equals("TCP")) {
				opcao = "";
				if (!running) {
					sensor.setAtributos(sleep, ini, fim, id,"TCP");
					sensor.setPorta(portaTcp);
					Thread gerando = new Thread(geraValores);
					gerando.start();
					running = true;
					System.out.println("Gerando valores para sensor do tipo TCP, digite BYE para sair!");
				}else {
					System.out.println("Rodando....");
				}
					
			}else if (opcao.equals("UDP")) {
				opcao = "";
				if (!running) {
					sensor.setAtributos(sleep, ini, fim, id,"UDP");
					sensor.setPorta(portaUdp);
					Thread gerando = new Thread(geraValoresUdp);
					gerando.start();
					running = true;
					System.out.println("Gerando valores para sensor do tipo UDP, digite BYE para sair!");
				}else {
				
					System.out.println("Rodando... - digite BYE para sair!");

				}
			}else if (opcao.equals("VALOR")){
				opcao = "";
				System.out.println("msg: " + sensor.getValor() + " Sensor: " + sensor.getIdSensor());

			}else if (opcao.equals("BYE")){
				System.out.println("Encerrando simulador");
				sensor.setRun(false);
				
			}else {
				System.out.println("comando incorreto");
			}
					
		}
		
		teclado.close();
		
	}

}
