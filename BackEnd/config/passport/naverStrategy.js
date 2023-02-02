const passport = require('passport');
const NaverStrategy = require('passport-naver-v2').Strategy;
const baseResponseStatus = require('../baseResponseStatus.js');

const userProvider = require('../../src/app/User/userProvider.js');
const userService = require('../../src/app/User/userService.js')
//const User = require('../models/user');
 
var redirectURI = encodeURI("http://127.0.0.1:3001/app/auth/naver/callback");

module.exports = () => {
   passport.use('naver-login',
    new NaverStrategy({ //passport.js에이동. 하고 export로이동해서하기....인
        clientID: process.env.NAVER_CLIENT,
        callbackURL: redirectURI,
        clientSecret: process.env.NAVER_SECRET,


      }, async (accessToken, refreshToken, profile, done) => {
        console.log(accessToken);

        const result = JSON.parse(JSON.stringify(profile));
        let userEmail=result.email;
        let userName=result.name;

        console.log(result);
//        res.send(obj);
        // res.send로 데이터전송.. 중복 아이디인지 확인을 먼저 해야?

        const newUser = await userService.createNaverUser({
            user_email: userEmail,
            user_Name: userName
        })
        done(null, newUser);

      },),
  );
};
