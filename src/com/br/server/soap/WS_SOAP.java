/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.server.soap;

import javax.xml.ws.Endpoint;

/**
 *
 * @author 141972
 */
public class WS_SOAP {
    public static void main(String[] args)
    {
        SensorServerImpl implementa = new SensorServerImpl();
        String URL = "http://localhost:9000/saida";
        System.out.println("Rodando");
        
        Endpoint.publish(URL, implementa);
    }
    
}
