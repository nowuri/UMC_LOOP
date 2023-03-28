package com.example.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivitySignup1Binding
import com.example.login.Login
import com.example.network.RetrofitClient
import com.example.network.SignUp1RequestBody
import com.example.network.SignUp1ResponseBody
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class SignUp1Activity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignup1Binding

    var name: String = ""
    var id: String = ""
    var pw: String = ""
    var tokenGet : String = ""
    var gettoken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignup1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val pwcheck = viewBinding.pwcheckinput.getText().toString()

        //일단 이름 edittext로 해놨음. 추후 데이터 받아오는 것으로 바꿔야 됨
        viewBinding.ETName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = viewBinding.ETName.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


        viewBinding.idinput.setOnKeyListener { v, keyCode, event ->
            var handled = false
            if (event.action == KeyEvent.ACTION_DOWN&& keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(viewBinding.idinput.windowToken,0)
                handled = true
            }
            handled
        }

        viewBinding.pwinput.setOnKeyListener { v, keyCode, event ->
            var handled = false
            if (event.action == KeyEvent.ACTION_DOWN&& keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(viewBinding.pwinput.windowToken,0)
                handled = true
            }
            handled
        }

        viewBinding.pwcheckinput.setOnKeyListener { v, keyCode, event ->
            var handled = false
            if (event.action == KeyEvent.ACTION_DOWN&& keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(viewBinding.pwcheckinput.windowToken,0)
                handled = true
            }
            handled
        }

        viewBinding.next.setOnClickListener(){
            id = viewBinding.idinput.text.toString()
            pw = viewBinding.pwinput.text.toString()
            val userData = SignUp1RequestBody(viewBinding.idinput.text.toString(),name, viewBinding.pwinput.text.toString())
            Log.e("test","id: $id, name: $name, pw: $pw")

            if(viewBinding.idinput.getText().toString().length >=6){
                if(viewBinding.pwinput.getText().toString().equals(viewBinding.pwcheckinput.getText().toString()) && viewBinding.pwinput.getText().length >= 8){
                    val service = RetrofitClient.emgMedService

                    service.addUser(userData)
                        .enqueue(object: retrofit2.Callback<SignUp1ResponseBody>{
                            override fun onResponse(
                                call: Call<SignUp1ResponseBody>,
                                response: Response<SignUp1ResponseBody>
                            ) {
                                if(response.isSuccessful){
                                    val result = response.body()
                                    Log.d("1차 회원가입 성공","$result")

                                    val getresult = response.body()?.result.toString()
                                    Log.d("result","$getresult")
                                    val split = getresult.split(':',',','=')
                                    Log.d("result 나누기","$split")
                                    gettoken = split[1]
                                    Log.d("token","$gettoken")

                                    val intent = Intent(this@SignUp1Activity,SignUp2Activity::class.java)
                                    Log.e("token check!","$gettoken")
                                    intent.putExtra("token",gettoken)
                                    startActivity(intent)
                                }
                            }

                            override fun onFailure(call: Call<SignUp1ResponseBody>, t: Throwable) {
                                Log.d("1차 회원가입 실패",t.message.toString())
                            }
                        })

//                    val intent = Intent(this,SignUp2Activity::class.java)
//                    Log.e("token check!","$gettoken")
//                    intent.putExtra("token",gettoken)
//                    startActivity(intent)

                }
                else if(viewBinding.pwinput.getText().length < 8){
                    Toast.makeText(this,"비밀번호를 8자리 미만입니다.",Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this,"비밀번호 확인 문자가 다릅니다.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
        }

        viewBinding.back.setOnClickListener(){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    class RetrofitWork(private val userInfo: SignUp1RequestBody){
        fun work(){
            val service = RetrofitClient.emgMedService

            service.addUser(userInfo)
                .enqueue(object: retrofit2.Callback<SignUp1ResponseBody>{
                    override fun onResponse(
                        call: Call<SignUp1ResponseBody>,
                        response: Response<SignUp1ResponseBody>
                    ) {
                        if(response.isSuccessful){
                            val result = response.body()
                            Log.d("1차 회원가입 성공","$result")

                            val getresult = response.body()?.result.toString()
                            Log.d("result","$getresult")
                            val split = getresult.split(':',',','=')
                            Log.d("result 나누기","$split")
                            val gettoken = split[1]
                            Log.d("token","$gettoken")
                        }
                    }

                    override fun onFailure(call: Call<SignUp1ResponseBody>, t: Throwable) {
                        Log.d("1차 회원가입 실패",t.message.toString())
                    }
                })
        }
    }
}