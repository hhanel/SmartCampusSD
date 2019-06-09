/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recepcao_Armazenamento;

import com.mongodb.*;
import com.mongodb.connection.Connection;
import com.mongodb.util.JSON;
import java.util.logging.*;

public class MongoConexao {

    DB baseDados;
    DBCollection colecao;

    /**
     * Conecta com o banco MongoDB através do localhost seguido da porta padrão
     * do MongoDB :27017. Caso não possua uma coleção, faz a criação dela,
     */
    public MongoConexao() {
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            baseDados = mongo.getDB("SmartCampusSensores");
        } catch (Exception e) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Insere na coleção Sensor um novo documento.
     *
     * @param dadosDoSensor String concatenada com cada caracteristicas do
     * sensor separadas por ponto e vírgula,
     * Exemplo:"_id;Descrição;Latitude;Longitude;Tipo".
     *
     * @return Valor booleano com relação ao sucesso ou não durante a inserção.
     */
    public boolean inserirDocumentoNaColecaoSensor(String dadosDoSensor) {

        String vetorDadosSensor[] = dadosDoSensor.split(";");
        colecao = baseDados.getCollection("Sensor");

        String query
                = "{'_id' : '" + vetorDadosSensor[0]
                + "','Descricao' : '" + vetorDadosSensor[1]
                + "','Latitude' : '" + vetorDadosSensor[2]
                + "','Longitude' : '" + vetorDadosSensor[3]
                + "','Tipo' : '" + vetorDadosSensor[4]
                + "'}";

        colecao.insert((DBObject) JSON.parse(query));
        return true;
    }

    /**
     * Insere na coleção Tipo Sensor um novo documento.
     *
     * @param dadosDoTipoSensor String concatenada com cada dado do tipo sensor
     * separado por ponto e vírgula,
     * Exemplo:"_id;Descrição;Comunica;Mínimo;Máximo,Intervalo".
     *
     * @return Valor booleano com relação ao sucesso ou não durante a inserção.
     */
    public boolean inserirDocumentoNaColecaoTipoSensor(String dadosDoTipoSensor) {

        String vetorDadosTipoSensor[] = dadosDoTipoSensor.split(";");
        colecao = baseDados.getCollection("TipoSensor");

        String query
                = "{'_id' : '" + vetorDadosTipoSensor[0]
                + "','Descricao' : '" + vetorDadosTipoSensor[1]
                + "','Comunica' : '" + vetorDadosTipoSensor[2]
                + "','Minimo' : '" + vetorDadosTipoSensor[3]
                + "','Maximo' : '" + vetorDadosTipoSensor[4]
                + "','Intervalo' : '" + vetorDadosTipoSensor[5]
                + "'}";

        colecao.insert((DBObject) JSON.parse(query));
        return true;
    }

    /**
     * Insere na coleção Dados um novo documento.
     *
     * @param dadosColetado String concatenada com os dados coletados pelos
     * sonsores. Exemplo: "ID ; Valor ; Data ; Hora".
     *
     * @return Valor booleano com relação ao sucesso ou não durante a inserção.
     */
    public boolean inserirDocumentoNaColecaoDados(String dadosColetado) {

        String vetorDadoColetado[] = dadosColetado.split(";");
        colecao = baseDados.getCollection("Dados");

        String query
                = "{'ID' : '" + vetorDadoColetado[0]
                + "','Valor' : '" + vetorDadoColetado[1]
                + "','Data' : '" + vetorDadoColetado[2]
                + "','Hora' : '" + vetorDadoColetado[3]
                + "'}";

        colecao.insert((DBObject) JSON.parse(query));
        return true;
    }

    /**
     * Coleta um ou mais documento(s) da coleção Sensor.
     *
     * @param id String com identificador do sensor.
     * @return Valor booleano com relação ao sucesso ou não durante a coleta.
     */
    public boolean coletarDocumentoNaColecaoSensor(String id) {

        DBCollection colecaoParaProcurar = baseDados.getCollection("Sensor");

        BasicDBObject documentoProcurado = new BasicDBObject();
        documentoProcurado.put("_id", id);

        DBCursor listaDeDocumentosEncontrados = colecaoParaProcurar.find(documentoProcurado);

        String _id, descricao, latitude, longitude, tipo;
        while (listaDeDocumentosEncontrados.hasNext()) {

            DBObject documentoColetado = listaDeDocumentosEncontrados.next();

            _id = documentoColetado.get("_id").toString();
            descricao = documentoColetado.get("Descricao").toString();
            latitude = documentoColetado.get("Latitude").toString();
            longitude = documentoColetado.get("Longitude").toString();
            tipo = documentoColetado.get("Tipo").toString();

            System.out.println("\nEncontrado: " + "ID: " + _id + "\nDescrição: " + descricao + "\nLatitude: " + latitude + "\nLongitude: " + longitude + "\nTipo: " + tipo + "\n");
        }

        return true;
    }

