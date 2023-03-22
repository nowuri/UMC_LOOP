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

  return res.send(result);
};

exports.getInterest = async (req, res) => {
  const user = req.user;
  
  const result = await userProvider.retrieveInterest(user);

  if (result.code === 1000) {
    return res.send(result);
  }
  else if (result.code === 3000){
    return res.staus(400).send(result);
  } else {
    return res.status(500).send(result);
  }
};

// req.body = 
// {
//    "token": string,
//    "phoneNumber": string,
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

  const { phoneNumber, address, agreePICU, agreeSMS, agreeKakao, userBirth, interested, unInterested } = req.body;

  // 만약 비어있는 폼 문항이 있다면
  if (!(phoneNumber && address && userBirth))
    return res.status(400).send(errResponse(baseResponseStatus.USER_DATA_EMPTY));

  if (phoneNumber.length !== 11)
    return res.status(400).send(errResponse(baseResponseStatus.USER_PHONENUMBER_ERROR_TYPE));

  // Check if User status is not 2
  const userStatus = await userProvider.statusCheck(user);
  if (userStatus === 1) {
    return res.status(400).send(errResponse(baseResponseStatus.SIGNUP_ALREADY_DONE));
  }

  // Additional info Patch : phoneNumber, address, agreePICU, agreeSMS, agreeKakao
  const infoPatchResult = await userService.patchAdditionalInfo(user, { phoneNumber, address, agreePICU, agreeSMS, agreeKakao, userBirth });

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

exports.findEmail = async (req, res) => {
  const user = req.user;
  const { user_name, user_phone } = req.body;


  // 만약 비어있는 폼 문항이 있다면
<<<<<<< Updated upstream
  const userData = { user_name: user_name, user_phone: user_phone };
  const email = userService.getUserEmail(userData);
  console.log("이메일이뭐냐면",email);
=======
  const userData = { "user_name": user_name, "user_phone": user_phone };
  const email = await userService.getUserEmail(userData);
  console.log("findEmail_이메일이뭐냐면",email);
>>>>>>> Stashed changes
  return res.send(email);

};

exports.findPasswd = async (req, res) => {
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



// ==============================================================================================================
// Test APIs 
// ==============================================================================================================
exports.getTest = async function(req, res) {
  return res.send(response(baseResponseStatus.SUCCESS));
}

exports.jwtTest = async function(req, res) {
  return res.send(response(baseResponseStatus.SUCCESS, req.user));
}

exports.frontTestAPI = async (req, res) => {
  let { cookies, body, query, params } = req;

  return res.status(200).send({ cookies, body, query, params });
};
