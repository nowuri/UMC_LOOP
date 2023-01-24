const express = require("../../../config/express");
var passport = require('passport');


module.exports = function(app) {
  const user = require('./userController');
  const jwtMiddleware = require('../../../config/jwtMiddleware');


  // 자체 작업 - 준/권준형
  // ----------------------------------------------
  // 닉제임 중복 확인 API
  app.get('/app/users/api/:userId', user.checkOverlappingUser);

  // SMS 전송 API
  app.post('/app/users/api/tokens', user.sendTokenToSMS);


  // UMC Template
  // ----------------------------------------------
  // 0. 테스트 API
  // app.get('/app/test', user.getTest)

  // 1. 유저 생성 (일반 회원가입) API
  // app.post('/app/users', user.postUsers);

  // 1-1. 네이버 회원가입 API

  
  app.get('/app/users/oAuth/naver/login', passport.authenticate('naver-login'));
//   app.get('/app/users/oAuth/naver/callback', passport.authenticate('naver-login', {
//     failureRedirect: '/',
// }), (req, res) => {
//     res.redirect('/');
// });
//혹은 다른 v2블로그
app.get('/app/users/oAuth/naver/callback', passport.authenticate('naver-login', { authType: 'reprompt' }));



  // 2. 유저 조회 API (+ 검색)
  // app.get('/app/users', user.getUsers);

  // 3. 특정 유저 조회 API
  // app.get('/app/users/:userId', user.getUserById);


  // TODO: After 로그인 인증 방법 (JWT)
  // 로그인 하기 API (JWT 생성)
  // app.post('/app/login', user.login);

  // 회원 정보 수정 API (JWT 검증 및 Validation - 메소드 체이닝 방식으로 jwtMiddleware 사용)
  // app.patch('/app/users/:userId', jwtMiddleware, user.patchUsers)



};


// TODO: 자동로그인 API (JWT 검증 및 Payload 내뱉기)
// JWT 검증 API
// app.get('/app/auto-login', jwtMiddleware, user.check);

// TODO: 탈퇴하기 API
