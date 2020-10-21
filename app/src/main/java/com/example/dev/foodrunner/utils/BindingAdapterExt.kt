package com.example.dev.foodrunner.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("imageLoading")
fun ImageView.bindImageLoading(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.apply {
        setStyle(CircularProgressDrawable.LARGE)
    }.start()

    Picasso.get().load(url).placeholder(circularProgressDrawable)
        .into(this, object : Callback {
            override fun onSuccess() {
                circularProgressDrawable.stop()
            }

            override fun onError(e: Exception?) {
                circularProgressDrawable.stop()
            }

        })

}



