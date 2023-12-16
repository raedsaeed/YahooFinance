package com.raed.yahoofinance.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.databinding.ItemQuoteBinding

/**
 * Created by Raed Saeed on 12/16/2023
 */
class QuoteAdapter : ListAdapter<Quote, QuoteAdapter.QuoteViewHolder>(QuoteDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class QuoteViewHolder(private val itemQuoteBinding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(itemQuoteBinding.root) {
        fun bind(quote: Quote) {
            itemQuoteBinding.ivItemQuoteTitle.text = quote.symbol
            itemQuoteBinding.ivItemQuoteEx.text = quote.exchange
            itemQuoteBinding.ivItemQuoteShortname.text = quote.shortname
        }
    }

    object QuoteDiffCallback : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.score == newItem.score
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }
}