package com.example.umc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.umc.R
import com.example.umc.databinding.FragmentPictureBinding
import com.example.umc.adapter.PagerAdapter
import com.example.umc.model.Faces

class PictureFragment : Fragment() {

    private lateinit var binding: FragmentPictureBinding
    val args: PictureFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPictureBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }
}