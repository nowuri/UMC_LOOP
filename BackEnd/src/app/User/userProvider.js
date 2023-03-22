const baseResponseStatus = require("../../../config/baseResponseStatus");
const { pool } = require("../../../config/database");
const { response, errResponse } = require("../../../config/response");
const { logger } = require("../../../config/winston");

const userDao = require("./userDao");

// Provider: Read 비즈니스 로직 처리
//
exports.retrieveInterest = async (user) => {
  try {
    const userIdx = user.idx;

    const connection = await pool.getConnection(async (conn) => conn);
    const userInterestResult = await userDao.selectUserInterest(connection, userIdx);

    connection.release();

    const checked = userInterestResult.filter(item => item.status === 1).map(item => item.category_code);
    const unChecked = userInterestResult.filter(item => item.status === 0).map(item => item.category_code);

    return response(baseResponseStatus.SUCCESS, {checked, unChecked});
    
  } catch (error) {
    console.error(error);
    return errResponse(baseResponseStatus.DB_ERROR);
  }

  
};

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

exports.statusCheck = async (user) => {
  const connection = await pool.getConnection(async (conn) => conn);
  const selectUserResult = await userDao.selectUserIdx(connection, user.idx);
  connection.release();

  // console.log(selectUserResult[0].status);
  
  return selectUserResult[0].status;
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

exports.emailNameCheck = async function(userEmail, userName) {
  const connection = await pool.getConnection(async (conn) => conn);
  console.log(userEmail, userName);
  const userResult = await userDao.selectUserIdForPassword(connection, userEmail, userName);
  connection.release();
  return userResult;
}

exports.namePhoneCheck = async function(userName, userPhone) {
  const connection = await pool.getConnection(async (conn) => conn);
  const userResult = await userDao.selectUserEmailForId(connection, userName, userPhone);
  connection.release();
  return userResult;
}
