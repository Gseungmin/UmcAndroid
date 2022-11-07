package com.example.umc.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc.R
import com.example.umc.databinding.ItemCardBinding
import com.example.umc.model.Profile

class CardStackAdapter(private val items : List<Profile>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(data : Profile) {
            if (data.name.equals("1")) {
                binding.ImageArea.setImageResource(R.drawable.snow)
                binding.location.text = data.location
                binding.name.text = data.title
            } else if (data.name.equals("2")) {
                binding.ImageArea.setImageResource(R.drawable.horse)
                binding.location.text = data.location
                binding.name.text = data.title
            } else if (data.name.equals("3")) {
                binding.ImageArea.setImageResource(R.drawable.sky)
                binding.location.text = data.location
                binding.name.text = data.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}