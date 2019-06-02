/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoMongoDB;

import com.mongodb.*;
import com.mongodb.connection.Connection;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.Test;

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
     * @param vetorDadosSensor Um vetor string de 5 posicoes com todos os dados
     * do Sensor. Seu conteudo se organiza desta forma no vetor:
     * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Latitude,vetor[3]=Longitude,vetor[4]=Tipo,
     *
     * @return Valor booleano com relação ao sucesso ou não durante a inserção.
     */
    public boolean inserirDocumentoNaColecaoSensor(String vetorDadosSensor[]) {

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
     * @param vetorDadosTipoSensor Um vetor string de 6 posicoes com todos os
     * dados do Tipo Sensor. Seu conteudo se organiza desta forma no vetor:
     * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Comunica,vetor[3]=Minimo,vetor[4]=Maximo,vetor[5]=Intervalo,
     *
     * @return Valor booleano com relação ao sucesso ou não durante a inserção.
     */
    public boolean inserirDocumentoNaColecaoTipoSensor(String vetorDadosTipoSensor[]) {

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
     * @param vetorDadoColetado Um vetor string de 4 posicoes com todos os dados
     * coletados. Seu conteudo se organiza desta forma no vetor:
     * vetor[0]=ID,vetor[1]=Valor,vetor[2]=Data,vetor[3]=Hora.
     *
     * @return Valor booleano com relação ao sucesso ou não durante a inserção.
     */
    public boolean inserirDocumentoNaColecaoDados(String vetorDadoColetado[]) {

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

    // Coletar --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Coleta um documento da coleção Sensor.
     *
     * @param id Utilize o Identificador do sensor a coletar dados na coleção.
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
    public String[] coletarDocumentoNaColecaoSensor(String id, int exibirDescricao, int exibirLatitude, int exibirLongitude, int exibirTipo) {

        DBCollection colecaoParaProcurar = baseDados.getCollection("Sensor");

        BasicDBObject documentoProcurado = new BasicDBObject();
        documentoProcurado.put("_id", id);

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

    /**
     * Coleta um documento da coleção Tipo Sensor.
     *
     * @param id Utilize o Identificador do tipo sensor a coletar dados na
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
    public String[] coletarDocumentoNaColecaoTipoSensor(String id, int exibirDescricao, int exibirComunica, int exibirMinimo, int exibirMaximo, int exibirIntervalo) {

        DBCollection colecaoParaProcurar = baseDados.getCollection("TipoSensor");

        BasicDBObject documentoProcurado = new BasicDBObject();
        documentoProcurado.put("_id", id);

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

    /**
     * Coleta um ou mais documento(s) da coleção Dados.
     *
     * @param id Utilize o Identificador do sensor a coletar dados na coleção.
     * @param exibirValor Utilize 1 para coletar ou 0 para não coletar o Valor.
     * @param exibirData Utilize 1 para coletar ou 0 para não coletar a Data.
     * @param exibirHora Utilize 1 para coletar ou 0 para não coletar a Hora.
     *
     * @return ArrayList<String[]> Um array list contendo vetores string de 4
     * posicoes com todos os dados dos documentos encontrados na coleção. O
     * conteudo de cada array string se organiza desta forma em cada nodo:
     * vetor[0]=ID,vetor[1]=Valor,vetor[2]=Data,vetor[3]=Hora.
     */
    public ArrayList<String[]> coletarDocumentoNaColecaoDados(String id, int exibirValor, int exibirData, int exibirHora) {

        DBCollection colecaoParaProcurar = baseDados.getCollection("Dados");

        BasicDBObject documentoProcurado = new BasicDBObject();
        documentoProcurado.put("ID", id);

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
     * Exibe todos os documentos da coleção Sensor.
     *
     * @return ArrayList<String[]> Um array list contendo vetores string de 5
     * posicoes com todos os dados dos documentos encontrados na coleção. O
     * conteudo de cada array string se organiza desta forma em cada nodo:
     * vetor[0]=_id,vetor[1]=Descricao,vetor[2]=Latitude,vetor[3]=Longitude,vetor[4]=Tipo.
     */
    public ArrayList<String[]> mostrarDocumentosDaColecaoSensor() {

        DBCollection colecaoParaMostrar = baseDados.getCollection("Sensor");
        DBCursor documentosEncontrados = colecaoParaMostrar.find();
        ArrayList<String[]> listaDocumentosEncontrados = new ArrayList<>();

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
    public ArrayList<String[]> mostrarDocumentosDaColecaoTipoSensor() {

        DBCollection colecaoParaMostrar = baseDados.getCollection("TipoSensor");
        DBCursor documentosEncontrados = colecaoParaMostrar.find();
        ArrayList<String[]> listaDocumentosEncontrados = new ArrayList<>();

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
    public ArrayList<String[]> mostrarDocumentosDaColecaoDados() {

        DBCollection colecaoParaMostrar = baseDados.getCollection("Dados");
        DBCursor documentosEncontrados = colecaoParaMostrar.find();
        ArrayList<String[]> listaDocumentosEncontrados = new ArrayList<>();

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

    // Remover -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Remove um documento da coleção Sensor.
     *
     * @param id Utilize o Identificador do sensor a ser removido da coleção.
     *
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
     * @param id Utilize o Identificador do sensor a ser removido da coleção.
     *
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
     * @param id Utilize o Identificador do sensor a ser removido da coleção.
     *
     * @return Valor booleano com relação ao sucesso ou não durante a remoção.
     */
    public boolean removerDocumentoDaColecaoDados(String id) {

        colecao = baseDados.getCollection("Dados");

        BasicDBObject documentoParaRemover = new BasicDBObject();
        documentoParaRemover.put("ID", id);

        colecao.remove(documentoParaRemover);
        return true;
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
