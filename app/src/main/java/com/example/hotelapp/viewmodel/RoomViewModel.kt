package com.example.hotelapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.use_case.GetRoomsUseCase
import com.example.hotelapp.model.RoomsListState
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
class RoomViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<RoomsListState>()
    val state: LiveData<RoomsListState> = _state

    init {
        getRooms()
    }

    private fun getRooms() {
        viewModelScope.launch(Dispatchers.IO) {
            getRoomsUseCase().flowOn(Dispatchers.IO)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            withContext(Dispatchers.Main) {
                                _state.value = RoomsListState(rooms = result.data)
                            }
                        }

                        is Resource.Error -> {
                            withContext(Dispatchers.Main) {
                                _state.value = RoomsListState(
                                    error = result.message ?: "An unexpected error occurred!"
                                )
                            }
                        }

                        is Resource.Loading -> {
                            withContext(Dispatchers.Main) {
                                _state.value = RoomsListState(isLoading = true)
                            }
                        }
                    }
                }
                .catch { throwable ->
                    withContext(Dispatchers.Main) {
                        Log.e("ERROR", throwable.stackTraceToString())
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}