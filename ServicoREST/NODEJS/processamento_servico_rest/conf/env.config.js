'use strict';

const dotenv = require('dotenv');

dotenv.config();

const
    path = require('path'),
    logger = require('../app/core/logFactory'),
    envSettings = require(path.join(__dirname, 'env.settings'));

// Extend the base configuration with environment
// Specific configuration
module.exports.init = function () {
    let
        key;

    logger.info('Env settings');
    for (key in envSettings) {
        global[key] = envSettings[key];
    }
};