const nodemailer = require('nodemailer');
const dotenv = require("dotenv").config();

const transporter = nodemailer.createTransport({
    service: 'gmail',
    host: 'helptheyounglope@gmail.com',
    port: 587,
    secure: false,
    auth: {
        user: process.env.MAILS_EMAIL,
        pass: process.env.MAILS_PWD,
    }
});

const info = transporter.sendMail({
            from: 'helptheyounglope@gmail.com',	// 보내는 사람
            to: 'ekgmljeong@naver.com',	// 받는 사람
            subject: 'Help The Young 임시 비밀번호 발급',	// 제목
            text: '임시비밀번호는 1313131 입니다.회원정보 수정에서 비밀번호를 반드시 변경해주시기 바랍니다.',	// 내용
            html: `
                    <h1>Reservation Complete</h1><br>
                    <b>Room Name: </b><br>
                    <b>Room Location:</b><br>
                    <b>Date:</b><br>
                `, // html로 표현한 내용 
        });