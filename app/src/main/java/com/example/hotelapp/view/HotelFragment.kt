package com.example.hotelapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.hotelapp.R
import com.example.hotelapp.adapter.HotelAdapter
import com.example.hotelapp.databinding.FragmentHotelBinding
import com.example.hotelapp.model.HotelSliderContent
import com.example.hotelapp.viewmodel.HotelViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HotelFragment : Fragment(), View.OnClickListener {

    private lateinit var adapter: HotelAdapter
    private val viewModel: HotelViewModel by viewModels()
    private lateinit var binding: FragmentHotelBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var peculiarities: ChipGroup
    private lateinit var navRoom: Button
    private lateinit var address: TextView
    private lateinit var rating: TextView
    private lateinit var minimalPrice: TextView
    private lateinit var priceForIt: TextView
    private lateinit var description: TextView
    private lateinit var name: TextView
    private lateinit var titleArg: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelBinding.inflate(inflater, container, false)

        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = getString(R.string.hotel_title)

        viewPager = binding.hotelBasicInfo.viewPager
        tabLayout = binding.hotelBasicInfo.tabLayout
        peculiarities = binding.hotelDetails.peculiarities
        navRoom = binding.hotelBottomBar.navRoom
        address = binding.hotelBasicInfo.address
        rating = binding.hotelBasicInfo.rating
        minimalPrice = binding.hotelBasicInfo.minimalPrice
        priceForIt = binding.hotelBasicInfo.priceForIt
        description = binding.hotelDetails.description
        name = binding.hotelBasicInfo.name

        navRoom.setOnClickListener(this)

        adapter = HotelAdapter()
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
        }.attach()

        viewModel.state.observe(viewLifecycleOwner) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {
                } else {
                    if (it.hotelEntity != null) {
                        val list = it.hotelEntity.imageUrls.mapIndexed { index, content ->
                            HotelSliderContent(index + 1, content)
                        }
                        adapter.updateData(list)
                        name.text = it.hotelEntity.name
                        address.text = it.hotelEntity.address
                        rating.text = buildString {
                            append(it.hotelEntity.rating.toString())
                            append(" ")
                            append(it.hotelEntity.ratingName)
                        }
                        minimalPrice.text = buildString {
                            append("от ")
                            append(it.hotelEntity.minimalPrice)
                            append(" ₽")
                        }
                        priceForIt.text = it.hotelEntity.priceForIt
                        description.text = it.hotelEntity.aboutTheHotelEntity.description

                        it.hotelEntity.aboutTheHotelEntity.peculiarities.forEach { content ->
                            val chip = Chip(context, null, R.attr.CustomChipStyle)
                            chip.text = content
                            peculiarities.addView(chip)
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        if (v?.id == navRoom.id) {
            titleArg = name.text.toString()
            val action =
                HotelFragmentDirections.actionHotelFragmentToRoomFragment(
                    titleArg
                )
            findNavController().navigate(action)
        }
    }
}