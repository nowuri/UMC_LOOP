package com.example.network
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

// 서버에서 호출할 메서드를 선언하는 인터페이스
// POST 방식으로 데이터를 주고 받을 때 넘기는 변수는 Field라고 해야한다.
interface RetrofitService {

    @Headers("Content-Type: application/json")
    @POST("signup")
    fun addUserByEnqueue(@Body userInfo: SignUpRequestBody): Call<SignUpResponseBody> // Call 은 흐름처리 기능을 제공해줌

}