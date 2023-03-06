const express = require('express');
const user = require('./userController');
const { isAuthenticated } = require('../../../config/jwtMiddleware.js');


module.exports = function(app) {

  // 2차 마무리 회원가입
  app.patch('/app/users/additional', isAuthenticated, user.additionalSignUp);

  // 유저가 선택한 관심 카테고리 가져오기 API
  app.post('/app/users/interests', isAuthenticated, user.getInterest);

  // 관심 카테고리 변경 API
  app.patch('/app/users/interests', isAuthenticated, user.changeInterest);

  // 비밀번호 변경
  app.post('/app/users/changePasswd', user.changePasswd);

  // 회원 탈퇴 API
  app.patch('/app/users/withdraw', isAuthenticated, user.withdrawUser);

  // 회원 정보 수정 API
  // app.patch('/app/users/info', isAuthenticated, user.changeInfo);

  // Small APIs
  // 닉제임 중복 확인 API
  app.post('/app/api/emails', user.checkOverlappingUser);

  // SMS 전송 API
  app.post('/app/api/tokens', user.sendTokenToSMS);



  // 0. 테스트 API
  app.get('/app/api/test', user.getTest);
  app.post('/app/api/jwtTest', isAuthenticated, user.jwtTest);
  
  app.post('/app/api/frontTest', user.frontTestAPI);

};

