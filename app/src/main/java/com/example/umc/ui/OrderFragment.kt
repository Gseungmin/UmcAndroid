package com.example.umc.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.example.umc.databinding.FragmentOrderBinding
import com.example.umc.viewmodel.ImageViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api.Client
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    lateinit var viewModel: ImageViewModel

    var mGoogleSignInClient : GoogleSignInClient ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentOrderBinding.inflate(layoutInflater)
        val view = binding.root

        viewModel = (activity as MainActivity).viewModel

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
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun signOut(mGoogleSignInClient : GoogleSignInClient) {
        Log.d("SIGNOUT", mGoogleSignInClient.toString())
        mGoogleSignInClient?.signOut()
    }
}