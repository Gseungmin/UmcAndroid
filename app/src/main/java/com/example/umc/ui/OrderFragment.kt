package com.example.umc.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.umc.R
import com.example.umc.Sort
import com.example.umc.databinding.FragmentOrderBinding
import com.example.umc.viewmodel.ImageViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class OrderFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding : FragmentOrderBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ImageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth

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

        binding.logout.setOnClickListener {

            /**
             * 구글 로그아웃
             */
            signOut()

//            /**
//             * kakao 로그아웃
//             * */
//            UserApiClient.instance.logout { error ->
//                if (error != null) {
//                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
//                }
//                else {
//                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
//                }
//            }
//
//            /**
//             * 로그아웃
//             * */
//            Log.d("LOGOUT", auth.uid.toString())
//            if (auth.currentUser!!.uid != null) {
//                auth.currentUser!!.delete()
//            }

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun signOut() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()
        var mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)

        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut()
        }
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