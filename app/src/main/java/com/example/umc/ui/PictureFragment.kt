package com.example.umc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.umc.R
import com.example.umc.databinding.FragmentPictureBinding
import com.example.umc.model.Profile
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
        val list = mutableListOf<Profile>()

        if (args.name.equals("눈")) {
            list.add(Profile(args.name, args.age, "1"))
            list.add(Profile(args.name, args.age, "2"))
        } else {
            list.add(Profile(args.name, args.age, "3"))
            list.add(Profile(args.name, args.age, "4"))
        }
        viewpager.adapter = PagerAdapter(list)

        //indicator 설정
        val indicator = binding.indicator
        indicator.setViewPager(viewpager)
        indicator.createIndicators(list.size, 0)

        //viewpager Setting
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_pictureFragment_to_fragment_home)
        }

        return view
    }
}