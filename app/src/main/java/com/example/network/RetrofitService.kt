package com.example.network
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import com.google.gson.JsonObject

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
    @PATCH("users/additional")
    fun Signup23Patch(@Body userInfo:Signup2RequestBody): Call<Signup2ResponseBody>

    //관심분야 변경 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("users/interests")
    fun getInterest(@Body Token: token): Call<changeGetResponseBody>

    //관심분야 변경 PATCH
    @Headers("accept: application/json", "Content-Type: application/json")
    @PATCH("users/interests")
    fun interestChange(@Body changeInterest: interestChangeRequestBody): Call <interestChangeResponseBody>

    //회원 탈퇴 PATCH
    @Headers("accept: application/json", "Content-Type: application/json")
    @PATCH("users/withdraw")
    fun eraseUser(@Body accessToken: token): Call<eraseUserResponseBody>

    //홈화면 정책 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("policies/home")
    fun HomeDataGet(
        @Query("region")region:String) : Call<JsonObject>

    //정책검색화면 분야별 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("policies/field")
    fun PolicyFieldGet(
        @Query("field")field:String) : Call<JsonObject>

    //정책검색화면 지역별 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("policies/region")
    fun PolicyRegionGet(
        @Query("region")region:String) : Call<JsonObject>

    //정책검색화면 분야별 검색어와 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("policies/search/field")
    fun PolicyFieldSearchGet(
        @Query("keyword")keyword:String,
        @Query("field")field:String) : Call<JsonObject>

    //정책검색화면 지역별 검색어와 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("policies/search/region")
    fun PolicyRegionSearchGet(
        @Query("keyword")keyword:String,
        @Query("region")region:String) : Call<JsonObject>

    //정책세부화면 GET
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("policies/{policyId}")
    fun PolicyDetailGet(
        @Path("policyId") policyId: String
    ): Call<JsonObject>

    //@Query("region")region:String) : Call<JsonObject>

    //비밀번호 찾기
    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("users/changePasswd")
    fun changePW(@Body userInfo: pwFindRequestBody): Call<pwFindResponseBody>

    //네이버 로그인
    @Headers("accept: application/json", "Content-Type: application/json")
    @GET("auth/naver")
    fun naverLogin(): Call<naverLoginResponseBody>
}
