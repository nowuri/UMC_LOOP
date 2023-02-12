package com.example.network
import com.google.gson.annotations.SerializedName

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
    val password: String?,
)

data class SignUp1ResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val ststus: String?
)