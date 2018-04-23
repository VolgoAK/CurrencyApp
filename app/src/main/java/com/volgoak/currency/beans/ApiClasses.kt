package com.volgoak.currency.beans


import com.google.gson.annotations.SerializedName

/**
 * Классы для парсинга Json ответа от сервера
 */
data class Price(@SerializedName("amount")
                 val amount: Double = 0.0,
                 @SerializedName("currency")
                 val currency: String = "")


data class Stock(@SerializedName("as_of")
                 val asOf: String = "",
                 @SerializedName("stock")
                 val stock: List<Currency>?)


data class Currency(@SerializedName("volume")
                    val volume: Int = 0,
                    @SerializedName("symbol")
                    val symbol: String = "",
                    @SerializedName("price")
                    val price: Price,
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("percent_change")
                    val percentChange: Double = 0.0)


