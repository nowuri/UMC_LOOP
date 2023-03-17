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

};
