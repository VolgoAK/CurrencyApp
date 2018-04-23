package com.volgoak.currency.utils

import android.view.View

/**
 * Created by alex on 4/23/18.
 */

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}