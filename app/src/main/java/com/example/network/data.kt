package com.example.network
import com.google.gson.annotations.SerializedName

data class SignUpRequestBody(
    val id: String?
)


data class SignUpResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val status: String?
)
