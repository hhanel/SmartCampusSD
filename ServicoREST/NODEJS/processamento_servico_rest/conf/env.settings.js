'use strict';

module.exports = {
    'databases': {
        'log_tag': 'dbfactory',
        'connections': [
            {
                'authConfig': {
                    'userName': process.env.DB_USERNAME,
                    'password': process.env.DB_PASS,
                    'server': process.env.DB_SERVER,
                    'port': typeof process.env.DB_PORT !== 'undefined' && process.env.DB_PORT.trim() !== '' ? process.env.DB_PORT : null,
                    'options': {
                        'instanceName': typeof process.env.DB_INSTANCENAME !== 'undefined' && process.env.DB_INSTANCENAME.trim() !== '' ? process.env.DB_INSTANCENAME : null,
                        'requestTimeout': process.env.DB_POOL_REQUESTTIMEOUT
                    }
                },
                'poolConfig': {
                    'min': process.env.DB_POOL_MIN,
                    'max': process.env.DB_POOL_MAX,
                    'idleTimeout': process.env.DB_POOL_IDLETIMEOUT,
                    'retryDelay': process.env.DB_POOL_RETRYDELAY,
                    'acquireTimeout': process.env.DB_POOL_ACQUIRETIMEOUT,
                    'log': typeof process.env.DB_INSTANCENAME !== 'undefined' && process.env.DB_POOL_LOG.toLocaleUpperCase() === 'TRUE'
                }
            }
        ]
    }
};