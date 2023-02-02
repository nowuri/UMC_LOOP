const jwt = require('jsonwebtoken');
const passport = require('passport');
const { ExtractJwt } = require('passport-jwt');

const { errResponse } = require('./response.js');
const baseResponseStatus = require("./baseResponseStatus.js");


// exports.jwtMiddleware = (req, res, next) => {
//   // read the token from header or url
//   const token = req.headers['x-access-token'] || req.query.token;
//   // token does not exist
//   if (!token) {
//     return res.send(errResponse(baseResponseStatus.TOKEN_EMPTY))
//   }

//   // create a promise that decodes the token
//   const p = new Promise(
//     (resolve, reject) => {
//       const jwtSecret = process.env.JWT_SECRET;
//       jwt.verify(token, jwtSecret, (err, verifiedToken) => {
//         if (err) reject(err);
//         resolve(verifiedToken)
//       })
//     }
//   );

//   // if it has failed to verify, it will return an error message
//   const onError = (error) => {
//     return res.send(errResponse(baseResponseStatus.TOKEN_VERIFICATION_FAILURE))
//   };
//   // process the promise
//   p.then((verifiedToken) => {
//     //비밀 번호 바뀌었을 때 검증 부분 추가 할 곳
//     req.verifiedToken = verifiedToken;
//     next();
//   }).catch(onError)
// };

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
  passport.authenticate('jwt', { session: false },
    async (authErr, user, info) => {
      console.log(authErr, user, info);
      if (authErr || !user) {
        console.error(authErr);
        // console.error(info);
        return res.send(errResponse(baseResponseStatus.TOKEN_VERIFICATION_FAILURE));
      }

      req.user = user;
      next();
    }
  )(req, res);
};

exports.isNotAuthenticated = async (req, res, next) => {
  passport.authenticate('jwt', { session: false },
    async(authErr, user, info) => {
      if (authErr || !user) 
        next();
      else {
        return res.send(errResponse(baseResponseStatus.LOGIN_DONE));
      }
    }
  )(req, res);
};
