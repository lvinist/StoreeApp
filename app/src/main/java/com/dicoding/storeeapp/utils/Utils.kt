package com.dicoding.storeeapp.utils

import android.util.Patterns

object Utils {
    fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
}