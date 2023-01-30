package com.example.interested
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.SignupPt2Binding
import com.example.signup.SignUp1Activity
import java.util.*


class SignUp2Activity: AppCompatActivity() {
    private lateinit var viewBinding: SignupPt2Binding
    var name: String = ""
    var tel: String = ""
    var inputUserTelAuth: String = ""
    var inputUserAddress: String = ""
    var ID: String =""
    var pw : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = SignupPt2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //뒤로가기 클릭했을 때
        viewBinding.back.setOnClickListener() {
//            이전 화면으로 돌아갈 수 있도록 함
            val intent = Intent(this, SignUp1Activity::class.java)
            startActivity(intent)
        }

        //여기서부터는 회원정보 입력
        //이름 입력
        viewBinding.ETName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = viewBinding.ETName.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        //전화번호 입력
        viewBinding.ETTelephone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tel = viewBinding.ETTelephone.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        //SMS 인증번호 발송
        //viewBinding.btn_numauth.setOnClickListener(new View.OnClickListener()){
        //    public void onClick(view: View){
        //        sendSMS(inputUserTel, "메시지 전송 테스트")
        //    }
        //}

        viewBinding.ETNumauth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputUserTelAuth = viewBinding.ETNumauth.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        //SMS 인증번호 확인
        //1분 카운트다운

        //생년월일 입력
        viewBinding.btnBirth.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    viewBinding.birth.setText("${year}년 ${month + 1}월 ${dayOfMonth}일")
                }
            }, year, month, date)
            dlg.show()
        }

        //우편번호 클릭
        // 버튼을 누르면 AddressActivity를 띄워주는 부분
        viewBinding.btnPostnum.setOnClickListener(View.OnClickListener {
            intent = Intent(this, SearchAddress::class.java)
            ChildForResult.launch(intent)
        })

        //상세주소 입력 (address_pt2)
        viewBinding.addressPt2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputUserAddress = viewBinding.addressPt2.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        if(intent.hasExtra("ID") && intent.hasExtra("PW")){
            ID = intent.getStringExtra("ID").toString()
            pw = intent.getStringExtra("PW").toString()
        }
        else{
            Toast.makeText(this,"받아온 값이 없습니다.",Toast.LENGTH_SHORT).show()
        }

        //다음 버튼 클릭
        viewBinding.next.setOnClickListener {
            val intent = Intent(this, SignUp3Activity::class.java)
            intent.putExtra("ID",ID)
            intent.putExtra("PW",pw)
            intent.putExtra("Name",name)
            intent.putExtra("tel",tel)
            intent.putExtra("birth",viewBinding.birth.getText().toString())
            intent.putExtra("address",inputUserAddress)

            startActivity(intent)
        }
    }
    private val ChildForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {result ->
            if (result.getResultCode()==RESULT_OK){
                if (result.getData() != null){
                    val myData: Intent? = result.getData()
                    val StringData: String? = myData?.getStringExtra("data")
                    viewBinding.addressPt1.setText(StringData)
                }
            }
        }


}

