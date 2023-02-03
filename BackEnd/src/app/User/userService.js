const { logger } = require("../../../config/winston");
const { pool } = require("../../../config/database");
const userProvider = require("./userProvider");
const userDao = require("./userDao.js");
const { response, errResponse } = require("../../../config/response");
const { createJwtToken } = require('../../../config/jwtMiddleware.js');

const baseResponseStatus = require("../../../config/baseResponseStatus");

// Service: Create, Update, Delete 비즈니스 로직 처리

exports.createUser = async function(newUserData) {
  try {
    // 이메일 중복 확인
    const emailRows = await userProvider.emailCheck(newUserData.userEmail);
    if (emailRows.length > 0) {

      // console.log(emailRows);
      const user = emailRows[0];
      // console.log(user);
      if (user.status === 2) {
        // console.log(emailRows[0]);
        const token = createJwtToken(user);
        // console.log(token);
        const result = { token, 'userIdx': user.idx };
        return response(baseResponseStatus.SIGNUP_ADDITIONAL_INFO_NEEDED, result );
      }

      return errResponse(baseResponseStatus.SIGNUP_REDUNDANT_EMAIL);
    }

    const connection = await pool.getConnection(async (conn) => conn);

    const userIdResult = await userDao.insertUserInfo(connection, [newUserData.userEmail, newUserData.userName, newUserData.password]);
    const userIdx = userIdResult[0].insertId;


    // Create JWT Token with Userdata
    const [userResult] = await userDao.selectUserIdx(connection, userIdx);
    // console.log(userResult);
    const token = createJwtToken(userResult)
    connection.release();

    return response(baseResponseStatus.SUCCESS, { token, userIdx });
  } catch (err) {
    logger.error(`App - createUser Service error\n: ${err.message}`);
    return errResponse(baseResponseStatus.DB_ERROR);
  }
};


// Gets User Object(from JWT) and interest (array of category code strings)
// interests = {
//    "interested": [],
//    "unInterested": []
// }
exports.patchInterests = async (user, interests) => {
  try {
    const connection = await pool.getConnection(async (conn) => conn);

    const interestedResults = [];
    interests.interested.forEach(async (code) => {
      interestedResults.push(await userDao.upsertInterest(connection, user.idx, code, 1));
    });

    const unInterestedResults = [];
    interests.unInterested.forEach(async (code) => {
      unInterestedResults.push(await userDao.upsertInterest(connection, user.idx, code, 0));
    });

    console.log('interestedResults');
    console.log(interestedResults);
    console.log('unInterestedResults');
    console.log(unInterestedResults);

    connection.release();

    return response(baseResponseStatus.SUCCESS);
  } catch (error) {
    console.error(error);
    return errResponse(baseResponseStatus.DB_ERROR);
  }
};

// 카카오 유저 CREATE
exports.createKakaoUser = async function(newKakaoUserData) {
  try {
    // 이메일 중복 확인

    const emailRows = await userProvider.emailCheck(newKakaoUserData.user_email);
    if (emailRows.length > 0) {

      // console.log(emailRows);
      const user = emailRows[0];
      console.log(user);
      if (user.status === 2) {
        // console.log(emailRows[0]);
        const token = createJwtToken(user);
        console.log(token);
        const result = { token, 'userIdx': user.idx };
        return response(baseResponseStatus.SIGNUP_ADDITIONAL_INFO_NEEDED, result );
      }
      return errResponse(baseResponse.SIGNUP_REDUNDANT_EMAIL);
    }

    const connection = await pool.getConnection(async (conn) => conn);

    const kakaoUserIdResult = await userDao.insertKakaoUserInfo(connection, Object.values(newKakaoUserData));
    console.log(`추가된 회원 : ${kakaoUserIdResult[0].insertId}`)
    connection.release();
    return response(baseResponse.SUCCESS);


  } catch (err) {
    logger.error(`App - createKakaoUser Service error\n: ${err.message}`);
    return errResponse(baseResponse.DB_ERROR);
  }
};


// TODO: After 로그인 인증 방법 (JWT)
// exports.postSignIn = async function(email, password) {
//   try {
//     // 이메일 여부 확인
//     const emailRows = await userProvider.emailCheck(email);
//     if (emailRows.length < 1) return errResponse(baseResponse.SIGNIN_EMAIL_WRONG);

//     const selectEmail = emailRows[0].email

//     // 비밀번호 확인
//     const hashedPassword = await crypto
//       .createHash("sha512")
//       .update(password)
//       .digest("hex");

//     const selectUserPasswordParams = [selectEmail, hashedPassword];
//     const passwordRows = await userProvider.passwordCheck(selectUserPasswordParams);

//     if (passwordRows[0].password !== hashedPassword) {
//       return errResponse(baseResponse.SIGNIN_PASSWORD_WRONG);
//     }

//     // 계정 상태 확인
//     const userInfoRows = await userProvider.accountCheck(email);

//     if (userInfoRows[0].status === "INACTIVE") {
//       return errResponse(baseResponse.SIGNIN_INACTIVE_ACCOUNT);
//     } else if (userInfoRows[0].status === "DELETED") {
//       return errResponse(baseResponse.SIGNIN_WITHDRAWAL_ACCOUNT);
//     }

//     console.log(userInfoRows[0].id) // DB의 userId

//     //토큰 생성 Service
//     let token = await jwt.sign(
//       {
//         userId: userInfoRows[0].id,
//       }, // 토큰의 내용(payload)
//       process.env.JWT_SECRET, // 비밀키
//       {
//         expiresIn: "365d",
//         subject: "userInfo",
//       } // 유효 기간 365일
//     );

//     return response(baseResponse.SUCCESS, { 'userId': userInfoRows[0].id, 'jwt': token });

//   } catch (err) {
//     logger.error(`App - postSignIn Service error\n: ${err.message} \n${JSON.stringify(err)}`);
//     return errResponse(baseResponse.DB_ERROR);
//   }
// };

// exports.editUser = async function(id, nickname) {
//   try {
//     console.log(id)
//     const connection = await pool.getConnection(async (conn) => conn);
//     const editUserResult = await userDao.updateUserInfo(connection, id, nickname)
//     connection.release();

//     return response(baseResponse.SUCCESS);

//   } catch (err) {
//     logger.error(`App - editUser Service error\n: ${err.message}`);
//     return errResponse(baseResponse.DB_ERROR);
//   }
// }
