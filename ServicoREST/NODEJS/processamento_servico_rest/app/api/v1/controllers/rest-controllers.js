'use strict';

const
    responseFactory = require('../../../core/responseFactory'),
    restModels = require('../models/rest-models');

module.exports.getConsulta = function (req, res, json) {
    try {
        restModels.getConsulta(req.query, (error, result) => {
            if (error)
                return responseFactory.serverError(req.query, res, error);

            return responseFactory.ok(req.query, res, result, json);
        });
    } catch (error) {
        return responseFactory.serverError(req.query, res, error);
    }
};

module.exports.postSensor = function (req, res) {
    try {
        restModels.postSensor(req, (error, result) => {
            if (error)
                return responseFactory.serverError(req, res, error);
            else
                return responseFactory.okPostDados(res, result);
        });
    } catch (error) {
        return responseFactory.serverError(req, res, error);
    }
};

module.exports.postDados = function (req, res) {
    try {
        restModels.postDados(req, (error, result) => {
            if (error)
                return responseFactory.serverError(req, res, error);
            else
                return responseFactory.okPostDados(res, result);
        });
    } catch (error) {
        return responseFactory.serverError(req, res, error);
    }
};
