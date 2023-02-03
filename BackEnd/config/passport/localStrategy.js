const passport = require('passport');
const emailValidator = require('email-validator');
const bcrypt = require('bcrypt');
const LocalStrategy = require('passport-local').Strategy;
const baseResponseStatus = require('../baseResponseStatus.js');

const userProvider = require('../../src/app/User/userProvider.js');

// 사용자가 local 로그인을 시도할 때, 로그인에 성공하는지 실패하지를 verify하는 코드
module.exports = () => {
  //? auth 라우터에서 /login 요청이 오면 local설정대로 이쪽이 실행되게 된다.
  passport.use(
    new LocalStrategy(
      {
        //* req.body 객체인자 하고 키값이 일치해야 한다.
        usernameField: 'userEmail', // req.body.userId
        passwordField: 'password', // req.body.password
        passReqToCallback: true,
        session: false,
        /*
        session: true, // 세션에 저장 여부
        passReqToCallback: false, 
          express의 req 객체에 접근 가능 여부. true일 때, 뒤의 callback 함수에서 req 인자가 더 붙음. 
            async (req, userId, password, done) => { } 가 됨
        */
      },
      //* 콜백함수의  userId과 password는 위에서 설정한 필드이다. 위에서 객체가 전송되면 콜백이 실행된다.
      async function verify(req, userEmail, password, done) {
        try {
          // 가입된 회원인지 아닌지 확인
          if (!emailValidator.validate(userEmail))
            done(null, false, baseResponseStatus.SIGNIN_EMAIL_ERROR_TYPE);
          const exUser = await userProvider.retrieveUserByEmail(userEmail);
          // 만일 가입된 회원이면
          // console.log(exUser);
          if (exUser) {
            // 해시비번을 비교
            const result = await bcrypt.compare(password, exUser.password);
            if (result) {
                done(null, exUser); //? 성공이면 done()의 2번째 인수에 선언
            } else {
              done(null, false, baseResponseStatus.SIGNIN_PASSWORD_NOT_MATCH); //? 실패면 done()의 2번째 인수는 false로 주고 3번째 인수에 선언
            }
            //? done()을 호출하면, /login 요청온 auth 라우터로 다시 돌아가서 미들웨어 콜백을 실행하게 된다.
          }
          // DB에 해당 userId가 없다면, 회원 가입 한적이 없다.
          else {
            done(null, false, baseResponseStatus.USER_USERID_NOT_EXIST);
          }
        } catch (error) {
          console.error(error);
          done(error); //? done()의 첫번째 함수는 err용. 특별한것 없는 평소에는 null로 처리.
        }
      },
    ),
  );
};
