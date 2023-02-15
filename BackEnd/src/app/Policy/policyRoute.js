const express = require('express');
const policy = require('../Policy/policyController');
const user = require("../User/userController");

module.exports = function(app) {

    // 정책 검색 api
    app.get('/app/policies/search/region', policy.SearchPoliciesForRegion); // 지역별
    app.get('/app/policies/search/field', policy.SearchPoliciesForField); // 분야별

    // 특정 정책 상세 정보 api
    app.get('/app/policies/:policyId', policy.getPolicyById);
}