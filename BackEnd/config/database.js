const mysql = require('mysql2/promise');
const {logger} = require('./winston');

// TODO: 본인의 DB 계정 입력
const { DB_HOST, DB_USER, DB_PORT, DB_PWD, DB_DB } = process.env;
const pool = mysql.createPool({
    host: DB_HOST,
    user: DB_USER,
    port: DB_PORT,
    password: DB_PWD,
    database: DB_DB
});

module.exports = {
    pool: pool
};