package com.example.umc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.umc.databinding.FragmentSaveBinding

class SaveFragment : Fragment() {

    private var _binding : FragmentSaveBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.saveBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_saveFragment_to_mainFragment);
        }

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_saveFragment_to_mainFragment);
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}