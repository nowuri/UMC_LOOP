const express = require('express');
const compression = require('compression');
const methodOverride = require('method-override');
const cors = require('cors');
const morgan = require('morgan');

// Passport.js 관련 모듈 import
const passport = require('passport');
const passportConfig = require('./passport/passport.js');

const session = require('express-session');
const MySQLStore = require('express-mysql-session')(session);
const { pool } = require('./database.js');

// Swagger.js 관련 모듈 import
const swaggerUi = require('swagger-ui-express');
const { openApiSpecification } = require('./swagger/swaggerConfig.js');

exports.app = async function() {
  const app = express();
  


  app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(openApiSpecification));

  app.use(morgan('dev'));

  app.use(compression());

  app.use(express.json());

  app.use(express.urlencoded({ extended: true }));

  app.use(methodOverride());

  app.use(cors());

  const connection = await pool.getConnection((async (conn) => conn));
  const sessionStore = new MySQLStore({}, connection);

  app.use(session({
    resave: false,
    saveUninitialized: false,
    secret: 'secret',
    cookie: {
      httpOnly: true,
      secure: false,
    },
    store: sessionStore,
  }));

  passportConfig();
  app.use(passport.initialize());
  app.use(passport.session());
  
  

  /* App (Android, iOS) */
  // TODO: 도메인을 추가할 경우 이곳에 Route를 추가하세요.
  require('../src/app/User/userRoute')(app);
  require('../src/app/Auth/authRoute')(app);

  return app;
};
