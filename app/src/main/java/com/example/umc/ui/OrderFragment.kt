package com.example.umc.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.coinstudy.dataStore.MyDataStore
import com.example.umc.R
import com.example.umc.Sort
import com.example.umc.databinding.FragmentOrderBinding
import com.example.umc.viewmodel.ImageViewModel
import com.example.umc.viewmodel.TokenViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch


class OrderFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding : FragmentOrderBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ImageViewModel
    lateinit var tokenViewModel: TokenViewModel


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
        tokenViewModel = (activity as MainActivity).tokenViewModel


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
             * ?????? ????????????
             */
            signOut()

            /**
             * kakao ????????????
             * */
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "???????????? ??????. SDK?????? ?????? ?????????", error)
                }
                else {
                    Log.i(TAG, "???????????? ??????. SDK?????? ?????? ?????????")
                }
            }


            tokenViewModel.deleteAccessToken()
//
//            /**
//             * ????????????
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
    //?????? datastore??? ??????
    private fun saveSettings() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) { //check??? ????????? ?????? ??? ???
                //???????????? sort?????? ???????????? ???????????? ??????
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            } //??????
            viewModel.saveSortMode(value)
        }
    }

    //radio button??? ??????
    private fun loadSettings() {
        lifecycleScope.launch {
            //????????? ?????? ???????????? ????????? ????????? ??????
            val buttonId = when (viewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }
    }

    //viewBinding??? ????????? ?????? ?????? ?????? null ?????? ??????
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}