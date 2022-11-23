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

        /**
         * viewPager 연결
         */
        val viewpager = binding.viewpager
        val list = initFaceData()
        viewpager.adapter = PagerAdapter(list)
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        /**
         * indicator 연결
         */
        val indicator = binding.indicator
        indicator.setViewPager(viewpager)
        indicator.createIndicators(list.size, 0)

        navigation(view)

        return view
    }

    /**
     * Navigation 기능
     * back 버튼 클릭시 HomeFragment로 이동
     * */
    private fun navigation(view: LinearLayout) {
        binding.back.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_pictureFragment_to_fragment_home)
        }
    }

    /**
     * ROOM이나 RETROFIT 사용 전 초기 데이터 셋팅
     * 추후에 삭제될 데이터
     * */
    private fun initFaceData(): MutableList<Faces> {
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
        return list
    }
}