package com.example.find

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.home.Home
import com.example.interested.databinding.ActivityResetPwBinding

class ResetPW : AppCompatActivity() {
    private lateinit var viewBinding: ActivityResetPwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityResetPwBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.endbutton.setOnClickListener(){
            if(viewBinding.pwinput.getText().length == 0 || viewBinding.pwcheckinput.getText().length == 0){
                Toast.makeText(this,"다시 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else if(viewBinding.pwinput.getText().toString() != viewBinding.pwcheckinput.getText().toString()){
                Toast.makeText(this,"비밀번호가 다릅니다.",Toast.LENGTH_SHORT).show()
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("비밀번호 재설정")
                    .setMessage("비밀번호가 재설정되었습니다.")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener{ dialog, id ->
                            val intent = Intent(this,Home::class.java)
                            startActivity(intent)
                        }
                    )
                builder.show()
            }
        }
    }
}