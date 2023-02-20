package com.example.interested
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.ActivitySignup2Binding
import com.example.login.Login
import com.example.network.NumberSendRequestBody
import com.example.network.NumberSendResponseBody
import com.example.network.RetrofitClient
import com.example.signup.SignUp1Activity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.math.roundToInt


class SignUp2Activity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignup2Binding
    var name: String = ""
    var tel: String = ""
    var inputUserTelAuth: String = ""
    var address_pt1: String = ""
    var address_pt2: String = ""

    var inputUserAddress: String = ""
    var ID: String =""
    var pw : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //뒤로가기 클릭했을 때
        viewBinding.back.setOnClickListener() {
//            이전 화면으로 돌아갈 수 있도록 함
            val intent = Intent(this, SignUp1Activity::class.java)
            startActivity(intent)
        }

        //여기서부터는 회원정보 입력

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
        viewBinding.btnNumauth.setOnClickListener{
            val numberSend = NumberSendRequestBody(viewBinding.ETTelephone.text.toString())

            val retrofitWork = SignUp2Activity.RetrofitWork(numberSend)
            retrofitWork.work()

            mCountDown.start()
        }

        //생년월일 입력
        viewBinding.btnBirth.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    viewBinding.birth.setText("${year}년 ${month + 1}월 ${dayOfMonth}일")
                    viewBinding.birth.setTextColor(Color.BLACK)
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
                address_pt2 = viewBinding.addressPt2.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

//        if(intent.hasExtra("ID") && intent.hasExtra("PW")&& intent.hasExtra("name")   ){
//            ID = intent.getStringExtra("ID").toString()
//          pw = intent.getStringExtra("PW").toString()
//            name = intent.getStringExtra("name").toString()
//        }
//        else{
//            Log.e("받아온 값","받아온 값이 없습니다.")
//        }

        //다음 버튼 클릭
        viewBinding.next.setOnClickListener {
            val intent = Intent(this, SignUp3Activity::class.java)
            inputUserAddress = address_pt1 +" "+ address_pt2

            intent.putExtra("ID",ID)
            intent.putExtra("PW",pw)
            intent.putExtra("Name",name)
            intent.putExtra("tel",tel)
            intent.putExtra("birth",viewBinding.birth.getText().toString())
            intent.putExtra("address",inputUserAddress)
            startActivity(intent)
        }
    }
    private val mCountDown: CountDownTimer = object : CountDownTimer(120000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val min = millisUntilFinished / 60000
            val sec = millisUntilFinished % 60000 / 1000

            var timeLeftText = "$min:"
            timeLeftText += sec

            viewBinding.numauthStatus.setText(timeLeftText)
        }

        override fun onFinish() {
            viewBinding.numauthStatus.setText("0:00")
        }
    }

    private val ChildForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {result ->
            if (result.getResultCode()==RESULT_OK){
                if (result.getData() != null){
                    val myData: Intent? = result.getData()
                    val StringData: String? = myData?.getStringExtra("data")
                    address_pt1 = StringData.toString()
                    viewBinding.addressPt1.setText(StringData)
                }
            }
        }


    class RetrofitWork(private val phoneNum: NumberSendRequestBody){
        fun work(){
            val service = RetrofitClient.emgMedService

            service.snsToken(phoneNum)
                .enqueue(object: retrofit2.Callback<NumberSendResponseBody>{
                    override fun onResponse(
                        call: Call<NumberSendResponseBody>,
                        response: Response<NumberSendResponseBody>,
                    ) {
                        if(response.isSuccessful){
                            val result = response.body()
                            Log.d("번호 보내기 성공","$result")
                        }
                    }

                    override fun onFailure(call: Call<NumberSendResponseBody>, t: Throwable) {
                        Log.d("번호 보내기 실패",t.message.toString())
                    }

                })
        }
    }
}

