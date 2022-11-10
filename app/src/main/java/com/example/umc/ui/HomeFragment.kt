package com.example.umc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.umc.databinding.FragmentHomeBinding
import com.example.umc.model.Profile
import com.example.umc.slider.CardStackAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var cardStackAdapter: CardStackAdapter
    lateinit var manager : CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        val cardStackView = binding.cardStackView
        manager = cardStackLayoutManager()

        val textList = initProfileData()

        cardStackAdapter = CardStackAdapter(textList)
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter

        setAdapterClickEvent()

        return view
    }

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
     * */
    private fun setAdapterClickEvent() {
        cardStackAdapter.itemClick = object : CardStackAdapter.ItemClick {
            override fun onClick(view: View, each: Profile) {
                val action = HomeFragmentDirections.actionFragmentHomeToPictureFragment(
                    each.job, each.name, each.age, each.id)
                findNavController().navigate(action);
            }
        }
    }

    private fun initProfileData(): MutableList<Profile> {
        val textList = mutableListOf<Profile>()
        textList.add(Profile("1", "수지", "26", "배우"))
        textList.add(Profile("2", "남주혁", "28", "대학생"))
        textList.add(Profile("3", "아이유", "24", "가수"))
        textList.add(Profile("4", "이광수", "31", "회사원"))
        return textList
    }
}