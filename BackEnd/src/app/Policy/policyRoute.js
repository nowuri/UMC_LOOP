const express = require('express');
const policy = require('../Policy/policyController');
const user = require("../User/userController");

module.exports = function(app) {

    // 홈화면 지역별 정책 api
    app.post('/app/policies/home', policy.HomeListForRegion);
    // 지역별 정책 api
    app.post('/app/policies/region', policy.getPolicyListForRegion);
    // 분야별 정책 api
    app.post('/app/policies/field', policy.getPolicyListForField);
    // 정책 검색 api
    app.post('/app/policies/region/search', policy.SearchPoliciesForRegion); // 지역별
    app.post('/app/policies/field/search', policy.SearchPoliciesForField); // 분야별

    // 특정 정책 상세 정보 api
    app.post('/app/policies/:policyId', policy.getPolicyById);
}