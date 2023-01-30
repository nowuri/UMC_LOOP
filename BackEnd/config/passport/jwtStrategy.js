const passport = require('passport');
const { Strategy: JwtStrategy, ExtractJwt } = require('passport-jwt');

const baseResponseStatus = require('../baseResponseStatus.js');
const userProvider = require('../../src/app/User/userProvider.js');

module.exports = () => {

  // const jwtFromRequest = ExtractJwt.fromHeader('authorization');
  const JWTConfig = {
    jwtFromRequest: req => req.cookies.jwt,
    secretOrKey: process.env.JWT_SECRET,
  };

  const JWTVerify = async (jwtPayload, done) => {
    try {
      // payload의 id값으로 유저의 데이터 조회
      console.log(jwtPayload);
      const user = await userProvider.retrieveUserByIdx(jwtPayload.userIdx);
      // 유저 데이터가 있다면 유저 데이터 객체 전송
      if (user) {
        done(null, user);
        return;
      }
      // 유저 데이터가 없을 경우 에러 표시
      done(null, false, { reason: '올바르지 않은 인증정보 입니다.' });
    } catch (error) {
      console.error(error);
      done(error);
    }
  };
  
  passport.use('jwt', new JwtStrategy(JWTConfig, JWTVerify));
}
