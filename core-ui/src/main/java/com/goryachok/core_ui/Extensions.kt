package com.goryachok.core_ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import androidx.core.content.ContextCompat

fun View.startBackgroundAnimation(colorFrom: Int, colorTo: Int) {
    ValueAnimator.ofObject(
        ArgbEvaluator(),
        ContextCompat.getColor(this.context, colorFrom),
        ContextCompat.getColor(this.context, colorTo)
    ).apply {
        duration = 500
        addUpdateListener { this@startBackgroundAnimation.setBackgroundColor(it.animatedValue as Int) }
        start()
    }
}