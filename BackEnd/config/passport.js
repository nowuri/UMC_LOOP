const passport = require('passport');
const KakaoStrategy = require('passport-kakao').Strategy

exports.myKakaoStrategy = (cb) => {
    return new KakaoStrategy({
        clientID: process.env.KAKAO_CLIENT_ID,
        callbackURL: 'http://localhost:3001/app/users/oAuth/kakao/callback'
    }, cb);
}