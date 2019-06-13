'use strict';

const
    restRoutes = require('../api/v1/routes/rest-routes'),
    logger = require('./logFactory');

module.exports.init = function (app) {

    // ***** Route test server on! *****
    app.all(['/', '/api', '/api/v1'], function (req, res) {
        res.json({
            core: 'Middleware is on',
            version: 'v1',
            date: new Date()
        });
    });

    logger.info('Initializing Routes');
    restRoutes.start(app);
};