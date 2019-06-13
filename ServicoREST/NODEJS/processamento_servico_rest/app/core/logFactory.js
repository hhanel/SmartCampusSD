'use strict';

const
    bunyan = require('bunyan'),
    config = require('../../conf/log.config'),
    fs = require('fs'),
    util = require('util'),
    objDepth = process.env.LOG_OBJECT_DEPTH || 4;

let
    _logger;

module.exports.initLog = function () {
    let
        logger;

    // create log folder
    if (!fs.existsSync(config.logging.folder))
        fs.mkdirSync(config.logging.folder);

    if (!logger) {
        config.logging.options.serializers = bunyan.stdSerializers;
        logger = bunyan.createLogger(config.logging.options);
    }
    _logger = logger;
};

module.exports.trace = function (logMsg) {
    return module.exports.logMessage(_logger, 'trace', logMsg);
};

module.exports.debug = function (logMsg) {
    return module.exports.logMessage(_logger, 'debug', logMsg);
};

module.exports.info = function (logMsg) {
    return module.exports.logMessage(_logger, 'info', logMsg);
};

module.exports.warn = function (logMsg) {
    return module.exports.logMessage(_logger, 'warn', logMsg);
};

module.exports.error = function (logMsg) {
    return module.exports.logMessage(_logger, 'error', logMsg);
};

module.exports.fatal = function (logMsg) {
    return module.exports.logMessage(_logger, 'fatal', logMsg);
};

module.exports.logMessage = function (lg, fn, msg) {
    // Quando não informada mensagem, retorna se a chamada do nível de log está habilitada
    if (typeof msg === 'undefined')
        return lg[fn]();
    // Caso o nível de log estiver desabilidade, não segue adiante
    if (!lg[fn]())
        return;
    // Caso objeto loga ele por completo
    if (typeof msg === 'object') {
        // Caso estiver em modo desenvolvimento, utiliza a console, é bem melhor do que a bunyan para verificar objetos
        if (process.env.NODE_ENV === 'development')
            console.log(util.format('[%s] %s', fn, util.inspect(msg, { showHidden: true, depth: objDepth })));
        return lg[fn](util.format('[%s] %s', fn, util.inspect(msg, { showHidden: true, depth: objDepth })));
    }
    return lg[fn](util.format('[%s] %s', fn, msg));
};  