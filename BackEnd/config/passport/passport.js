const passport = require('passport');
const local = require('./localStrategy.js');
const google = require('./googleStrategy.js');
const jwt =require('./jwtStrategy.js');

const userProvider = require('../../src/app/User/userProvider.js');

module.exports = () => {
  // Passport가 session을 처리하는 방법 

  // login 성공 시, 인자로 전달된 callback함수 호출
  // localStrategy.js의 성공적인 done함수의 User로부터 전달받음
  passport.serializeUser((user, done) => {
    const userIdx = user.idx;
    done(null, userIdx);
  });

  passport.deserializeUser(async (userIdx, done) => {
    try {
      const user = await userProvider.retrieveUserByIdx(userIdx);
      done(null, user);
      
    } catch (error) {
      console.error(error);
      done(error);
    }
  });

  jwt();
  local();
  // google();
}
