const jwtMiddleware = require("../../../config/jwtMiddleware");
const emailValidator = require('email-validator');

const userProvider = require("../../app/User/userProvider");
const userService = require("../../app/User/userService");
const baseResponseStatus = require("../../../config/baseResponseStatus");
const { response, errResponse } = require("../../../config/response");
const { createJwtToken } = require('../../../config/jwtMiddleware.js');

const { checkPhoneValidation, sendTokenToSMS, getToken } = require('../../../config/coolsms.js');


// LOPE 작업 코드
// ============================================================

/** 
* API Name : 유저 이메일 중복 체크 API
* Description : 중복된 아이디가 없을 경우 Success Return
* [GET] /app/users/api/email?userEmail=jaykwon1234@naver.com
*/
exports.checkOverlappingUser = async (req, res) => {
  const email = req.query.userEmail;

  console.log(email);
  // Check Empty email (formal validation)
  if (!email)
    return res.status(406).send(errResponse(baseResponseStatus.USER_USEREMAIL_EMPTY));

  // Validate Email
  if (!emailValidator.validate(email))
    return res.status(406).send(errResponse(baseResponseStatus.SIGNUP_EMAIL_ERROR_TYPE));

  const userByEmail = await userProvider.emailCheck(email);

  return (userByEmail.length === 0) ? res.send(response(baseResponseStatus.SUCCESS)) : res.send(errResponse(baseResponseStatus.SIGNUP_REDUNDANT_USERID));
};


exports.sendTokenToSMS = async (req, res) => {
  const phoneNumber = req.body.phoneNumber;
  // 1. Check formal validation of phoneNumber.
  const isValid = checkPhoneValidation(phoneNumber);

  // 2. if isValid, get 6 digit token
  if (isValid) {
    const tok = getToken();

    // 3. send SMS and return token in json
    const result = await sendTokenToSMS(phoneNumber, tok);

    // console.log(result);
    // console.log(`token: ${tok}`);
    return res.send(response(baseResponseStatus.SUCCESS, { token: tok }));
  }
  
  return res.send(errResponse(baseResponseStatus.USER_PHONENUMBER_ERROR_TYPE))
}


exports.changeInterest = async (req, res) => {
  const user = req.user;
  const { interested, unInterested } = req.body;

  const result = await userService.patchInterests(user, { interested, unInterested });

  res.send(result);
};


// phoneNumber, postalCode, address, agreePICU, agreeSMS, agreeKakao
// req.body = {
//  phoneNumber: string,
//  postalCode: string, 
//  address: string, 
//  agreePICU: int, 
//  agreeSMS: int, 
//  agreeKakao: int
// }
// Interested
// : Array of Category Code Strings
// ex) req.body = 
// {
//    "phoneNumber": string,
//    "postalCode": string, 
//    "userBirth": string,
//    "address": string, 
//    "agreePICU": int, 
//    "agreeSMS": int, 
//    "agreeKakao": int,
//    "interested" : [ "004001001", "004001002", "004003001", "004003002" ],
//    "unInterested" : ["004001003", "004001004", "004002001", "004002002", "004002003", "004003003", "004004001", "004004002", "004005001", "004005002", "004005003", 
//    "004006001", "004006002", "004006003", "004006004", "004006005", "004006006"]
// }
exports.additionalSignUp = async (req, res) => {
  const user = req.user;

  const { phoneNumber, postalCode, address, agreePICU, agreeSMS, agreeKakao, userBirth, interested, unInterested } = req.body;

  // 만약 비어있는 폼 문항이 있다면
  if (!(phoneNumber && postalCode && address && userBirth))
    return res.status(400).send(errResponse(baseResponseStatus.USER_DATA_EMPTY));

  if (phoneNumber.length !== 11)
    return res.status(400).send(errResponse(baseResponseStatus.USER_PHONENUMBER_ERROR_TYPE));

  // Check if User status is not 2
  const userStatus = await userProvider.statusCheck(user);
  if (userStatus === 1) {
    return res.status(400).send(errResponse(baseResponseStatus.SIGNUP_ALREADY_DONE));
  }

  // Additional info Patch : phoneNumber, postalCode, address, agreePICU, agreeSMS, agreeKakao
  const infoPatchResult = await userService.patchAdditionalInfo(user, { phoneNumber, postalCode, address, agreePICU, agreeSMS, agreeKakao, userBirth });

  // Interest Patch
  await userService.patchInterests(user, { interested, unInterested });

  // Change User SignUp Status to 1
  const patchUserStatusResult = await userService.patchUserStatus(user, 1);

  return res.send(response(baseResponseStatus.SUCCESS));
};

exports.withdrawUser = async (req, res) => {
  const user = req.user;

  await userService.patchUserStatus(user, 3);

  return res.send(response(baseResponseStatus.SUCCESS));
};


