const express = require('express');
const compression = require('compression');
const methodOverride = require('method-override');
const cors = require('cors');
const morgan = require('morgan');
const passport = require('passport');

module.exports = function () {
    const app = express();

    app.use(morgan('dev'));

    app.use(compression());

    app.use(express.json());

    app.use(express.urlencoded({extended: true}));

    app.use(methodOverride());

    app.use(cors());
    // app.use(express.static(process.cwd() + '/public'));

    /* App (Android, iOS) */
    // TODO: 도메인을 추가할 경우 이곳에 Route를 추가하세요.
    require('../src/app/User/userRoute')(app);
    // require('../src/app/Board/boardRoute')(app);

    
    // const passportConfig = require('../passport');
    // passportConfig(app);

    const userRouter = require('../src/app/User/userRoute');
    app.use('/app/users/oAuth', [userRouter]);

    app.use(passport.initialize());
    app.use(passport.session());

    return app;
};
