package com.example.myprofile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.interested.MainActivity_interest
import com.example.interested.R
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityMyprofileBinding
import com.example.qna.Dialog1
import java.io.File

class MyProfileActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val REQ_GALLERY = 1
        const val PERMISSION_Album = 1
    }

    private lateinit var viewBinding : ActivityMyprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMyprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //프로필 아이콘 클릭 시 프로필 변경
        viewBinding.myprofileIdCircle.setOnClickListener(){
            requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
        }
        //회원정보 변경 클릭 시 회원가입2로 이동
        viewBinding.btnChangeInfo.setOnClickListener(){
            val FirstIntent = Intent(this, SignUp2Activity::class.java)
            startActivity(FirstIntent)
        }
        //관심분야 변경 클릭 시 관심분야 페이지로 이동
        viewBinding.btnChangeInterest.setOnClickListener(){
            val SecondIntent = Intent(this, InterestChange::class.java)
            startActivity(SecondIntent)
        }

        //회원 탈퇴 클릭 시 카드뷰 띄움
        viewBinding.btnRemoveUser.setOnClickListener(this)
    }

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result->
        if(result.resultCode == RESULT_OK){
            val imageUri = result.data?.data
            imageUri?.let{
                var imageFile= File(getRealPathFromURI(it))

                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .apply(RequestOptions().override(500,500))
                    .into(viewBinding.myprofileIdCircle)
            }
        }
    }

    fun getRealPathFromURI(uri: Uri):String {
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiamoi")){
            return uri.path!!
        }
        var columnIndex=0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null, null, null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        //권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED){
            //권한 요청
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE), REQ_GALLERY)
        }else{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            imageResult.launch(intent)
        }
    }

    //권한 요청
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            // isAllPermissionsGranted : 권한이 모두 승인 되었는지 여부 저장
            // all 메서드를 사용하면 배열 속에 들어 있는 모든 값을 체크할 수 있다.
            val isAllPermissionsGranted =
                permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }

    //사용자가 권한을 승인하거나 거부한 다음에 호출되는 메서드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    private fun permissionGranted(requestCode: Int){
        when(requestCode){
            PERMISSION_Album -> selectGallery()
        }
    }
    private fun permissionDenied(requestCode: Int){
        when(requestCode) {
            PERMISSION_Album -> Toast.makeText(
                this,
                "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onClick(v: View?) {
            when(v?.id){
                viewBinding.btnRemoveUser.id ->{
                    val dlg = RemoveUserDialog(this)
                    dlg.show()
                }
            }
        }
}
