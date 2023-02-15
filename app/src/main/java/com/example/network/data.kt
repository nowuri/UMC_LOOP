package com.example.network
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class SignUpRequestBody(
    val id: String?,
)

data class SignUpResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val status: String?
)

data class SignUp1RequestBody(
    val userEmail: String?,
    val userName: String?,
    val password: String?
)

data class SignUp1ResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val ststus: String?
)

data class SigninRequestBody(
    val userEmail: String?,
    val password: String?
)

data class SigninResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val ststus: String?
)

data class NumberSendRequestBody(
    val phoneNumber: String?
)

data class NumberSendResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val ststus: String?
)

//data class kakaoRequestBody(

//)
data class kakaoResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val status: String?
)

data class regionSearchRequestBody(
    val keyword: String,
    val region: String
)

data class regionSearchResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val status: String?,
)