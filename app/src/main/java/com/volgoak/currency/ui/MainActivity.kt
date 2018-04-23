package com.volgoak.currency.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.volgoak.currency.CurrencyViewModel
import com.volgoak.currency.R
import com.volgoak.currency.utils.RvAdapter
import com.volgoak.currency.beans.Currency
import com.volgoak.currency.utils.gone
import com.volgoak.currency.utils.show
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RvAdapter()

        rvCurrencyMain.layoutManager = LinearLayoutManager(this)
        rvCurrencyMain.adapter = adapter


        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)

        viewModel.currencyLiveData.observe(this, Observer { list ->
            list?.let { onCurrencyUpdate(it) }
        })

        viewModel.commandLiveData.observe(this, Observer { command ->
            command?.let {
                when (it) {
                    CurrencyViewModel.Command.LOADING_ERROR -> onLoadError()
                    CurrencyViewModel.Command.UPDATING_ERROR -> onUpdateError()
                    CurrencyViewModel.Command.NO_NETWORK -> onNetworkDisconnect()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_refresh -> {
                refresh()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.startLoading()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopLoading()
    }

    private fun refresh() {
        groupCurrency.gone()
        progressLoading.show()
        tvError.gone()
        viewModel.startLoading()
    }

    private fun onLoadError() {
        groupCurrency.gone()
        progressLoading.gone()
        tvError.show()
    }

    private fun onUpdateError() {
        Toast.makeText(this, R.string.update_error, Toast.LENGTH_LONG).show()
        groupCurrency.show()
        progressLoading.gone()
        tvError.gone()
    }

    private fun onNetworkDisconnect() {
        Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show()
    }

    private fun onCurrencyUpdate(currencyList: List<Currency>) {
        Timber.d("currency list size ${currencyList.size}")
        groupCurrency.show()
        progressLoading.gone()
        tvError.gone()

        adapter.setData(currencyList)
    }
}
