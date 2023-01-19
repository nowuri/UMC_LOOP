package com.example.search

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.interested.R
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityMainSearchBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.NullPointerException
import java.lang.reflect.Type

class Search : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMainSearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        //검색 부분
        val itemlist = resources.getStringArray(R.array.itemList)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,itemlist)
        viewBinding.spinner.adapter = adapter

        setupSpinnerHandler()

        //검색어 입력
        var input: String =""
        viewBinding.search.isEnabled = false
        viewBinding.searchinput.setBackgroundColor(Color.parseColor("#FFFFFF"))
        viewBinding.searchinput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                input = viewBinding.searchinput.text.toString()
                viewBinding.search.isEnabled = input.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //검색 버튼
        viewBinding.search.setOnClickListener(){
            Toast.makeText(this, input+"를/을 검색합니다", Toast.LENGTH_SHORT).show()
        }

        //로그인
        viewBinding.login.setOnClickListener(){
            val intent = Intent(this,SignUp2Activity::class.java)
            startActivity(intent)
        }

        val tabTitleArray = arrayOf(
            "취업", "창업","주거","금융","건강","기타"
        )

        val tabTitleArray2 = arrayOf(
            "서울","경기","충청\n전라","강원\n경상","제주"
        )

        //정책 어댑터
        val mainVpAdapter = VpAdapter(this)
        viewBinding.vpAdapter.adapter = mainVpAdapter

        TabLayoutMediator(viewBinding.tablayout, viewBinding.vpAdapter){
                tab, position-> tab.text = tabTitleArray[position]
        }.attach()

        //분야별, 지역별
        viewBinding.field.setOnClickListener(){
            viewBinding.field.setTypeface(null, Typeface.BOLD)
            viewBinding.region.setTypeface(null,Typeface.NORMAL)
            val mainVpAdapter = VpAdapter(this)
            viewBinding.vpAdapter.adapter = mainVpAdapter
            TabLayoutMediator(viewBinding.tablayout, viewBinding.vpAdapter){
                    tab, position-> tab.text = tabTitleArray[position]
            }.attach()
        }

        viewBinding.region.setOnClickListener(){
            viewBinding.region.setTypeface(null, Typeface.BOLD)
            viewBinding.field.setTypeface(null,Typeface.NORMAL)
            val mainVpAdapter2 = VpAdapter2(this)
            viewBinding.vpAdapter.adapter = mainVpAdapter2
            TabLayoutMediator(viewBinding.tablayout, viewBinding.vpAdapter){
                    tab, position-> tab.text = tabTitleArray2[position]
            }.attach()
        }

//        TabLayoutMediator(viewBinding.tablayout, viewBinding.vpAdapter){
//            tab, position-> tab.text = tabTitleArray[position]
//        }.attach()

    }

    private fun setupSpinnerHandler(){
        viewBinding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewBinding.tablayout.setTabTextColors(Color.rgb(29,45,105), Color.rgb(255,255,255))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}

