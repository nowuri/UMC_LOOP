// 모든 유저 조회
async function selectUser(connection) {
  const selectUserListQuery = `
                SELECT email, nickname 
                FROM UserInfo;
                `;
  const [userRows] = await connection.query(selectUserListQuery);
  return userRows;
}

// 이메일로 회원 조회
async function selectUserEmail(connection, email) {
  const selectUserEmailQuery = `
                SELECT *
                FROM user
                WHERE user_email = ?;
                `;
  const [emailRows] = await connection.query(selectUserEmailQuery, email);
  return emailRows;
}

// userId 회원 조회
async function selectUserIdx(connection, userIdx) {
  const selectUserIdQuery = `
                 SELECT * 
                 FROM user
                 WHERE idx = ?;
                 `;
  const [userRow] = await connection.query(selectUserIdQuery, userIdx);
  return userRow;
}

// userId 회원 조회
async function selectUserId(connection, userId) {
  const selectUserIdQuery = `
                 SELECT * 
                 FROM user
                 WHERE user_id = ?;
                 `;
  const [userRow] = await connection.query(selectUserIdQuery, userId);
  return userRow;
}

// 유저 생성
async function insertUserInfo(connection, insertUserInfoParams) {
  const insertUserInfoQuery = `
        INSERT INTO user(user_email, user_name, password, status)
        VALUES (?, ?, ?, 2);
    `;
  const insertUserInfoRow = await connection.query(
    insertUserInfoQuery,
    insertUserInfoParams,
  );

  return insertUserInfoRow;
}

// 카카오 유저 생성
async function insertKakaoUserInfo(connection, insertKakaoUserInfoParams ) {
  console.log(insertKakaoUserInfoParams);
  const insertKakaoUserInfoQuery = "INSERT INTO user(user_email, user_name, provider, sns_id, status) VALUES (?, ?, ?, ?, 2);";

  const insertKakaoUserInfoRow = await connection.query(
    insertKakaoUserInfoQuery,
    insertKakaoUserInfoParams
  );

  return insertKakaoUserInfoRow;
}

// 패스워드 체크
async function selectUserPassword(connection, selectUserPasswordParams) {
  const selectUserPasswordQuery = `
        SELECT password
        FROM user 
        WHERE user_email = ? AND password = ?;`;
  const selectUserPasswordRow = await connection.query(
    selectUserPasswordQuery,
    selectUserPasswordParams
  );

  return selectUserPasswordRow;
}

// 유저 계정 상태 체크 (jwt 생성 위해 id 값도 가져온다.)
async function selectUserAccount(connection, email) {
  const selectUserAccountQuery = `
        SELECT status, id
        FROM UserInfo 
        WHERE email = ?;`;
  const selectUserAccountRow = await connection.query(
    selectUserAccountQuery,
    email
  );
  return selectUserAccountRow[0];
}

async function updateUserInfo(connection, id, nickname) {
  const updateUserQuery = `
  UPDATE UserInfo 
  SET nickname = ?
  WHERE id = ?;`;
  const updateUserRow = await connection.query(updateUserQuery, [nickname, id]);
  return updateUserRow[0];
}

async function upsertInterest(connection, userIdx, code, val) {
  const upsertInterestQuery = `
    INSERT INTO table_name (column1, column2, column3, ...)
    VALUES (value1, value2, value3, ...)
    ON DUPLICATE KEY UPDATE column1 = VALUES(column1), column2 = VALUES(column2), column3 = VALUES(column3), ...
  ;`;

  const upsertInterestRow = await connection.query(
    upsertInterestQuery,
    userIdx
  );

  return upsertInterestRow[0];
}

module.exports = {
  selectUser,
  selectUserEmail,
  selectUserIdx,
  selectUserId,
  insertUserInfo,
  insertKakaoUserInfo,
  selectUserPassword,
  selectUserAccount,
  updateUserInfo,
  upsertInterest,
};