    /**
     * Coleta um ou mais documento(s) da coleção Tipo Sensor.
     *
     * @param id String com identificador do tipo sensor.
     * @return Valor booleano com relação ao sucesso ou não durante a coleta.
     */
    public boolean coletarDocumentoNaColecaoTipoSensor(String id) {

        DBCollection colecaoParaProcurar = baseDados.getCollection("TipoSensor");

        BasicDBObject documentoProcurado = new BasicDBObject();
        documentoProcurado.put("_id", id);

        DBCursor listaDeDocumentosEncontrados = colecaoParaProcurar.find(documentoProcurado);

        String _id, descricao, comunica, minimo, maximo, intervalo;
        while (listaDeDocumentosEncontrados.hasNext()) {

            DBObject documentoColetado = listaDeDocumentosEncontrados.next();

            _id = documentoColetado.get("_id").toString();
            descricao = documentoColetado.get("Descricao").toString();
            comunica = documentoColetado.get("Comunica").toString();
            minimo = documentoColetado.get("Minimo").toString();
            maximo = documentoColetado.get("Maximo").toString();
            intervalo = documentoColetado.get("Intervalo").toString();

            System.out.println("\nEncontrado: " + "ID: " + _id + "\nDescrição: " + descricao + "\nComunica: " + comunica + "\nMínimo: " + minimo + "\nMáximo: " + maximo + "\nIntervalo: " + intervalo + "\n");
        }

        return true;
    }

    /**
     * Coleta um ou mais documento(s) da coleção Dados.
     *
     * @param id String com o id do sensor a coletar dados.
     * @return Valor booleano com relação ao sucesso ou não durante a coleta.
     */
    public boolean coletarDocumentoNaColecaoDados(String id) {

        DBCollection colecaoParaProcurar = baseDados.getCollection("Dados");

        BasicDBObject documentoProcurado = new BasicDBObject();
        documentoProcurado.put("ID", id);

        DBCursor listaDeDocumentosEncontrados = colecaoParaProcurar.find(documentoProcurado);

        String ID, data, hora, valor;
        while (listaDeDocumentosEncontrados.hasNext()) {

            DBObject documentoColetado = listaDeDocumentosEncontrados.next();

            ID = documentoColetado.get("ID").toString();
            data = documentoColetado.get("Data").toString();
            hora = documentoColetado.get("Hora").toString();
            valor = documentoColetado.get("Valor").toString();

            System.out.println("\nEncontrado: " + "ID: " + ID + "\nData: " + data + "\nHora: " + hora + "\nValor: " + valor + "\n");
        }

        return true;
    }

    /**
     * Exibe todos os documentos da coleção Sensor.
     */
    public void mostrarDocumentosDaColecaoSensor() {

        DBCollection colecaoParaMostrar = baseDados.getCollection("Sensor");
        DBCursor listaDeDocumentosEncontrados = colecaoParaMostrar.find();

        String _id, descricao, latitude, longitude, tipo;
        while (listaDeDocumentosEncontrados.hasNext()) {

            DBObject documentoColetado = listaDeDocumentosEncontrados.next();

            _id = documentoColetado.get("_id").toString();
            descricao = documentoColetado.get("Descricao").toString();
            latitude = documentoColetado.get("Latitude").toString();
            longitude = documentoColetado.get("Longitude").toString();
            tipo = documentoColetado.get("Tipo").toString();

            System.out.println("\nEncontrado: " + "ID: " + _id + "\nDescrição: " + descricao + "\nLatitude: " + latitude + "\nLongitude: " + longitude + "\nTipo: " + tipo + "\n");
        }
    }

