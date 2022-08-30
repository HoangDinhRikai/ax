package com.rikai.tx.repositories

import androidx.lifecycle.LiveData
import com.rikai.tx.models.Profile
import com.rikai.tx.models.User
import com.rikai.tx.utils.UseCaseResult

interface AppRepository {
    suspend fun getUsersPagination(startPage: String, numPage: String): UseCaseResult<List<User>>
    suspend fun getUsers(): UseCaseResult<List<User>>
    suspend fun get(login: String): UseCaseResult<Profile>
    suspend fun clear()
    suspend fun insertAll(users: List<User>)
    val users: LiveData<List<User>>
}