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

  // Social Login - Naver

  app.post('/app/auth/naver/login', auth.naverLogin);
  app.get('/app/auth/naver/login', passport.authenticate('naver-login'));
  app.get('/app/auth/naver/callback', passport.authenticate('naver-login', {
    failureRedirect: '/',
 }), (req, res) => {
     res.redirect('/');
 });



  //  // TODO: After 로그인 인증 방법 (JWT)
  //  // 로그인 하기 API (JWT 생성)
  //  app.post('/app/login', user.login);

  //  // 회원 정보 수정 API (JWT 검증 및 Validation - 메소드 체이닝 방식으로 jwtMiddleware 사용)
  //  app.patch('/app/users/:userId', jwtMiddleware, user.patchUsers);


};

// TODO: 자동로그인 API (JWT 검증 및 Payload 내뱉기)

// //* 카카오로 로그인하기 라우터 ***********************
// //? /kakao로 요청오면, 카카오 로그인 페이지로 가게 되고, 카카오 서버를 통해 카카오 로그인을 하게 되면, 다음 라우터로 요청한다.
// router.get('/kakao', passport.authenticate('kakao'));

// //? 위에서 카카오 서버 로그인이 되면, 카카오 redirect url 설정에 따라 이쪽 라우터로 오게 된다.
// router.get(
//    '/kakao/callback',
//    //? 그리고 passport 로그인 전략에 의해 kakaoStrategy로 가서 카카오계정 정보와 DB를 비교해서 회원가입시키거나 로그인 처리하게 한다.
//    passport.authenticate('kakao', {
//       failureRedirect: '/', // kakaoStrategy에서 실패한다면 실행
//    }),
//    // kakaoStrategy에서 성공한다면 콜백 실행
//    (req, res) => {
//       res.redirect('/');
//    },
// );



// JWT 검증 API
// app.get('/app/auto-login', jwtMiddleware, user.check);

// TODO: 탈퇴하기 API
