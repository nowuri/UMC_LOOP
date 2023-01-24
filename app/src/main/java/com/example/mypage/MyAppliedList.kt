package com.example.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interested.databinding.ActivityMyappliedlistBinding
import com.example.interested.databinding.ActivityMypageBinding

class MyAppliedList : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMyappliedlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMyappliedlistBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}