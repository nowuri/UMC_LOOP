package com.example.interested

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.ActivitySignup3Binding
import java.util.*

class SignUp3Activity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignup3Binding
    //private val listItem = ArrayList<Any>()

    var ID : String = ""
    var pw : String = ""
    var Name: String = ""
    var tel : String = ""
    var birth: String = ""
    var address: String = ""

    var checkbox_status_sms: String = ""
    var checkbox_status_kkt: String = ""
    var checkbox_status_info: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignup3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //뒤로가기
        viewBinding.back.setOnClickListener() {
            val intent = Intent(this, SignUp2Activity::class.java)
            startActivity(intent)
            Toast.makeText(this@SignUp3Activity, "뒤로 돌아가기", Toast.LENGTH_SHORT).show()
        }

        //SMS 정보수신
        viewBinding.checkboxSms.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { //체크
                //listItem.add(isChecked);
                checkbox_status_sms = "1"

            } else { //체크 해제
                //listItem.remove(isChecked);
                checkbox_status_sms = "0"
            }
        }

        //카카오톡 정보수신
        viewBinding.checkboxKkt.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { //체크를 할 때
                checkbox_status_kkt = "1"
            } else { //체크 해제될 때
                checkbox_status_kkt = "0"
            }
        }

        //개인정보 정책 더보기
        viewBinding.privpolicyDetail.setOnClickListener() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("개인정보 정책 타이틀 입니다.")
                .setMessage("개인정보 정책 본문입니다.")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            // 다이얼로그를 띄워주기
            builder.show()
        }

        //개인정보 제공동의 체크박스
        viewBinding.checkboxConsent.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { //체크를 할 때
                checkbox_status_info = "1"
            } else { //체크 해제될 때
                checkbox_status_info = "0"
            }
        }

        if(intent.hasExtra("ID") && intent.hasExtra("PW") && intent.hasExtra("Name") && intent.hasExtra("tel")
            && intent.hasExtra("birth") && intent.hasExtra("address")){
            ID = intent.getStringExtra("ID").toString()
            pw = intent.getStringExtra("PW").toString()
            Name = intent.getStringExtra("Name").toString()
            tel = intent.getStringExtra("tel").toString()
            birth = intent.getStringExtra("birth").toString()
            address = intent.getStringExtra("address").toString()

            checkbox_status_sms = intent.getStringExtra("checkbox_status_sms").toString()
            checkbox_status_kkt = intent.getStringExtra("checkbox_status_kkt").toString()
            checkbox_status_info = intent.getStringExtra("checkbox_status_info").toString()

            Log.e("가져온 값",ID+" "+ pw + " "+ Name + " "+ tel+" "+birth+ " "+address+" "
                    +checkbox_status_sms+" "+checkbox_status_kkt+" "+checkbox_status_info)
        }
        else{
            Toast.makeText(this,"받은 값이 없습니다.",Toast.LENGTH_SHORT).show()
        }
        //다음 버튼 클릭
        viewBinding.next.setOnClickListener{
            val intent = Intent(this, MainActivity_interest::class.java)
            intent.putExtra("ID",ID)
            intent.putExtra("pw",pw)
            intent.putExtra("Name",Name)
            intent.putExtra("tel",tel)
            intent.putExtra("birth",birth)
            intent.putExtra("address",address)
            intent.putExtra("checkbox_status_sms",checkbox_status_sms)
            intent.putExtra("checkbox_status_kkt",checkbox_status_kkt)
            intent.putExtra("checkbox_status_info",checkbox_status_info)

            startActivity(intent)
        }
    }
}