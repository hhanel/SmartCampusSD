'use strict';

const
    ConnectionPool = require('tedious-connection-pool'),
    logger = require('./logFactory');

let
    pool;

module.exports.initPool = function () {
    logger.info('Initializing pool');

    // Se o log estiver ligado, ajusta para logar com bunyan
    if (global.databases.connections[0].poolConfig.log) {
        global.databases.connections[0].poolConfig.log = module.exports.debug;
    }

    // Inicializa o pool
    pool = new ConnectionPool(global.databases.connections[0].poolConfig, global.databases.connections[0].authConfig);

    pool.on('error', function (err) {
        logger.error('Pool not initialized - See details below');
        logger.error(err);
    });

    logger.info('Pool initialized');
};

module.exports.open = function (callback) {
    pool.acquire(function (err, conn) {
        logger.debug('acquire conn');
        if (err) {
            logger.error("database acquire error");
            logger.error(err);
            return callback(err);
        } else {
            return callback(null, conn);
        }
    });
};

module.exports.release = function (conn) {
    logger.debug('close conn');
    conn.release(function (err) {
        if (err) {
            logger.error("database release error");
            logger.error(err);
        }
    });
};

module.exports.debug = function (text) {
    logger.debug('Pool: ' + text);
};