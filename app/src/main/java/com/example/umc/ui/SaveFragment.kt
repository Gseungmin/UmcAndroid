package com.example.umc.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.Navigation
import com.example.umc.R
import com.example.umc.databinding.FragmentSaveBinding
import com.example.umc.db.MemoEntity
import com.example.umc.viewmodel.MemoViewModel
import java.util.*

class SaveFragment : Fragment() {

    private var _binding : FragmentSaveBinding? = null
    private val binding get() = _binding!!
    private lateinit var memoViewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        val view = binding.root

        memoViewModel = (activity as MainActivity).viewModel

        /**
         * 네비게이션 기능
         * saveBtn 클릭시 DB에 저장하는 기능 추가
         * */
        binding.saveBtn.setOnClickListener {
            memoViewModel.saveMemo(MemoEntity(0,binding.title.text.toString(),
                binding.notes.text.toString(),binding.date.text.toString()))
            Navigation.findNavController(view).navigate(R.id.action_saveFragment_to_mainFragment);
        }

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_saveFragment_to_mainFragment);
        }

        /**
         * 달력 기능
         * */
        val dateSelectBtn = binding.date

        dateSelectBtn!!.setOnClickListener {

            val today = GregorianCalendar()
            val year : Int = today.get(Calendar.YEAR)
            val month : Int = today.get(Calendar.MONTH)
            val date : Int = today.get(Calendar.DATE)

            //날짜를 픽하면 해당 날짜 출력
            val calender = DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    dateSelectBtn.setText("${p1}, ${p2+1}, ${p3}")
                }
            }, year, month, date)
            calender.show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}