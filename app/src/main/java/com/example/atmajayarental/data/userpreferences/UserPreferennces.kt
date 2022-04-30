package com.example.atmajayarental.data.userpreferences

import com.example.atmajayarental.data.api.model.AuthResponse
import kotlinx.coroutines.flow.Flow

interface UserPreferennces {
    suspend fun saveUserLogin(authResponse: AuthResponse)

    suspend fun getUserLogin(): Flow<AuthResponse>
}