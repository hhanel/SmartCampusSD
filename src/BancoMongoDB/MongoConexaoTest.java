/**
 * Classe Para Testes De Conexao Java Com Banco De Dados MongoDB
 *
 * Materia; Sistemas Distribuidos 2019/1 Trabalho Final; Smart Campus Sensors
 * Docente; Marcelo Trindade Rebonatto
 *
 * @author Discente: Willian Muller
 */
package BancoMongoDB;

import BancoMongoDB.*;
import java.io.IOException;
import java.util.ArrayList;

public class MongoConexaoTest {

	public MongoConexaoTest() {
	}

	public static void main(String[] args) throws IOException {

//        testInserirDocumentoNaColecaoTipoSensor();
//        testInserirDocumentoNaColecaoSensor();
//        testInserirDocumentoNaColecaoDados();
////-----------------------------------------------------------------------------------------
//        testColetarDocumentoNaColecaoTipoSensor();
//        testColetarDocumentoNaColecaoSensor();
//        testColetarDocumentoNaColecaoDados();
////-----------------------------------------------------------------------------------------
//        testMostrarTodosDocumentosDaColecaoTipoSensor();
//        testMostrarTodosDocumentosDaColecaoSensor();
//        testMostrarTodosDocumentosDaColecaoDados();
////-----------------------------------------------------------------------------------------
//        testAlterarDocumentoNaColecaoTipoSensor();
//        testAlterarDocumentoNaColecaoSensor();
////-----------------------------------------------------------------------------------------
//        testRemoverDocumentoDaColecaoTipoSensor();
//        testRemoverDocumentoDaColecaoSensor();
//        testRemoverDocumentoDaColecaoDados();
////-----------------------------------------------------------------------------------------
//        testExcluirBaseDados();
//        testExcluirColecaoSensor();
//        testExcluirColecaoTipoSensor();
//        testExcluirColecaoDados();
	}

//  Inserir --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testInserirDocumentoNaColecaoTipoSensor() {

		MongoConexao mongoConexao = new MongoConexao();

