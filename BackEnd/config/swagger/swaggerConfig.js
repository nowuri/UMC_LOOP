// Swagger Config
const swaggerJsdoc = require('swagger-jsdoc');

const options = {
  failOnErrors: true, // Whether or not to throw when parsing errors. Defaults to false.
  swaggerDefinition: {
    openapi: '3.0.0',
    info: {
      title: 'LOPE 활성화 API 명세서',
      version: '1.0.0',
    },
  },
  // Path relative to where Node.js is ran (app.js)
  apis: ['./config/swagger/*.swagger.yml'],
};

exports.openapiSpecification = swaggerJsdoc(options);
