package com.example.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interested.databinding.ActivityMylikedlistBinding
import com.example.interested.databinding.ActivityMypageBinding

class MyLikedList : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMylikedlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMylikedlistBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}