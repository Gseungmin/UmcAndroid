package com.example.umc.ui

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.umc.adapter.PagerAdapter
import com.example.umc.databinding.FragmentUserBinding
import com.example.umc.model.Image
import com.example.umc.viewmodel.ImageViewModel
import com.example.umc.viewmodel.MyInfoViewModel
import java.io.ByteArrayOutputStream
import java.util.*

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    lateinit var viewModel: ImageViewModel
    lateinit var myInfoViewModel: MyInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentUserBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        myInfoViewModel = (activity as MainActivity).myInfoViewModel

        //초기화
        viewModel.getMyPicture()
        myInfoViewModel.getMyInfo()

        myInfoViewModel.email.observe(viewLifecycleOwner) {
            binding.email.text = it
        }

        myInfoViewModel.name.observe(viewLifecycleOwner) {
            binding.name.text = it
        }

        val initList = mutableListOf<Uri>()

        /**
         * viewPager 연결
         */
        val viewpager = binding.viewpager
        viewpager.adapter = PagerAdapter(initList)
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        /**
         * indicator 연결
         */
        val indicator = binding.indicator
        indicator.setViewPager(viewpager)
        indicator.createIndicators(initList.size, 0)

        viewModel.imageList.observe(viewLifecycleOwner) {

            val imageList = mutableListOf<Uri>()

            for (each in it) {
                val imageUri = getImageUri(each.image)
                imageList.add(imageUri)
            }

            /**
             * viewPager 연결
             */
            viewpager.adapter = PagerAdapter(imageList)
            viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            /**
             * indicator 연결
             */
            indicator.setViewPager(viewpager)
            indicator.createIndicators(imageList.size, 0)
        }
    }

    /**
     * Transform From bitmap to Uri
     * */
    private fun getImageUri(bitmap: Bitmap): Uri {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        val rand = Random()
        val randNo: Int = rand.nextInt(1000)
        val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver,
            bitmap,
            "IMG_$randNo",
            null)
        return Uri.parse(path)
    }
}
