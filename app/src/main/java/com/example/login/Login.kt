package com.example.login

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.find.Find
import com.example.interested.databinding.ActivityLoginBinding
import com.example.network.*
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
            getNaverLogin().work()
        }

        viewBinding.kakologin.setOnClickListener(){
            getKakaoLogin().work()
            val uri = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fresponse_type%3Dcode%26redirect_uri%3Dhttp%253A%252F%252Fhelptheyouth-lope.com%252Fapp%252Fauth%252Fkakao%252Fcallback%26through_account%3Dtrue%26client_id%3D563af4f084cf7785bf173ce9f7af43a5#login"
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(uri))
            startActivity(intent)
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

    class getKakaoLogin(){
        fun work(){
            Log.e("카카오로그인","시작")
            val service = RetrofitClient.emgMedService

            service.kakaoLogin()
                .enqueue(object: retrofit2.Callback<kakaoResponseBody>{
                    override fun onResponse(call: Call<kakaoResponseBody>, response: Response<kakaoResponseBody>) {
                        if(response.isSuccessful){
                            val result = response
                            Log.d("카카오 로그인 성공","$result")
                        }
                    }

                    override fun onFailure(call: Call<kakaoResponseBody>, t: Throwable) {
                        Log.d("카카오 로그인 실패",t.message.toString())
                    }

                })
        }
    }

    class getNaverLogin(){
        fun work(){
            val service = RetrofitClient.emgMedService

            service.naverLogin()
                .enqueue(object: retrofit2.Callback<naverLoginResponseBody>{
                    override fun onResponse(
                        call: Call<naverLoginResponseBody>,
                        response: Response<naverLoginResponseBody>,
                    ) {
                        val result = response
                        Log.d("네이버 로그인 성공","$result")
                    }

                    override fun onFailure(call: Call<naverLoginResponseBody>, t: Throwable) {
                        Log.d("네이버 로그인 실패",t.message.toString())
                    }

                })
        }
    }
}