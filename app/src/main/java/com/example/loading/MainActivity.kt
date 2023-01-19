package com.example.loading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.interested.databinding.ActivityMainLoadingBinding
import com.example.search.Search

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMainLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainLoadingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        Handler().postDelayed({ startActivity(Intent(this, Search::class.java)) }, 2000L)
    }
}