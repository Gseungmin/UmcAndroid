package com.example.umc.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc.databinding.ItemPagerBinding

class PagerAdapter(private val items : List<String>) : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPagerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(data : String) {
            binding.text.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}