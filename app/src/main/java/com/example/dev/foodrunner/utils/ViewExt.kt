package com.example.dev.foodrunner.utils

import android.text.Layout
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.dev.foodrunner.Event
import com.google.android.material.snackbar.Snackbar


fun View.moveToFrag(@IdRes resIdToMove: Int, @IdRes resIdToPop: Int, popToInclusive: Boolean) {
    this.findNavController().apply {
        popBackStack(resIdToPop, popToInclusive)
        navigate(resIdToMove)
    }
}


fun View.setupSnackbar(lifecycleOwner: LifecycleOwner, message: LiveData<Event<String>>) {
    message.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            Snackbar.make(this, it, Snackbar.LENGTH_LONG).show()
        }
    })
}

