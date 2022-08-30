package com.rikai.tx.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rikai.tx.models.Profile
import com.rikai.tx.repositories.AppRepositoryImpl
import com.rikai.tx.utils.UseCaseResult
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    private val appRepositoryImpl: AppRepositoryImpl
) : ViewModel() , CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val showResult= MutableLiveData<Profile>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getUser(login: String){
        showLoading.value = true
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    appRepositoryImpl.get(login)
                }
                showLoading.value= false
                when (result) {
                    is UseCaseResult.Success -> {
                        showResult.value = result.data
                    }
                    is UseCaseResult.Error -> {
                        println(result.errorMessage)
                    }
                }
            } catch (networkError: IOException) {
                showLoading.value= false
            }

        }
    }
}