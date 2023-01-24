const passport =require('passport');
const NaverStrategy = require('passport-naver-v2').Strategy

exports.myNaverStrategy = (cb) => {
    return new NaverStrategy({
        clientID: process.env.NAVER_CLIENT,
        callbackURL: "http://127.0.0.1:3001/app/users/oAuth/naver/callback",
        clientSecret: process.env.NAVER_SECRET
    }, cb);
}
