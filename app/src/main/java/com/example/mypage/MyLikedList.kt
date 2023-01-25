package com.example.mypage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.ActivityMylikedlistBinding

class MyLikedList : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMylikedlistBinding

    var dataList: ArrayList<LikeData> = arrayListOf(
        LikeData("정책이름 1","부서이름 1","D-2"),
        LikeData("정책이름 2","부서이름 2","D-7"),
        LikeData("정책이름 3","부서이름 3","D-8"),
        LikeData("정책이름 4","부서이름 4","D-6"),
        LikeData("정책이름 5","부서이름 5","D-5"),
        LikeData("정책이름 6","부서이름 6","D-5"),
        LikeData("정책이름 7","부서이름 7","D-4"),
        LikeData("정책이름 8","부서이름 8","D-3"),
        LikeData("정책이름 9","부서이름 9","D-2"),
        LikeData("정책이름 10","부서이름 10","D-1"),
        LikeData("정책이름 11","부서이름 11","D-0")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMylikedlistBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val list: ArrayList<LikeData> = dataList
        list.sortBy { it.Dday }
        val LikeRVAdapter = MyLikeAdapter(this, list)
        viewBinding.vplike.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        viewBinding.vplike.adapter = LikeRVAdapter

        var input: String = ""
        viewBinding.search.isEnabled=false

        viewBinding.searchinput.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                input = viewBinding.searchinput.text.toString()
                viewBinding.search.isEnabled = input.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewBinding.search.setOnClickListener(){
            Toast.makeText(this,input+"를/을 검색합니다",Toast.LENGTH_SHORT).show()
        }

        viewBinding.searchinput.setOnKeyListener{v, keyCode, event ->
            var handled = false
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER){
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(viewBinding.searchinput.windowToken,0)
                handled = true
                Toast.makeText(this,input+"을/를 검색합니다",Toast.LENGTH_SHORT).show()
            }
            handled
        }
    }
}