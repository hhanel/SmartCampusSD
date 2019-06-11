package SimuladorSensores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class SimuladorSensores {
	
	
	
	public static void main(String[] args) {

		
		boolean running = false;
		
		String addrNet = "192.168.137.252"; //Endereço de IP do concentrador
		int portaTcp = 1983;
		int portaUdp = 1995;
		String opcao = "FIND";
		int sleep = 0;
		int ini = 0;
		int fim = 0;
		int id = 0;
		Scanner teclado = new Scanner(System.in);
		
		
		System.out.println("------Simulador de Sensores------");
		System.out.println("INFORME O CODIGO DO SENSOR: ");
		
		opcao = teclado.nextLine();
		opcao = opcao.toUpperCase();
		
		
		try {
			URL url = new URL("http://localhost:3002/RestWebserviceDemo/rest/json/product/dynamicData?size=5");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				teclado.close();
				throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
				
			}
        
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();
            
            final Gson gson = new GsonBuilder().create();
            JsonObject json = gson.fromJson(br.toString(), JsonObject.class);
			
            id = json.id;
			sleep = json.sleep;
			ini = json.range_inicial;
			fim = json.range_final;
			
            if (id == 0) {
            	opcao = "BYE";
            	System.out.println("sensor nao encontrado!");
			}else {
				opcao = json.comunicador;
			}
			
		}catch (Exception e) {
			System.out.println("Exception" + e);
			opcao = "BYE";
		}
		
		
		Sensor sensor = new Sensor(addrNet);
		
		
		
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
				   opcao = teclado.nextLine();
				   opcao = opcao.toUpperCase();
			}
					
		}
		
		teclado.close();
		
	}

   static class JsonObject {

    @SerializedName("id")
    public int id;
    @SerializedName("comunicador")
    public String comunicador;
    @SerializedName("sleep")
    public int sleep;
    @SerializedName("range_inicial")
    public int range_inicial;
    @SerializedName("range_final")
    public int range_final;
    
    }
	
}



