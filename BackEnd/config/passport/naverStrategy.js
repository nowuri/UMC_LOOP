const passport = require('passport');
const NaverStrategy = require('passport-naver-v2').Strategy;
const bcrypt = require('bcrypt');
const baseResponseStatus = require('../baseResponseStatus.js');

const userProvider = require('../../src/app/User/userProvider.js');
const userService = require('../../src/app/User/userService.js')
 

module.exports = () => {
   passport.use('naver', new NaverStrategy({
        clientID: process.env.NAVER_CLIENT,
        clientSecret: process.env.NAVER_SECRET,
        callbackURL: 'http://helptheyouth-lope.com/app/auth/naver/callback',
        session: false,
      }, async (accessToken, refreshToken, profile, done) => {
        console.log(accessToken);
        const result = JSON.parse(JSON.stringify(profile));
        const userEmail=result.email;
        const userName=result.name;
        const snsId=result.id;
        const userProvider=result.provider;
        console.log(result);
//        res.send(obj);
        // res.send로 데이터전송.. 중복 아이디인지 확인을 먼저 해야?

        const newUser = await userService.createNaverUser({
            user_email: userEmail,
            user_name: userName,
            user_provider: userProvider,
            sns_id: snsId
        })
        done(null, newUser);

      },
      //* 콜백함수의  userId과 password는 위에서 설정한 필드이다. 위에서 객체가 전송되면 콜백이 실행된다.
      async function verify(req, userEmail, done) {
        try {
          // 가입된 회원인지 아닌지 확인
          if (!emailValidator.validate(userEmail))
            done(null, false, baseResponseStatus.SIGNIN_EMAIL_ERROR_TYPE);
          const exUser = await userProvider.retrieveUserByEmail(userEmail);
          // 만일 가입된 회원이면
          // console.log(exUser);
          if (exUser) {
            done(null, exUser); //? 성공이면 done()의 2번째 인수에 선언
          }
            //? done()을 호출하면, /login 요청온 auth 라우터로 다시 돌아가서 미들웨어 콜백을 실행하게 된다.
          // DB에 해당 userId가 없다면, 회원 가입 한적이 없다.
          else {
            done(null, false, baseResponseStatus.USER_USERID_NOT_EXIST);
          }
        } catch (error) {
          console.error(error);
          done(error); //? done()의 첫번째 함수는 err용. 특별한것 없는 평소에는 null로 처리.
        }
      },),
  );
};
