package com.example.test_overcome.ui.details.edit.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_overcome.R
import com.example.test_overcome.data.repository.LocalTicketRepository
import com.example.test_overcome.data.repository.RemoteTicketRepository
import com.example.test_overcome.model.Files
import com.example.test_overcome.model.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTicketDialogFormViewModel @Inject constructor(
    private val localTicketRepository: LocalTicketRepository,
    private val remoteTicketRepository: RemoteTicketRepository
): ViewModel(){
    private val _uIState = MutableStateFlow(UIState())
    val uIState: StateFlow<UIState> = _uIState

    fun setIsLoading(it: Boolean){
        _uIState.update { state ->
            state.copy(isLoading = it)
        }
    }

    fun setTitle(it: String){
        _uIState.update { state ->
            state.copy(title = it)
        }
    }

    fun setName(it: String){
        _uIState.update { state ->
            state.copy(name = it)
        }
    }

    fun setTeam(it: String){
        _uIState.update { state ->
            state.copy(teamSelected = it)
        }
    }

    fun setType(it: String){
        _uIState.update { state ->
            state.copy(typeSelected = it)
        }
    }

    fun setSeverity(it: String){
        _uIState.update { state ->
            state.copy(severitySelected = it)
        }
    }

    fun setVersion(it: String){
        _uIState.update { state ->
            state.copy(version = it)
        }
    }

    fun setDescription(it: String){
        _uIState.update { state ->
            state.copy(description = it)
        }
    }

    private fun validateForm(): Boolean{
        var isValid = true

        val title = _uIState.value.title
        val name = _uIState.value.name
        val type = _uIState.value.typeSelected
        val severity = _uIState.value.severitySelected
        val description = _uIState.value.description

        if (title.isEmpty()){
            _uIState.update { it.copy(titleError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(titleError = 0) }
        }

        if (name.isEmpty()){
            _uIState.update { it.copy(nameError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(nameError = 0) }
        }

        if (type.isEmpty()){
            _uIState.update { it.copy(typeSelectedError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(typeSelectedError = 0) }
        }

        if (severity.isEmpty()){
            _uIState.update { it.copy(severitySelectedError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(severitySelectedError = 0) }
        }

        if (description.isEmpty()){
            _uIState.update { it.copy(descriptionError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(descriptionError = 0) }
        }

        return isValid
    }

    fun getTicketById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        localTicketRepository.getTicketById(id).collect{
            _uIState.update {state ->
                state.copy(
                    title = it?.title ?: "",
                    name = it?.responsible ?: "",
                    teamSelected = it?.team ?: "",
                    typeSelected = it?.type ?: "",
                    severitySelected = it?.severity ?: "",
                    version = it?.version ?: "",
                    description = it?.description ?: "",
                )
            }
        }
    }

    fun updateTicket(id: Int, onAccept: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val updatedTicket = Ticket(
            id = id,
            title = _uIState.value.title,
            responsible = _uIState.value.name,
            team = _uIState.value.teamSelected,
            type = _uIState.value.typeSelected,
            severity = _uIState.value.severitySelected,
            version = _uIState.value.version,
            description = _uIState.value.description,
            files = Files(videoUrl = emptyList(), audioUrl =  emptyList(), imageUrl = emptyList())
        )
        localTicketRepository.updateTicket(updatedTicket)
        remoteTicketRepository.updateTicket(updatedTicket)
        clearForm()
        onAccept()
    }


    private fun clearForm(){
        setTitle("")
        setName("")
        setTeam("")
        setType("")
        setSeverity("")
        setVersion("")
        setDescription("")
    }

    data class UIState(
        var isLoading: Boolean = true,
        var title: String = "",
        var titleError: Int = 0,
        var name: String = "",
        var nameError: Int = 0,
        var teamSelected: String = "",
        var typeSelected: String = "",
        var typeSelectedError: Int = 0,
        var severitySelected: String = "",
        var severitySelectedError: Int = 0,
        var version: String = "",
        var description: String = "",
        var descriptionError: Int = 0,
    )
}