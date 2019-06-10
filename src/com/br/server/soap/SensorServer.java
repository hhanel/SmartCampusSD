package com.br.server.soap;

import java.util.ArrayList;

public interface SensorServer {

    public boolean addSensor(String sensor);

    public String addTipoSensor(String tpSensor);

    public String deleteSensor(Integer id);

    public String deleteTipoSensor(Integer id);

    public String[] getSensor(String id);

    public String[] getTipoSensor(String id);

    public ArrayList<String[]> getAllSensors();

    public ArrayList<String[]> getAllTipoSensors();
}
