const express = require('express');
const { isLoggedIn, isNotLoggedIn } = require('../../../config/middlewares.js');
const auth = require('./authController.js');
const { passportJWTMiddleware, isAuthenticated, isNotAuthenticated } = require('../../../config/jwtMiddleware.js');
const passport = require('passport');

module.exports = function(app) {
  // 로그인되어 있지 않다면, 회원가입 진행
  app.post('/app/auth/signUp', isNotAuthenticated, auth.localSignUp);

  app.post('/app/auth/signIn', isNotAuthenticated, auth.localSignIn);

  // JWT - Authorization Bearer Token 미들웨어 확인후 다음 미들웨어로 이동
  app.post('/app/auth', isAuthenticated, auth.verifyJWT);


  // app.post('/app/auth/naver/login', auth.naverLogin);
  app.get('/app/auth/naver/login', auth.naverLogin);
  app.get('/app/auth/naver/callback', auth.naverLogin);


  //  // TODO: After 로그인 인증 방법 (JWT)
  //  // 로그인 하기 API (JWT 생성)
  //  app.post('/app/login', user.login);

  //  // 회원 정보 수정 API (JWT 검증 및 Validation - 메소드 체이닝 방식으로 jwtMiddleware 사용)
  //  app.patch('/app/users/:userId', jwtMiddleware, user.patchUsers);

  //app.post('/app/auth/kakao', auth.kakaoLogin);
  app.get('/app/auth/kakao', auth.kakaoLogin);
  app.get('/app/auth/kakao/callback', auth.kakaoLogin);

};
