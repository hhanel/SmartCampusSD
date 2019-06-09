package com.br.server.soap;

import java.util.ArrayList;

import BancoMongoDB.MongoConexao;
import javax.jws.WebService;

@WebService
public class SensorServerImpl implements SensorServer {

    MongoConexao mongoConexao = new MongoConexao();

    public boolean addSensor(String sensor[]) {
        if (!sensor.equals(null)) {
            if (mongoConexao.inserirDocumentoNaColecaoSensor(sensor)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteSensor(String id) {
        if (!id.equals(null) & !id.isEmpty()) {
            return mongoConexao.removerDocumentoDaColecaoSensor(id);
        } else {
            return false;
        }
    }

    @Override
    public String[] getSensor(String id) {
        if (!id.equals(null) & !id.isEmpty()) {
            return mongoConexao.coletarDocumentoNaColecaoSensor(id, 1, 1, 1, 1);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<String[]> getAllSensors() {
        return mongoConexao.mostrarDocumentosDaColecaoSensor();
    }

    @Override
    public boolean addTipoSensor(String tpSensor[]) {
        if (!tpSensor.equals(null)) {
            if (mongoConexao.inserirDocumentoNaColecaoTipoSensor(tpSensor)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteTipoSensor(String id) {
        if (!id.equals(null) & !id.isEmpty()) {
            return mongoConexao.removerDocumentoDaColecaoTipoSensor(id);
        } else {
            return false;
        }
    }

    @Override
    public String[] getTipoSensor(String id) {
        if (!id.equals(null) & !id.isEmpty()) {
            return mongoConexao.coletarDocumentoNaColecaoTipoSensor(id, 1, 1, 1, 1, 1);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<String[]> getAllTipoSensors() {
        return mongoConexao.mostrarDocumentosDaColecaoTipoSensor();
    }
}