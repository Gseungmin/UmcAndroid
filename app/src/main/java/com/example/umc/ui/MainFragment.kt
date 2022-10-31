package com.example.umc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengesecond.adapter.MemoModel
import com.example.challengesecond.adapter.MemoRVAdapter
import com.example.umc.R
import com.example.umc.databinding.FragmentMainBinding
import com.example.umc.viewmodel.MemoViewModel

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}