exports.changeInfo = async (req, res) => {
  const user = req.user;

  const changeInfoResult = await userService.updateUserInfo(user, info);

  if (changeInfoResult.code === 4000) {
    return res.status(400).send(changeInfoResult);
  }
  return res.send(changeInfoResult);

};


exports.changePasswd = async (req, res) => {
  const user = req.user;
  const { user_email, user_name } = req.body;


  // 만약 비어있는 폼 문항이 있다면
  const userData = { user_email: user_email, user_name: user_name };
  userService.updateUserPassword(userData);
  //여기서 일치한지 확인 (일치하지 않으면 return errResponse)

  //일치하면 임시비번 메일 보내기

  //비번 업데이트 하기.  
  return res.send(response(baseResponseStatus.SUCCESS));

};


// 템플릿 코드
// ========================================================================


/**
 * API No. 0
 * API Name : 테스트 API
 * [GET] /app/test
 */
exports.getTest = async function(req, res) {
  return res.send(response(baseResponseStatus.SUCCESS));
}

exports.frontTestAPI = async (req, res) => {
  let { cookies, body, query, params } = req;

  return res.status(200).send({ cookies, body, query, params });
};

/**
 * API No. 1
 * API Name : 유저 생성 (회원가입) API
 * [POST] /app/users
 */
// exports.postUsers = async function(req, res) {

//   /**
//    * Body: email, password, nickname
//    */
//   const { email, password, nickname } = req.body;

//   // 빈 값 체크
//   if (!email)
//     return res.send(response(baseResponse.SIGNUP_EMAIL_EMPTY));

//   // 길이 체크
//   if (email.length > 30)
//     return res.send(response(baseResponse.SIGNUP_EMAIL_LENGTH));

//   // 형식 체크 (by 정규표현식)
//   if (!regexEmail.test(email))
//     return res.send(response(baseResponse.SIGNUP_EMAIL_ERROR_TYPE));

//   // 기타 등등 - 추가하기


//   const signUpResponse = await userService.createUser(
//     email,
//     password,
//     nickname
//   );

//   return res.send(signUpResponse);
// };

/**
 * API No. 2
 * API Name : 유저 조회 API (+ 이메일로 검색 조회)
 * [GET] /app/users
 */
// exports.getUsers = async function(req, res) {

//   /**
//    * Query String: email
//    */
//   const email = req.query.email;

//   if (!email) {
//     // 유저 전체 조회
//     const userListResult = await userProvider.retrieveUserList();
//     return res.send(response(baseResponse.SUCCESS, userListResult));
//   } else {
//     // 유저 검색 조회
//     const userListByEmail = await userProvider.retrieveUserList(email);
//     return res.send(response(baseResponse.SUCCESS, userListByEmail));
//   }
// };

/**
 * API No. 3
 * API Name : 특정 유저 조회 API
 * [GET] /app/users/{userId}
 */
// exports.getUserById = async function(req, res) {

//   /**
//    * Path Variable: userId
//    */
//   const userId = req.params.userId;

//   if (!userId) return res.send(errResponse(baseResponse.USER_USERID_EMPTY));

//   const userByUserId = await userProvider.retrieveUser(userId);
//   return res.send(response(baseResponse.SUCCESS, userByUserId));
// };



// TODO: After 로그인 인증 방법 (JWT)
/**
 * API No. 4
 * API Name : 로그인 API
 * [POST] /app/login
 * body : email, passsword
 */
// exports.login = async function(req, res) {

//   const { email, password } = req.body;

//   // TODO: email, password 형식적 Validation

//   const signInResponse = await userService.postSignIn(email, password);

//   return res.send(signInResponse);
// };


/**
 * API No. 5
 * API Name : 회원 정보 수정 API + JWT + Validation
 * [PATCH] /app/users/:userId
 * path variable : userId
 * body : nickname
 */
// exports.patchUsers = async function(req, res) {

//   // jwt - userId, path variable :userId

//   const userIdFromJWT = req.verifiedToken.userId

//   const userId = req.params.userId;
//   const nickname = req.body.nickname;

//   if (userIdFromJWT != userId) {
//     res.send(errResponse(baseResponse.USER_ID_NOT_MATCH));
//   } else {
//     if (!nickname) return res.send(errResponse(baseResponse.USER_NICKNAME_EMPTY));

//     const editUserInfo = await userService.editUser(userId, nickname)
//     return res.send(editUserInfo);
//   }
// };

// exports.kakaoCallback = async function (req, res) {

// }


// exports.addUser = function(newUser, callback){
//     bcrypt.genSalt(10, (err, salt) => {
//       bcrypt.hash(newUser.password, salt, (err, hash) => {
//         if(err) throw err;
//         newUser.password = hash;
//         newUser.save(callback);
//       });
//     });
// }












/** JWT 토큰 검증 API
 * [GET] /app/auto-login
 */
// exports.check = async function(req, res) {
//   const userIdResult = req.verifiedToken.userId;
//   console.log(userIdResult);
//   return res.send(response(baseResponse.TOKEN_VERIFICATION_SUCCESS));
// };
