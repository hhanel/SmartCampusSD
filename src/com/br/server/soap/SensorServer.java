package com.br.server.soap;

import java.util.ArrayList;

public interface SensorServer {

	public boolean addSensor(String sensor);

	public String addTipoSensor(String tpSensor);

	public String deleteSensor(Integer id);

	public String deleteTipoSensor(Integer id);

	public String[] getSensor(Integer id);

	public String[] getTipoSensor(Integer id);

	public ArrayList<String[]> getAllSensors();

	public ArrayList<String[]> getAllTipoSensors();
}
