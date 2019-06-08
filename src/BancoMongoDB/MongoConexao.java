package BancoMongoDB;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
	 * Conecta com o banco MongoDB através do localhost seguido da porta padrão
	 * do MongoDB :27017. Caso não possua uma coleção, faz a criação dela,
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
	 * Insere na coleção Sensor um novo documento.
	 *
	 * @param dadosSensor Uma string subdividida por ponto e virgula, cada
	 * posição contém um dos 5 dados coletados sobre o cadastro de um sensor.
	 *
	 * Exemplo de string valida: " _id;Descricao;Latitude;Longitude;Tipo "
	 *
	 * @return String com status do sucesso ou não durante a inserção.
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
			return "CANCELADA;INSERCAO;SENSOR;_id (" + vetorDadosSensor[0] + ") já esta cadastrado na coleção.";
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
	 * Insere na coleção Tipo Sensor um novo documento.
	 *
	 * @param dadosTipoSensor Uma string subdividida por ponto e virgula, cada
	 * posição contém 1 dos 6 dados coletados sobre o cadastro de 1 tipo sensor.
	 *
	 * Exemplo string válida: "_id;Descricao;Comunica;Minimo;Maximo;Intervalo"
	 *
	 * @return String com status do sucesso ou não durante a inserção.
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
			return "CANCELADA;INSERCAO;TIPOSENSOR;_id (" + vetorDadosTipoSensor[0] + ") já esta cadastrado na coleção.";
		} else {
			colecao = baseDados.getCollection("TipoSensor");
			colecao.insert(novoDocumento);
			return "EFETUADA;INSERCAO;TIPOSENSOR";
		}
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Valida se determinado id existe ou nao na coleção Sensor
	 *
	 * @param _id Inteiro com o identificador que se deseja analisar.
	 *
	 * @return Valor booleano com valor verdadeiro caso o documento ja exista na
	 * coleção, caso contrário, valor falso.
	 */
	public boolean validarExistenciaDoSensor(int _id) {
		colecao = baseDados.getCollection("Sensor");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		return colecao.find(documentoProcurado).count() > 0;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Valida se determinado id existe ou nao na colecao TipoSensor
	 *
	 * @param _id Inteiro com o identificador que se deseja analisar.
	 *
	 * @return Valor booleano com verdadeiro caso o documento ja esteja na
	 * coleção, ou caso contrário, falso.
	 */
	public boolean validarExistenciaDoTipoSensor(int _id) {

		colecao = baseDados.getCollection("TipoSensor");
		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		return colecao.find(documentoProcurado).count() > 0;
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Insere na coleção Dados um novo documento.
	 *
	 * @param dadoColetado Uma string subdividida por ponto e virgula, cada
	 * posição contém um dos 4 dados coletados sobre um sensor.
	 *
	 * Exemplo de string válida: " ID;Valor;Data;Hora "
	 *
	 * @return String com status do sucesso ou não durante a inserção.
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

	// Coletar --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Coleta um documento da coleção Sensor.
	 *
	 * @param _id Utilize o Identificador do sensor a coletar dados na coleção.
	 * @param exibirDescricao Utilize 1 para coletar ou 0 para não coletar a
	 * Descricao.
	 * @param exibirLatitude Utilize 1 para coletar ou 0 para não coletar a
	 * Latitude.
	 * @param exibirLongitude Utilize 1 para coletar ou 0 para não coletar a
	 * Longitude.
	 * @param exibirTipo Utilize 1 para coletar ou 0 para não coletar o Tipo.
	 *
	 * @return Um vetor string de 5 posicoes com todos os dados do documento
	 * encontrado na coleção. Seu conteudo se organiza desta forma no vetor:
	 * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Latitude,vetor[3]=Longitude,vetor[4]=Tipo,
	 */
	public String[] coletarDocumentoNaColecaoSensor(String _id, int exibirDescricao, int exibirLatitude, int exibirLongitude, int exibirTipo) {

		DBCollection colecaoParaProcurar = baseDados.getCollection("Sensor");

		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		DBCursor documentosEncontrados = colecaoParaProcurar.find(documentoProcurado);
		DBObject documentoEncontrado = documentosEncontrados.next();

		String dadosColetados[] = {"", "", "", "", ""};
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
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Coleta um documento da coleção Tipo Sensor.
	 *
	 * @param _id Utilize o Identificador do tipo sensor a coletar dados na
	 * coleção.
	 * @param exibirDescricao Utilize 1 para coletar ou 0 para não coletar a
	 * Descricao.
	 * @param exibirComunica Utilize 1 para coletar ou 0 para não coletar a
	 * Comunica.
	 * @param exibirMinimo Utilize 1 para coletar ou 0 para não coletar o
	 * Minimo.
	 * @param exibirMaximo Utilize 1 para coletar ou 0 para não coletar o
	 * Maximo.
	 * @param exibirIntervalo Utilize 1 para coletar ou 0 para não coletar o
	 * Intervalo.
	 * @return Um vetor string de 6 posicoes com todos os dados do documento
	 * encontrado na coleção. Seu conteudo se organiza desta forma no vetor:
	 * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Comunica,vetor[3]=Minimo,vetor[4]=Maximo,vetor[5]=Intervalo,
	 */
	public String[] coletarDocumentoNaColecaoTipoSensor(String _id, int exibirDescricao, int exibirComunica, int exibirMinimo, int exibirMaximo, int exibirIntervalo) {

		DBCollection colecaoParaProcurar = baseDados.getCollection("TipoSensor");

		BasicDBObject documentoProcurado = new BasicDBObject();
		documentoProcurado.put("_id", _id);

		DBCursor documentosEncontrados = colecaoParaProcurar.find(documentoProcurado);
		DBObject documentoEncontrado = documentosEncontrados.next();

		String dadosColetados[] = {"", "", "", "", "", ""};
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
	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Coleta um ou mais documento(s) da coleção Dados.
	 *
	 * @param _id Utilize o Identificador do sensor a coletar dados na coleção.
	 * @param exibirValor Utilize 1 para coletar ou 0 para não coletar o Valor.
	 * @param exibirData Utilize 1 para coletar ou 0 para não coletar a Data.
	 * @param exibirHora Utilize 1 para coletar ou 0 para não coletar a Hora.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 4
	 * posicoes com todos os dados dos documentos encontrados na coleção. O
	 * conteudo de cada array string se organiza desta forma em cada nodo:
	 * vetor[0]=ID,vetor[1]=Valor,vetor[2]=Data,vetor[3]=Hora.
	 */
	public ArrayList<String[]> coletarDocumentoNaColecaoDados(String _id, int exibirValor, int exibirData, int exibirHora) {

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

	/*
	// Mostrar -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exibe todos os documentos da coleção Sensor.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 5
	 * posicoes com todos os dados dos documentos encontrados na coleção. O
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

	/**
	 * Exibe todos os documentos da coleção Tipo Sensor.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 6
	 * posicoes com todos os dados dos documentos encontrados na coleção. O
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

	/**
	 * Exibe todos os documentos da coleção Dados.
	 *
	 * @return ArrayList<String[]> Um array list contendo vetores string de 4
	 * posicoes com todos os dados dos documentos encontrados na coleção. O
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
	 * Altera um documento na colecao Sensor.
	 *
	 * @param _id Inteiro com o identificador do sensor a ser alterado.
	 * @param nomeCampoAtualizar String com o nome do campo a ser alterado.
	 * @param novoValor String com o novo valor do campo.
	 *
	 * @return String com status do sucesso ou não durante a alteração.
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
						return "CANCELADA;ALTERACAO;SENSOR;O novo valor para o Tipo é o mesmo valor que ele contém neste momento, mantém-se";
					} else {
						if (validarExistenciaDoTipoSensor(Integer.parseInt(novoValor))) {
							colecao = baseDados.getCollection("Sensor");
							DBObject documentoPrrocurar = new BasicDBObject("_id", _id);
							DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Tipo", Integer.parseInt(novoValor)));
							DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
							return "EFETUADA;ALTERACAO;SENSOR;TIPO";
						} else {
							return "CANCELADA;ALTERACAO;SENSOR;O tipo (" + novoValor + ") não é válido na coleçção Tipo Sensor";
						}
					}
				} else {
					colecao = baseDados.getCollection("Sensor");
					DBObject documentoPrrocurar = new BasicDBObject("_id", _id);
					if ("DESCRICAO".equals(nomeCampoAtualizar)) {
						if (documentoEncontrado.get("Descricao").toString().equals(novoValor)) {
							return "CANCELADA;ALTERACAO;SENSOR;O novo valor para a  Descrição é o mesmo valor que ela contém neste momento, mantém-se";
						} else {
							DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Descricao", novoValor));
							DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
							return "EFETUADA;ALTERACAO;SENSOR;DESCRICAO";
						}
					} else {
						if ("LATITUDE".equals(nomeCampoAtualizar)) {
							if (documentoEncontrado.get("Latitude").toString().equals(novoValor)) {
								return "CANCELADA;ALTERACAO;SENSOR;O novo valor para a Latitude é o mesmo valor que ela contém neste momento, mantém-se";
							} else {
								DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Latitude", Double.parseDouble(novoValor)));
								DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
								return "EFETUADA;ALTERACAO;SENSOR;LATITUDE";
							}
						} else {
							if (documentoEncontrado.get("Longitude").toString().equals(novoValor)) {
								return "CANCELADA;ALTERACAO;SENSOR;O novo valor para a  Longitude é o mesmo valor que ela contém neste momento, mantém-se";
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
			return "CANCELADA;ALTERACAO;SENSOR;O identificador( " + _id + ") nao foi encontrado na coleção.";
		}
	}

	/**
	 * Aletra um campo da colecao Tipo Sensor.
	 *
	 * @param _id Inteiro com o identificador do sensor a ser alterado.
	 * @param nomeCampoAtualizar String com o nome do campo a ser alterado.
	 * @param novoValor String com o novo valor do campo.
	 *
	 * @return String com status do sucesso ou não durante a alteração.
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
						return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para a Descricao é o mesmo valor que ela contém neste momento, mantém-se";
					} else {
						DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Descricao", novoValor));
						DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
						return "EFETUADA;ALTERACAO;TIPOSENSOR;DESCRICAO";
					}
				} else {
					if ("COMUNICA".equals(nomeCampoAtualizar)) {
						if ("TCP".equals(novoValor) || "UDP".equals(novoValor)) {
							if (documentoEncontrado.get("Comunica").toString().equals(novoValor)) {
								return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para a Comunicacao é o mesmo valor que ela contém neste momento, mantém-se";
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
								return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para o Minimo é o mesmo valor que ela contém neste momento, mantém-se";
							} else {
								DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Minimo", Integer.parseInt(novoValor)));
								DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
								return "EFETUADA;ALTERACAO;TIPOSENSOR;MINIMO";

							}
						} else {
							if ("MAXIMO".equals(nomeCampoAtualizar)) {
								if (documentoEncontrado.get("Maximo").toString().equals(novoValor)) {
									return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para o Maximo é o mesmo valor que ela contém neste momento, mantém-se";
								} else {
									DBObject documentoUpdate = new BasicDBObject("$set", new BasicDBObject("Maximo", Integer.parseInt(novoValor)));
									DBObject result = colecao.findAndModify(documentoPrrocurar, null, null, false, documentoUpdate, true, true);
									return "EFETUADA;ALTERACAO;TIPOSENSOR;MAXIMO";
								}
							} else {
								if (documentoEncontrado.get("Intervalo").toString().equals(novoValor)) {
									return "CANCELADA;ALTERACAO;TIPOSENSOR;O novo valor para o Intervalo é o mesmo valor que ela contém neste momento, mantém-se";
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
			return "CANCELADA;ALTERACAO;TIPOSENSOR;O identificador( " + _id + ") nao foi encontrado na coleção.";
		}
	}

// Remover -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Remove um documento da coleção Sensor.
	 *
	 * @param _id Utilize o Identificador do sensor a ser removido da coleção.
	 *
	 * @return Valor booleano com relação ao sucesso ou não durante a remoção.
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

	/**
	 * Remove um documento da coleção Tipo Sensor.
	 *
	 * @param _id Utilize o Identificador do sensor a ser removido da coleção.
	 *
	 * @return Valor booleano com relação ao sucesso ou não durante a remoção.
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
			return "CANCELADA;REMOCAO;TIPOSENSOR;O identificador (" + _id + ") nao possui cadastro na colecao Sensor";
		}
	}

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

	/**
	 * Remove um documento da coleção Dados.
	 *
	 * @param _id Utilize o Identificador do sensor a ser removido da coleção.
	 *
	 * @return Valor booleano com relação ao sucesso ou não durante a remoção.
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

	// Excluir --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Exclui toda a Base De Dados.
	 *
	 * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
	 */
	public boolean excluirBaseDados() {

		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover toda a base de dados e seu conteudo?", "MongoDB - AVISO DE REMOÇÃO DE BASE DE DADOS", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.dropDatabase();
			JOptionPane.showMessageDialog(null, "Base De Dados Removida Com Sucesso Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remoção Da Base De Dados No MongoDB, Cancelada!");
			return false;
		}

		return false;
	}

	/**
	 * Exclui toda a coleção Sensor.
	 *
	 * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
	 */
	public boolean excluirColecaoSensor() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a coleção \"Sensor\" e seu conteudo?", "MongoDB - AVISO - REMOÇÃO DE COLEÇÃO", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.getCollection("Sensor").drop();
			JOptionPane.showMessageDialog(null, "Coleção \"Sensor\" Removida Com Sucesso!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remoção Da Coleção \"Sensor\" Cancelada!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return false;
		}

		return false;
	}

	/**
	 * Exclui toda a coleção Tipo Sensor.
	 *
	 * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
	 */
	public boolean excluirColecaoTipoSensor() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a coleção \"Tipo Sensor\" e seu conteudo?", "MongoDB - AVISO - REMOÇÃO DE COLEÇÃO", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.getCollection("TipoSensor").drop();
			JOptionPane.showMessageDialog(null, "Coleção \"Tipo Sensor\" Removida Com Sucesso!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remoção Da Coleção \"Tipo Sensor\" Cancelada!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return false;
		}

		return false;
	}

	/**
	 * Exclui toda a coleção Dados.
	 *
	 * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
	 */
	public boolean excluirColecaoDados() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente remover a coleção \"Dados\" e seu conteudo?", "MongoDB - AVISO - REMOÇÃO DE COLEÇÃO", JOptionPane.YES_NO_OPTION);

		if (resposta == JOptionPane.YES_OPTION) {
			baseDados.getCollection("Sensor").drop();
			JOptionPane.showMessageDialog(null, "Coleção \"Dados\" Removida Com Sucesso!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return true;
		} else if (resposta == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Remoção Da Coleção \"Dados\" Cancelada!\nBase De Dados (" + baseDados.getName() + ") Do MongoDB");
			return false;
		}

		return false;
	}
}
