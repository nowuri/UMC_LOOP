const passport = require('passport');
const emailValidator = require('email-validator');
const bcrypt = require('bcrypt');

const { createJwtToken } = require('../../../config/jwtMiddleware.js');
const userProvider = require("../../app/User/userProvider");
const userService = require("../../app/User/userService");
const baseResponseStatus = require("../../../config/baseResponseStatus");
const { response, errResponse } = require("../../../config/response");

// req.body.newUserData = {
//  userEmail, userName, password, 
// }
exports.localSignUp = async (req, res) => {
  try {
    const { newUserData } = req.body;
    if (!newUserData)
      return res.status(400).send(errResponse(baseResponseStatus.SIGNUP_EMAIL_EMPTY));

    // Validate Email
    if (!emailValidator.validate(newUserData.userEmail)) {
      return res.send(errResponse(baseResponseStatus.SIGNUP_EMAIL_ERROR_TYPE));
    }

    // 비밀번호 암호화
    const hashed = await bcrypt.hash(newUserData.password, 10);
    newUserData.password = hashed;

    const createUserResult = await userService.createUser(newUserData);

    // console.log(createUserResult);

    if (createUserResult.code === 1000) {
      return res.send(createUserResult);
    }
    else if (createUserResult.code === 3003) {
      return res.status(300).send(createUserResult);
    }

    res.status(400).send(createUserResult);

  } catch (error) {
    console.error(error);
    return res.status(500).send(errResponse(baseResponseStatus.SERVER_ERROR));
  }
};

// phoneNumber, postalCode, address, agreePICU, agreeSMS, agreeKakao
exports.additionalSignUp = async (req, res) => {

};
/*
* Passsport Local Login 처리과정
1. 로그인 요청이 라우터로 들어옴.
2. 미들웨어를 거치고, passport.authenticate() 호출
3. authenticate에서 passport/localStrategy.js 호출
5. done()정보를 토대로, 로그인 성공 시 사용자 정보 객체와 함께 req.login()를 자동으로 호출
6. req.login 메서드가 passport.serializeUser() 호출 (passport/passport.js)
7. req.session에 사용자 아이디 키값만 저장 (메모리 최적화를 위해서)
8. passport.deserializeUser()로 바로 넘어가서 sql조회후 req.user 객체를 등록후, done() 반환하여 req.login 미들웨어로 다시 되돌아감.
9. 미들웨어 처리후, res.send(response(baseResponseStatus.SUCCESS, user))을 응답하면, 세션쿠키를 브라우저에 보내게 된다.
10. 로그인 완료 처리 (이제 세션쿠키를 통해서 통신하며 로그인됨 상태를 알 수 있다.)
*/

// Session이 아닌 jwt를 활용하기에 session: false 옵션을 넣어준다
// 주의할 점: authenticate 함수는 미들웨어이지만 즉시 실행 함수로 (req, res)를 전달하면 실행한다는 점에 유의
exports.localSignIn = async (req, res) => {
  passport.authenticate('local', { session: false },
    (authError, user, info) => {
      if (authError) {
        console.log(info);
        console.error(authError);
        return res.status(500).send(errResponse(baseResponseStatus.SIGNIN_PASSPORT_AUTH_ERROR));
      }

      if (!user) {
        if (parseInt(info.code / 2000))
          res.status(400);
        return res.send(errResponse(info));
      }

      const token = createJwtToken(user);
            // 만약 유저의 회원가입이 완료되지 않았다면
      if (user.status === 2) {
        res.status(300);
        return res.send(response(baseResponseStatus.SIGNUP_ADDITIONAL_INFO_NEEDED, { token, "userIdx": user.idx }));
      }  

      return res.send(response(baseResponseStatus.SUCCESS, { token }));
    }
  )(req, res);
}


exports.verifyJWT = async (req, res) => {
  console.log(req.user);
  return res.send(response(baseResponseStatus.SUCCESS, req.user));
};

exports.naverLogin = async (req, res) => {
  passport.authenticate('naver-login', {session: false},
  (authError, user, info) => {
    if (authError) {
      console.log(info);
      console.error(authError);
      return res.status(500).send(errResponse(baseResponseStatus.SIGNIN_PASSPORT_AUTH_ERROR));
    }

    if (!user) {
      if (parseInt(info.code / 2000))
        res.status(400);
      return res.send(errResponse(info));
    }

    const token = createJwtToken(user);
    // 만약 유저의 회원가입이 완료되지 않았다면
    if (user.status === 2) {
      res.status(300);
      return res.send(response(baseResponseStatus.SIGNUP_ADDITIONAL_INFO_NEEDED, { token, "userIdx": user.idx }));
    }  

    return res.send(response(baseResponseStatus.SUCCESS, { token }));
  }
)(req, res);
}

exports.kakaoLogin = async (req, res) => {
  passport.authenticate('kakao', {session: false},
  (authError, user, info) => {
    if (authError) {
      console.log(info);
      console.error(authError);
      return res.status(500).send(errResponse(baseResponseStatus.SIGNIN_PASSPORT_AUTH_ERROR));
    }

    if (!user) {
      if (parseInt(info.code / 2000))
        res.status(400);
      return res.send(errResponse(info));
    }

    const token = createJwtToken(user);
    console.log(token + "jwt 토큰**************");
    // 만약 유저의 회원가입이 완료되지 않았다면
    if (user.status === 2) {
      res.status(300);
      return res.send(response(baseResponseStatus.SIGNUP_ADDITIONAL_INFO_NEEDED, { token, "userIdx": user.idx }));
    }  

    return res.send(response(baseResponseStatus.SUCCESS, { token }));
  }
)(req, res);
}

