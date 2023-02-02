package com.example.description
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.DialogCommentBinding
import com.example.interested.databinding.SignupPt2Binding
import com.example.signup.SignUp1Activity

class CommentDialog(private val context : AppCompatActivity) {

    private lateinit var binding : DialogCommentBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    fun show() {
        binding = DialogCommentBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        //ok 버튼 동작
        binding.btnFinish.setOnClickListener {
            //작성 완료
            onClickedListener.onClicked(binding.inputComment.text.toString())
            dlg.dismiss()
        }

        //cancel 버튼 동작
        binding.btnClose.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    interface ButtonClickListener {
        fun onClicked(myName: String)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

}