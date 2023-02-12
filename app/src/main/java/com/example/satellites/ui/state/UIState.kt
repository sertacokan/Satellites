package com.example.satellites.ui.state

import androidx.annotation.StringRes

sealed interface UIState<out T>

object Loading : UIState<Nothing>
data class Success<T>(val data: T) : UIState<T>
data class Error(@StringRes val messageRes: Int) : UIState<Nothing>