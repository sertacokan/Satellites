package com.example.satellites.ui.fragment.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satellites.R
import com.example.satellites.database.list.SatelliteEntity
import com.example.satellites.mapper.SatelliteEntityMapper
import com.example.satellites.network.adapter.ErrorState
import com.example.satellites.network.adapter.FailureState
import com.example.satellites.network.adapter.SuccessState
import com.example.satellites.repository.SatelliteListRepository
import com.example.satellites.ui.state.Error
import com.example.satellites.ui.state.Loading
import com.example.satellites.ui.state.Success
import com.example.satellites.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SatelliteListViewModel(
    private val satelliteListRepository: SatelliteListRepository,
    private val satelliteEntityMapper: SatelliteEntityMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<SatelliteEntity>>>(Loading)
    val uiState: StateFlow<UIState<List<SatelliteEntity>>> = _uiState

    init {
        viewModelScope.launch {
            val dbSatelliteCount = satelliteListRepository.getSatelliteCount()
            if (dbSatelliteCount > 0) return@launch

            fetchSatellites()
        }
        viewModelScope.launch {
            _uiState.value = Loading
            satelliteListRepository.satelliteListFlow
                .collect { satelliteEntities ->
                    _uiState.value = Success(satelliteEntities)
                }
        }
    }

    fun searchSatellite(query: String) {
        viewModelScope.launch {
            _uiState.value = Loading
            satelliteListRepository.searchSatellite(query)
                .collect { satelliteEntities ->
                    _uiState.value = Success(satelliteEntities.orEmpty())
                }
        }
    }

    private suspend fun fetchSatellites() {
        when (val satelliteListNetworkState = satelliteListRepository.fetchSatelliteList()) {
            ErrorState -> {
                _uiState.value = Error(R.string.error_message)
            }

            is FailureState -> {
                Log.d("Fetch Satellite", satelliteListNetworkState.throwable.message.orEmpty())
                _uiState.value = Error(R.string.error_message)
            }

            is SuccessState -> {
                val satellites = satelliteListNetworkState.data ?: return
                val satelliteEntities = satelliteEntityMapper.map(satellites)
                satelliteListRepository.insertSatellites(satelliteEntities)
            }
        }
    }
}