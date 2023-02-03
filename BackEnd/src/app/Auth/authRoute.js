const express = require('express');
const { isLoggedIn, isNotLoggedIn } = require('../../../config/middlewares.js');
const auth = require('./authController.js');
const { passportJWTMiddleware, isAuthenticated, isNotAuthenticated } = require('../../../config/jwtMiddleware.js');

module.exports = function(app) {
  // 로그인되어 있지 않다면, 회원가입 진행
  app.post('/app/auth/signUp', isNotAuthenticated, auth.localSignUp);

  app.post('/app/auth/signIn', isNotAuthenticated, auth.localSignIn);

  // JWT - Authorization Bearer Token 미들웨어 확인후 다음 미들웨어로 이동
  app.post('/app/auth', isAuthenticated, auth.verifyJWT);

  //app.post('/app/auth/kakao', auth.kakaoLogin);
  app.get('/app/auth/kakao', auth.kakaoLogin);
  app.get('/app/auth/kakao/callback', auth.kakaoLogin);
  // app.get('/app/auth/kakao', passport.authenticate('kakao', { session: false }),
  // (req, res) => {
  // }
  // ,);
  // app.get('/app/auth/kakao/callback',passport.authenticate('kakao', {
  //   failureRedirect: '/', // kakaoStrategy에서 실패한다면 실행
  // }),
  // // kakaoStrategy에서 성공한다면 콜백 실행
  // (req, res) => {
  //    res.redirect('/');
  // },)

};
