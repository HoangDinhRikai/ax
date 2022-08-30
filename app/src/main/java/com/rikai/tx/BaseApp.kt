package com.rikai.tx

import android.app.Application
import com.rikai.tx.api.AppService
import com.rikai.tx.databases.AppDatabase
import com.rikai.tx.repositories.AppRepositoryImpl

class BaseApp : Application() {
    companion object {
        var baseApp: BaseApp? = null
    }

    lateinit var appService: AppService
    lateinit var appDatabase: AppDatabase
    lateinit var appRepositoryImpl: AppRepositoryImpl

    override fun onCreate() {
        super.onCreate()
        baseApp = this
        appService = AppService.getInstance()
        appDatabase = AppDatabase.getInstance(this)
        appRepositoryImpl = AppRepositoryImpl(
            appService,
            appDatabase
        )
    }
}