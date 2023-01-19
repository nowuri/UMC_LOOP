package com.example.interested

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.SignupPt3Binding
import java.util.*

class SignUp3Activity: AppCompatActivity() {
    private lateinit var viewBinding: SignupPt3Binding
    private var daum_result: TextView? = null
    private var daum_webView: WebView? = null
    private var handler: Handler? = null
    private val listItem = ArrayList<Any>()


    // WebView 초기화
    //init_webView()
    // 핸들러를 통한 JavaScript 이벤트 반응
    //handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = SignupPt3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //daum_result = viewBinding.text_wv_address

        //뒤로가기 클릭했을 때
        viewBinding.back.setOnClickListener() {
            val intent = Intent(this, SignUp2Activity::class.java)
            startActivity(intent)
            Toast.makeText(this@SignUp3Activity, "뒤로 돌아가기", Toast.LENGTH_SHORT).show()
        }

        //이메일 입력
        viewBinding.ETEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var inputUserEmail = viewBinding.ETEmail.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        //SMS 정보수신
        viewBinding.checkboxSms.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this, isChecked.toString(), Toast.LENGTH_SHORT).show();
            if (isChecked) { //체크를 할 때
                listItem.add(isChecked);
            } else { //체크 해제될 때
                viewBinding.checkboxSms.setChecked(false)
                listItem.remove(isChecked);
            }
        }
        //카카오톡 정보수신
        viewBinding.checkboxKkt.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this, isChecked.toString(), Toast.LENGTH_SHORT).show();
            if (isChecked) { //체크를 할 때
                listItem.add(isChecked);
            } else { //체크 해제될 때
                viewBinding.checkboxSms.setChecked(false)
                listItem.remove(isChecked);
            }
        }

        //개인정보 정책 더보기
        viewBinding.privpolicyDetail.setOnClickListener() {
        }

        //개인정보 제공동의 체크박스
        viewBinding.checkboxConsent.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this, isChecked.toString(), Toast.LENGTH_SHORT).show();
            if (isChecked) { //체크를 할 때
                listItem.add(isChecked);
            } else { //체크 해제될 때
                viewBinding.checkboxSms.setChecked(false)
                listItem.remove(isChecked);
            }
        }

        //다음 버튼 클릭
        viewBinding.next.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}

