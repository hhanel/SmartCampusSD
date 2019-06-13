'use strict';

const LOG_FOLDER = './logs';

module.exports = {

    // logging settings
    'logging': {

        // destination folder
        'folder': `${LOG_FOLDER}`,

        // bunyan options
        'options': {

            // log reference
            'name': 'REST',

            // output streams
            'streams': [

                // console output
                {
                    'stream': process.stdout,
                    'level': process.env.LOG_LEVEL
                },

                // file output
                {
                    'type': 'rotating-file',
                    'period': '1d',
                    'count': 365,
                    'path': `${LOG_FOLDER}/core.api.${process.pid}.log`,
                    'level': process.env.LOG_LEVEL
                }
            ]
        }
    }
};
