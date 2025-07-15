package com.fhmsyhd.pokemon.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fhmsyhd.pokemon.core.data.local.preference.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    fun getUsername(): Flow<String?> = userPreferences.username

    fun logout() {
        viewModelScope.launch {
            userPreferences.clear()
        }
    }
}