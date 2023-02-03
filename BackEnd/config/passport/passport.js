const passport = require('passport');
const local = require('./localStrategy.js');
const kakao = require('./kakaoStrategy.js');
const jwt = require('./jwtStrategy.js');

const userProvider = require('../../src/app/User/userProvider.js');

module.exports = () => {

  jwt();
  local();
  kakao();
}
