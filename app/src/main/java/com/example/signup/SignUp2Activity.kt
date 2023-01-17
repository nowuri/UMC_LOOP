package com.example.interested

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.R
import android.os.Handler
import android.webkit.WebChromeClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.SignupPt2Binding
import com.example.loading.MainActivity_Loading
import java.util.*

class SignUp2Activity: AppCompatActivity() {
    private lateinit var viewBinding: SignupPt2Binding
    private var daum_result: TextView? = null
    private var daum_webView: WebView? = null
    private var handler: Handler? = null

    var num = 0
    var possible=-1

    // WebView 초기화
    //init_webView()
    // 핸들러를 통한 JavaScript 이벤트 반응
    //handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = SignupPt2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //daum_result = viewBinding.text_wv_address

        //뒤로가기 클릭했을 때
        viewBinding.back.setOnClickListener(){
//            이전 화면으로 돌아갈 수 있도록 함
//            var intent = Intent(this, )
//            startActivity(intent)
            Toast.makeText(this@SignUp2Activity,"뒤로 돌아가기",Toast.LENGTH_SHORT).show()
        }

        var inputUserName: String = ""


        //여기서부터는 회원정보 입력할 때
        //이름 입력값 저장하고 EditText에 표시
        viewBinding.ETName.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputUserName = viewBinding.ETName.text.toString()
                //viewBinding.ETName.setText(inputUserName)
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        //전화번호 입력값 저장하고 EditText에 표시
        viewBinding.ETTelephone.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var inputUserTel = viewBinding.ETTelephone.text.toString()
                //viewBinding.ETTelephone.setText(inputUserTel)
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        //SMS 인증번호 발송
        //viewBinding.btn_numauth.setOnClickListener(new View.OnClickListener()){
        //    public void onClick(view: View){
        //        sendSMS(inputUserTel, "메시지 전송 테스트")
        //    }
        //}

        viewBinding.ETNumauth.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var inputUserTelAuth = viewBinding.ETNumauth.text.toString()
                //viewBinding.ETNumauth.setText(inputUserTelAuth)
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        //SMS인증번호 확인
        //1분 카운트다운

        //생년월일 입력
        viewBinding.btnBirth.setOnClickListener{
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    viewBinding.birth.setText("${year}년 ${month+1}월 ${dayOfMonth}일")
                }
            }, year, month, date)
            dlg.show()
        }

        //우편번호 클릭

        //다음 버튼 클릭
        viewBinding.next.setOnClickListener{
            val intent = Intent(this, SignUp3Activity::class.java)
            startActivity(intent)
        }
    }

    fun init_webView() {
        // WebView 설정
        daum_webView = viewBinding.wvAddress
        // JavaScript 허용
        daum_webView!!.settings.javaScriptEnabled = true
        // JavaScript의 window.open 허용
        daum_webView!!.settings.javaScriptCanOpenWindowsAutomatically = true
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        daum_webView!!.addJavascriptInterface(AndroidBridge(), "TestApp")
        // web client 를 chrome 으로 설정
        daum_webView!!.webChromeClient = WebChromeClient()
        // webview url load. php 파일 주소
        daum_webView!!.loadUrl("http://192.168.25.60:80/daum_address.php")
    }

    private inner class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String?, arg2: String?, arg3: String?) {
            handler!!.post {
                daum_result!!.text = String.format("(%s) %s %s", arg1, arg2, arg3)
                // WebView를 초기화 하지않으면 재사용할 수 없음
                init_webView()
            }
        }
    }



}