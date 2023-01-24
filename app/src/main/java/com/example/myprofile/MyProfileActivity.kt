package com.example.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interested.databinding.ActivityMypageBinding
import com.example.interested.databinding.ActivityMyprofileBinding

class MyProfileActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMyprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMyprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}