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
import com.example.submission1androidnouval.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {
    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: Adapter
    private lateinit var finishedViewModel: FinishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)

        finishedViewModel = ViewModelProvider(this).get(FinishedViewModel::class.java)

        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())
        eventAdapter = Adapter(listOf())
        binding.rvFinished.adapter = eventAdapter

        observeEvents()
        finishedViewModel.load.observe(viewLifecycleOwner) { isLoading ->
            binding?.LoadingWait?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        return binding.root
    }

    private fun observeEvents() {
        finishedViewModel.eventFinished.observe(viewLifecycleOwner, Observer{ events->
            Log.d("finishedFragment", "Received events: $events")
            if (events != null && events.isNotEmpty()){
                eventAdapter.updateData(events)
            }else{
                Toast.makeText(requireContext(), "No Events available", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
