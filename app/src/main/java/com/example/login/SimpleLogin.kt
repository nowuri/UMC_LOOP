package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.interested.R
import com.example.interested.databinding.ActivitySimpleLoginBinding
import com.example.network.*
import com.example.signup.SignUp1Activity
import retrofit2.Call
import retrofit2.Response
import com.example.network.RetrofitClient

class SimpleLogin : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySimpleLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySimpleLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.login.setOnClickListener(){
            val id = viewBinding.id.getText().toString()
            val pw = viewBinding.pw.getText().toString()

            val login = SigninRequestBody(id, pw)

            val retrofitWork = Login.RetrofitWork(login)
            retrofitWork.work()
        }
    }

    class RetrofitWrok(private val login: SigninRequestBody){
        fun work(){
            val service = RetrofitClient.emgMedService

            service.login(login)
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