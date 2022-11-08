package com.example.umc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.umc.R
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
        manager = CardStackLayoutManager(requireContext(), object : CardStackListener{
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
        val textList = mutableListOf<Profile>()
        textList.add(Profile("자유롭지 못한 말", "제주 우도", "2"))
        textList.add(Profile("비 온 뒤 노을", "울산", "3"))
        textList.add(Profile("자유롭지 못한 말", "제주 우도", "2"))
        textList.add(Profile("비 온 뒤 노을", "울산", "3"))
        cardStackAdapter = CardStackAdapter(textList)
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter


        //각 어댑터 클릭 리스너 설정
        cardStackAdapter.itemClick = object : CardStackAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val action = HomeFragmentDirections.actionFragmentHomeToPictureFragment("지승민", "26")
                findNavController().navigate(action);
            }
        }

        return view
    }
}