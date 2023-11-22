package com.example.hotelapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelapp.R
import com.example.hotelapp.adapter.BookingAdapter
import com.example.hotelapp.databinding.FragmentBookingBinding
import com.example.hotelapp.model.Tourist
import com.example.hotelapp.util.EmailWatcher
import com.example.hotelapp.util.FillTableLayout
import com.example.hotelapp.util.PhoneWatcher
import com.example.hotelapp.viewmodel.BookingViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment : Fragment(), View.OnClickListener {

    private val viewModel: BookingViewModel by viewModels()
    private lateinit var binding: FragmentBookingBinding
    private lateinit var bookingAdapter: BookingAdapter

    private lateinit var navPayment: Button

    private lateinit var detailsTableLayout: TableLayout
    private lateinit var priceTableLayout: TableLayout

    private lateinit var fillTableLayout: FillTableLayout

    private lateinit var hotelName: TextView
    private lateinit var address: TextView
    private lateinit var rating: TextView

    private lateinit var buyerNumber: EditText
    private lateinit var buyerEmail: EditText
    private lateinit var buyerEmailLayout: TextInputLayout

    private lateinit var touristRecycler: RecyclerView
    private lateinit var addTouristButton: ImageButton

    private lateinit var touristList: MutableList<Tourist>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        fillTableLayout = FillTableLayout(requireContext())
        bookingAdapter = BookingAdapter()
        touristRecycler = binding.bookingTouristInfo.recycler


        val touristData = listOf(
            "Имя",
            "Фамилия",
            "Дата рождения",
            "Гражданство",
            "Номер загранпаспорта",
            "Срок действия загранпаспорта"
        )
        val tourist = Tourist(bookingAdapter.itemCount + 1, touristData)
        touristList = mutableListOf(tourist)

        with(touristRecycler) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = bookingAdapter
        }

        hotelName = binding.bookingTitle.hotelName
        address = binding.bookingTitle.address
        rating = binding.bookingTitle.rating

        buyerNumber = binding.bookingBuyerInfo.buyerNumber
        buyerEmail = binding.bookingBuyerInfo.buyerEmail
        buyerEmailLayout = binding.bookingBuyerInfo.buyerEmailLayout

        touristRecycler = binding.bookingTouristInfo.recycler
        addTouristButton = binding.bookingTouristInfo.addTouristButton

        detailsTableLayout = binding.bookingDetails.detailsTable
        priceTableLayout = binding.bookingPrice.priceTable

        navPayment = binding.bookingBottomBar.navPayment
        navPayment.setOnClickListener(this)
        addTouristButton.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookingAdapter.submitData(touristList)

        viewModel.state.observe(viewLifecycleOwner) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {
                } else {
                    if (it.bookingData != null) {
                        hotelName.text = it.bookingData.hotelName
                        address.text = it.bookingData.hotelAddress
                        rating.text = it.bookingData.rating
                        navPayment.text = it.bookingData.buttonText

                        buyerNumber.addTextChangedListener(PhoneWatcher(buyerNumber))
                        buyerEmail.addTextChangedListener(
                            EmailWatcher(
                                buyerEmailLayout,
                                lifecycleScope
                            )
                        )

                        setTableLayout(
                            detailsTableLayout,
                            it.bookingData.bookingDetailsKeys,
                            it.bookingData.bookingDetailsValues,
                            textAlignmentStart = true,
                            firstColumnWeight = false
                        )
                        setTableLayout(
                            priceTableLayout,
                            it.bookingData.bookingPriceKeys,
                            it.bookingData.bookingPriceValues,
                            textAlignmentStart = false,
                            firstColumnWeight = true
                        )
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            navPayment.id -> {
                val item = touristRecycler.findViewHolderForAdapterPosition(0) as BookingAdapter.OuterViewHolder
                val recycler = item.innerRecycler
                val adapter = recycler.adapter as BookingAdapter.InnerAdapter

                for (i in 0 until recycler.childCount) {
                    val viewHolder = recycler.getChildAt(i)?.let { recycler.getChildViewHolder(it) } as BookingAdapter.InnerAdapter.InnerViewHolder
                    viewHolder.updateEditTextHint()
                }

                if (adapter.checkFilledFields()) {
                    findNavController().navigate(R.id.action_bookingFragment_to_paymentFragment)
                }
            }

            addTouristButton.id -> {
                val touristData = listOf(
                    "Имя",
                    "Фамилия",
                    "Дата рождения",
                    "Гражданство",
                    "Номер загранпаспорта",
                    "Срок действия загранпаспорта"
                )
                val tourist = Tourist(bookingAdapter.itemCount + 1, touristData)
                touristList.add(tourist)
                bookingAdapter.submitData(touristList)
                touristRecycler.requestLayout()
            }
        }
    }

    private fun setTableLayout(
        tableLayout: TableLayout,
        keyList: List<String>,
        valueList: List<String>,
        textAlignmentStart: Boolean,
        firstColumnWeight: Boolean
    ) {
        val map = keyList.zip(valueList).map { (key, value) -> mapOf(key to value) }

        for (pair in map) {
            val tableRow =
                fillTableLayout.createTableRow(pair)
            tableLayout.addView(tableRow)
        }

        fillTableLayout.setTextViewAttributes(
            tableLayout,
            textAlignmentStart,
            firstColumnWeight
        )
    }
}