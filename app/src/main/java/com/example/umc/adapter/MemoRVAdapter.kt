package com.example.challengesecond.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc.databinding.MemoRvItemBinding
import com.example.umc.db.MemoEntity

class MemoRVAdapter(private val dataSet : List<MemoEntity>) : RecyclerView.Adapter<MemoRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : MemoRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item : MemoEntity) {
            val memoDate = binding.memoDate
            val memoTitle = binding.memoTitle
            memoDate.text = item.date
            memoTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = MemoRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}