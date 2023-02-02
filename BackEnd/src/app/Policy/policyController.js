const { response, errResponse } = require('../../../config/response.js');
const baseResponseStatus = require('../../../config/baseResponseStatus.js');
const userProvider = require("../User/userProvider");
const axios = require('axios').default;
const fs = require('fs');
const xml2js = require('xml2js');
/**
 * API Name : 카테고리 API
 * Description : 중복된 아이디가 없을 경우 Success Return
 * [GET] /app/policies
 */


//필드에 리퀘스트 받는것 구현하기 지역별? 정책별?
exports.getPolicies = async function(req, res) {

  // request 에서 원하는 필터 받아오기(지역, 정책)
  const fields = req.params.fields; // bizTycdSel (지역)
  const region = req.params.region; // srchPolyBizSecd (정책)
  // let fields = req.param('fields'); // bizTycdSel (지역)
  // let region = req.param('region'); // srchPolyBizSecd (정책)

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: '2809748344615158ecb64134',
      bizTycdSel: fields, //정책 id
      srchPolyBizSecd: region, //지역 id
      display: 10,
      pageIndex: 1
    },
  }

  axios(config).then((policyResponse) => {
    xml2js.parseString(policyResponse.data, (err, data) => {
      if (err) {
        console.error(err);
        res.send(errResponse(baseResponseStatus.SIGNUP_POLICY_ERROR_TYPE))
      } else {
        console.dir(data.empsInfo.emp.length, { depth: null });
        res.send(response(baseResponseStatus.SUCCESS, data.empsInfo.emp))
      }
    });
  }).catch((error) => {
    res.send(response(baseResponseStatus.POLICY_CALL_ERROR_TYPE));
  })
};
