package com.example.test_overcome.ui.home.add.dialog

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_overcome.R
import com.example.test_overcome.data.repository.StorageMediaRepository
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddTicketDialogFormViewModel @Inject constructor(
    private val localTicketRepository: LocalTicketRepository,
    private val storageMediaRepository: StorageMediaRepository,
    private val remoteTicketRepository: RemoteTicketRepository
) : ViewModel() {
    private val _uIState = MutableStateFlow(UIState())
    val uIState: StateFlow<UIState> = _uIState

    private val _images = MutableLiveData<MutableList<Bitmap>>(mutableListOf())
    val images: LiveData<MutableList<Bitmap>> = _images

    private val _imagesUrl = MutableLiveData<MutableList<String>>(mutableListOf())

    private val _videoUris = MutableLiveData<MutableList<Uri>>(mutableListOf())
    val videoUris: LiveData<MutableList<Uri>> = _videoUris

    private val _videoUrls = MutableLiveData<MutableList<String>>(mutableListOf())

    fun setImageDialog(it: Boolean) {
        _uIState.update { state ->
            state.copy(imageDialog = it)
        }
    }

    fun setVideoDialog(it: Boolean) {
        _uIState.update { state ->
            state.copy(videoDialog = it)
        }
    }

    fun setTitle(it: String) {
        _uIState.update { state ->
            state.copy(title = it)
        }
    }

    fun setName(it: String) {
        _uIState.update { state ->
            state.copy(name = it)
        }
    }

    fun setTeam(it: String) {
        _uIState.update { state ->
            state.copy(teamSelected = it)
        }
    }

    fun setType(it: String) {
        _uIState.update { state ->
            state.copy(typeSelected = it)
        }
    }

    fun setSeverity(it: String) {
        _uIState.update { state ->
            state.copy(severitySelected = it)
        }
    }

    fun setVersion(it: String) {
        _uIState.update { state ->
            state.copy(version = it)
        }
    }

    fun setDescription(it: String) {
        _uIState.update { state ->
            state.copy(description = it)
        }
    }

    fun addImageBitmap(bitmap: Bitmap) {
        val currentList = _images.value ?: mutableListOf()
        if (currentList.size < 3) {
            currentList.add(bitmap)
            _images.value =
                currentList // Establecer el valor de _images para notificar a los observadores
        }
    }

    fun addVideo(videoUri: Uri) {
        val currentList = _videoUris.value ?: mutableListOf()
        currentList.add(videoUri)
        _videoUris.value =
            currentList // Establecer el valor de _videoUrls para notificar a los observadores
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

    @SuppressLint("SuspiciousIndentation")
    fun addTicket(onAccept: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        if (validateForm()){
            var imagesUrl: List<String> = emptyList()
            _images.value?.forEach {
                _imagesUrl.value?.add(storageMediaRepository.uploadImage(it) ?: "")
            }
            imagesUrl = _imagesUrl.value!!

            var videoUrls: List<String> = emptyList()
            _videoUris.value?.forEach {
                _videoUrls.value?.add(storageMediaRepository.uploadVideo(it))
            }
            videoUrls = _videoUrls.value!!

            val ticket = Ticket(
                title = _uIState.value.title,
                date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                responsible = _uIState.value.name,
                team = _uIState.value.teamSelected,
                type = _uIState.value.typeSelected,
                severity = _uIState.value.severitySelected,
                version = _uIState.value.version,
                description = _uIState.value.description,
                files = Files(
                    videoUrl = videoUrls,
                    audioUrl = emptyList(),
                    imageUrl = imagesUrl
                )
            )
            remoteTicketRepository.insertTicket(ticket)
            localTicketRepository.insertTicket(ticket)
            clearForm()
            onAccept()
        }
    }

    private fun clearForm() {
        setTitle("")
        setName("")
        setTeam("")
        setType("")
        setSeverity("")
        setVersion("")
        setDescription("")
    }

    data class UIState(
        var isLoading: Boolean = false,
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
        var imageDialog: Boolean = false,
        var videoDialog: Boolean = false,
    )
}