package com.example.umc.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc.adapter.ImageUploadAdapter
import com.example.umc.databinding.ActivityUploadBinding
import com.example.umc.db.DataBase
import com.example.umc.repository.DataRepository
import com.example.umc.viewmodel.ImageViewModel
import com.example.umc.viewmodel.ImageViewModelFactory
import java.io.ByteArrayOutputStream

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private val PICK_IMAGE_FROM_GALLERY = 1000
    private val PICK_IMAGE_FROM_GALLERY_PERMISSION = 1010
    private lateinit var imageRVAdapter : ImageUploadAdapter
    private var datas = mutableListOf<Uri>()
    lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val database = DataBase.getDatabase(this)
        val dataRepository = DataRepository(database)
        val factory = ImageViewModelFactory(dataRepository)
        viewModel = ViewModelProvider(this, factory).get(ImageViewModel::class.java)

        // 사진첨부 버튼 클릭 이벤트 구현
        binding.btnShowGallery.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> showGallery(this@UploadActivity)

                // 갤러리 접근 권한이 없는 경우 && 교육용 팝업을 보여줘야 하는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> showPermissionContextPopup()

                // 권한 요청 하기
                else -> requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PICK_IMAGE_FROM_GALLERY_PERMISSION)
            }
        }

        //사진 저장 이벤트 구현
        binding.btnSave.setOnClickListener {
            for (item in datas) {
                Log.d("BITMAP1", item.toString())
                val imageBitmap = setImageBitmap(item)
                viewModel.insertData(imageBitmap, "오사카의 밤", "도본토리")
                viewModel.getData()
//                val imageUri = getImageUri(imageBitmap)
//                Log.d("BITMAP4", imageUri.toString())
            }
        }
    }

    private fun showGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        activity.startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY)
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PICK_IMAGE_FROM_GALLERY_PERMISSION)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    // 사진 선택(갤러리에서 나온) 이후 실행되는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            val list = mutableListOf<Uri>()

            data?.let { it ->
                if (it.clipData != null) {   // 사진을 여러개 선택한 경우
                    val count = it.clipData!!.itemCount
                    if (count > 4) {
                        Toast.makeText(this@UploadActivity, "사진은 4장까지 선택 가능합니다.", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    for (i in 0 until count) {
                        val imageUri = it.clipData!!.getItemAt(i).uri
                        list.add(imageUri)
                    }
                } else {      // 1장 선택한 경우
                    val imageUri = it.data!!
                    list.add(imageUri)
                }
            }
            datas = list
            imageRVAdapter = ImageUploadAdapter(datas)
            binding.recyclerView.adapter = imageRVAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    // 권한 요청 승인 이후 실행되는 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PICK_IMAGE_FROM_GALLERY_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    showGallery(this@UploadActivity)
                else
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setImageBitmap(uri: Uri) : Bitmap{
        val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(contentResolver, uri)
            )
        }else{
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }
        Log.d("BITMAP2", bitmap.toString())
        return bitmap
    }
}