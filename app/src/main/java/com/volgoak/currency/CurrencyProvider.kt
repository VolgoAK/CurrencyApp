package com.volgoak.currency

import com.google.gson.GsonBuilder
import com.volgoak.currency.beans.Currency
import com.volgoak.currency.beans.Stock
import io.reactivex.Single
import java.net.URL

/**
 * Created by alex on 4/23/18.
 */
class CurrencyProvider {

    val gson = GsonBuilder().create()

    fun getCurrencySingle(): Single<List<Currency>> {
        return Single.create {
            val list = loadCurrency()
            if (list != null) {
                it.onSuccess(list)
            } else {
                it.onError(RuntimeException("Downloading error"))
            }
        }
    }

    private fun loadCurrency(): List<Currency>? {
        val response = URL("http://phisix-api3.appspot.com/stocks.json").readText()
        val stock: Stock = gson.fromJson(response, Stock::class.java)
        return stock.stock
    }
}