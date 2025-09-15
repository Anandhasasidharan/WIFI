package com.example.wifimonitor.repository

import com.example.wifimonitor.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

/**
 * Repository handling Firebase Authentication operations.
 * Manages user login, registration, and role-based access.
 */
class AuthRepository : BaseRepository() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Get current authenticated user
     */
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    /**
     * Sign in with email and password
     */
    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Register new user with email and password
     */
    suspend fun registerWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sign out current user
     */
    fun signOut() {
        firebaseAuth.signOut()
    }

    /**
     * Check if user is authenticated
     */
    fun isUserAuthenticated(): Boolean = firebaseAuth.currentUser != null

    /**
     * Get user role from custom claims or database
     * For simplicity, assuming role is stored in Firestore
     */
    // Note: In real implementation, you'd fetch from Firestore or custom claims
}