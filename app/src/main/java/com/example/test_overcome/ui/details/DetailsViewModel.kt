package com.example.test_overcome.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_overcome.data.repository.LocalTicketRepository
import com.example.test_overcome.data.repository.RemoteTicketRepository
import com.example.test_overcome.model.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val localTicketRepository: LocalTicketRepository,
    private val remoteTicketRepository: RemoteTicketRepository
) : ViewModel() {
    private val _uIState = MutableStateFlow(UIState())
    val uIState: StateFlow<UIState> = _uIState

    fun getTicketById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        localTicketRepository.getTicketById(id).collect{
            _uIState.update {state ->
                state.copy(ticket = it, isLoadin = false)
            }
        }
    }

    fun updateTicket(status: String) = viewModelScope.launch(Dispatchers.IO) {
        _uIState.value.ticket?.let { remoteTicketRepository.updateTicket(it.copy(status = status)) }
    }

    data class UIState(
        var isLoadin: Boolean = true,
        var ticket: Ticket? = null,
    )
}