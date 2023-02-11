const express = require('express');
const policy = require('../Policy/policyController');
const user = require("../User/userController");

module.exports = function(app) {

    // 정책 Api

    // 정책 전체 목록 api
    app.get('/app/policies', policy.getPolicies);
    
    // 지역별 정책 목록
    //app.get('/app/policies/region', policy.getPolicyListForRegion);

    // 분야별 정책 목록
    //app.get('/app/policies/category', policy.getPolicyListForCategory);

    // 정책 검색 api
    app.get('/app/policies/search/:keyword/region', policy.SearchPoliciesForRegion); // 지역별
    app.get('/app/policies/search/field', policy.SearchPoliciesForField); // 분야별

    // 특정 정책 상세 정보 api
    app.get('/app/policies/:policyId', policy.getPolicyById);
}