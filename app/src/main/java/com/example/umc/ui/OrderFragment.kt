package com.example.umc.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.umc.R
import com.example.umc.Sort
import com.example.umc.databinding.FragmentOrderBinding
import com.example.umc.viewmodel.ImageViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api.Client
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch


class OrderFragment : Fragment() {

    private var _binding : FragmentOrderBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ImageViewModel

    var mGoogleSignInClient : GoogleSignInClient ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = (activity as MainActivity).viewModel

        saveSettings()
        loadSettings()

        binding.delete.setOnClickListener {
            viewModel.deleteData()
        }

        binding.upload.setOnClickListener {
            val intent = Intent(activity, UploadActivity::class.java)
            startActivity(intent)
        }

        binding.map.setOnClickListener {
            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        var mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)

        binding.logout.setOnClickListener {
            signOut(mGoogleSignInClient)

            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun signOut(mGoogleSignInClient : GoogleSignInClient) {
        Log.d("SIGNOUT", mGoogleSignInClient.toString())
        mGoogleSignInClient?.signOut()
    }

    //datastore code
    //값을 datastore에 반영
    private fun saveSettings() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) { //check된 버튼을 확인 한 후
                //해당되는 sort값을 받아와서 정의해서 저장
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            } //저장
            viewModel.saveSortMode(value)
        }
    }

    //radio button에 반영
    private fun loadSettings() {
        lifecycleScope.launch {
            //불러온 값을 확인한후 라디오 버튼에 반영
            val buttonId = when (viewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }
    }

    //viewBinding이 더이상 필요 없을 경우 null 처리 필요
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}