package com.rikai.tx.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rikai.tx.repositories.AppRepositoryImpl

class DetailViewModelFactory(
    private val appRepositoryImpl: AppRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(appRepositoryImpl) as T
        }else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}