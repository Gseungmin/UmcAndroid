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
import com.example.umc.model.Faces
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
        val list = mutableListOf<Faces>()

        if (args.id.equals("1")) {
            list.add(Faces(args.id, "w1"))
            list.add(Faces(args.id, "w12"))
            list.add(Faces(args.id, "w13"))
        } else if (args.id.equals("2")) {
            list.add(Faces(args.id, "m1"))
            list.add(Faces(args.id, "m12"))
        } else if (args.id.equals("3")) {
            list.add(Faces(args.id, "w2"))
            list.add(Faces(args.id, "w22"))
            list.add(Faces(args.id, "w23"))
            list.add(Faces(args.id, "w24"))
        } else if (args.id.equals("4")) {
            list.add(Faces(args.id, "m2"))
            list.add(Faces(args.id, "m22"))
            list.add(Faces(args.id, "m23"))
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