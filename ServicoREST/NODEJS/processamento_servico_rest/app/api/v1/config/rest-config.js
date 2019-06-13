'use strict';

let
    validator = require('express-joi-validation')({ passError: true }),
    BaseJoi = require('joi'),
    Extension = require('joi-date-extensions'),
    Joi = BaseJoi.extend(Extension);

exports.getConsulta_config = function () {
    let
        payload = Joi.object().keys({
            'tipSensor': Joi.string(),
            'codSensor': Joi.string(),
            'dadoSensor': Joi.boolean(),
            'data': Joi.string().max(10)
        });

    return validator.query(payload);
};

exports.postSensor_config = function () {
    let
        payload = Joi.object().keys({
            'codSensor': Joi.string(),
            'desSensor': Joi.string(),
            'latitude': Joi.number(),
            'longitude': Joi.number(),
            'tipSensor': Joi.string()
        });

    return validator.body(payload);
};

exports.postDados_config = function () {
    let
        payload = Joi.object().keys({
            'codSensor': Joi.string(),
            'valor': Joi.number()
        });

    return validator.body(payload);
};