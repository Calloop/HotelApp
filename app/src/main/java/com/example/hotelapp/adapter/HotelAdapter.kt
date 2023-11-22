package com.example.hotelapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelapp.R
import com.example.hotelapp.databinding.CarouselItemPageBinding
import com.example.hotelapp.model.HotelSliderContent

class HotelAdapter :
    RecyclerView.Adapter<HotelAdapter.ImageViewHolder>() {
    private val asyncListDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<HotelSliderContent>() {
            override fun areItemsTheSame(
                oldItem: HotelSliderContent,
                newItem: HotelSliderContent
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HotelSliderContent,
                newItem: HotelSliderContent
            ): Boolean {
                return oldItem.url == newItem.url
            }
        }
    )

    fun updateData(newData: List<HotelSliderContent>) {
        asyncListDiffer.submitList(newData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CarouselItemPageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = asyncListDiffer.currentList[position]

        Glide.with(holder.itemView.context)
            .load(image.url)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.imageView)
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    inner class ImageViewHolder(binding: CarouselItemPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.carouselImage
    }
}