    /**
     * Exibe todos os documentos da coleção Tipo Sensor.
     */
    public void mostrarDocumentosDaColecaoTipoSensor() {

        DBCollection colecaoParaMostrar = baseDados.getCollection("TipoSensor");
        DBCursor listaDeDocumentosEncontrados = colecaoParaMostrar.find();

        String _id, descricao, comunica, minimo, maximo, intervalo;
        while (listaDeDocumentosEncontrados.hasNext()) {

            DBObject documentoColetado = listaDeDocumentosEncontrados.next();

            _id = documentoColetado.get("_id").toString();
            descricao = documentoColetado.get("Descricao").toString();
            comunica = documentoColetado.get("Comunica").toString();
            minimo = documentoColetado.get("Minimo").toString();
            maximo = documentoColetado.get("Maximo").toString();
            intervalo = documentoColetado.get("Intervalo").toString();

            System.out.println("\nEncontrado: " + "ID: " + _id + "\nDescrição: " + descricao + "\nComunica: " + comunica + "\nMínimo: " + minimo + "\nMáximo: " + maximo + "\nIntervalo: " + intervalo + "\n");
        }
    }

    /**
     * Exibe todos os documentos da coleção Dados.
     */
    public void mostrarDocumentosDaColecaoDados() {

        DBCollection colecaoParaMostrar = baseDados.getCollection("Dados");
        DBCursor listaDeDocumentosEncontrados = colecaoParaMostrar.find();

        String ID, data, hora, valor;
        while (listaDeDocumentosEncontrados.hasNext()) {

            DBObject documentoColetado = listaDeDocumentosEncontrados.next();

            ID = documentoColetado.get("ID").toString();
            data = documentoColetado.get("Data").toString();
            hora = documentoColetado.get("Hora").toString();
            valor = documentoColetado.get("Valor").toString();

            System.out.println("\nEncontrado: " + "ID: " + ID + "\nData: " + data + "\nHora: " + hora + "\nValor: " + valor + "\n");
        }
    }

    /**
     * Remove um documento da coleção Sensor.
     *
     * @param id String com identificador do sensor.
     * @return Valor booleano com relação ao sucesso ou não durante a remoção.
     */
    public boolean removerDocumentoDaColecaoSensor(String id) {

        colecao = baseDados.getCollection("Sensor");

        BasicDBObject documentoParaRemover = new BasicDBObject();
        documentoParaRemover.put("_id", id);

        colecao.remove(documentoParaRemover);
        return true;
    }

    /**
     * Remove um documento da coleção Tipo Sensor.
     *
     * @param id String com identificador do tipo sensor.
     * @return Valor booleano com relação ao sucesso ou não durante a remoção.
     */
    public boolean removerDocumentoDaColecaoTipoSensor(String id) {

        colecao = baseDados.getCollection("TipoSensor");

        BasicDBObject documentoParaRemover = new BasicDBObject();
        documentoParaRemover.put("_id", id);

        colecao.remove(documentoParaRemover);
        return true;
    }

    /**
     * Remove um documento da coleção Dados.
     *
     * @param id String com a chave do documento dentro da coleção.
     * @return Valor booleano com relação ao sucesso ou não durante a remoção.
     */
    public boolean removerDocumentoDaColecaoDados(String id) {

        colecao = baseDados.getCollection("Dados");

        BasicDBObject documentoParaRemover = new BasicDBObject();
        documentoParaRemover.put("ID", id);

        colecao.remove(documentoParaRemover);
        return true;
    }

    /**
     * Exclui toda a Base De Dados.
     *
     * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
     */
    public boolean excluirBaseDados() {
        baseDados.dropDatabase();
        return true;
    }

    /**
     * Exclui toda a coleção Sensor.
     *
     * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
     */
    public boolean excluirColecaoSensor() {
        baseDados.getCollection("Sensor").drop();
        return true;
    }

    /**
     * Exclui toda a coleção Tipo Sensor.
     *
     * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
     */
    public boolean excluirColecaoTipoSensor() {
        baseDados.getCollection("TipoSensor").drop();
        return true;
    }

    /**
     * Exclui toda a coleção Dados.
     *
     * @return Valor booleano com relação ao sucesso ou não durante a exclusão.
     */
    public boolean excluirColecaoDados() {
        baseDados.getCollection("Dados").drop();
        return true;
    }
}
