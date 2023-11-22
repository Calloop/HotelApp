package com.example.hotelapp.adapter

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.example.hotelapp.R
import com.example.hotelapp.databinding.BookingTouristInfoListItemBinding
import com.example.hotelapp.databinding.BookingTouristInfoListItemContentBinding
import com.example.hotelapp.model.Tourist

class BookingAdapter : RecyclerView.Adapter<BookingAdapter.OuterViewHolder>() {
    private val outerDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<Tourist>() {
            override fun areItemsTheSame(oldItem: Tourist, newItem: Tourist): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tourist, newItem: Tourist): Boolean {
                return oldItem.dataList == newItem.dataList
            }
        }
    )

    fun submitData(items: List<Tourist>) {
        outerDiffer.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BookingTouristInfoListItemBinding.inflate(inflater, parent, false)
        return OuterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val item = outerDiffer.currentList[position]
        holder.touristTitle.text = "Турист №${position + 1}"
        holder.bind(item.dataList)
    }

    override fun getItemCount(): Int {
        return outerDiffer.currentList.size
    }

    inner class OuterViewHolder(binding: BookingTouristInfoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val touristTitle = binding.touristTitle
        private val showMoreButton = binding.showMoreButton
        val innerRecycler = binding.recycler
        private var initialHeight = 0
        private var heightSet = false

        init {
            showMoreButton.setOnClickListener {
                if (binding.root.childCount > 0 && !heightSet) {
                    heightSet = true
                    initialHeight = binding.root.height
                }

                val isExpanded = binding.root.height == initialHeight
                val newHeight: Int

                if (isExpanded) {
                    newHeight = 230
                    val newDrawable = ContextCompat.getDrawable(binding.root.context, R.drawable.vector_down)
                    showMoreButton.setImageDrawable(newDrawable)
                } else {
                    newHeight = initialHeight
                    val newDrawable = ContextCompat.getDrawable(binding.root.context, R.drawable.vector_up)
                    showMoreButton.setImageDrawable(newDrawable)
                }

                val valueAnimator = ValueAnimator.ofInt(binding.root.height, newHeight)
                valueAnimator.addUpdateListener { animation ->
                    val animatedValue = animation.animatedValue as Int
                    val layoutParams = binding.root.layoutParams
                    layoutParams.height = animatedValue
                    binding.root.layoutParams = layoutParams
                }
                valueAnimator.start()
            }
        }

        fun bind(data: List<String>) {
            val innerAdapter = InnerAdapter()

            with(innerRecycler) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = innerAdapter
            }

            innerAdapter.submitData(data)
        }
    }

    inner class InnerAdapter : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

        private var fieldsValidated = true

        private val innerDiffer = AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String
                ): Boolean {
                    return oldItem == newItem
                }
            }
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = BookingTouristInfoListItemContentBinding.inflate(inflater, parent, false)
            return InnerViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return innerDiffer.currentList.size
        }

        fun checkFilledFields(): Boolean {
            return fieldsValidated
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
            val item = innerDiffer.currentList[position]

            holder.textInput.hint = item
        }

        fun submitData(items: List<String>) {
            innerDiffer.submitList(items)
        }

        inner class InnerViewHolder(binding: BookingTouristInfoListItemContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            private val editText = binding.editText
            val textInput = binding.textInput

            fun updateEditTextHint() {
                var isFilledFields = true

                if (editText.text.isNullOrEmpty()) {
                    textInput.isErrorEnabled = true
                    textInput.error = "Поле должно быть заполнено"
                    val color = ColorStateList.valueOf(Color.parseColor("#26EB5757"))
                    editText.backgroundTintList = color
                    isFilledFields = false
                } else {
                    textInput.isErrorEnabled = false
                    textInput.error = null
                    editText.backgroundTintList = null
                }

                fieldsValidated = isFilledFields
            }
        }
    }
}