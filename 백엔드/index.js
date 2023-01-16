// Configure dotenv
require('dotenv').config();

const express = require('./config/express');
const {logger} = require('./config/winston');

console.log(process.env.PORT);

const port = process.env.PORT || 3001;
express().listen(port);
logger.info(`${process.env.NODE_ENV} - API Server Start At Port ${port}`);
