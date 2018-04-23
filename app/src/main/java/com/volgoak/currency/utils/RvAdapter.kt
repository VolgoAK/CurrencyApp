package com.volgoak.currency.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.volgoak.currency.R
import com.volgoak.currency.beans.Currency
import java.lang.RuntimeException

/**
 * Адаптер для recyclerView, с возможностью расширения
 * под другие типы данных
 */
class RvAdapter : RecyclerView.Adapter<BaseHolder>() {

    private var data: List<Any>? = null

    var listener: ((Any) -> Unit)? = null

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val item = data!![position]
        when (holder) {
            is CurrencyHolder -> holder.bind(item as Currency)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val holder = when (viewType) {
            VIEW_TYPE_CURRENCY -> CurrencyHolder(view)
            else -> throw RuntimeException("Unsupported item type")
        }

        holder.listener = listener

        return holder
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        val item = data!![position]
        return when (item) {
            is Currency -> VIEW_TYPE_CURRENCY
            else -> throw RuntimeException("Unsupported item type")
        }
    }

    fun setData(list: List<Any>?) {
        data = list
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_CURRENCY = R.layout.holder_currency
    }
}