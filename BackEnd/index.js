// Configure dotenv
require('dotenv').config();

// const express = require('./config/express');
const { app } = require('./config/express.js');
const { logger } = require('./config/winston');

const port = process.env.PORT || 3001;
// express().listen(port);
(async () => {
  const expressApp = await app();
  expressApp.listen(port);
})();
logger.info(`${process.env.NODE_ENV} - API Server Start At Port ${port}`);
