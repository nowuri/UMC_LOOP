package com.example.myprofile
import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.DialogRemoveuserBinding

class RemoveUserDialog(private val context : AppCompatActivity) {

    private lateinit var binding : DialogRemoveuserBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    fun show() {
        binding = DialogRemoveuserBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        //ok 버튼 동작
        binding.btnConfirm.setOnClickListener {
            //TODO: 탈퇴 처리 코드
            dlg.dismiss()
        }

        //cancel 버튼 동작
        binding.btnClose.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }
}