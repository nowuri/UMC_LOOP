package com.example.loading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interested.databinding.ActivityMainLoadingBinding

class MainActivity_Loading : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMainLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainLoadingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}