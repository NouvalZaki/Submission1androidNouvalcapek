package com.example.submission1androidnouval

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission1androidnouval.data.database.FavoriteEntity
import com.example.submission1androidnouval.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailActivityViewModel
    private var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get(DetailActivityViewModel::class.java)
        val eventId = intent.getIntExtra("EVENT_ID", 0)

        if (eventId != 0) {
            detailViewModel.fetchDetailEvent(eventId.toString())
            observeEventDetail()
            detailViewModel.load.observe(this) { isLoading ->
                binding.LoadingWait.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
            detailViewModel.isItemExists(eventId).observe(this) { isFavorite ->
                updateFabIcon(isFavorite)
                isFavorited = isFavorite
            }
        } else {
            showToast("Invalid Event ID")
            finish()
        }

        setupButtonActions()
    }

    private fun observeEventDetail() {
        detailViewModel.eventDetail.observe(this) { detailResponse ->
            detailResponse?.let {
                val event = it.event
                val remain = event.quota - event.registrants

                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(event.imageLogo)
                        .into(picture)
                    title.text = event.name
                    summary.text = event.summary
                    description.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    city.text = event.cityName
                    owner.text = event.ownerName
                    category.text = event.category
                    remainingQuota.text = remain.toString()
                    time.text = "${event.beginTime} - ${event.endTime}"
                }
            } ?: run {
                Log.e("DetailActivity", "Event detail is null")
                showToast("Failed to load event details")
            }
        }
    }

    private fun setupButtonActions() {
        binding.registBut.setOnClickListener {
            val regUrl = detailViewModel.eventDetail.value?.event?.link
            if (!regUrl.isNullOrEmpty()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(regUrl)))
            } else {
                showToast("Registration Link not Available")
            }
        }

        binding.fab.setOnClickListener {
            // Toggle favorite status
            isFavorited = !isFavorited
            val event = detailViewModel.eventDetail.value?.event

            if (event != null) {
                val favoriteEntity = FavoriteEntity(
                    id = event.id,
                    name = event.name,
                    description = event.description,
                    ownerName = event.ownerName,
                    cityName = event.cityName,
                    quota = event.quota,
                    registrants = event.registrants,
                    imageLogo = event.imageLogo,
                    beginTime = event.beginTime,
                    endTime = event.endTime,
                    link = event.link,
                    mediaCover = event.mediaCover,
                    summary = event.summary,
                    category = event.category,
                    isFavorite = true
                )

                if (isFavorited) {
                    detailViewModel.insertFavorite(favoriteEntity)
                    showToast("Added to Favorites")
                } else {
                    detailViewModel.deleteFavorite(favoriteEntity) // Update with correct delete method
                    showToast("Removed from Favorites")
                }
                updateFabIcon(isFavorited)
            } else {
                showToast("Event data is not available.")
            }
        }
    }

    private fun updateFabIcon(isFavorite: Boolean) {
        binding.fab.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        binding.fab.contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
