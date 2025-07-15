package com.fhmsyhd.pokemon.ui.pokemonuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fhmsyhd.pokemon.core.data.local.preference.UserPreferences
import com.fhmsyhd.pokemon.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _state.update { it.copy(username = newUsername) }
    }

    fun onPasswordChange(newPassword: String) {
        _state.update { it.copy(password = newPassword) }
    }

    fun login(
        onSuccess: () -> Unit
    ) {
        _state.update { it.copy(isLoading = true, errorMessage = "") }
        viewModelScope.launch {
            val user = userUseCase.login(
                _state.value.username,
                _state.value.password
            )
            if (user != null) {
                _state.update { it.copy(isLoading = false) }
                userPreferences.setLoggedIn(true)
                userPreferences.setUsername(user.username)
                onSuccess()
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Username atau password salah"
                    )
                }
            }
        }
    }

    fun register(
        onSuccess: () -> Unit
    ) {
        val state = _state.value
        if (state.username.isBlank() || state.password.isBlank()) {
            _state.update { it.copy(errorMessage = "Semua field harus diisi") }
            return
        }

        _state.update { it.copy(isLoading = true, errorMessage = "") }

        viewModelScope.launch {
            val existing = userUseCase.getUser(state.username)
            if (existing != null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Username sudah terdaftar"
                    )
                }
            } else {
                userUseCase.register(state.username, state.password)
                _state.update { it.copy(isLoading = false) }
                onSuccess()
            }
        }
    }
}