/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoMongoDB;

import BancoMongoDB.*;
import BancoMongoDB.MongoConexao;
import BancoMongoDB.MongoConexao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.junit.*;

/**
 *
 * @author WiLLian MuLLer
 */
public class MongoConexaoTest {

    public MongoConexaoTest() {
    }

    // Inserir --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testInserirDocumentoNaColecaoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        String vetorDadosSensor[] = {"", "", "", "", ""};
        Random random = new Random();

        for (int i = 1; i <= 20; ++i) {
            vetorDadosSensor[0] = String.valueOf(i);
            vetorDadosSensor[1] = "Sensor medidor de pressão";
            vetorDadosSensor[2] = String.valueOf(random.nextInt((50000 - 20000 + 1)) + 20000);
            vetorDadosSensor[3] = String.valueOf(random.nextInt((50000 - 20000 + 1)) + 20000);
            vetorDadosSensor[4] = (i % 2 == 0) ? "TCP" : "UDP";

            mongoConexao.inserirDocumentoNaColecaoSensor(vetorDadosSensor);
        }
    }

    @Test
    public void testInserirDocumentoNaColecaoTipoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        String vetorDadosSensor[] = {"", "", "", "", "", ""};
        Random random = new Random();

        for (int i = 21; i <= 40; ++i) {
            vetorDadosSensor[0] = String.valueOf(i);
            vetorDadosSensor[1] = "Sensor medidor de temperatura";
            vetorDadosSensor[2] = (i % 2 == 0) ? "TCP" : "UDP";
            vetorDadosSensor[3] = String.valueOf(random.nextInt((25 - 0 + 1)) + 0);
            vetorDadosSensor[4] = String.valueOf(random.nextInt((50 - 25 + 1)) + 25);
            vetorDadosSensor[5] = String.valueOf(random.nextInt((5000 - 0 + 1000)) + 0);

            mongoConexao.inserirDocumentoNaColecaoTipoSensor(vetorDadosSensor);
        }
    }

    @Test
    public void testInserirDocumentoNaColecaoDados() {
        MongoConexao mongoConexao = new MongoConexao();

        String vetorDadosSensor[] = {"", "", "", ""};
        Random random = new Random();

        Date data = new Date();
        SimpleDateFormat mascara = new SimpleDateFormat("dd/MM/yy");

        Calendar hora = Calendar.getInstance();
        int horas = hora.get(Calendar.HOUR_OF_DAY);
        int minutos = hora.get(Calendar.MINUTE);
        int segundos = hora.get(Calendar.SECOND);

        for (int i = 1; i <= 40; ++i) {
            vetorDadosSensor[0] = String.valueOf(i);
            vetorDadosSensor[1] = String.valueOf(random.nextInt((50 - 0 + 1)) + 0);
            vetorDadosSensor[2] = mascara.format(data);
            vetorDadosSensor[3] = horas + ":" + minutos + ":" + segundos;

            mongoConexao.inserirDocumentoNaColecaoDados(vetorDadosSensor);
        }
    }

    // Coletar --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testColetarDocumentoNaColecaoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        String vetor[];

        for (int i = 1; i <= 20; ++i) {
            vetor = mongoConexao.coletarDocumentoNaColecaoSensor(String.valueOf(i), 0, 1, 1, 1);
            System.out.println("\nId: " + vetor[0] + "\nDescricao: " + vetor[1] + "\nLatitude: " + vetor[2] + "\nLongitude: " + vetor[3] + "\nTipo: " + vetor[4]);
        }
    }

    @Test
    public void testColetarDocumentoNaColecaoTipoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        String vetor[];

        for (int i = 21; i <= 40; ++i) {
            vetor = mongoConexao.coletarDocumentoNaColecaoTipoSensor(String.valueOf(i), 0, 1, 1, 1, 0);
            System.out.println("\nId: " + vetor[0] + "\nDescricao: " + vetor[1] + "\nComunica: " + vetor[2] + "\nMinimo: " + vetor[3] + "\nMaximo: " + vetor[4] + "\nIntervalo: " + vetor[5]);
        }
    }

    @Test
    public void testColetarDocumentoNaColecaoDados() {
        MongoConexao mongoConexao = new MongoConexao();

        ArrayList<String[]> listaDocumentosColetado = mongoConexao.coletarDocumentoNaColecaoDados("2", 1, 1, 1);

        for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
            System.out.println("\nID: " + listaDocumentosColetado.get(i)[0] + "\nValor: " + listaDocumentosColetado.get(i)[1] + "\nData: " + listaDocumentosColetado.get(i)[2] + "\nHora: " + listaDocumentosColetado.get(i)[3]);
        }
    }

    // Mostrar -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testMostrarDocumentosDaColecaoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        ArrayList<String[]> listaDocumentosColetado = mongoConexao.mostrarDocumentosDaColecaoSensor();

        for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
            System.out.println("\n_id: " + listaDocumentosColetado.get(i)[0] + "\nDescricao: " + listaDocumentosColetado.get(i)[1] + "\nLatitude: " + listaDocumentosColetado.get(i)[2] + "\nLongitude: " + listaDocumentosColetado.get(i)[3] + "\nTipo: " + listaDocumentosColetado.get(i)[4]);
        }
    }

    @Test
    public void testMostrarDocumentosDaColecaoTipoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        ArrayList<String[]> listaDocumentosColetado = mongoConexao.mostrarDocumentosDaColecaoTipoSensor();

        for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
            System.out.println("\n_id: " + listaDocumentosColetado.get(i)[0] + "\nDescricao: " + listaDocumentosColetado.get(i)[1] + "\nComunica: " + listaDocumentosColetado.get(i)[2] + "\nMinimo: " + listaDocumentosColetado.get(i)[3] + "\nMaximo: " + listaDocumentosColetado.get(i)[4] + "\nIntervalo: " + listaDocumentosColetado.get(i)[5]);
        }
    }

    @Test
    public void testMostrarDocumentosDaColecaoDados() {
        MongoConexao mongoConexao = new MongoConexao();

        ArrayList<String[]> listaDocumentosColetado = mongoConexao.mostrarDocumentosDaColecaoDados();

        for (int i = 0; i < listaDocumentosColetado.size(); ++i) {
            System.out.println("\nID: " + listaDocumentosColetado.get(i)[0] + "\nValor: " + listaDocumentosColetado.get(i)[1] + "\nData: " + listaDocumentosColetado.get(i)[2] + "\nHora: " + listaDocumentosColetado.get(i)[3]);
        }
    }

    // Remover ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testRemoverDocumentoDaColecaoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        for (int i = 1; i <= 20; ++i) {
            mongoConexao.removerDocumentoDaColecaoSensor(String.valueOf(i));
        }
    }

    @Test
    public void testRemoverDocumentoDaColecaoTipoSensor() {
        MongoConexao mongoConexao = new MongoConexao();

        for (int i = 21; i <= 40; ++i) {
            mongoConexao.removerDocumentoDaColecaoTipoSensor(String.valueOf(i));
        }
    }

    @Test
    public void testRemoverDocumentoDaColecaoDados() {
        MongoConexao mongoConexao = new MongoConexao();

        for (int i = 1; i <= 40; ++i) {
            mongoConexao.removerDocumentoDaColecaoDados(String.valueOf(i));
        }
    }

    // Excluir --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testExcluirBaseDados() {
        MongoConexao mongoConexao = new MongoConexao();
        mongoConexao.excluirBaseDados();
    }

    @Test
    public void testExcluirColecaoSensor() {
        MongoConexao mongoConexao = new MongoConexao();
        mongoConexao.excluirColecaoSensor();
    }

    @Test
    public void testExcluirColecaoTipoSensor() {
        MongoConexao mongoConexao = new MongoConexao();
        mongoConexao.excluirColecaoTipoSensor();
    }

    @Test
    public void testExcluirColecaoDados() {
        MongoConexao mongoConexao = new MongoConexao();
        mongoConexao.excluirColecaoDados();
    }
}
