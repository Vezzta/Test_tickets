package com.example.test_overcome.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_overcome.R
import com.example.test_overcome.data.repository.AuthRepository
import com.example.test_overcome.utils.Extensions.isEmail
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uIState = MutableStateFlow(UIState())
    val uIState: StateFlow<UIState> = _uIState.asStateFlow()

    fun setEmail(it: String) {
        _uIState.update { uiState ->
            uiState.copy(email = it)
        }
    }

    fun setPass(it: String) {
        _uIState.update { uiState ->
            uiState.copy(pass = it)
        }
    }

    fun setRegistryEmail(it: String){
        _uIState.update { uiState ->
            uiState.copy(registryEmail = it)
        }
    }

    fun setRegistryPass(it: String){
        _uIState.update { uiState ->
            uiState.copy(registryPass = it)
        }
    }

    fun setConfirmPass(it: String){
        _uIState.update { uiState ->
            uiState.copy(confirmPass = it)
        }
    }

    fun setName(it: String){
        _uIState.update { uiState ->
            uiState.copy(name = it)
        }
    }

    private fun validateLogin(): Boolean{
        var isValid = true

        val email = _uIState.value.email
        val pass = _uIState.value.pass

        if (email!!.isEmpty()){
            _uIState.update { it.copy(emailError = R.string.empty_error) }
            isValid = false
        }else if (!email.isEmail()){
            _uIState.update { it.copy(emailError = R.string.character_invalid_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(emailError = 0) }
        }

        if (pass!!.isEmpty()){
            _uIState.update { it.copy(passError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(passError = 0) }
        }

        return isValid
    }
    private fun validateRegistry(): Boolean{
        var isValid = true

        val email = _uIState.value.registryEmail
        val pass = _uIState.value.registryPass
        val confirmPass = _uIState.value.confirmPass
        val name = _uIState.value.name

        if (email!!.isEmpty()){
            _uIState.update { it.copy(registryEmailError = R.string.empty_error) }
            isValid = false
        }else if (!email.isEmail()){
            _uIState.update { it.copy(registryEmailError = R.string.character_invalid_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(registryEmailError = 0) }
        }

        if (pass!!.isEmpty()){
            _uIState.update { it.copy(registryPassError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(registryPassError = 0) }
        }

        if (confirmPass!!.isEmpty()){
            _uIState.update { it.copy(confirmPassError = R.string.empty_error) }
        } else if (confirmPass != pass){
            _uIState.update { it.copy(confirmPassError = R.string.pass_confirm_error) }
            isValid = false
        }else{
            _uIState.update { it.copy(confirmPassError = 0) }
        }

        if (name!!.isEmpty()){
            _uIState.update { it.copy(nameError = R.string.empty_error) }
            isValid = false
        } else{
            _uIState.update { it.copy(nameError = 0) }
        }

        return isValid
    }

    fun createUser(email: String, pass: String, context: Context, name: String, success: () -> Unit) = viewModelScope.launch(
        Dispatchers.IO) {
        Log.e("TEST", "entró")
        if (validateRegistry()){
            authRepository.createUserWithEmailAndPassword(email = email, pass = pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("TEST", "registro correcto")
                        authRepository.updateUsername(name)
                        success()
                    } else {
                        Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
                        Log.e("TEST", "${task.exception?.message}")
                    }
                }
        }
    }

    fun signIn(email: String, pass: String, context: Context, success: () -> Unit) = viewModelScope.launch(
        Dispatchers.IO) {
        Log.e("TEST", "entró")
        if (validateLogin()){
            authRepository.signInWithEmailAndPassword(email = email, pass = pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("TEST", "login correcto")
                        success()
                    } else {
                        if (task.exception?.message == "There is no user record corresponding to this identifier. The user may have been deleted."){
                            Toast.makeText(context, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
        }
    }

    data class UIState(
        var email: String = "",
        var pass: String = "",
        var registryEmail: String = "",
        var registryPass: String = "",
        var confirmPass: String = "",
        var emailError: Int = 0,
        var passError: Int = 0,
        var registryEmailError: Int = 0,
        var registryPassError: Int = 0,
        var confirmPassError: Int = 0,
        var name: String = "",
        var nameError: Int = 0,
    )

}