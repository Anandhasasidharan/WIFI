package com.example.wifimonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifimonitor.repository.AuthRepository
import com.example.wifimonitor.model.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel handling authentication-related UI state and operations.
 * Manages login, registration, and user session state.
 */
class AuthViewModel : BaseViewModel() {

    private val authRepository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    init {
        checkCurrentUser()
    }

    /**
     * Check if user is currently authenticated
     */
    private fun checkCurrentUser() {
        val user = authRepository.getCurrentUser()
        _currentUser.value = user
        _authState.value = if (user != null) AuthState.Authenticated else AuthState.Unauthenticated
    }

    /**
     * Sign in with email and password
     */
    fun signIn(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.signInWithEmail(email, password)
            result.fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    _authState.value = AuthState.Authenticated
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Login failed")
                }
            )
        }
    }

    /**
     * Register new user
     */
    fun register(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.registerWithEmail(email, password)
            result.fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    _authState.value = AuthState.Authenticated
                },
                onFailure = { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Registration failed")
                }
            )
        }
    }

    /**
     * Sign out current user
     */
    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
        _authState.value = AuthState.Unauthenticated
    }
}