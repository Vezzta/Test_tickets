package com.example.test_overcome.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_overcome.data.repository.LocalTicketRepository
import com.example.test_overcome.data.repository.RemoteTicketRepository
import com.example.test_overcome.model.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localTicketRepository: LocalTicketRepository,
    private val remoteTicketRepository: RemoteTicketRepository
): ViewModel(){
    private val _uIState = MutableStateFlow(UIState())
    val uIState: StateFlow<UIState> = _uIState

    fun getTickets() {
        viewModelScope.launch(Dispatchers.IO) {
            remoteTicketRepository.observeTickets().collect{ remoteTickets ->
                remoteTickets.forEach {
                    if (it != null) {
                        localTicketRepository.insertTicket(it)
                    }
                }
                val localTickets = localTicketRepository.getAllTickets().first()
                _uIState.value = UIState(tickets = localTickets, isLoading = false)
            }
        }
    }

    data class UIState(
        var tickets: List<Ticket?> = emptyList(),
        var isLoading: Boolean = true
    )
}