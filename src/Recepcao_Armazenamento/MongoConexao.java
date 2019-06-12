package BancoMongoDB;

/**
 * Classe Para Conexao Java Com Banco De Dados MongoDB
 *
 * Materia; Sistemas Distribuidos 2019/1 Trabalho Final; Smart Campus Sensors
 * Docente; Marcelo Trindade Rebonatto
 *
 * @author Discente: Willian Muller
 */
import com.mongodb.*;
import com.mongodb.connection.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MongoConexao {

	DB baseDados;
	DBCollection colecao;

	// Conexao -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Conecta com o banco MongoDB atraves do localhost seguido da porta padrao
	 * do MongoDB :27017. Caso nao possua uma colecao, faz a criacao dela,
	 */
	public MongoConexao() {

		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);

		try {
			Mongo mongo = new Mongo("localhost", 27017);
			baseDados = mongo.getDB("SmartCampusSensores");
		} catch (Exception e) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	// Inserir --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Insere na colecao Tipo Sensor um novo documento.
	 *
	 * @param dadosTipoSensor Uma string subdividida por ponto e virgula, cada
	 * posicao contem 1 dos 6 dados coletados sobre o cadastro de 1 tipo sensor.
	 *
	 * Exemplo string valida: "_id;Descricao;Comunica;Minimo;Maximo;Intervalo"
	 *
	 * @return String com status do sucesso ou nao durante a insercao.
	 */
	public String inserirDocumentoNaColecaoTipoSensor(String dadosTipoSensor) {

		String[] vetorDadosTipoSensor = dadosTipoSensor.split(";");
		BasicDBObject novoDocumento = new BasicDBObject();

		novoDocumento.put("_id", Integer.parseInt(vetorDadosTipoSensor[0]));
		novoDocumento.put("Descricao", vetorDadosTipoSensor[1]);
		novoDocumento.put("Comunica", vetorDadosTipoSensor[2]);
		novoDocumento.put("Minimo", Integer.parseInt(vetorDadosTipoSensor[3]));
		novoDocumento.put("Maximo", Integer.parseInt(vetorDadosTipoSensor[4]));
		novoDocumento.put("Intervalo", Integer.parseInt(vetorDadosTipoSensor[5]));

		if (validarExistenciaDoTipoSensor(Integer.parseInt(vetorDadosTipoSensor[0]))) {
			return "CANCELADA;INSERCAO;TIPOSENSOR;_id (" + vetorDadosTipoSensor[0] + ") ja esta cadastrado na colecao.";
		} else {
			colecao = baseDados.getCollection("TipoSensor");
			colecao.insert(novoDocumento);
			return "EFETUADA;INSERCAO;TIPOSENSOR";
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Insere na colecao Sensor um novo documento.
	 *
	 * @param dadosSensor Uma string subdividida por ponto e virgula, cada
	 * posicao contem um dos 5 dados coletados sobre o cadastro de um sensor.
	 *
	 * Exemplo de string valida: " _id;Descricao;Latitude;Longitude;Tipo "
	 *
	 * @return String com status do sucesso ou nao durante a insercao.
	 */
	public String inserirDocumentoNaColecaoSensor(String dadosSensor) {

		String[] vetorDadosSensor = dadosSensor.split(";");
		BasicDBObject novoDocumento = new BasicDBObject();

		novoDocumento.put("_id", Integer.parseInt(vetorDadosSensor[0]));
		novoDocumento.put("Descricao", vetorDadosSensor[1]);
		novoDocumento.put("Latitude", Double.parseDouble(vetorDadosSensor[2]));
		novoDocumento.put("Longitude", Double.parseDouble(vetorDadosSensor[3]));
		novoDocumento.put("Tipo", Integer.parseInt(vetorDadosSensor[4]));

		if (validarExistenciaDoSensor(Integer.parseInt(vetorDadosSensor[0]))) {
			return "CANCELADA;INSERCAO;SENSOR;_id (" + vetorDadosSensor[0] + ") ja esta cadastrado na colecao.";
		} else {
			if (validarExistenciaDoTipoSensor(Integer.parseInt(vetorDadosSensor[4]))) {
				colecao = baseDados.getCollection("Sensor");
				colecao.insert(novoDocumento);
				return "EFETUADA;INSERCAO;SENSOR";
			} else {
				return "CANCELADA;INSERCAO;SENSOR;O tipo deste sensor (" + vetorDadosSensor[4] + ") nao e valido.";
			}
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Insere na colecao Dados um novo documento.
	 *
	 * @param dadoColetado Uma string subdividida por ponto e virgula, cada
	 * posicao contem um dos 4 dados coletados sobre um sensor.
	 *
	 * Exemplo de string valida: " ID;Valor;Data;Hora "
	 *
	 * @return String com status do sucesso ou nao durante a insercao.
	 */
	public String inserirDocumentoNaColecaoDados(String dadoColetado) {

		String[] vetorDadoColetado = dadoColetado.split(";");
		BasicDBObject novoDocumento = new BasicDBObject();

		novoDocumento.put("ID", Integer.parseInt(vetorDadoColetado[0]));
		novoDocumento.put("Valor", Integer.parseInt(vetorDadoColetado[1]));
		novoDocumento.put("Data", new Date(vetorDadoColetado[2]));
		novoDocumento.put("Hora", validarHora(vetorDadoColetado[3]));

		colecao = baseDados.getCollection("Dados");
		colecao.insert(novoDocumento);

		return "EFETUADA;INSERCAO;DADOS";
	}

	// Coletar --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Coleta um documento da colecao Tipo Sensor.
	 *
	 * @param _id Utilize o Identificador do tipo sensor a coletar dados na
	 * colecao.
	 * @param exibirDescricao Utilize 1 para coletar ou 0 para nao coletar a
	 * Descricao.
	 * @param exibirComunica Utilize 1 para coletar ou 0 para nao coletar a
	 * Comunica.
	 * @param exibirMinimo Utilize 1 para coletar ou 0 para nao coletar o
	 * Minimo.
	 * @param exibirMaximo Utilize 1 para coletar ou 0 para nao coletar o
	 * Maximo.
	 * @param exibirIntervalo Utilize 1 para coletar ou 0 para nao coletar o
	 * Intervalo.
	 * @return Um vetor string de 6 posicoes com todos os dados do documento
	 * encontrado na colecao. Seu conteudo se organiza desta forma no vetor:
	 * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Comunica,vetor[3]=Minimo,vetor[4]=Maximo,vetor[5]=Intervalo,
	 */
	public String[] coletarDocumentoNaColecaoTipoSensor(int _id, int exibirDescricao, int exibirComunica, int exibirMinimo, int exibirMaximo, int exibirIntervalo) {

		DBCollection colecaoParaProcurar = baseDados.getCollection("TipoSensor");

		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		DBCursor documentosEncontrados = colecaoParaProcurar.find(documentoProcurado);
		String dadosColetados[] = {"", "", "", "", "", ""};

		if (documentosEncontrados.count() > 0) {
			DBObject documentoEncontrado = documentosEncontrados.next();
			dadosColetados[0] = documentoEncontrado.get("_id").toString();
			if (exibirDescricao == 1) {
				dadosColetados[1] = documentoEncontrado.get("Descricao").toString();
			}
			if (exibirComunica == 1) {
				dadosColetados[2] = documentoEncontrado.get("Comunica").toString();
			}
			if (exibirMinimo == 1) {
				dadosColetados[3] = documentoEncontrado.get("Minimo").toString();
			}
			if (exibirMaximo == 1) {
				dadosColetados[4] = documentoEncontrado.get("Maximo").toString();
			}
			if (exibirIntervalo == 1) {
				dadosColetados[5] = documentoEncontrado.get("Intervalo").toString();
			}
			return dadosColetados;
		} else {
			dadosColetados[0] = "Tipo Sencor Com ID(" + String.valueOf(_id) + ") Nao Encontrado";
			return dadosColetados;
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Coleta um documento da colecao Sensor.
	 *
	 * @param _id Utilize o Identificador do sensor a coletar dados na colecao.
	 * @param exibirDescricao Utilize 1 para coletar ou 0 para nao coletar a
	 * Descricao.
	 * @param exibirLatitude Utilize 1 para coletar ou 0 para nao coletar a
	 * Latitude.
	 * @param exibirLongitude Utilize 1 para coletar ou 0 para nao coletar a
	 * Longitude.
	 * @param exibirTipo Utilize 1 para coletar ou 0 para nao coletar o Tipo.
	 *
	 * @return Um vetor string de 5 posicoes com todos os dados do documento
	 * encontrado na colecao. Seu conteudo se organiza desta forma no vetor:
	 * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Latitude,vetor[3]=Longitude,vetor[4]=Tipo,
	 */
	public String[] coletarDocumentoNaColecaoSensor(int _id, int exibirDescricao, int exibirLatitude, int exibirLongitude, int exibirTipo) {

		DBCollection colecaoParaProcurar = baseDados.getCollection("Sensor");

		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		DBCursor documentosEncontrados = colecaoParaProcurar.find(documentoProcurado);
		String dadosColetados[] = {"", "", "", "", ""};

		if (documentosEncontrados.count() > 0) {
			DBObject documentoEncontrado = documentosEncontrados.next();

			dadosColetados[0] = documentoEncontrado.get("_id").toString();
			if (exibirDescricao == 1) {
				dadosColetados[1] = documentoEncontrado.get("Descricao").toString();
			}
			if (exibirLatitude == 1) {
				dadosColetados[2] = documentoEncontrado.get("Latitude").toString();
			}
			if (exibirLongitude == 1) {
				dadosColetados[3] = documentoEncontrado.get("Longitude").toString();
			}
			if (exibirTipo == 1) {
				dadosColetados[4] = documentoEncontrado.get("Tipo").toString();
			}
			return dadosColetados;
		} else {
			dadosColetados[0] = "Sensor Com ID(" + String.valueOf(_id) + ") Nao Encontrado";
			return dadosColetados;
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Coleta um ou mais documento(s) da colecao Dados.
	 *
	 * @param _id Utilize o Identificador do sensor a coletar dados na colecao.
	 * @param exibirValor Utilize 1 para coletar ou 0 para nao coletar o Valor.
	 * @param exibirData Utilize 1 para coletar ou 0 para nao coletar a Data.
	 * @param exibirHora Utilize 1 para coletar ou 0 para nao coletar a Hora.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 4
	 * posicoes com todos os dados dos documentos encontrados na colecao. O
	 * conteudo de cada array string se organiza desta forma em cada nodo:
	 * vetor[0]=ID,vetor[1]=Valor,vetor[2]=Data,vetor[3]=Hora.
	 */
	public ArrayList<String[]> coletarDocumentoNaColecaoDados(int _id, int exibirValor, int exibirData, int exibirHora) {

		DBCollection colecaoParaProcurar = baseDados.getCollection("Dados");

		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("ID", _id);

		DBCursor documentosEncontrados = colecaoParaProcurar.find(documentoProcurado);
		ArrayList<String[]> listaDocumentosEncontrados = new ArrayList<String[]>();

		while (documentosEncontrados.hasNext()) {
			DBObject documentoColetado = documentosEncontrados.next();

			String dadosColetados[] = {"", "", "", ""};
			dadosColetados[0] = documentoColetado.get("ID").toString();
			if (exibirValor == 1) {
				dadosColetados[1] = documentoColetado.get("Valor").toString();
			}
			if (exibirData == 1) {
				dadosColetados[2] = documentoColetado.get("Data").toString();
			}
			if (exibirHora == 1) {
				dadosColetados[3] = documentoColetado.get("Hora").toString();
			}
			listaDocumentosEncontrados.add(dadosColetados);
		}

		return listaDocumentosEncontrados;
	}

	// Mostrar -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exibe todos os documentos da colecao Tipo Sensor.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 6
	 * posicoes com todos os dados dos documentos encontrados na colecao. O
	 * conteudo de cada array string se organiza desta forma em cada nodo:
	 * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Comunica,vetor[3]=Minimo,vetor[4]=Maximo,vetor[5]=Intervalo.
	 */
	public ArrayList<String[]> mostrarTodosDocumentosDaColecaoTipoSensor() {

		DBCollection colecaoParaMostrar = baseDados.getCollection("TipoSensor");
		DBCursor documentosEncontrados = colecaoParaMostrar.find();
		ArrayList<String[]> listaDocumentosEncontrados = new ArrayList();

		while (documentosEncontrados.hasNext()) {
			DBObject documentoColetado = documentosEncontrados.next();
			String dadosColetados[] = {"", "", "", "", "", ""};

			dadosColetados[0] = documentoColetado.get("_id").toString();
			dadosColetados[1] = documentoColetado.get("Descricao").toString();
			dadosColetados[2] = documentoColetado.get("Comunica").toString();
			dadosColetados[3] = documentoColetado.get("Minimo").toString();
			dadosColetados[4] = documentoColetado.get("Maximo").toString();
			dadosColetados[5] = documentoColetado.get("Intervalo").toString();

			listaDocumentosEncontrados.add(dadosColetados);
		}

		return listaDocumentosEncontrados;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exibe todos os documentos da colecao Sensor.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 5
	 * posicoes com todos os dados dos documentos encontrados na colecao. O
	 * conteudo de cada array string se organiza desta forma em cada nodo:
	 * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Latitude,vetor[3]=Longitude,vetor[4]=Tipo.
	 */
	public ArrayList<String[]> mostrarTodosDocumentosDaColecaoSensor() {

		DBCollection colecaoParaMostrar = baseDados.getCollection("Sensor");
		DBCursor documentosEncontrados = colecaoParaMostrar.find();
		ArrayList<String[]> listaDocumentosEncontrados = new ArrayList();

		while (documentosEncontrados.hasNext()) {
			DBObject documentoColetado = documentosEncontrados.next();
			String dadosColetados[] = {"", "", "", "", ""};

			dadosColetados[0] = documentoColetado.get("_id").toString();
			dadosColetados[1] = documentoColetado.get("Descricao").toString();
			dadosColetados[2] = documentoColetado.get("Latitude").toString();
			dadosColetados[3] = documentoColetado.get("Longitude").toString();
			dadosColetados[4] = documentoColetado.get("Tipo").toString();

			listaDocumentosEncontrados.add(dadosColetados);
		}

		return listaDocumentosEncontrados;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exibe todos os documentos da colecao Dados.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 4
	 * posicoes com todos os dados dos documentos encontrados na colecao. O
	 * conteudo de cada array string se organiza desta forma em cada nodo:
	 * vetor[0]=ID,vetor[1]=Valor,vetor[2]=Data,vetor[3]=Hora.
	 */
	public ArrayList<String[]> mostrarTodosDocumentosDaColecaoDados() {

		DBCollection colecaoParaMostrar = baseDados.getCollection("Dados");
		DBCursor documentosEncontrados = colecaoParaMostrar.find();
		ArrayList<String[]> listaDocumentosEncontrados = new ArrayList();

		while (documentosEncontrados.hasNext()) {
			DBObject documentoColetado = documentosEncontrados.next();
			String dadosColetados[] = {"", "", "", ""};

			dadosColetados[0] = documentoColetado.get("ID").toString();
			dadosColetados[1] = documentoColetado.get("Valor").toString();
			dadosColetados[2] = documentoColetado.get("Data").toString();
			dadosColetados[3] = documentoColetado.get("Hora").toString();

			listaDocumentosEncontrados.add(dadosColetados);
		}

		return listaDocumentosEncontrados;
	}

	// Alterar --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Aletra um campo da colecao Tipo Sensor.
	 *
	 * @param _id Inteiro com o identificador do sensor a ser alterado.
	 * @param nomeCampoAtualizar String com o nome do campo a ser alterado.
	 * @param novoValor String com o novo valor do campo.
	 *
	 * @return String com status do sucesso ou nao durante a alteracao.
	 */
	public String alterarDocumentoNaColecaoTipoSensor(int _id, String nomeCampoAtualizar, String novoValor) {

		nomeCampoAtualizar = nomeCampoAtualizar.toUpperCase();

		colecao = baseDados.getCollection("TipoSensor");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);
		DBCursor documentosEncontrados = colecao.find(documentoProcurado);
		DBObject documentoEncontrado = documentosEncontrados.next();

		if (validarExistenciaDoTipoSensor(_id)) {
			if ("_ID".equals(nomeCampoAtualizar) || "ID".equals(nomeCampoAtualizar)) {
				return "CANCELADA;ALTERACAO;TIPOSENSOR;O identificador (" + _id + ") e uma chave primaria e nao pode ser alterado";
			} else {
				colecao = baseDados.getCollection("TipoSensor");
				DBObject documentoPrrocurar = new BasicDBObject("_id", _id);
				if ("DESCRICAO".equals(nomeCampoAtualizar)) {
					if (documentoEncontrado.get("Descricao").toString().equals(novoValor)) {
						return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para a Descricao e o mesmo valor que ela contem neste momento, mantem-se";
					} else {
						DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Descricao", novoValor));
						DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
						return "EFETUADA;ALTERACAO;TIPOSENSOR;DESCRICAO";
					}
				} else {
					if ("COMUNICA".equals(nomeCampoAtualizar)) {
						if ("TCP".equals(novoValor) || "UDP".equals(novoValor)) {
							if (documentoEncontrado.get("Comunica").toString().equals(novoValor)) {
								return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para a Comunicacao e o mesmo valor que ela contem neste momento, mantem-se";
							} else {
								DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Comunica", novoValor));
								DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
								return "EFETUADA;ALTERACAO;TIPOSENSOR;COMUNICA";
							}
						} else {
							return "CANCELADA;ALTERACAO;TIPOSENSOR;(" + novoValor + ") Nao e um valor de Comunicacao valido na colecao Tipo Sensor";
						}
					} else {
						if ("MINIMO".equals(nomeCampoAtualizar)) {
							if (documentoEncontrado.get("Minimo").toString().equals(novoValor)) {
								return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para o Minimo e o mesmo valor que ela contem neste momento, mantem-se";
							} else {
								DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Minimo", Integer.parseInt(novoValor)));
								DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
								return "EFETUADA;ALTERACAO;TIPOSENSOR;MINIMO";

							}
						} else {
							if ("MAXIMO".equals(nomeCampoAtualizar)) {
								if (documentoEncontrado.get("Maximo").toString().equals(novoValor)) {
									return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para o Maximo e o mesmo valor que ela contem neste momento, mantem-se";
								} else {
									DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Maximo", Integer.parseInt(novoValor)));
									DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
									return "EFETUADA;ALTERACAO;TIPOSENSOR;MAXIMO";
								}
							} else {
								if (documentoEncontrado.get("Intervalo").toString().equals(novoValor)) {
									return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para o Intervalo e o mesmo valor que ela contem neste momento, mantem-se";
								} else {
									DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Intervalo", Integer.parseInt(novoValor)));
									DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
									return "EFETUADA;ALTERACAO;TIPOSENSOR;INTERVALO";
								}
							}
						}
					}
				}
			}
		} else {
			return "CANCELADA;ALTERACAO;TIPOSENSOR;O identificador( " + _id + ") nao foi encontrado na colecao.";
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Altera um documento na colecao Sensor.
	 *
	 * @param _id Inteiro com o identificador do sensor a ser alterado.
	 * @param nomeCampoAtualizar String com o nome do campo a ser alterado.
	 * @param novoValor String com o novo valor do campo.
	 *
	 * @return String com status do sucesso ou nao durante a alteracao.
	 */
	public String alterarDocumentoNaColecaoSensor(int _id, String nomeCampoAtualizar, String novoValor) {

		nomeCampoAtualizar = nomeCampoAtualizar.toUpperCase();

		colecao = baseDados.getCollection("Sensor");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);
		DBCursor documentosEncontrados = colecao.find(documentoProcurado);
		DBObject documentoEncontrado = documentosEncontrados.next();

		if (validarExistenciaDoSensor(_id)) {
			if ("_ID".equals(nomeCampoAtualizar) || "ID".equals(nomeCampoAtualizar)) {
				return "CANCELADA;ALTERACAO;SENSOR;O identificador (" + _id + ") e uma chave primaria e nao pode ser alterado";
			} else {
				if ("TIPO".equals(nomeCampoAtualizar)) {
					if (documentoEncontrado.get("Tipo").toString().equals(novoValor)) {
						return "CANCELADA;ALTERACAO;SENSOR;O novo valor para o Tipo e o mesmo valor que ele contem neste momento, mantem-se";
					} else {
						if (validarExistenciaDoTipoSensor(Integer.parseInt(novoValor))) {
							colecao = baseDados.getCollection("Sensor");
							DBObject documentoPrrocurar = new BasicDBObject("_id", _id);
							DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Tipo", Integer.parseInt(novoValor)));
							DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
							return "EFETUADA;ALTERACAO;SENSOR;TIPO";
						} else {
							return "CANCELADA;ALTERACAO;SENSOR;O tipo (" + novoValor + ") nao e valido na coleccao Tipo Sensor";
						}
					}
				} else {
					colecao = baseDados.getCollection("Sensor");
					DBObject documentoPrrocurar = new BasicDBObject("_id", _id);
					if ("DESCRICAO".equals(nomeCampoAtualizar)) {
						if (documentoEncontrado.get("Descricao").toString().equals(novoValor)) {
							return "CANCELADA;ALTERACAO;SENSOR;O novo valor para a  Descricao e o mesmo valor que ela contem neste momento, mantem-se";
						} else {
							DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Descricao", novoValor));
							DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
							return "EFETUADA;ALTERACAO;SENSOR;DESCRICAO";
						}
					} else {
						if ("LATITUDE".equals(nomeCampoAtualizar)) {
							if (documentoEncontrado.get("Latitude").toString().equals(novoValor)) {
								return "CANCELADA;ALTERACAO;SENSOR;O novo valor para a Latitude e o mesmo valor que ela contem neste momento, mantem-se";
							} else {
								DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Latitude", Double.parseDouble(novoValor)));
								DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
								return "EFETUADA;ALTERACAO;SENSOR;LATITUDE";
							}
						} else {
							if (documentoEncontrado.get("Longitude").toString().equals(novoValor)) {
								return "CANCELADA;ALTERACAO;SENSOR;O novo valor para a  Longitude e o mesmo valor que ela contem neste momento, mantem-se";
							} else {
								DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Longitude", Double.parseDouble(novoValor)));
								DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
								return "EFETUADA;ALTERACAO;SENSOR;LONGITUDE";
							}
						}
					}
				}
			}
		} else {
			return "CANCELADA;ALTERACAO;SENSOR;O identificador( " + _id + ") nao foi encontrado na colecao.";
		}
	}

// Remover -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Remove um documento da colecao Tipo Sensor.
	 *
	 * @param _id Utilize o Identificador do sensor a ser removido da colecao.
	 *
	 * @return Valor booleano com relacao ao sucesso ou nao durante a remocao.
	 */
	public String removerDocumentoDaColecaoTipoSensor(int _id) {

		BasicDBObject documentoParaRemover = new BasicDBObject();
		documentoParaRemover.put("_id", _id);

		if (validarExistenciaDoTipoSensor(_id)) {
			if (validarExistenciaDeSensorComTipoAtrelado(_id)) {
				return "CANCELADA;REMOCAO;TIPOSENSOR;O TipoSensor com identificador (" + _id + ") esta atrelado a um ou mais sensores da colecao Sensor";
			} else {
				colecao = baseDados.getCollection("TipoSensor");
				colecao.remove(documentoParaRemover);
				return "EFETUADA;REMOCAO;TIPOSENSOR";
			}
		} else {
			return "CANCELADA;REMOCAO;TIPOSENSOR;O identificador (" + _id + ") nao possui cadastro na colecao Tipo Sensor";
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Remove um documento da colecao Sensor.
	 *
	 * @param _id Utilize o Identificador do sensor a ser removido da colecao.
	 *
	 * @return Valor booleano com relacao ao sucesso ou nao durante a remocao.
	 */
	public String removerDocumentoDaColecaoSensor(int _id) {

		BasicDBObject documentoParaRemover = new BasicDBObject();
		documentoParaRemover.put("_id", _id);

		if (validarExistenciaDoSensor(_id)) {
			if (validarExistenciaDeDadosDesteSensor(_id)) {
				return "CANCELADA;REMOCAO;SENSOR;O sensor com ID (" + _id + ") ja esta vinculado a dados gerados;";
			} else {
				colecao = baseDados.getCollection("Sensor");
				colecao.remove(documentoParaRemover);
				return "EFETUADA;REMOCAO;SENSOR";
			}
		} else {
			return "CANCELADA;REMOCAO;SENSOR;O identificador (" + _id + ") nao possui cadastro na colecao Sensor";
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Remove um documento da colecao Dados.
	 *
	 * @param _id Utilize o Identificador do sensor a ser removido da colecao.
	 *
	 * @return Valor booleano com relacao ao sucesso ou nao durante a remocao.
	 */
	public String removerDocumentoDaColecaoDados(int _id) {

		colecao = baseDados.getCollection("Dados");

		BasicDBObject documentoParaRemover = new BasicDBObject();
		documentoParaRemover.put("ID", _id);

		if (validarExistenciaDeDadosDesteSensor(_id)) {
			colecao.remove(documentoParaRemover);
			return "EFETUADA;REMOCAO;DADOS";
		} else {
			return "CANCELADA;REMOCAO;DADOS;O ID (" + _id + ") nao possui dados gerados na colecao Dadosr";
		}
	}

	// Validacoes ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Valida se determinado id existe ou nao na colecao TipoSensor
	 *
	 * @param _id Inteiro com o identificador que se deseja analisar.
	 *
	 * @return Valor booleano com verdadeiro caso o documento ja esteja na
	 * colecao, ou caso contrario, falso.
	 */
	public boolean validarExistenciaDoTipoSensor(int _id) {

		colecao = baseDados.getCollection("TipoSensor");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		return colecao.find(documentoProcurado).count() > 0;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Valida se determinado id existe ou nao na colecao Sensor
	 *
	 * @param _id Inteiro com o identificador que se deseja analisar.
	 *
	 * @return Valor booleano com valor verdadeiro caso o documento ja exista na
	 * colecao, caso contrario, valor falso.
	 */
	public boolean validarExistenciaDoSensor(int _id) {
		colecao = baseDados.getCollection("Sensor");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		return colecao.find(documentoProcurado).count() > 0;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Valida se determinado TipoSensor ja esta ou nao vinculado a um Sensor
	 *
	 * @param tipo Inteiro com o identificador do TipoSensor que se deseja
	 * analisar.
	 *
	 * @return Valor booleano com verdadeiro caso o documento ja esteja atrelado
	 * a um sensor da colecao Sensor, ou caso contrario, falso.
	 */
	public boolean validarExistenciaDeSensorComTipoAtrelado(int tipo) {

		colecao = baseDados.getCollection("Sensor");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("Tipo", tipo);

		return colecao.find(documentoProcurado).count() > 0;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Valida se determinado ID ja esta ou nao na colecao Dado
	 *
	 * @param ID Inteiro com o identificador que se deseja analisar.
	 *
	 * @return Valor booleano com verdadeiro caso o documento ja esteja na
	 * colecao, ou caso contrario, falso.
	 */
	public boolean validarExistenciaDeDadosDesteSensor(int ID) {

		colecao = baseDados.getCollection("Dados");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("ID", ID);

		return colecao.find(documentoProcurado).count() > 0;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Formata a hora para que cada campo possua 2 digitos.
	 *
	 * @param hora String subdividida por dois pontos onde os campos juntos
	 * formam por exemplo: hh : mm : ss
	 *
	 * @return Uma string com a hora formatada.
	 */
	public String validarHora(String hora) {

		DecimalFormat mascaraHora = new DecimalFormat("00");
		String[] vetorHora = hora.split(":");

		String horaFormatada = "";
		horaFormatada = mascaraHora.format(Integer.parseInt(vetorHora[0]));
		horaFormatada += ":";
		horaFormatada += mascaraHora.format(Integer.parseInt(vetorHora[1]));
		horaFormatada += ":";
		horaFormatada += mascaraHora.format(Integer.parseInt(vetorHora[2]));

		return horaFormatada;
	}

	// Excluir  Base De Dados----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exclui toda a colecao Tipo Sensor.
	 *
	 * @return Valor booleano com relacao ao sucesso ou nao durante a exclusao.
	 */
	public boolean excluirColecaoTipoSensor() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a colecao \"Tipo Sensor\" e seu conteudo?", "MongoDB - AVISO - REMOÇÃO DE COLEÇÃO", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.getCollection("TipoSensor").drop();
			JOptionPane.showMessageDialog(null, "Colecao \"Tipo Sensor\" Removida Com Sucesso!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remocao Da Colecao \"Tipo Sensor\" Cancelada!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return false;
		}

		return false;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exclui toda a colecao Sensor.
	 *
	 * @return Valor booleano com relacao ao sucesso ou nao durante a exclusao.
	 */
	public boolean excluirColecaoSensor() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a colecao \"Sensor\" e seu conteudo?", "MongoDB - AVISO - REMOÇÃO DE COLEÇÃO", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.getCollection("Sensor").drop();
			JOptionPane.showMessageDialog(null, "Colecao \"Sensor\" Removida Com Sucesso!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remocao Da Colecao \"Sensor\" Cancelada!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return false;
		}

		return false;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exclui toda a colecao Dados.
	 *
	 * @return Valor booleano com relacao ao sucesso ou nao durante a exclusao.
	 */
	public boolean excluirColecaoDados() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a colecao \"Dados\" e seu conteudo?", "MongoDB - AVISO - REMOÇÃO DE COLEÇÃO", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.getCollection("Sensor").drop();
			JOptionPane.showMessageDialog(null, "Colecao \"Dados\" Removida Com Sucesso!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remocao Da Colecao \"Dados\" Cancelada!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return false;
		}

		return false;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exclui toda a Base De Dados.
	 *
	 * @return Valor booleano com relacao ao sucesso ou nao durante a exclusao.
	 */
	public boolean excluirBaseDados() {

		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover toda a base de dados e seu conteudo?", "MongoDB - AVISO DE REMOÇÃO DE BASE DE DADOS", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.dropDatabase();
			JOptionPane.showMessageDialog(null, "Base De Dados Removida Com Sucesso Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remocao Da Base De Dados No MongoDB, Cancelada!");
			return false;
		}

		return false;
	}
}
