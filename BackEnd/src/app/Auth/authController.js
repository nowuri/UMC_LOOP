const passport = require('passport');
const jwt = require('jsonwebtoken');
const axios = require('axios');

const { createJwtToken } = require('../../../config/jwtMiddleware.js');
const userProvider = require("../../app/User/userProvider");
const userService = require("../../app/User/userService");
const baseResponseStatus = require("../../../config/baseResponseStatus");
const { response, errResponse } = require("../../../config/response");

// req.body.newUserData = {
// userId, password, userEmail, userName, phoneNumber, postalCode, address, agreePICU, agreeSMS, agreeKakao
// }
exports.localSignUp = async (req, res) => {
  const { newUserData } = req.body;

  try {
    const exUser = await userProvider.retrieveUser(newUserData.userId);
    if (exUser) {
      return res.send(errResponse(baseResponseStatus.SIGNUP_REDUNDANT_USERID));
    }
    // 비밀번호 암호화 - 보류 구현이 우선
    const createUserResult = await userService.createUser(newUserData);
    console.log(createUserResult);
    res.send(response(baseResponseStatus.SUCCESS, createUserResult));

  } catch (error) {
    console.error(error);
    return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  }
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



exports.kakaoLogin = async(req, res) => {
  // 이미 카카오 로그인에서 중복회원검증햇으니까
  // 여기는 그냥 db에 넣기..?
  const { AccessToken } = req.body;
  // try {
    
  //   const createKakaoUserResult = await userService.createKakaoUser(AccessToken);
  //   console.log(createKakaoUserResult);
  //   res.send(response(baseResponseStatus.SUCCESS, createKakaoUserResult));

  // } catch (error) {
  //   console.error(error);
  //   return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  // }
  // passport.authenticate('kakao-login', { session: false },
  // (req, res) => {
  //   //const createUserResult = await userService.createUser(newUserData);
  //   console.log(req);
  //   console.log(req.user);

  //   res.send(response.baseResponseStatus.SUCCESS, req.user);
  // }
  // );

  let kakaoProfile; //값을 수정해주어야 하므로 const가 아닌 let 사용

	try{ //axios 모듈을 이용하여 Profile 정보를 가져온다.
            kakaoProfile = await axios.get('https://kapi.kakao.com/v2/user/me', {
                headers: {
                    Authorization: 'Bearer ' + AccessToken,
                    'Content-Type': 'application/json'
                }
            })
     } catch (err) {
          return res.send(errResponse(baseResponse.ACCESS_TOKEN_VERIFICATION_FAILURE));
     }
};
exports.localLogin = async (req, res) => {
  // session이 아닌 jwt를 이용하므로 session: false 옵션을 넣어준다.
  passport.authenticate('local', { session: false },
    (authError, user, info) => {
      if (authError) {
        console.log(info);
        console.error(authError);
        return res.send(errResponse(baseResponseStatus.SIGNIN_PASSPORT_AUTH_ERROR));
      }
      if (!user) {
        // console.log(info);
        return res.send(errResponse(info));
      }

      // req.login 함수가 passport.js의 serializeUser 함수를 호출
      // serializeUser 이후 deserializeUser 바로 호출
      // => jwt 사용 시에는 serialize, deserialize를 사용하지 않음.
      return req.login(user, (loginError) => {
        // console.log(req.user);
        if (loginError) {
          console.error(loginError);
          return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
        }
        // 클라이언트에게 jwt 생성 후 반환
        const token = createJwtToken(user);
        res.cookie('jwt', jwt, { httpOnly: true, secure: true });
        return res.send(response(baseResponseStatus.SUCCESS, { accessToken: token }));
      });
    })(req, res);
};

exports.verifyJWT = async (req, res) => {
  passport.authenticate('jwt', { session: false }, 
  (req, res) => {
      console.log(req);
      console.log(req.user);

      res.send(response.baseResponseStatus.SUCCESS, req.user);
    }
  );
};
