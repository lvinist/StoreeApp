package com.dicoding.storeeapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.storeeapp.R
import com.dicoding.storeeapp.data.story.Story
import com.dicoding.storeeapp.databinding.ActivityDetailStoryBinding
import com.dicoding.storeeapp.utils.Utils.withDateFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupView() {
        val story = intent.getParcelableExtra<Story>("Story") as Story
        binding.nameTextView.text = story.name
        binding.dateTextView.text = getString(R.string.date, story.createdAt.withDateFormat())
        binding.descTextView.text = story.description

        Glide.with(this)
            .load(story.photoUrl)
            .into(binding.previewImageView)
    }
}