package com.example.mypage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home.Home
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityMypageBinding
import com.example.myprofile.MyProfileActivity
import com.example.qna.Question
import com.example.search.Search

class MyPage_MainActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //버튼 클릭 시 해당 페이지 이동
        //프로필 변경
        viewBinding.mypageProfilechange.setOnClickListener(){
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }
        //알림 목록 (수정필요)
        viewBinding.mypageNotilist.setOnClickListener(){
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }
        //지원한 정책
        viewBinding.mypageAppliedlist.setOnClickListener(){
            val intent = Intent(this, MyAppliedList::class.java)
            startActivity(intent)
        }
        //좋아요한 정책
        viewBinding.mypageLikedlist.setOnClickListener(){
            val intent = Intent(this, MyLikedList::class.java)
            startActivity(intent)
        }
        //공지사항
        viewBinding.mypageNotice.setOnClickListener(){
            val address = "https://www.youthcenter.go.kr/board/boardList.do?bbsNo=3&pageUrl=board/board"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }
        //FAQ
        viewBinding.mypageFaq.setOnClickListener(){
            val address = "https://www.youthcenter.go.kr/board/boardList.do?bbsNo=4&pageUrl=board/board"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }


        //상단 로그인, 하단바
        viewBinding.menuHome.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        viewBinding.menuSearch.setOnClickListener(){
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }
        viewBinding.menuQa.setOnClickListener(){
            val intent = Intent(this, Question::class.java)
            startActivity(intent)
        }
        viewBinding.login.setOnClickListener(){
            val intent = Intent(this, SignUp2Activity::class.java)
            startActivity(intent)
        }
    }
}