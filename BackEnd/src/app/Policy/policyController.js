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
      display: 15,
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

// 지역별 정책 조회
exports.getPolicyListForRegion = async function(req,res){

  let region = '제주';

  switch(region){
    case '서울':
      region = '003002001';
      break;
    case '경기':
      // 인천 경기
      region = '003002004, 003002008';
      break;
    case '충청전라':
      // 광주 대전 충북 충남 전북 전남 세종
      region = '003002005,  003002006, 00300201, 003002011, 003002012, 003002013, 003002017';
      break;
    case '강원경상':
      // 부산 대구 강원 경북 경남
      region = '003002002, 003002003, 003002009, 003002014, 003002015';
      break;
    case '제주':
      region = '003002016';
      break;           
       
  }
  console.log(region + "지역이름");

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      bizTycdSel: ' ', //정책 id
      srchPolyBizSecd: region, //지역 id
      display: 20,
      pageIndex: 1
    },
  }

  axiosConfig.xmlToJson(config);

  // try {
  //   const policyResponse = await axios(config);
  //   //console.log(policyResponse.data);
  //   const parsedData = await axiosConfig.myParser(policyResponse.data);
  
  //   //console.dir(parsedData, { depth: null });
  //   return res.send(response(baseResponseStatus.SUCCESS, parsedData));
  
  // } catch (error) {
  //   console.error(error);
  //   return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  // }
};


// 분야별 정책 조회
exports.getPolicyListForCategory = async function(req,res){

  let field = '주거';
  // 주거 문화 금융 일자리 코로나 창업 건강 기타
  switch(field){
    case '주거':
      field = '004003002';
      break;
    case '문화':
      field = '004004002';
      break;
    case '금융':
      field = '004003001, 004003003';
      break;
    case '일자리':
      field = '004001';
      break;
    case '코로나':
      field = '004006';
      break;
    case '창업':
      field = '004002';
      break;
    case '건강':
      field = '004004001';
      break;
    case '기타':
      field = '004005';
      break;                   
       
  }

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      bizTycdSel: field, // 정책 id
      display: 10,
      pageIndex: 1
    },
  }

  try {
    const policyResponse = await axios(config);
    //console.log(policyResponse.data);
    const parsedData = await axiosConfig.myParser(policyResponse.data);
  
    //console.dir(parsedData, { depth: null });
    return res.send(response(baseResponseStatus.SUCCESS, parsedData));
  
  } catch (error) {
    console.error(error);
    return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
  }
};

// 정책 검색 (지역별)
exports.SearchPoliciesForRegion = async function(req,res){

  //const keyWord = '창업';
  const keyWord = req.body.keyWord;
  //let region = '제주'; 
  let region = req.body.region;

  switch(region){
    case '서울':
      region = '003002001';
      break;
    case '경기':
      // 인천 경기
      region = '003002004, 003002008';
      break;
    case '충청전라':
      // 광주 대전 충북 충남 전북 전남 세종
      region = '003002005,  003002006, 00300201, 003002011, 003002012, 003002013, 003002017';
      break;
    case '강원경상':
      // 부산 대구 강원 경북 경남
      region = '003002002, 003002003, 003002009, 003002014, 003002015';
      break;
    case '제주':
      region = '003002016';
      break;           
       
  }
  console.log(region + "지역이름");
  // const category = req.params.category;

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      query: keyWord,
      srchPolyBizSecd: region, //지역 id
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

// 정책 검색 (분야별)
exports.SearchPoliciesForField = async function(req,res){

  //const keyWord = '창업'; 
  const keyword = req.body.keyword;
  //let field = '주거';
  let field = req.body.field;

  // 주거 문화 금융 일자리 코로나 창업 건강 기타
  switch(field){
    case '주거':
      field = '004003002';
      break;
    case '문화':
      field = '004004002';
      break;
    case '금융':
      field = '004003001, 004003003';
      break;
    case '일자리':
      field = '004001';
      break;
    case '코로나':
      field = '004006';
      break;
    case '창업':
      field = '004002';
      break;
    case '건강':
      field = '004004001';
      break;
    case '기타':
      field = '004005';
      break;                   
  }
  console.log(field + "지역이름");

  const config = {
    method: 'get',
    url: 'https://www.youthcenter.go.kr/opi/empList.do',
    params: {
      openApiVlak: process.env.OPEN_API_KEY,
      query: keyWord,
      bizTycdSel: field, // 정책 id
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



