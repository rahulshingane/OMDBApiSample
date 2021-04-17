package com.app.omdbdemo.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Extention function for load image with glide
 */
fun ImageView.loadImageNoPlaceHolder(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}