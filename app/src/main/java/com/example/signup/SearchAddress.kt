package com.example.interested
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.ActivitySearchBinding

class SearchAddress : AppCompatActivity() {
    private var webView: WebView? = null
    var TAG = "SearchAddress";
    lateinit var activityAddressApiBinding : ActivitySearchBinding

    inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            // 주소검색창에서 주소를 선택하면 그 결과값이 data 에 들어옴
            // 받아서 회원가임2창으로 넘김
            val intent = Intent()
            intent.putExtra("data", data)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // firebase로 호스팅한 url
        val blogspot = "https://searchaddress-cdb2f.web.app/"

        activityAddressApiBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activityAddressApiBinding.root)
        activityAddressApiBinding.webView!!.settings.javaScriptEnabled = true
        activityAddressApiBinding.webView!!.addJavascriptInterface(MyJavaScriptInterface(), "Android")
        activityAddressApiBinding.webView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // 위 웹페이지가 load가 끝나면 코드에서 작성했던 script 을 호출한다.
                view.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }
        // url 호출
        activityAddressApiBinding.webView!!.loadUrl(blogspot)
    }
}