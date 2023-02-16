package com.example.myprofile
import android.app.Dialog
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.DialogRemoveuserBinding
import com.example.login.Login
import com.example.network.RetrofitClient
import com.example.network.eraseUserRequestBody
import com.example.network.eraseUserResponseBody
import retrofit2.Call
import retrofit2.Response

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
            val token = "yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                    "eyJkYXRhIjp7InVzZXJJZHgiOjEsInVzZXJJZCI6ImpheSIsInVzZXJuYW1lI" +
                    "joi6raM7KSA7ZiVIn0sImlhdCI6MTY3NTMxMzc5N30.rJuQ2oo3C0-L0ksH" +
                    "thWG8brqCmkwUiPVt6OxLDw5hTc"

            val retrofitWork = RetrofitWork(token)
            retrofitWork.work()

            dlg.dismiss()
        }

        //cancel 버튼 동작
        binding.btnClose.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    class RetrofitWork(private val token: String){
        fun work(){
            val service = RetrofitClient.emgMedService

            service.eraseUser(token)
                .enqueue(object: retrofit2.Callback<eraseUserResponseBody>{
                    override fun onResponse(
                        call: Call<eraseUserResponseBody>,
                        response: Response<eraseUserResponseBody>,
                    ) {
                        val result = response.body()
                        Log.d("회원탈퇴 성공", "$result")
                    }

                    override fun onFailure(call: Call<eraseUserResponseBody>, t: Throwable) {
                        Log.d("회원탈퇴 실패",t.message.toString())
                    }

                })
        }
    }
}