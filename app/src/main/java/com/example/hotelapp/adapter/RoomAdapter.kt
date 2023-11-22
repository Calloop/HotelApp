package com.example.hotelapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.RoomEntity
import com.example.hotelapp.R
import com.example.hotelapp.databinding.CarouselItemPageBinding
import com.example.hotelapp.databinding.RoomListItemBinding
import com.example.hotelapp.model.HotelSliderContent
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator

interface OnButtonClickListener {
    fun onButtonClick(position: Int)
}

class RoomAdapter(private val onButtonClickListener: OnButtonClickListener) : RecyclerView.Adapter<RoomAdapter.OuterViewHolder>() {
    private val outerDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<RoomEntity>() {
            override fun areItemsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
                return oldItem.name == newItem.name
            }
        }
    )

    fun submitData(items: List<RoomEntity>) {
        outerDiffer.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RoomListItemBinding.inflate(inflater, parent, false)
        return OuterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val item = outerDiffer.currentList[position]

        holder.name.text = item.name
        holder.price.text = item.price
        holder.pricePer.text = item.pricePer

        item.peculiarities.forEach { content ->
            val chip = Chip(holder.peculiarities.context, null, R.attr.CustomChipStyle)
            chip.text = content
            holder.peculiarities.addView(chip)
        }

        val list = item.imageUrls.mapIndexed { index, content ->
            HotelSliderContent(index + 1, content)
        }
        holder.bind(list)
    }

    override fun getItemCount(): Int {
        return outerDiffer.currentList.size
    }

    inner class OuterViewHolder(binding: RoomListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val price = binding.price
        val pricePer = binding.pricePer
        val peculiarities = binding.peculiarities
        private val viewPager = binding.viewPager
        private val tabLayout = binding.tabLayout
        private var adapter = InnerAdapter()
        private val navBooking = binding.navBooking

        init {
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            }.attach()

            navBooking.setOnClickListener {
                val position = adapterPosition
                onButtonClickListener.onButtonClick(position)
            }
        }

        fun bind(data: List<HotelSliderContent>) {
            adapter.submitList(data)
        }
    }

    inner class InnerAdapter : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

        private val innerDiffer: AsyncListDiffer<HotelSliderContent> =
            AsyncListDiffer(this, StringDiffCallback())

        inner class StringDiffCallback : DiffUtil.ItemCallback<HotelSliderContent>() {
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CarouselItemPageBinding.inflate(inflater, parent, false)
            return InnerViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return innerDiffer.currentList.size
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
            val image = innerDiffer.currentList[position]

            Glide.with(holder.itemView.context)
                .load(image.url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView)
        }

        fun submitList(items: List<HotelSliderContent>) {
            innerDiffer.submitList(items)
        }

        inner class InnerViewHolder(binding: CarouselItemPageBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val imageView = binding.carouselImage
        }
    }
}