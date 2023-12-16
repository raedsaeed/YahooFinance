package com.raed.yahoofinance.ui

import android.content.Intent
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
import com.raed.yahoofinance.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Raed Saeed on 12/16/2023
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SummaryViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var timeInterval = 30 * 1000L
    private val quoteAdapter = QuoteAdapter {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DETAILS_PAYLOAD, it)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvActivityMainList.layoutManager = LinearLayoutManager(this)
        binding.rvActivityMainList.adapter = quoteAdapter


        binding.svActivityMainSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.search(text)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    populateUI(uiState = uiState)
                }
            }
        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                while (true) {
//                    viewModel.getSummary()
//                    delay(timeInterval)
//                }
//            }
//        }

        viewModel.getSummary()
    }

    private fun populateUI(uiState: UiState) {
        binding.pbActivityMainLoading.isVisible = uiState.isLoading && quoteAdapter.itemCount == 0

        if (uiState.error != null) {
            Snackbar.make(binding.root, uiState.error, Snackbar.LENGTH_LONG).show()
        }

        quoteAdapter.submitList(uiState.quotes)
    }
}