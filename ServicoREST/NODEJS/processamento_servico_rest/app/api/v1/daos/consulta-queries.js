'use strict';

let
    TYPES = require('tedious').TYPES,
    moment = require('moment');

exports.selectConsultaBase = function (req) {
    let
        selectQueryFields =
            ' SELECT ' +
            ' sen.COD_SENSOR, ' +
            ' sen.LATITUDE, ' +
            ' sen.LONGITUDE, ' +
            ' sen.TIP_SENSOR, ' +
            ' tip.DES_TIPO_SENSOR, ' +
            ' tip.COMUNICACAO, ' +
            ' tip.MINIMO, ' +
            ' tip.MAXIMO, ' +
            ' tip.INTERVALO ',
        selectQueryJoin =
            ' FROM BaseSensores.dbo.SENSOR sen JOIN BaseSensores.dbo.TIPO_SENSOR tip ON sen.TIP_SENSOR = tip.COD_TIPO_SENSOR ',
        selectQueryCondition = '',
        bindings = [];

    if (req.hasOwnProperty('tipSensor')) {
        if (selectQueryCondition === '')
            selectQueryCondition += ' WHERE tip.COD_TIPO_SENSOR = @tipSensor '
        else
            selectQueryCondition += ' AND tip.COD_TIPO_SENSOR = @tipSensor ';
        bindings.push(['tipSensor', TYPES.NVarChar, req.tipSensor]);
    }

    if (req.hasOwnProperty('codSensor')) {
        if (selectQueryCondition === '')
            selectQueryCondition += ' WHERE sen.COD_SENSOR = @codSensor '
        else
            selectQueryCondition += ' AND sen.COD_SENSOR = @codSensor ';
        bindings.push(['codSensor', TYPES.NVarChar, req.codSensor]);
    }

    if (req.hasOwnProperty('data') && req.dadoSensor || req.hasOwnProperty('data') && !req.hasOwnProperty('dadoSensor')) {
        if (selectQueryCondition === '')
            selectQueryCondition += ' WHERE CONVERT(DATE, dad.DATAHORA) = @data '
        else
            selectQueryCondition += ' AND CONVERT(DATE, dad.DATAHORA) = @data ';

        bindings.push(['data', TYPES.NVarChar, moment(new Date(req.data).toISOString()).utc().format('YYYY-DD-MM')]);
    }

    if (req.hasOwnProperty('dadoSensor') && req.dadoSensor || !req.hasOwnProperty('dadoSensor')) {
        selectQueryJoin += ' JOIN BaseSensores.dbo.DADOS dad ON dad.COD_SENSOR = sen.COD_SENSOR '
        selectQueryFields += ', dad.DATAHORA, dad.VALOR '
    }

    return {
        'query': selectQueryFields + selectQueryJoin + selectQueryCondition + ';',
        'bindings': bindings
    };
};

exports.selectSensorBase = function (req) {
    let
        selectQueryFields =
            'SELECT * FROM BaseSensores.dbo.SENSOR sen WHERE SEN.COD_SENSOR = @codSensor',
        bindings = [['codSensor', TYPES.NVarChar, req.codSensor]];

    return {
        'query': selectQueryFields + ';',
        'bindings': bindings
    };
};

exports.insertDadosBase = function (req) {
    let
        insertQueryFields =
            'INSERT INTO BaseSensores.dbo.DADOS (COD_SENSOR, SEQ_DADOS, DATAHORA, VALOR) ' +
            'OUTPUT INSERTED.SEQ_DADOS VALUES ' +
            '(@codSensor, NEXT VALUE FOR BaseSensores.dbo.SEQ_DADOS, CURRENT_TIMESTAMP, @valor)',
        bindings = [
            ['codSensor', TYPES.NVarChar, req.codSensor],
            ['valor', TYPES.NVarChar, req.valor]
        ];

    return {
        'query': insertQueryFields + ';',
        'bindings': bindings
    };
};

exports.insertSensorBase = function (req) {
    let
        insertQueryFields =
            'INSERT INTO BaseSensores.dbo.SENSOR (COD_SENSOR, DES_SENSOR, LATITUDE, LONGITUDE, TIP_SENSOR) ' +
            'VALUES (@codSensor, @desSensor, @latitude, @longitude, @tipSensor);',
        bindings = [
            ['codSensor', TYPES.NVarChar, req.codSensor],
            ['desSensor', TYPES.NVarChar, req.desSensor],
            ['latitude', TYPES.NVarChar, req.latitude],
            ['longitude', TYPES.NVarChar, req.longitude],
            ['tipSensor', TYPES.NVarChar, req.tipSensor]
        ];

    return {
        'query': insertQueryFields + ';',
        'bindings': bindings
    };
};