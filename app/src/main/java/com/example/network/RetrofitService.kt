package com.example.network
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

// 서버에서 호출할 메서드를 선언하는 인터페이스
// POST 방식으로 데이터를 주고 받을 때 넘기는 변수는 Field라고 해야한다.
interface RetrofitService {

    @Headers("Content-Type: application/json")
    @POST("frontTest")
    fun addUserByEnqueue(@Body userInfo: SignUpRequestBody): Call<SignUpResponseBody> // Call 은 흐름처리 기능을 제공해줌

    //1차 회원가입 POST
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("auth/signUp")
    fun addUser(@Body userInfo1: SignUp1RequestBody): Call<SignUp1ResponseBody>

    //로그인
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("auth/signIn")
    fun login(@Body login: SigninRequestBody): Call<SigninResponseBody>

    //인증번호 번호 post
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("api/tokens")
    fun snsToken(@Body token: NumberSendRequestBody): Call<NumberSendResponseBody>

    //kakao 로그인 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("auth/kakao")
    fun kakaoLogin(): Call<kakaoResponseBody>

    //2차회원가입 PATCH
    @Headers("accept: application/json", "Content-Type: application/json")
    @HTTP(method="PATCH", path="users/additional", hasBody = true)
    //@PATCH("users/additional")
    fun Signup23Patch(
        @Header("Authorization") accessToken: String,
        @Body userInfo:Signup2RequestBody
    ): Call<Signup2ResponseBody>

    //관심분야 변경 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    //@HTTP(method = "GET", path = "users/interests", hasBody = true)
    @POST("users/interests")
    fun getInterest(@Body Token: String): Call<changeGetResponseBody>

    //관심분야 변경 PATCH
    @Headers("accept: application/json", "Content-Type: application/json")
    @PATCH("users/interests")
    fun interestChange(@Header("authorization") accessToken: String, @Body changeInterest: interestChangeRequestBody): Call <interestChangeResponseBody>

    //회원 탈퇴 PATCH
    @Headers("accept: application/json", "Content-Type: application/json")
    @PATCH("users/withdraw")
    fun eraseUser(@Header("authorization") accessToken: String): Call<eraseUserResponseBody>

    //홈화면 정책 지역별 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("policies/search/region")
    fun HomeDataGet(
        @Query("keyword") keyword: String?="청년",
        @Query("region") region: String?="서울"
    ): Call<HomeDataResponseBody>

}
