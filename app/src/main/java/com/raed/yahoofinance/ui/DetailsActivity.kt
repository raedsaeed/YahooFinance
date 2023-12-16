package com.raed.yahoofinance.ui

import android.os.Bundle
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

        val quote = intent.getParcelableExtra(DETAILS_PAYLOAD) as? Quote
        quote?.let {
            binding.tvActivityDetailsExchange.text = it.exchange
            binding.tvActivityDetailsShortname.text = it.shortName
            binding.tvActivityDetailsTitle.text = it.symbol
            binding.tvActivityDetailsLongName.text = it.fullExchangeName
        }
    }

    companion object {
        const val DETAILS_PAYLOAD = "com.finance.payload"
    }
}