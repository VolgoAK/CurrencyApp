package com.volgoak.currency.utils

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.volgoak.currency.R
import com.volgoak.currency.beans.Currency

/**
 * Created by alex on 4/23/18.
 */
abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
    var listener: ((Any) -> Unit)? = null
}

class CurrencyHolder(view: View) : BaseHolder(view) {
    val tvName: TextView = view.findViewById(R.id.tvCurrencyName)
    val tvVolume: TextView = view.findViewById(R.id.tvVolume)
    val tvAmount: TextView = view.findViewById(R.id.tvAmount)

    @SuppressLint("SetTextI18n")
    fun bind(item: Currency) {
        tvName.text = item.name
        tvVolume.text = item.volume.toString()
        tvAmount.text = "%.2f".format(item.price.amount)
    }
}