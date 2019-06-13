'use strict';

// Path
const path = require('path');

// Getting Env
require('dotenv').config({ path: path.join(__dirname, "/.env") });

// Getting Launcher
const launcher = require('./app/launcher');

// Set Configuration
launcher.config();

// Starting application
launcher.run();
