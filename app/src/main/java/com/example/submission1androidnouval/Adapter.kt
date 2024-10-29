package com.example.submission1androidnouval

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1androidnouval.data.response.DetailEvent
import com.example.submission1androidnouval.data.response.ListEvent
import com.example.submission1androidnouval.databinding.EventBinding
import com.example.submission1androidnouval.databinding.FragmentUpcomingBinding

class Adapter (private var listEvents: List<ListEvent> = listOf()) : RecyclerView.Adapter<Adapter.EventViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private var Justbinding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = Justbinding!!

    inner class EventViewHolder(private val binding: EventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEvent) {
            binding.Judul.text = event.name
            binding.Overview.text = event.summary
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.gambar)
            binding.cardMantap.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                context.startActivity(intent)
            }

        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(eventId: String)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.LoadingWait.visibility = View.VISIBLE
        } else {
            binding.LoadingWait.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EventViewHolder {
        val binding =
            EventBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {

        viewHolder.bind(listEvents[position])
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun getItemCount(): Int = listEvents.size

    fun updateData(newEvents: List<ListEvent>) {
        listEvents = newEvents
        notifyDataSetChanged()
    }


    }
