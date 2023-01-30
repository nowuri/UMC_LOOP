const { response, errResponse } = require('../../../config/response.js');
const baseResponseStatus = require('../../../config/baseResponseStatus.js');
const baseResponse = require("../../../config/baseResponseStatus");
const regexEmail = require("regex-email");
const userProvider = require("../User/userProvider");
// const {request} = require("express");
// const request = require('request-promise-native')
const axios = require('axios').default;
const fs = require('fs');
const xml2js = require('xml2js');
/**
 * API Name : 카테고리 API
 * Description : 중복된 아이디가 없을 경우 Success Return
 * [GET] /app/policies
 */
exports.getPolicies = async function (req, res) {
    // console.log('req: ' + req);
    // const fields = req.body.policyField;
    //
    // console.log(fields);
    // // Check Empty policy (formal validation)
    // if (!fields)
    //     return res.send(errResponse(baseResponse.USER_POLICY_EMPTY));

    // // 유효한 정책인지 DB에서 체크 (하려면 DB에 유효한 정책 데이터가 있어야함 - 유효한 정책 데이터를 어떻게 넣을 것인지 고민 필요)
    // // 해당 단계가 필요할수도 필요하지 않을수도 있음
    // const userByPolicy = await userProvider.policyCheck(fields);

    // OPEN API 호출해서 목록 가져와서 return
    const config = {
        method: 'get',
        url: 'https://www.youthcenter.go.kr/opi/empList.do',
        params: {
            openApiVlak: '2809748344615158ecb64134',
            //srchPolicyId: 'R2022011101102', //정책id
            display:10,
            pageIndex: 1
        },
    }

    const getXml = async () => {
        try {
            const data = (await axios(config)).data;
            console.log('data' + data);
            return data;
        } catch (err) {
            console.error(err);
        }
    }

    getXml().then(xml => {
        const parseString = xml2js.parseString;
        parseString(xml, (err, data) => {
            if (err) {
                console.error(err);
                return res.send(errResponse(baseResponseStatus.SIGNUP_POLICY_ERROR_TYPE))
            }
            else {
                console.dir(data.empsInfo.emp.length, { depth: null });
                for(var i =0; i<data.empsInfo.emp.length; i++){
                    console.log(`------ ${i}번째 정책의 아이디와 시리얼 번호 -----`)
                    console.log(data.empsInfo.emp[i].bizId[0])
                    console.log(data.empsInfo.emp[i].polyBizSecd[0])
                }

                return res.send(response(baseResponseStatus.SUCCESS, data.empsInfo.emp))
            }
        });
    });
};
