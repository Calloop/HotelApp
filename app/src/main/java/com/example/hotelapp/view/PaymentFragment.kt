package com.example.hotelapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelapp.R
import com.example.hotelapp.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var paymentInfo: TextView
    private lateinit var navHotel: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        paymentInfo = binding.paymentInfo
        navHotel = binding.paymentBottomBar.navHotel
        navHotel.setOnClickListener(this)

        val randomNumber = (100000..999999).random()
        val randomString = getString(R.string.payment_info, randomNumber)
        paymentInfo.text = randomString

        return binding.root
    }

    override fun onClick(v: View?) {
        if (v?.id == navHotel.id) {
            findNavController().popBackStack(R.id.hotelFragment, false)
        }
    }
}