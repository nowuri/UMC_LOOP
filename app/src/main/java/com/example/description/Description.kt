package com.example.description

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interested.databinding.ActivityDescriptionBinding

class Description : AppCompatActivity() {
    private lateinit var viewBinding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityDescriptionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.button2.setOnClickListener(){
            val intent = Intent(this,Recommend::class.java)
            startActivity(intent)
        }
    }
}