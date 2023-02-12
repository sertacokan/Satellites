package com.example.satellites.ui.fragment.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satellites.R
import com.example.satellites.mapper.SatelliteDetailEntityMapper
import com.example.satellites.network.adapter.ErrorState
import com.example.satellites.network.adapter.FailureState
import com.example.satellites.network.adapter.SuccessState
import com.example.satellites.repository.SatelliteDetailRepository
import com.example.satellites.repository.SatellitePositionRepository
import com.example.satellites.ui.state.Error
import com.example.satellites.ui.state.Loading
import com.example.satellites.ui.state.Success
import com.example.satellites.ui.state.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SatelliteDetailViewModel(
    private val detailRepository: SatelliteDetailRepository,
    private val positionRepository: SatellitePositionRepository,
    private val detailEntityMapper: SatelliteDetailEntityMapper,
    private val satelliteId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<DetailUIModel>>(Loading)
    val uiState: Flow<UIState<DetailUIModel>> = _uiState

    init {
        viewModelScope.launch {
            detailRepository.satelliteDetailFlow(satelliteId)
                .combine(positionRepository.fetchSatellitePositions(satelliteId)) { satelliteDetailEntity, (posX, posY) ->
                    DetailUIModel(
                        satelliteDetailEntity?.costPerLaunch,
                        satelliteDetailEntity?.firstFlightDate,
                        satelliteDetailEntity?.height,
                        satelliteDetailEntity?.mass,
                        posX,
                        posY
                    )
                }.catch {
                    _uiState.value = Error(R.string.detail_error_message)
                }.collect { detailUIModel ->
                    _uiState.value = Success(detailUIModel)
                }
        }
        viewModelScope.launch {
            val dbSatelliteDetailCount = detailRepository.getSatelliteDetailCount(satelliteId)
            if (dbSatelliteDetailCount > 0) return@launch

            when (val satelliteDetailNetworkState = detailRepository.fetchSatelliteDetail(satelliteId)) {
                ErrorState -> {
                    _uiState.value = Error(R.string.detail_error_message)
                }

                is FailureState -> {
                    Log.d("Fetch Satellite Detail", satelliteDetailNetworkState.throwable.message.orEmpty())
                    _uiState.value = Error(R.string.detail_error_message)
                }

                is SuccessState -> {
                    val satelliteDetail = satelliteDetailNetworkState.data ?: return@launch
                    val detailEntity = detailEntityMapper.map(satelliteDetail)
                    detailRepository.insertSatelliteDetail(detailEntity)
                }
            }
        }
    }
}

data class DetailUIModel(
    val costPerLaunch: Int?,
    val firstFlightDate: String?,
    val height: Int?,
    val mass: Int?,
    val posX: Float?,
    val posY: Float?
)