package com.example.submission1androidnouval.fragmen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1androidnouval.Adapter
import com.example.submission1androidnouval.databinding.FragmentUpcomingBinding


class UpcomingFragment : Fragment() {
    private var justBinding: FragmentUpcomingBinding? = null

    private val binding get() = justBinding!!
    private lateinit var eventAdapter: Adapter
    private lateinit var  upcomingViewmodel: UpcomingViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        justBinding = FragmentUpcomingBinding.inflate(inflater, container, false)

        upcomingViewmodel = ViewModelProvider(this).get(UpcomingViewmodel::class.java)

        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext())
        eventAdapter = Adapter(listOf())
        binding.rvUpcoming.adapter = eventAdapter

        observeEvents()
        upcomingViewmodel.load.observe(viewLifecycleOwner){ isLoading->
            binding?.LoadingWait?.visibility = if(isLoading) View.VISIBLE else View.GONE
        }

        return binding.root

    }

    private fun observeEvents() {
        upcomingViewmodel.upcomingEvent.observe(viewLifecycleOwner, Observer{ events->
            Log.d("UpcomingFragment", "Received events: $events")
            if (events != null && events.isNotEmpty()){
                eventAdapter.updateData(events)
            }else{
                Toast.makeText(requireContext(), "No Events available", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        justBinding = null
    }
}