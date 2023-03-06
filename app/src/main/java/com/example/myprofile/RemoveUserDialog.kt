package com.example.myprofile
import android.app.Dialog
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.DialogRemoveuserBinding
import com.example.login.Login
import com.example.network.RetrofitClient
import com.example.network.eraseUserResponseBody
import com.example.network.token
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RemoveUserDialog(private val context : AppCompatActivity) {

    private lateinit var binding : DialogRemoveuserBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    val jsonObject = JSONObject("{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJJZHgiOjEsInVzZXJuYW1lIjoiam9vbiJ9LCJpYXQiOjE2NzUzNTQ3OTh9.MaPPaQjlXqgDR6P84mO2UNj8Oi6lvtUsljGEJZxbuc8\"}")
    val data = token(
        jsonObject.getString("token")
    )

    fun show() {
        binding = DialogRemoveuserBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        //ok 버튼 동작
        binding.btnConfirm.setOnClickListener {

            val retrofitWork = RetrofitWork(data)
            retrofitWork.work()

            dlg.dismiss()
        }

        //cancel 버튼 동작
        binding.btnClose.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    class RetrofitWork(private val token: token){
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