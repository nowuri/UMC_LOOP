const express = require('express');
// const { isLoggedIn, isNotLoggedIn } = require('../../../config/middlewares.js');
const auth = require('./authController.js');
const { isAuthenticated, isNotAuthenticated } = require('../../../config/jwtMiddleware.js');

module.exports = function(app) {
  // 로그인되어 있지 않다면, 회원가입 진행
  app.post('/app/auth/signUp', isNotAuthenticated, auth.localSignUp);

  app.post('/app/auth/signIn', isNotAuthenticated, auth.localSignIn);

  // JWT - Authorization Bearer Token 미들웨어 확인후 다음 미들웨어로 이동
  app.post('/app/auth', isNotAuthenticated, auth.verifyJWT);


  // app.post('/app/auth/naver/login', auth.naverLogin);
  app.get('/app/auth/naver', auth.naverLogin);
  app.get('/app/auth/naver/callback', auth.naverLogin);


  //app.post('/app/auth/kakao', auth.kakaoLogin);
  app.get('/app/auth/kakao', auth.kakaoLogin);
  app.get('/app/auth/kakao/callback', auth.kakaoLogin);
//   app.get(
//     'app/auth/kakao/callback',
//     //? 그리고 passport 로그인 전략에 의해 kakaoStrategy로 가서 카카오계정 정보와 DB를 비교해서 회원가입시키거나 로그인 처리하게 한다.
//     passport.authenticate('kakao', {
//        failureRedirect: '/', // kakaoStrategy에서 실패한다면 실행
//     }),
//     // kakaoStrategy에서 성공한다면 콜백 실행
//     (req, res) => {
//        res.redirect('/');
//     },
//  );

};
