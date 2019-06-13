'use strict';

const
    logger = require('../../../core/logFactory'),
    dbFactory = require('../../../core/dbFactory'),
    consultaDaos = require('../daos/consulta-daos');

module.exports.getConsulta = function (req, callback) {
    dbFactory.open((err, conn) => {
        if (err)
            return callback(err);

        let pConsultaDados = module.exports.selectConsultaPromise(conn, req);
        module.exports.selectConsultaPromiseResolve(conn, pConsultaDados, callback);
    });
};

module.exports.selectConsultaPromise = function (conn, req) {
    return new Promise(function (resolve, reject) {
        consultaDaos.selectConsultaBase(conn, req, function (err, infoSensores) {
            if (err)
                return reject(err);
            else
                return resolve(infoSensores);
        });
    });
};

module.exports.selectConsultaPromiseResolve = function (conn, pConsultaDados, callback) {
    pConsultaDados
        .then((infoSensores) => {
            let retornoInfoSensores;

            logger.debug('Pesquisa na base concluída com sucesso!');
            dbFactory.release(conn);

            if (infoSensores === 'NotFound') {
                logger.debug('Nenhuma dado encontrado.');

                retornoInfoSensores = { 'mensagem': 'Nenhuma dado encontrado.' };
                return callback(null, retornoInfoSensores);
            } else {
                logger.debug('Foram encontrados ' + infoSensores.length + ' sensores.');

                retornoInfoSensores = { 'Response': infoSensores };
                return callback(null, retornoInfoSensores);
            }
        })
        .catch((err) => {
            dbFactory.release(conn);
            logger.error('Erro ao processar mensagem na base.');
            // logger.error(err); detalhes do erro
            return callback(err);
        });
};

module.exports.postDados = function (req, callback) {
    dbFactory.open((err, conn) => {
        if (err)
            return callback(err);

        let pSelectSensor = module.exports.selectSensorPromise(conn, req);
        module.exports.selectSensorPromiseResolve(conn, pSelectSensor, req, callback);
    });
};

module.exports.selectSensorPromise = function (conn, req) {
    return new Promise(function (resolve, reject) {
        consultaDaos.selectSensorBase(conn, req, function (err) {
            if (err)
                return reject(err);
            else
                return resolve();
        });
    });
};

module.exports.selectSensorPromiseResolve = function (conn, pSelectSensor, req, callback) {
    pSelectSensor
        .then(() => {
            let pInsereDados = module.exports.insertDadosPromise(conn, req);
            module.exports.insertDadosPromiseResolve(conn, pInsereDados, callback);
        })
        .catch((err) => {
            dbFactory.release(conn);
            logger.error('Erro ao processar mensagem na base.');
            // logger.error(err); detalhes do erro
            return callback(err);
        });
};

module.exports.insertDadosPromise = function (conn, req) {
    return new Promise(function (resolve, reject) {
        consultaDaos.insertDadosBase(conn, req, function (err, dadoInserido) {
            if (err)
                return reject(err);
            else
                return resolve(dadoInserido);
        });
    });
};

module.exports.insertDadosPromiseResolve = function (conn, pInsereDados, callback) {
    pInsereDados
        .then((dadoInserido) => {
            logger.debug('Dados inseridos com sucesso');
            dbFactory.release(conn);
            return callback(null, dadoInserido);
        })
        .catch((err) => {
            dbFactory.release(conn);
            logger.error('Erro ao processar mensagem na base.');
            // logger.error(err); detalhes do erro
            return callback(err);
        });
};

module.exports.postSensor = function (req, callback) {
    dbFactory.open((err, conn) => {
        if (err)
            return callback(err);

        let pInsertSensor = module.exports.insertSensorPromise(conn, req);
        module.exports.insertSensorPromiseResolve(conn, pInsertSensor, callback);
    });
};

module.exports.insertSensorPromise = function (conn, req) {
    return new Promise(function (resolve, reject) {
        consultaDaos.insertSensorBase(conn, req, function (err, sensorInserido) {
            if (err)
                return reject(err);
            else
                return resolve(sensorInserido);
        });
    });
};

module.exports.insertSensorPromiseResolve = function (conn, pInsertSensor, callback) {
    pInsertSensor
        .then((sensorInserido) => {
            logger.debug('Sensor inserido com sucesso!');
            dbFactory.release(conn);
            return callback(null, sensorInserido);
        })
        .catch((err) => {
            dbFactory.release(conn);
            logger.error('Erro ao processar mensagem na base.');
            // logger.error(err); detalhes do erro
            return callback(err);
        });
};