package com.raed.yahoofinance.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SummaryViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var quotes: List<Quote>? = null
    private val quoteAdapter = QuoteAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvActivityMainList.layoutManager = LinearLayoutManager(this)
        binding.rvActivityMainList.adapter = quoteAdapter


        binding.svActivityMainSearch.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                val newList = quotes?.filter {
                    it.shortName.toString().contains(text.toString(), true) ||
                            it.fullExchangeName.toString().contains(text.toString(), true) ||
                            it.symbol.toString().contains(text.toString(), true)
                }
                quoteAdapter.submitList(newList)
            } else {
                quoteAdapter.submitList(quotes)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    populateUI(uiState = uiState)
                }
            }
        }
        viewModel.getSummary()
    }

    private fun populateUI(uiState: UiState) {
        binding.pbActivityMainLoading.isVisible = uiState.isLoading

        if (uiState.error != null) {
            Snackbar.make(binding.root, uiState.error, Snackbar.LENGTH_LONG).show()
        }

        this.quotes = uiState.dataSet
        quoteAdapter.submitList(quotes)
    }
}