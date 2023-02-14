package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.find.Find
import com.example.interested.databinding.ActivityLoginBinding
import com.example.network.PreferenceUtil
import com.example.network.RetrofitClient
import com.example.network.SigninRequestBody
import com.example.network.SigninResponseBody
import com.example.signup.SignUp1Activity
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Call
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var viewBinding: ActivityLoginBinding

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        prefs = PreferenceUtil(applicationContext)

        viewBinding.naverlogin.setOnClickListener(){
            Toast.makeText(this,"네이버 로그인을 시작합니다.", Toast.LENGTH_SHORT).show()
        }

        viewBinding.kakologin.setOnClickListener(){
            Toast.makeText(this,"카카오 로그인을 시작합니다", Toast.LENGTH_SHORT).show()
        }

        viewBinding.loginbtn.setOnClickListener(){
            val intent = Intent(this,SimpleLogin::class.java)
            startActivity(intent)
        }
        viewBinding.signup.setOnClickListener(){
            val intent = Intent(this, SignUp1Activity::class.java)
            startActivity(intent)
        }

        viewBinding.find.setOnClickListener(){
            val intent = Intent(this, Find::class.java)
            startActivity(intent)
        }
    }

    class RetrofitWork(private val loginInfo: SigninRequestBody){
        fun work(){
            val service = RetrofitClient.emgMedService

            service.login(loginInfo)
                .enqueue(object: retrofit2.Callback<SigninResponseBody>{
                    override fun onResponse(
                        call: Call<SigninResponseBody>,
                        response: Response<SigninResponseBody>,
                    ) {
                        if(response.isSuccessful){
                            val result = response.body()
                            Log.d("로그인 성공","$result")
                        }
                    }

                    override fun onFailure(call: Call<SigninResponseBody>, t: Throwable) {
                        Log.d("로그인 실패",t.message.toString())
                    }

                })
        }
    }
}