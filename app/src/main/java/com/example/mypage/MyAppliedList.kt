package com.example.mypage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.ActivityMyappliedlistBinding
import com.example.interested.databinding.ActivityMypageBinding

class MyAppliedList : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMyappliedlistBinding

    var dataList: ArrayList<ApplyData> = arrayListOf(
        ApplyData("정책이름1","부서이름1"),
        ApplyData("정책이름2","부서이름2"),
        ApplyData("정책이름3","부서이름3"),
        ApplyData("정책이름4","부서이름4"),
        ApplyData("정책이름5","부서이름5"),
        ApplyData("정책이름6","부서이름6"),
        ApplyData("정책이름7","부서이름7"),
        ApplyData("정책이름8","부서이름8"),
        ApplyData("정책이름9","부서이름9"),
        ApplyData("정책이름10","부서이름10"),

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMyappliedlistBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val list: ArrayList<ApplyData> = dataList
        val ApplyAdapter = MyApplyAdapter(this, list)
        viewBinding.vpApply.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        viewBinding.vpApply.adapter = ApplyAdapter

        var input: String = ""
        viewBinding.search.isEnabled=false

        viewBinding.searchinput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                input = viewBinding.searchinput.text.toString()
                viewBinding.search.isEnabled = input.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewBinding.search.setOnClickListener(){
            Toast.makeText(this,input+"를/을 검색합니다", Toast.LENGTH_SHORT).show()
        }

        viewBinding.searchinput.setOnKeyListener{v, keyCode, event ->
            var handled = false
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(viewBinding.searchinput.windowToken,0)
                handled = true
                Toast.makeText(this,input+"을/를 검색합니다", Toast.LENGTH_SHORT).show()
            }
            handled
        }
    }
}