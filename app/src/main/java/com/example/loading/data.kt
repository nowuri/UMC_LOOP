package com.example.loading
import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("id") var id: String = "",
    @SerializedName("name") var name: String = ""
){}



