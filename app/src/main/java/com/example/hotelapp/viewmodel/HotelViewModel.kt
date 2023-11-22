package com.example.hotelapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.use_case.GetHotelUseCase
import com.example.hotelapp.model.HotelListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getHotelUseCase: GetHotelUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HotelListState>()
    val state: LiveData<HotelListState> = _state

    init {
        getHotel()
    }

    private fun getHotel() {
        viewModelScope.launch(Dispatchers.IO) {
            getHotelUseCase().flowOn(Dispatchers.IO)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            withContext(Dispatchers.Main) {
                                _state.value = HotelListState(hotelEntity = result.data)
                            }
                        }

                        is Resource.Error -> {
                            withContext(Dispatchers.Main) {
                                _state.value = HotelListState(
                                    error = result.message ?: "An unexpected error occurred!"
                                )
                            }
                        }

                        is Resource.Loading -> {
                            withContext(Dispatchers.Main) {
                                _state.value = HotelListState(isLoading = true)
                            }
                        }
                    }
                }
                .catch { throwable ->
                    withContext(Dispatchers.Main) {
                        Log.e("ERROR", throwable.toString())
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}