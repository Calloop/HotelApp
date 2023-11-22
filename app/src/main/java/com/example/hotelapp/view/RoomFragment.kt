package com.example.hotelapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelapp.R
import com.example.hotelapp.adapter.OnButtonClickListener
import com.example.hotelapp.adapter.RoomAdapter
import com.example.hotelapp.databinding.FragmentRoomBinding
import com.example.hotelapp.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : Fragment(), OnButtonClickListener {

    private val args: RoomFragmentArgs by navArgs()
    private val viewModel: RoomViewModel by viewModels()
    private lateinit var adapter: RoomAdapter
    private lateinit var binding: FragmentRoomBinding
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)

        val toolbar = (activity as AppCompatActivity).supportActionBar

        if (args.roomTitle.isEmpty()) {
            toolbar?.title = getString(R.string.room_title)
        } else {
            toolbar?.title = args.roomTitle
        }

        viewModel.state.observe(viewLifecycleOwner) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {
                } else {
                    if (it.rooms?.roomEntities != null) {
                        adapter.submitData(it.rooms.roomEntities)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RoomAdapter(this)
        recycler = binding.recycler
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }

    override fun onButtonClick(position: Int) {
        val action =
            RoomFragmentDirections.actionRoomFragmentToBookingFragment(
            )
        findNavController().navigate(action)
    }
}