package com.br.server.soap;

import java.util.ArrayList;

import BancoMongoDB.MongoConexao;
import javax.jws.WebService;

@WebService
public class SensorServerImpl implements SensorServer {

    MongoConexao mongoConexao = new MongoConexao();

    @Override
    public boolean addSensor(String sensor) {
        if (!sensor.equals(null)) {
            return !mongoConexao.inserirDocumentoNaColecaoSensor(sensor).isEmpty();
        } else {
            return false;
        }
    }

    @Override
    public String deleteSensor(Integer id) {
        if (!id.equals(null)) {
            return mongoConexao.removerDocumentoDaColecaoSensor(id);
        } else {
            return null;
        }
    }

    @Override
    public String[] getSensor(Integer id) {
        if (!id.equals(null) & !id.isEmpty()) {
            return mongoConexao.coletarDocumentoNaColecaoSensor(id, 1, 1, 1, 1);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<String[]> getAllSensors() {
        return mongoConexao.mostrarTodosDocumentosDaColecaoSensor();
    }

    @Override
    public String addTipoSensor(String tpSensor) {
        if (!tpSensor.equals(null)) {
            return mongoConexao.inserirDocumentoNaColecaoTipoSensor(tpSensor);
        } else {
            return null;
        }
    }

    @Override
    public String deleteTipoSensor(Integer id) {
        if (!id.equals(null)) {
            return mongoConexao.removerDocumentoDaColecaoTipoSensor(id);
        } else {
            return null;
        }
    }

    @Override
    public String[] getTipoSensor(Integer id) {
        if (!id.equals(null) & !id.isEmpty()) {
            return mongoConexao.coletarDocumentoNaColecaoTipoSensor(id, 1, 1, 1, 1, 1);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<String[]> getAllTipoSensors() {
        return mongoConexao.mostrarTodosDocumentosDaColecaoTipoSensor();
    }
}
