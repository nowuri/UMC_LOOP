package com.example.qna

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.example.interested.R

class Dialog2 (context: Context) {
    private val dialog = Dialog(context)

    fun myDig(){
        dialog.setContentView(R.layout.qna_dialog2)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()
    }

}