package com.phucduong.searchgitprofile.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.phucduong.searchgitprofile.R


object BindingUtils {
    @BindingAdapter("app:img")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_user)
            .circleCrop()
            .into(imageView)
    }
}