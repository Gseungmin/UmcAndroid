package com.example.umc.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengesecond.adapter.MemoModel
import com.example.challengesecond.adapter.MemoRVAdapter
import com.example.umc.R
import com.example.umc.databinding.FragmentMainBinding
import com.example.umc.viewmodel.MemoViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var memoRVAdapter: MemoRVAdapter;
    private lateinit var memoViewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        memoViewModel = (activity as MainActivity).viewModel

        setupTouchHelper(view)

        memoViewModel.getAllMemo()

        //RecyclerView 기능
        memoViewModel.memoList.observe(viewLifecycleOwner) {
            memoRVAdapter = MemoRVAdapter(it)
            val memo_rv : RecyclerView = binding.rv
            memo_rv.adapter = memoRVAdapter
            memo_rv.layoutManager = LinearLayoutManager(requireContext())
        }

        //네비게이션 기능
        binding.saveBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_saveFragment);
        }
        return view
    }

    //아이템을 왼쪽으로 스와이프하면 데이터 삭제
    private fun setupTouchHelper(view: View) {
        //아이템 swipe를 위해 SimpleCallback를 만듬
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT //드래그는 사용하지 않을 것이기때문에 0, swipe 방향은 왼쪽만 인식
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            //swipe 동작이 발생했을때 발생하는 작업
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //터치한 viewHolder 위치
                val position = viewHolder.layoutPosition
                //터치한 viewHolder 위치를 Adapter에 전달하여 현재 아이템을 획득
                val memo = memoViewModel.memoList.value?.get(position)
                if (memo != null) {
                    memoViewModel.deleteMemo(memo)
                }
                //undo를 누르면 다시 아이템 저장
                Snackbar.make(view, "Book has deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        if (memo != null) {
                            memoViewModel.saveMemo(memo)
                        }
                    }
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            //attachToRecyclerView 드래그 동작 인식
            //recyclerView와 연결해 swipe나 drag동작 인식
            attachToRecyclerView(binding.rv)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}