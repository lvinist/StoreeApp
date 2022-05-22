package com.dicoding.storeeapp.utils

import android.util.Patterns
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.withDateFormat(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = format.parse(this) as Date
        return DateFormat.getDateInstance(DateFormat.FULL).format(date)
    }
}

@BindingAdapter("load_image")
fun loadImage(view: ImageView, any: Any?) {
    any?.let { Glide.with(view).load(it).into(view) }
}