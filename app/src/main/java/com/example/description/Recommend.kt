package com.example.description

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.ActivityRecommendBinding
import com.example.search.RVdata

class Recommend : AppCompatActivity() {
    private lateinit var viewBinding: ActivityRecommendBinding

    val dataList: ArrayList<RecommendData> = arrayListOf(
        RecommendData("ID ABCDEF","2023.1.28","이유를 적어주세요"),
        RecommendData("ID ㅇㅇㅇ","2023.1.29","이유를 적어주세요"),
        RecommendData("ID ㄹㄹㄹ","2023.1.30","이유를 적어주세요"),
        RecommendData("ID ㄷㄷㄷ","2023.2.1","이유를 적어주세요"),
        RecommendData("ID ㅈㅈㅈ","2023.2.2","이유를 적어주세요")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val RecommendVPAdapter = RecommendVPAdapter(this, dataList)
        viewBinding.recommendVPAdapter.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        viewBinding.recommendVPAdapter.adapter = RecommendVPAdapter

        viewBinding.not.setOnClickListener(){
            val intent = Intent(this,NotRecommend::class.java)
            startActivity(intent)
        }
    }
}