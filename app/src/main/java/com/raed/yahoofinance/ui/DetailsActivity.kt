package com.raed.yahoofinance.ui

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.databinding.ActivityDetailsBinding


/**
 * Created by Raed Saeed on 12/16/2023
 */
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbActivityDetailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        val quote = intent.getParcelableExtra(DETAILS_PAYLOAD) as? Quote
        quote?.let {
            binding.tvActivityDetailsExchange.text = it.exchange
            binding.tvActivityDetailsShortname.text = it.shortName
            binding.tvActivityDetailsTitle.text = it.symbol
            binding.tvActivityDetailsLongName.text = it.fullExchangeName
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val DETAILS_PAYLOAD = "com.finance.payload"
    }
}