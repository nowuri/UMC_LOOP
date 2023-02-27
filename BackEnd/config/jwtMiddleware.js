const jwt = require('jsonwebtoken');

const userProvider = require('../src/app/User/userProvider');

const { errResponse } = require('./response.js');
const baseResponseStatus = require("./baseResponseStatus.js");


exports.createJwtToken = (user) => {
  const token = jwt.sign({
    data: {
      userIdx: user.idx,
      userId: user.user_id,
      username: user.user_name,
    }
  }, process.env.JWT_SECRET);
  return token;
};

exports.isAuthenticated = async (req, res, next) => {
  try {
    const token = req.body.token;
    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    const userIdx = decoded.data.userIdx;
    const userInfo = await userProvider.retrieveUserByIdx(userIdx);
    req.user = userInfo;
    next();
  } catch (error) {
    console.error(error);
    return res.status(400).send(errResponse(baseResponseStatus.TOKEN_VERIFICATION_FAILURE));
  }
};

exports.isNotAuthenticated = async (req, res, next) => {
  try {
    const token = req.body.token;
    if (!token) {
      next();
    }

    return res.status(400).send(errResponse(baseResponseStatus.LOGIN_DONE));
  } catch (error) {
    console.error(error);
  }
};
