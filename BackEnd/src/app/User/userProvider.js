const { pool } = require("../../../config/database");
const { logger } = require("../../../config/winston");

const userDao = require("./userDao");

// Provider: Read 비즈니스 로직 처리

exports.retrieveUserList = async function(email) {
  if (!email) {
    const connection = await pool.getConnection(async (conn) => conn);
    const userListResult = await userDao.selectUser(connection);
    connection.release();

    return userListResult;

  } else {
    const connection = await pool.getConnection(async (conn) => conn);
    const userListResult = await userDao.selectUserEmail(connection, email);
    connection.release();

    return userListResult;
  }
};

exports.retrieveUserByEmail = async function(userEmail) {
  const connection = await pool.getConnection(async (conn) => conn);
  const userResult = await userDao.selectUserEmail(connection, userEmail);

  connection.release();

  return userResult[0];
};

exports.retrieveUserByIdx = async function(userIdx) {
  // console.log(`in retrieveUserByIdx, userIdx: ${userIdx}`);
  const connection = await pool.getConnection(async (conn) => conn);
  const userResult = await userDao.selectUserIdx(connection, userIdx);

  connection.release();

  return userResult[0];
};

exports.emailCheck = async function(email) {
  const connection = await pool.getConnection(async (conn) => conn);
  const emailCheckResult = await userDao.selectUserEmail(connection, email);
  connection.release();

  // console.log(emailCheckResult);
  return emailCheckResult;
};

exports.passwordCheck = async function(selectUserPasswordParams) {
  const connection = await pool.getConnection(async (conn) => conn);
  const passwordCheckResult = await userDao.selectUserPassword(
    connection,
    selectUserPasswordParams
  );
  connection.release();
  return passwordCheckResult[0];
};

exports.accountCheck = async function(email) {
  const connection = await pool.getConnection(async (conn) => conn);
  const userAccountResult = await userDao.selectUserAccount(connection, email);
  connection.release();

  return userAccountResult;
};

exports.emailNameCheck = async function(userEmail, userId) {
  const connection = await pool.getConnection(async (conn) => conn);
  const userResult = await userDao.selectUserIdForPassword(connection, userEmail, userId);
  connection.release();
  return userResult;
}