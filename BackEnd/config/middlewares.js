const { response, errResponse } = require('./response.js');
const baseResponseStatus = require('./baseResponseStatus.js');

exports.isLoggedIn = (req, res, next) => {
  // if Logged in, req.isAuthenticated() === true
  if (req.isAuthenticated()) {
    next();
  } else {
    // 로그인이 필요합니다.
    res.status(403).send(errResponse(baseResponseStatus.LOGIN_NEEDED));
  }
};

exports.isNotLoggedIn = (req, res, next) => {
  if (!req.isAuthenticated()) {
    next();
  } else {
    res.send(errResponse(baseResponseStatus.LOGIN_DONE));
  }
};
