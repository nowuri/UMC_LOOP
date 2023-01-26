package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.find.Find
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityLoginBinding
import com.example.signup.SignUp1Activity

class Login : AppCompatActivity() {
   private lateinit var viewBinding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.naverlogin.setOnClickListener(){
            Toast.makeText(this,"네이버 로그인을 시작합니다.",Toast.LENGTH_SHORT).show()
        }

        viewBinding.kakologin.setOnClickListener(){
            Toast.makeText(this,"카카오 로그인을 시작합니다",Toast.LENGTH_SHORT).show()
        }

        viewBinding.googlelogin.setOnClickListener(){
            Toast.makeText(this,"구글 로그인을 시작합니다.",Toast.LENGTH_SHORT).show()
        }

        viewBinding.login.setOnClickListener(){
            Toast.makeText(this,"로그인을 시작합니다.",Toast.LENGTH_SHORT).show()
        }
        viewBinding.signup.setOnClickListener(){
            val intent = Intent(this, SignUp1Activity::class.java)
            startActivity(intent)
        }

        viewBinding.find.setOnClickListener(){
            val intent = Intent(this,Find::class.java)
            startActivity(intent)
        }
    }
}