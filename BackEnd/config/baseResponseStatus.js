module.exports = {

    // Success
    SUCCESS : { "isSuccess": true, "code": 1000, "message":"성공" },

    // Common
    TOKEN_EMPTY : { "isSuccess": false, "code": 2000, "message":"JWT 토큰을 입력해주세요." },
    TOKEN_VERIFICATION_FAILURE : { "isSuccess": false, "code": 3000, "message":"JWT 토큰 검증 실패" },
    TOKEN_VERIFICATION_SUCCESS : { "isSuccess": true, "code": 1001, "message":"JWT 토큰 검증 성공" }, // ?
    LOGIN_NEEDED : { "isSuccess": false, "code": 2021, "message": "로그인 필요" },
    LOGIN_DONE : { "isSuccess": false, "code": 2022, "message": "이미 로그인한 상태입니다." },

    //Request error
    SIGNUP_EMAIL_EMPTY : { "isSuccess": false, "code": 2001, "message":"이메일을 입력해주세요" },
    SIGNUP_EMAIL_LENGTH : { "isSuccess": false, "code": 2002, "message":"이메일은 30자리 미만으로 입력해주세요." },
    SIGNUP_EMAIL_ERROR_TYPE : { "isSuccess": false, "code": 2003, "message":"이메일을 형식을 정확하게 입력해주세요." },
    SIGNUP_PASSWORD_EMPTY : { "isSuccess": false, "code": 2004, "message": "비밀번호를 입력 해주세요." },
    SIGNUP_PASSWORD_LENGTH : { "isSuccess": false, "code": 2005, "message":"비밀번호는 6~20자리를 입력해주세요." },
    SIGNUP_USERID_EMPTY : { "isSuccess": false, "code": 2006, "message":"닉네임을 입력 해주세요." },
    SIGNUP_USERID_ERROR_TYPE : { "isSuccess": false, "code": 2007, "message": "아이디에 공백문자가 있습니다." },
    SIGNUP_USERID_LENGTH : { "isSuccess": false,"code": 2008,"message":"닉네임은 최대 15자리를 입력해주세요." },

    SIGNIN_EMAIL_EMPTY : { "isSuccess": false, "code": 2009, "message":"이메일을 입력해주세요" },
    SIGNIN_EMAIL_LENGTH : { "isSuccess": false, "code": 2010, "message":"이메일은 30자리 미만으로 입력해주세요." },
    SIGNIN_EMAIL_ERROR_TYPE : { "isSuccess": false, "code": 2011, "message":"이메일을 형식을 정확하게 입력해주세요." },
    SIGNIN_PASSWORD_EMPTY : { "isSuccess": false, "code": 2012, "message": "비밀번호를 입력 해주세요." },
    SIGNIN_PASSWORD_NOT_MATCH : { "isSuccess": false, "code": 2012, "message": "옳지 않은 비밀번호입니다." },
    SIGNIN_PASSPORT_AUTH_ERROR : { "isSuccess": false, "code": 2013, "message": "Passport Auth Error" },

    USER_USERID_EMPTY : { "isSuccess": false, "code": 2014, "message": "userId를 입력해주세요." },
    USER_USERID_NOT_EXIST : { "isSuccess": false, "code": 2015, "message": "해당 회원이 존재하지 않습니다." },

    USER_USEREMAIL_EMPTY : { "isSuccess": false, "code": 2016, "message": "이메일을 입력해주세요." },
    USER_USEREMAIL_NOT_EXIST : { "isSuccess": false, "code": 2017, "message": "해당 이메일을 가진 회원이 존재하지 않습니다." },
    USER_ID_NOT_MATCH : { "isSuccess": false, "code": 2018, "message": "유저 아이디 값을 확인해주세요" },
    USER_NICKNAME_EMPTY : { "isSuccess": false, "code": 2019, "message": "변경할 닉네임 값을 입력해주세요" },
    USER_STATUS_EMPTY : { "isSuccess": false, "code": 2020, "message": "회원 상태값을 입력해주세요" },
    USER_DATA_EMPTY : { "isSuccess": false, "code": 2023, "message": "Form 데이터 중 비어있는 곳이 있습니다." },
    USER_PHONENUMBER_ERROR_TYPE : { "isSuccess": false, "code": 2024, "message": "옳지 않은 전화번호 형식입니다." },

    // Response error
    SIGNUP_REDUNDANT_EMAIL : { "isSuccess": false, "code": 3001, "message":"중복된 이메일입니다." },
    SIGNUP_REDUNDANT_USERID : { "isSuccess": false, "code": 3002, "message":"중복된 아이디입니다." },
    SIGNUP_ADDITIONAL_INFO_NEEDED : { "isSuccess": false, "code": 3003, "message": "회원의 추가 정보가 필요합니다. 해당 토큰과 idx를 가지고 PATCH /app/users/additional 로 Redirect 하십시오"},
    SIGNUP_ALREADY_DONE : { "isSuccess": false, "code" : 3004, "message": "이미 회원가입이 완료된 회원입니다." },


    SIGNIN_EMAIL_WRONG : { "isSuccess": false, "code": 3100, "message": "아이디가 잘못 되었습니다." },
    SIGNIN_PASSWORD_WRONG : { "isSuccess": false, "code": 3101, "message": "비밀번호가 잘못 되었습니다." },
    SIGNIN_INACTIVE_ACCOUNT : { "isSuccess": false, "code": 3102, "message": "비활성화 된 계정입니다. 고객센터에 문의해주세요." },
    SIGNIN_WITHDRAWAL_ACCOUNT : { "isSuccess": false, "code": 3103, "message": "탈퇴 된 계정입니다. 고객센터에 문의해주세요." },

    //Connection, Transaction 등의 서버 오류
    DB_ERROR : { "isSuccess": false, "code": 4000, "message": "데이터 베이스 에러"},
    SERVER_ERROR : { "isSuccess": false, "code": 4001, "message": "서버 에러"},

    USER_POLICY_EMPTY : { "isSuccess": false, "code": 5001, "message": "유저 정책은 비어있을 수 없습니다."},
    USER_REGION_EMPTY : { "isSuccess": false, "code": 5001, "message": "정책 지역은 비어있을 수 없습니다."},
    SIGNUP_POLICY_ERROR_TYPE : { "isSuccess": false, "code": 5001, "message": "유저 정책 어쩌고 에러~~"},
    POLICY_CALL_ERROR_TYPE : { "isSuccess": false, "code": 5002, "message": "알 수 없는 이유로 정책 조회에 실패하였습니다."},
}
