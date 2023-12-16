package com.raed.yahoofinance.domain.usecase

import com.raed.yahoofinance.data.api.NetworkViewState
import com.raed.yahoofinance.data.repo.GetSummaryRepoImpl
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Raed Saeed on 12/16/2023
 */
class GetSummaryUseCase @Inject constructor(private val repo: GetSummaryRepoImpl) {
    operator fun invoke() = flow {
        emit(NetworkViewState.Loading)
        val response = repo.getSummary()
        emit(NetworkViewState.Success(response))
    }.catch { e -> emit(NetworkViewState.Error(e)) }
}