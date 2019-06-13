'use strict';

let
    express = require('express'),
    app = express(),
    cors = require('cors'),
    port = process.env.PORT || 3000,
    envConfig = require('../conf/env.config'),
    logger = require('./core/logFactory'),
    routesFactory = require('./core/routesFactory'),
    dbFactory = require('./core/dbFactory'),
    methodOverride = require('method-override'),
    bodyParser = require('body-parser');

exports.config = function () {

    // ***** Starting container log system *****
    logger.initLog();
    logger.info('Initializing Logs system');

    // ***** Starting CORS *****
    app.use(cors());
    logger.info('Initializing CORS');
    app.use('/dashboard', express.static('dashboard'));

    // ***** General expres app configurations *****
    app.use(methodOverride());
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({ extended: true }));

    // ***** Set environment variables *****
    envConfig.init();

    // ***** Starting Routes *****
    routesFactory.init(app);

    // ***** Starting DBFactory *****
    dbFactory.initPool();
};

exports.run = function () {
    app.listen(port, () => {
        logger.info('Listening at http://localhost:' + port);
    }).on('error', (err) => {
        logger.error('Err: Error listen server: ' + err);
    });
};