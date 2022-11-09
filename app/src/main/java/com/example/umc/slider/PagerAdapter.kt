package com.example.umc.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc.R
import com.example.umc.databinding.ItemPagerBinding
import com.example.umc.model.Faces

class PagerAdapter(private val items : List<Faces>) : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPagerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(data : Faces) {
            if (data.pic.equals("m1")) {
                binding.img.setImageResource(R.drawable.m1)
            } else if (data.pic.equals("m12")) {
                binding.img.setImageResource(R.drawable.m12)
            } else if (data.pic.equals("m2")) {
                binding.img.setImageResource(R.drawable.m2)
            } else if (data.pic.equals("m22")) {
                binding.img.setImageResource(R.drawable.m22)
            } else if (data.pic.equals("m23")) {
                binding.img.setImageResource(R.drawable.m23)
            } else if (data.pic.equals("w1")) {
                binding.img.setImageResource(R.drawable.w1)
            } else if (data.pic.equals("w12")) {
                binding.img.setImageResource(R.drawable.w12)
            } else if (data.pic.equals("w13")) {
                binding.img.setImageResource(R.drawable.w13)
            } else if (data.pic.equals("w2")) {
                binding.img.setImageResource(R.drawable.w2)
            } else if (data.pic.equals("w22")) {
                binding.img.setImageResource(R.drawable.w22)
            } else if (data.pic.equals("w23")) {
                binding.img.setImageResource(R.drawable.w23)
            } else if (data.pic.equals("w24")) {
                binding.img.setImageResource(R.drawable.w24)
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