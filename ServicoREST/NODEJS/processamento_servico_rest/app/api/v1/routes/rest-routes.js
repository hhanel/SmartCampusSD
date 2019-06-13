'use strict';

const
    responseFactory = require('../../../core/responseFactory'),
    restConfig = require('../config/rest-config'),
    restControllers = require('../controllers/rest-controllers'),
    logger = require('../../../core/logFactory');

module.exports.start = function (app) {
    logger.info('Initializing Routes...');

    app.get('/consulta/sensor', restConfig.getConsulta_config(), function (req, res) {

        restControllers.getConsulta(req, res, false);
    });

    app.get('/consulta/sensor/json', restConfig.getConsulta_config(), function (req, res) {

        restControllers.getConsulta(req, res, true);
    });

    app.post('/insere/sensor', restConfig.postSensor_config(), function (req, res) {

        restControllers.postSensor(req.body, res);
    });

    app.post('/insere/dados', restConfig.postDados_config(), function (req, res) {

        restControllers.postDados(req.body, res);
    });

    app.use((err, req, res, next) => {
        logger.debug('Processando mensagem...');
        logger.debug(req.query);

        if (err.error && typeof err.error.isJoi !== 'undefined') {
            responseFactory.badRequest(req, res, err.error);
        } else {
            next(err);
        }
    });
};