package com.example.umc.slider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc.R
import com.example.umc.databinding.ItemCardBinding
import com.example.umc.model.Profile

class CardStackAdapter(private val items : List<Profile>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

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

    /**
     * Adapter에서 클릭메소드를 처리하다 보면 다양한 기능을 구현하는데 어려움
     * 따라서 adapter에서 clickListener를 추상화 메소드로 만든 뒤
     * adapter를 생성한 fragment나 activity에서 override해서 다양한 기능을 사용할 수 있게 함
     * */
    interface ItemClick{ //인터페이스
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position])
        if (itemClick != null){
            holder?.binding.ImageArea.setOnClickListener(View.OnClickListener {
                itemClick?.onClick(it, position)
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}