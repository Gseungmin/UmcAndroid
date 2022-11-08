package com.example.umc.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc.R
import com.example.umc.databinding.ItemPagerBinding
import com.example.umc.model.Profile

class PagerAdapter(private val items : List<Profile>) : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPagerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(data : Profile) {
            if (data.name.equals("1")) {
                binding.img.setImageResource(R.drawable.snow)
            } else if (data.name.equals("2")) {
                binding.img.setImageResource(R.drawable.snow2)
            } else if (data.name.equals("3")) {
                binding.img.setImageResource(R.drawable.horse)
            } else if (data.name.equals("4")) {
                binding.img.setImageResource(R.drawable.horse2)
            }
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