package com.volgoak.currency

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.volgoak.currency.beans.Currency
import com.volgoak.currency.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by alex on 4/23/18.
 */
class CurrencyViewModel : ViewModel() {

    private val currencyProvider = CurrencyProvider()

    val currencyLiveData: MutableLiveData<List<Currency>> = MutableLiveData()
    val commandLiveData: MutableLiveData<Command> = SingleLiveEvent()

    var disposable: Disposable? = null

    fun startLoading() {
        disposable?.dispose()

        disposable = currencyProvider.getCurrencySingle()
                .repeatWhen { completed -> completed.delay(15, TimeUnit.SECONDS) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> currencyLiveData.value = list },
                        { error ->
                            onLoadingError(error)
                        })
    }

    fun stopLoading() {
        disposable?.dispose()
    }

    /**
     * Ошибка обрабатывается различными способами, в зависимости
     * от того, были ли ранее загруженны данные
     */
    private fun onLoadingError(e: Throwable) {
        Timber.e(e)
        if (currencyLiveData.value == null) {
            commandLiveData.value = Command.LOADING_ERROR
        } else {
            commandLiveData.value = Command.UPDATING_ERROR
        }
    }

    /**
     * Команды для управления Activity
     */
    enum class Command {
        LOADING_ERROR,
        UPDATING_ERROR,
        NO_NETWORK
    }
}