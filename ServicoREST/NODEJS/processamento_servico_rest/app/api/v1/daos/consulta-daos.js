'use strict';

const
    logger = require('../../../core/logFactory'),
    queries = require('./consulta-queries'),
    moment = require('moment'),
    Request = require('tedious').Request;

module.exports.selectConsultaBase = function (conn, req, callback) {
    let
        i,
        selectConsultaBase = queries.selectConsultaBase(req),
        results = [],
        request;

    request = new Request(selectConsultaBase.query, (err, rowCount) => {
        if (err) {
            logger.error('selectConsultaBase.error');
            // logger.error(err); detalhes do erro na base
            return callback(err);
        }
        if (rowCount === 0)
            return callback(null, 'NotFound');
        else
            return callback(null, results);
    });

    for (i = 0; i < selectConsultaBase.bindings.length; i++) {
        request.addParameter(selectConsultaBase.bindings[i][0], selectConsultaBase.bindings[i][1], selectConsultaBase.bindings[i][2]);
    }

    request.on('row', function (columns) {
        let dadosSensores, encontrado;
        if (req.hasOwnProperty('dadoSensor') && req.dadoSensor || !req.hasOwnProperty('dadoSensor')) {
            for (let j = 0; j < results.length; j++) {
                if (results[j].codSensor === columns[0].value) {
                    encontrado = j;
                    break;
                }
            }
            if (typeof encontrado !== 'undefined') {
                if (typeof results[encontrado].dados === 'undefined')
                    results[encontrado].dados = []

                let novosDados = { "Data": columns[9].value, "Valor": columns[10].value };
                results[encontrado].dados.push(novosDados);
            }
            else {
                dadosSensores = {
                    "codSensor": columns[0].value,
                    "latitude": columns[1].value,
                    "longitude": columns[2].value,
                    "tipSensor": columns[3].value,
                    "desTipoSensor": columns[4].value,
                    "comunicacao": columns[5].value === 'U' ? 'UDP' : 'TCP',
                    "minimo": columns[6].value,
                    "maximo": columns[7].value,
                    "intervalo": columns[8].value + 'seg',
                    "dados": [{ "Data": columns[9].value, "Valor": columns[10].value }]
                };

                results.push(dadosSensores);
            }
        }
        else {
            dadosSensores = {
                "codSensor": columns[0].value,
                "latitude": columns[1].value,
                "longitude": columns[2].value,
                "tipSensor": columns[3].value,
                "desTipoSensor": columns[4].value,
                "comunicacao": columns[5].value === 'U' ? 'UDP' : 'TCP',
                "minimo": columns[6].value,
                "maximo": columns[7].value,
                "intervalo": columns[8].value + 'seg'
            };

            results.push(dadosSensores);
        }
    });

    conn.execSql(request);
};

module.exports.selectSensorBase = function (conn, req, callback) {
    let
        i,
        selectSensorBase = queries.selectSensorBase(req),
        request;

    request = new Request(selectSensorBase.query, (err, rowCount) => {
        if (err) {
            logger.error('selectConsultaSensorBase.error');
            // logger.error(err); detalhes do erro na base
            return callback(err);
        }
        if (rowCount === 0) {
            let error = {}
            error.message = 'Código do sensor não localizado na base de dados';
            return callback(error);
        }
        else
            return callback(null);
    });

    for (i = 0; i < selectSensorBase.bindings.length; i++) {
        request.addParameter(selectSensorBase.bindings[i][0], selectSensorBase.bindings[i][1], selectSensorBase.bindings[i][2]);
    }

    conn.execSql(request);
};

module.exports.insertDadosBase = function (conn, req, callback) {
    let
        i,
        insertDadosBase = queries.insertDadosBase(req),
        request;

    request = new Request(insertDadosBase.query, (err) => {
        if (err) {
            logger.error('insertDadosBase.error');
            // logger.error(err); detalhes do erro na base
            return callback(err);
        }
    });

    for (i = 0; i < insertDadosBase.bindings.length; i++) {
        request.addParameter(insertDadosBase.bindings[i][0], insertDadosBase.bindings[i][1], insertDadosBase.bindings[i][2]);
    }

    request.on('row', function (columns) {
        let dadoInserido =
        {
            'codSensor': req.codSensor,
            'seqDados': columns[0].value,
            'data': moment(new Date().toISOString()).utc().format('DD-MM-YYYY HH:mm'),
            'valor': req.valor,
            'mensagem': 'Dados inseridos na base com sucesso!'
        };

        return callback(null, dadoInserido);
    });

    conn.execSql(request);
};

module.exports.insertSensorBase = function (conn, req, callback) {
    let
        i,
        insertSensorBase = queries.insertSensorBase(req),
        request;

    request = new Request(insertSensorBase.query, (err) => {
        if (err) {
            logger.error('insertSensorBase.error');
            // logger.error(err); detalhes do erro na base
            if (err.message.match('Violation of PRIMARY KEY constraint \'PK_SENSOR\'')) {
                err.message = 'Erro na tentativa de inserção do sensor, codSensor ja cadastrado na base.';
            }
            if (err.message.match('The INSERT statement conflicted with the FOREIGN KEY constraint')) {
                err.message = 'Erro na tentativa de inserção do sensor, tipSensor não localizado.';
            }
            return callback(err);
        } else {
            let sensorInserido = { 'mensagem': 'Sensor ' + req.codSensor + '-' + req.desSensor + ', cadastrado com sucesso!' };

            return callback(null, sensorInserido);
        }
    });

    for (i = 0; i < insertSensorBase.bindings.length; i++) {
        request.addParameter(insertSensorBase.bindings[i][0], insertSensorBase.bindings[i][1], insertSensorBase.bindings[i][2]);
    }

    conn.execSql(request);
};
