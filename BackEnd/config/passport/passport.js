const passport = require('passport');
const local = require('./localStrategy.js');
const google = require('./googleStrategy.js');
const jwt = require('./jwtStrategy.js');

const userProvider = require('../../src/app/User/userProvider.js');

module.exports = () => {

  jwt();
  local();
  // google();
}
