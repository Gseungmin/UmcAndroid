package com.example.umc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.umc.R
import com.example.umc.databinding.FragmentOrderBinding
import com.example.umc.databinding.FragmentPictureBinding
import com.example.umc.slider.PagerAdapter

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

        //viewPager 설정
        val viewpager = binding.viewpager
        val list = mutableListOf<String>()
        list.add(args.name)
        list.add(args.age)
        list.add(args.name)
        list.add(args.age)
        viewpager.adapter = PagerAdapter(list)

        //indicator 설정
        val indicator = binding.indicator
        indicator.setViewPager(viewpager)
        indicator.createIndicators(list.size, 0)

        //viewpager Setting
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return view
    }
}