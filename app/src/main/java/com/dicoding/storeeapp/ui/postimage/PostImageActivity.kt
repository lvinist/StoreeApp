package com.dicoding.storeeapp.ui.postimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.storeeapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_image)
    }
}