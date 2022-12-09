package com.example.umc.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.umc.R
import com.example.umc.databinding.ItemPagerBinding
import com.example.umc.model.Faces
import com.example.umc.model.Image

class PagerAdapter(private val items : List<Uri>) : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPagerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(data : Uri) {
            binding.img.load(data)
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