const express = require('express');
const compression = require('compression');
const methodOverride = require('method-override');
const cors = require('cors');
const morgan = require('morgan');
const cookieParser = require('cookie-parser');

// Passport.js 관련 모듈 import
const passport = require('passport');
const passportConfig = require('./passport/passport.js');

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


  app.use(cookieParser());

  passportConfig();
  app.use(passport.initialize());

  /* App (Android, iOS) */
  // TODO: 도메인을 추가할 경우 이곳에 Route를 추가하세요.
  require('../src/app/User/userRoute')(app);
  require('../src/app/Auth/authRoute')(app);
  require('../src/app/Policy/policyRoute')(app);

  return app;
};
