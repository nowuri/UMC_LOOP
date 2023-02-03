const { response, errResponse } = require('../../../config/response.js');
const baseResponseStatus = require('../../../config/baseResponseStatus.js');
const userProvider = require("../User/userProvider");
const axios = require('axios').default;
const fs = require('fs');
const xml2js = require('xml2js');
const axiosConfig = require('../../../config/axios.js');
/**
 * API Name : 카테고리 API
 * Description : 중복된 아이디가 없을 경우 Success Return
 * [GET] /app/policies
 */

// 지역별
// 서울 : 003002001
// 경기 
// 충청전라
// 강원경상
// 제주


//필드에 리퀘스트 받는것 구현하기 지역별? 정책별?
exports.getPolicies = async function(req, res) {

  // request 에서 원하는 필터 받아오기(지역, 정책)
  const fields = req.params.fields; // bizTycdSel (정책)
  const region = req.params.region; // srchPolyBizSecd (지역)
  // let fields = req.param('fields'); // bizTycdSel (정책)
  // let region = req.param('region'); // srchPolyBizSecd (지역)

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      bizTycdSel: fields, //정책 id
      srchPolyBizSecd: region, //지역 id
      display: 10,
      pageIndex: 1
    },
  }

  // axios(config).then((policyResponse) => {
  //   xml2js.parseString(policyResponse.data, (err, data) => {
  //     if (err) {
  //       console.error(err);
  //       res.send(errResponse(baseResponseStatus.SIGNUP_POLICY_ERROR_TYPE))
  //     } else {
  //       console.dir(data.empsInfo.emp.length, { depth: null });
  //       res.send(response(baseResponseStatus.SUCCESS, data.empsInfo.emp))
  //     }
  //   });
  // }).catch((error) => {
  //   res.send(response(baseResponseStatus.POLICY_CALL_ERROR_TYPE));
  // })

  // const axiosConfig = async (xml) => {
  //   const promise = await new Promise((resolve, reject) => {
  //     const parser = new xml2js.Parser();
  
  //     parser.parseString(xml, (error, result) => {
  //       if (error) reject(error);
  //       else resolve(result);
  //     });
  //   });
  //   return promise;
  // }
  
  try {
    const policyResponse = await axios(config);
    console.log(policyResponse.data);
    const parsedData = await axiosConfig.myParser(policyResponse.data);
  
    console.dir(parsedData, { depth: null });
    return res.send(response(baseResponseStatus.SUCCESS, parsedData));
  
    // xml2js.parseString(policyResponse.data, (err, result) => {
    //   if (err) console.error(err);
    //   else {
    //     console.dir(result, { depth: null });
    //     return res.send(response(baseResponseStatus.SUCCESS));
    //   } 
    // });
  
  
  } catch (error) {
    console.error(error);
    return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  }
};

// 지역별 정책 조회
exports.getPolicyListForRegion = async function(req,res){

  //const { region } = req.query.region;

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      //bizTycdSel: fields, //정책 id
      srchPolyBizSecd: '003002017001,003002012001', //지역 id
      display: 20,
      pageIndex: 1
    },
  }

  try {
    const policyResponse = await axios(config);
    console.log(policyResponse.data);
    const parsedData = await axiosConfig.myParser(policyResponse.data);
  
    console.dir(parsedData, { depth: null });
    return res.send(response(baseResponseStatus.SUCCESS, parsedData));
  
  } catch (error) {
    console.error(error);
    return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  }
};


// 분야별 정책 조회
exports.getPolicyListForCategory = async function(req,res){

  const { category } = req.query.category;

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      bizTycdSel: category, // 정책 id
      display: 10,
      pageIndex: 1
    },
  }

  try {
    const policyResponse = await axios(config);
    console.log(policyResponse.data);
    const parsedData = await axiosConfig.myParser(policyResponse.data);
  
    console.dir(parsedData, { depth: null });
    return res.send(response(baseResponseStatus.SUCCESS, parsedData));
  
  } catch (error) {
    console.error(error);
    return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  }
};

// 정책 검색
exports.SearchPolicies = async function(req,res){

  const keyWord = req.params.keyWord;

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      query: keyWord,
      display: 10,
      pageIndex: 1
    },
  }

  try {
    const policyResponse = await axios(config);
    console.log(policyResponse.data);
    const parsedData = await axiosConfig.myParser(policyResponse.data);
  
    console.dir(parsedData, { depth: null });
    return res.send(response(baseResponseStatus.SUCCESS, parsedData));
  
  } catch (error) {
    console.error(error);
    return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  }
}

// 특정 정책 상세 정보 조회
exports.getPolicyById = async function(req,res){

  const policyId = req.params.policyId;

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      srchPolicyId: policyId,
      display: 10,
      pageIndex: 1
    },
  }

  try {
    const policyResponse = await axios(config);
    console.log(policyResponse.data);
    const parsedData = await axiosConfig.myParser(policyResponse.data);
  
    console.dir(parsedData, { depth: null });
    return res.send(response(baseResponseStatus.SUCCESS, parsedData));
  
  } catch (error) {
    console.error(error);
    return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  }

}



