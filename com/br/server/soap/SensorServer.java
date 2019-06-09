package com.br.server.soap;

import java.util.ArrayList;

public interface SensorServer {

    public boolean addSensor(String sensor[]);

    public boolean addTipoSensor(String tpSensor[]);

    public boolean deleteSensor(String id);

    public boolean deleteTipoSensor(String id);

    public String[] getSensor(String id);

    public String[] getTipoSensor(String id);

    public ArrayList<String[]> getAllSensors();

    public ArrayList<String[]> getAllTipoSensors();
}
