package com.goryachok.core_ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class ErrorSnackBar private constructor() {

    class Builder(private val context: Context) {

        private var layout: View? = null

        private var message: Int? = null

        private var duration: Int? = null

        private var backgroundColor: Int = 0

        private var textColor: Int = 0

        fun setLayout(view: View): Builder {
            layout = view
            return this
        }

        fun setMessage(message: Int): Builder {
            this.message = message
            return this
        }

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setBackgroundColor(color: Int): Builder {
            backgroundColor = color
            return this
        }

        fun setTextColor(color: Int): Builder {
            textColor = color
            return this
        }

        fun build(): Snackbar {
            val snackBar = Snackbar.make(
                layout?.let { it } ?: throw IllegalArgumentException(),
                message?.let { context.getString(it) } ?: "",
                duration ?: Snackbar.LENGTH_SHORT
            )
            if (backgroundColor != 0) snackBar.setBackgroundTint(
                ContextCompat.getColor(
                    context,
                    backgroundColor
                )
            )
            if (textColor != 0) snackBar.setTextColor(ContextCompat.getColor(context, textColor))
            return snackBar
        }
    }
}