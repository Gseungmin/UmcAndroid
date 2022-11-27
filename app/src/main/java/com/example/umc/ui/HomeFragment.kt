package com.example.umc.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.umc.R
import com.example.umc.adapter.CardStackAdapter
import com.example.umc.databinding.FragmentHomeBinding
import com.example.umc.model.Image
import com.example.umc.viewmodel.ImageViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import java.io.ByteArrayOutputStream
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var cardStackAdapter: CardStackAdapter
    lateinit var manager : CardStackLayoutManager
    lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        //초기화
        viewModel.getData()

        val cardStackView = binding.cardStackView
        manager = cardStackLayoutManager()

        viewModel.imageList.observe(viewLifecycleOwner) {

            val imageList = mutableListOf<Image>()

            for (each in it) {
                val imageUri = getImageUri(each.image)
                val image = makeImage(each.title, each.location, imageUri)
                imageList.add(image)
            }

            connectCardStackView(imageList, cardStackView)
        }

        val textList = mutableListOf<Image>()
        connectCardStackView(textList, cardStackView)

        /**
         * RecyclerView item 클릭 이벤트
         * */
        setAdapterClickEvent()

        /**
         * RecyclerView Map Item 클릭 이벤트
         */
        setMapAdapterClickEvent()
    }

    /**
     * CardStackView 연결
     */
    private fun connectCardStackView(
        imageList: MutableList<Image>,
        cardStackView: CardStackView,
    ) {
        cardStackAdapter = CardStackAdapter(imageList)
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter
    }


    private fun makeImage(title: String, location: String, imageUri: Uri): Image {
        return Image(title, location, imageUri)
    }

    /**
     * CardStackLayoutManager 사용을 위한 오버라이딩 메소드
     * */
    private fun cardStackLayoutManager() =
        CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }
        })

    /**
     * 각 어댑터 클릭 리스너 설정
     * RecyclerView Adapter 개별 item 클릭시 발생하는 이벤트 처리
     * safe Args를 통해 해당 item 데이터를 같이 보내면 item에 해당하는 뷰를 보여줌
     * */
    private fun setAdapterClickEvent() {
        cardStackAdapter.itemClick = object : CardStackAdapter.ItemClick {
            override fun onClick(view: View, each: Image) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_home_to_pictureFragment)
            }
        }
    }

    /**
     * 각 어댑터 클릭 리스너 설정
     * RecyclerView Adapter 개별 item 클릭시 발생하는 이벤트 처리
     * safe Args를 통해 해당 item 데이터를 같이 보내면 item에 해당하는 뷰를 보여줌
     * */
    private fun setMapAdapterClickEvent() {
        cardStackAdapter.mapClick = object : CardStackAdapter.ItemClick {
            override fun onClick(view: View, each: Image) {
                startActivity(Intent(requireActivity(), UploadActivity::class.java))
            }
        }
    }


    /**
     * Transform From bitmap to Uri
     * */
    private fun getImageUri(bitmap: Bitmap) : Uri {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        val rand = Random()
        val randNo: Int = rand.nextInt(1000)
        val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver, bitmap, "IMG_$randNo", null)
        return Uri.parse(path)
    }
}