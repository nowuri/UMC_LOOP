package com.example.qna

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.contentValuesOf
import com.example.interested.R

class Dialog1 (context: Context) {
    private val dialog = Dialog(context)

    fun myDig(){
        dialog.setContentView(R.layout.qna_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()
    }

}