		// Efetua insercão
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("1;Sensor De Temperatura;TCP;30;50;1000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("2;Sensor De Ar;TCP;30;50;2000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("3;Sensor De Vento;TCP;30;50;3000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("4;Sensor De Raios;TCP;30;50;1000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("5;Sensor De Solar;TCP;30;50;2000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("6;Sensor De Terra;UDP;30;50;3000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("7;Sensor De Agua;UDP;30;50;1000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("8;Sensor De Pressao;UDP;30;50;2000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("9;Sensor De Clima;UDP;30;50;3000"));

		// Cancela insercão e acusa _ids ja existentes
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("1;Sensor De Temperatura;TCP;30;50;1000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("2;Sensor De Ar;TCP;30;50;2000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("3;Sensor De Vento;TCP;30;50;3000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("4;Sensor De Raios;TCP;30;50;1000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("5;Sensor De Solar;TCP;30;50;2000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("6;Sensor De Terra;UDP;30;50;3000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("7;Sensor De Agua;UDP;30;50;1000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("8;Sensor De Pressao;UDP;30;50;2000"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoTipoSensor("9;Sensor De Clima;UDP;30;50;3000"));
	}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testInserirDocumentoNaColecaoSensor() {

		MongoConexao mongoConexao = new MongoConexao();

		// Efetua insercão
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("11;ICEG;28.1;30.4;1"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("12;FEAR;28.1;30.4;2"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("13;DCE;28.1;30.4;3"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("14;PARQ;28.1;30.4;4"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("15;CAMP1;28.1;30.4;5"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("16;CAMP2;28.1;30.4;6"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("17;CAMP3;28.1;30.4;7"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("18;CAMP4;28.1;30.4;8"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("19;CAMP5;28.1;30.4;3"));

		// Cancela insercão e acusa _ids ja existentes
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("11;ICEG;28.1;30.4;1"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("12;FEAR;28.1;30.4;2"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("13;DCE;28.1;30.4;3"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("14;PARQ;28.1;30.4;4"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("15;CAMP1;28.1;30.4;5"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("16;CAMP2;28.1;30.4;6"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("17;CAMP3;28.1;30.4;7"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("18;CAMP4;28.1;30.4;8"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("19;CAMP5;28.1;30.4;3"));

		// Cancela insercão e acusa Tipos inexistentes
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("21;ICEG;28.1;30.4;11"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("22;FEAR;28.1;30.4;32"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("23;DCE;28.1;30.4;19"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("24;PARQ;28.1;30.4;34"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("25;CAMP1;28.1;30.4;15"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("26;CAMP2;28.1;30.4;26"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("27;CAMP3;28.1;30.4;27"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("28;CAMP4;28.1;30.4;18"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoSensor("29;CAMP5;28.1;30.4;33"));
	}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testInserirDocumentoNaColecaoDados() {

		MongoConexao mongoConexao = new MongoConexao();

		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("17;42;06/13/2019;10:28:35"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("19;31;06/13/2019;11:29:36"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("13;36;06/13/2019;12:30:37"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("12;47;06/13/2019;13:31:38"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("11;50;06/13/2019;14:32:39"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("13;39;06/13/2019;15:33:40"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("14;45;06/13/2019;16:34:41"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("11;50;06/14/2019;17:35:42"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("16;35;06/14/2019;18:36:43"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("11;41;06/14/2019;19:37:44"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("12;43;06/14/2019;20:38:45"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("15;34;06/14/2019;21:39:46"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("13;49;06/14/2019;22:40:47"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("16;30;06/15/2019;23:41:48"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("19;39;06/15/2019;01:42:49"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("14;37;06/15/2019;02:43:50"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("17;48;06/15/2019;03:44:51"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("15;47;06/15/2019;04:46:52"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("12;38;06/15/2019;05:47:53"));
		System.out.println(mongoConexao.inserirDocumentoNaColecaoDados("19;44;06/15/2019;06:48:54"));
	}

//   Coletar --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testColetarDocumentoNaColecaoTipoSensor() {

		MongoConexao mongoConexao = new MongoConexao();
		String vetor[];

		// Efetua Coleta
		for (int i = 1; i <= 9; ++i) {
			vetor = mongoConexao.coletarDocumentoNaColecaoTipoSensor(i, 1, 1, 1, 1, 1);
			System.out.println("\nId: " + vetor[0] + "\nDescricao: " + vetor[1] + "\nComunica: " + vetor[2] + "\nMinimo: " + vetor[3] + "\nMaximo: " + vetor[4] + "\nIntervalo: " + vetor[5]);
		}
		// Acusa que Id nao foi encontrado.
		for (int i = 10; i <= 19; ++i) {
			vetor = mongoConexao.coletarDocumentoNaColecaoTipoSensor(i, 1, 1, 1, 1, 1);
			System.out.println("\nId: " + vetor[0] + "\nDescricao: " + vetor[1] + "\nComunica: " + vetor[2] + "\nMinimo: " + vetor[3] + "\nMaximo: " + vetor[4] + "\nIntervalo: " + vetor[5]);
		}
	}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testColetarDocumentoNaColecaoSensor() {

		MongoConexao mongoConexao = new MongoConexao();
		String vetor[];

		// Acusa que Id nao foi encontrado.
		for (int i = 1; i <= 10; ++i) {
			vetor = mongoConexao.coletarDocumentoNaColecaoSensor(i, 0, 1, 1, 1);
			System.out.println("\nId: " + vetor[0] + "\nDescricao: " + vetor[1] + "\nLatitude: " + vetor[2] + "\nLongitude: " + vetor[3] + "\nTipo: " + vetor[4]);
		}
		// Efetua Coleta
		for (int i = 11; i <= 19; ++i) {
			vetor = mongoConexao.coletarDocumentoNaColecaoSensor(i, 0, 1, 1, 1);
			System.out.println("\nId: " + vetor[0] + "\nDescricao: " + vetor[1] + "\nLatitude: " + vetor[2] + "\nLongitude: " + vetor[3] + "\nTipo: " + vetor[4]);
		}
	}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testColetarDocumentoNaColecaoDados() {

		MongoConexao mongoConexao = new MongoConexao();
		ArrayList<String[]> listaDocumentosColetado;

		// Encontra Documento
		listaDocumentosColetado = mongoConexao.coletarDocumentoNaColecaoDados(11, 1, 1, 1);

		for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
			System.out.println("\nID: " + listaDocumentosColetado.get(i)[0] + "\nValor: " + listaDocumentosColetado.get(i)[1] + "\nData: " + listaDocumentosColetado.get(i)[2] + "\nHora: " + listaDocumentosColetado.get(i)[3]);
		}
	}

	// Mostrar Todos-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testMostrarTodosDocumentosDaColecaoTipoSensor() {

		MongoConexao mongoConexao = new MongoConexao();
		ArrayList<String[]> listaDocumentosColetado = mongoConexao.mostrarTodosDocumentosDaColecaoTipoSensor();

		for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
			System.out.println("\n_id: " + listaDocumentosColetado.get(i)[0] + "\nDescricao: " + listaDocumentosColetado.get(i)[1] + "\nComunica: " + listaDocumentosColetado.get(i)[2] + "\nMinimo: " + listaDocumentosColetado.get(i)[3] + "\nMaximo: " + listaDocumentosColetado.get(i)[4] + "\nIntervalo: " + listaDocumentosColetado.get(i)[5]);
		}
	}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testMostrarTodosDocumentosDaColecaoSensor() {

		MongoConexao mongoConexao = new MongoConexao();
		ArrayList<String[]> listaDocumentosColetado = mongoConexao.mostrarTodosDocumentosDaColecaoSensor();

		for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
			System.out.println("\n_id: " + listaDocumentosColetado.get(i)[0] + "\nDescricao: " + listaDocumentosColetado.get(i)[1] + "\nLatitude: " + listaDocumentosColetado.get(i)[2] + "\nLongitude: " + listaDocumentosColetado.get(i)[3] + "\nTipo: " + listaDocumentosColetado.get(i)[4]);
		}
	}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testMostrarTodosDocumentosDaColecaoDados() {

		MongoConexao mongoConexao = new MongoConexao();
		ArrayList<String[]> listaDocumentosColetado = mongoConexao.mostrarTodosDocumentosDaColecaoDados();

		for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
			System.out.println("\nID: " + listaDocumentosColetado.get(i)[0] + "\nValor: " + listaDocumentosColetado.get(i)[1] + "\nData: " + listaDocumentosColetado.get(i)[2] + "\nHora: " + listaDocumentosColetado.get(i)[3]);
		}
	}

// Alterar -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testAlterarDocumentoNaColecaoTipoSensor() {
		MongoConexao mongoConexao = new MongoConexao();

		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "_id", "4"));                                    // Acusa jnao poder alterar chave primaria

		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Descricao", "Sensor De RUV"));  // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Descricao", "Sensor De RUV"));  // Acusa ja estar com este valor

		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Comunica", "UDP"));                   // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Comunica", "UDP"));                   // Acusa ja estar com este valor
		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Comunica", "TDP"));                   // Acusa comunicacao invalida

		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Minimo", "31"));                           // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Minimo", "31"));                           // Acusa ja estar com este valor

		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Maximo", "49"));                         // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Maximo", "49"));                         // Acusa ja estar com este valor

		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Intervalo", "1000"));                   // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoTipoSensor(3, "Intervalo", "1000"));                   // Acusa ja estar com este valor

	}

// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testAlterarDocumentoNaColecaoSensor() {

		MongoConexao mongoConexao = new MongoConexao();

		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "_id", "12"));                                  // Acusa jnao poder alterar chave primaria

		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Tipo", "6"));                                 // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Tipo", "6"));                                 // Acusa ja estar com este valor
		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Tipo", "13"));                               // Acusa tipo invalido

		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Descricao", "DCE"));                   // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Descricao", "DCE"));                   // Acusa ja estar com este valor

		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Latitude", "25.2"));                       // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Latitude", "25.2"));                       // Acusa ja estar com este valor

		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Longitude", "28.6"));                    // Atualiza
		System.out.println(mongoConexao.alterarDocumentoNaColecaoSensor(14, "Longitude", "28.6"));                    // Acusa ja estar com este valor
	}

	// Remover ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testRemoverDocumentoDaColecaoTipoSensor() {

		MongoConexao mongoConexao = new MongoConexao();

		for (int i = 1; i <= 20; ++i) {
			System.out.println(mongoConexao.removerDocumentoDaColecaoTipoSensor(i));
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testRemoverDocumentoDaColecaoSensor() {

		MongoConexao mongoConexao = new MongoConexao();

		for (int i = 1; i <= 20; ++i) {
			System.out.println(mongoConexao.removerDocumentoDaColecaoSensor(i));
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testRemoverDocumentoDaColecaoDados() {

		MongoConexao mongoConexao = new MongoConexao();

		for (int i = 1; i <= 20; ++i) {
			System.out.println(mongoConexao.removerDocumentoDaColecaoDados(i));
		}
	}

	// Excluir Base De Dados-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testExcluirBaseDados() {
		MongoConexao mongoConexao = new MongoConexao();
		mongoConexao.excluirBaseDados();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testExcluirColecaoSensor() {
		MongoConexao mongoConexao = new MongoConexao();
		mongoConexao.excluirColecaoSensor();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testExcluirColecaoTipoSensor() {
		MongoConexao mongoConexao = new MongoConexao();
		mongoConexao.excluirColecaoTipoSensor();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void testExcluirColecaoDados() {
		MongoConexao mongoConexao = new MongoConexao();
		mongoConexao.excluirColecaoDados();
	}
}
