const coolsms = require('coolsms-node-sdk').default;

exports.checkPhoneValidation = (phoneNumber) => {
  return (phoneNumber.length !== 11) ? false : true;
}

exports.getToken = (digit = 6) => {
  const token = Math.floor(Math.random() * 10**digit).toString().padStart(digit, '0');
  return token;
};

exports.sendTokenToSMS = async (phoneNumber, token) => {
  // apiKey, apiSecret 설정
  const { COOLSMS_API_KEY, COOLSMS_API_SECRET, COOlSMS_SENDER } = process.env;
  const messageService = new coolsms(COOLSMS_API_KEY, COOLSMS_API_SECRET);
  const params = {
    to: phoneNumber,
    from: '01053971011',
    text: `[Help The Youth - LOPE]\nYour Validation Token is ${token}.`
  }

  const result = await messageService.sendOne(params);

}
