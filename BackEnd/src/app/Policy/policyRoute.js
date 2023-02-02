const express = require('express');
const policy = require('../Policy/policyController');
const user = require("../User/userController");

module.exports = function(app) {

    // 정책 Api

    // 정책 목록 api
    app.get('/app/policies', policy.getPolicies);
}