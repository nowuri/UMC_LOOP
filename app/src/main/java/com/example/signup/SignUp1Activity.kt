package com.example.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.home.Home
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivitySignUp1Binding
import com.example.login.Login
import java.sql.Types.NULL

class SignUp1Activity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUp1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUp1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val id = viewBinding.idinput
        val pw = viewBinding.pwinput.getText().toString()
        val pwcheck = viewBinding.pwcheckinput.getText().toString()

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
            if(viewBinding.idinput.getText().toString().length != 0){
                if(viewBinding.pwinput.getText().toString().equals(viewBinding.pwcheckinput.getText().toString())){
                    val intent = Intent(this,SignUp2Activity::class.java)
                    startActivity(intent)
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
}