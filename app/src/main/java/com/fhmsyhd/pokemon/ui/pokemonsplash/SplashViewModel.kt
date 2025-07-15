package com.fhmsyhd.pokemon.ui.pokemonsplash

import androidx.lifecycle.ViewModel
import com.fhmsyhd.pokemon.core.data.local.preference.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    fun getIsLoggedIn(): Flow<Boolean> = userPreferences.isLoggedIn
}