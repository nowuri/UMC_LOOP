const passport = require('passport');
const KakaoStrategy = require('passport-kakao').Strategy
const jwt = require('jsonwebtoken');


module.exports = () => {
    passport.use('kakao-login', new KakaoStrategy({
        clientID: process.env.KAKAO_CLIENT_ID,
        callbackURL: 'http://localhost:3001/app/users/oAuth/kakao/callback',
        //session: false,
    }, async (accessToken, refreshToken, profile, done) => {
        console.log(accessToken);
        console.log(refreshToken);
        console.log(profile);
        const profile_json = JSON.stringify(profile);
        console.log(profile_json + "**json으로 변환한 데이터**");
    
        // const newUser = await userService.createKakaoUser({
        //   user_email: profile._json && profile._json.kaccount_email,
        //   user_name: profile.displayName,
        //   provider: 'kakao',
        //   sns_id: profile.id,
        // });
        // done(null, newUser);
        //user_email, user_name, provider, sns_id
        //return(res.JSON(profile_json));
    }));
};