package com.example.healthgrind.firebase.general

import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.auth.debuglog.LogServiceImpl
import com.example.healthgrind.firebase.auth.register.AccountService
import com.example.healthgrind.firebase.auth.register.AccountServiceImpl
import com.example.healthgrind.firebase.database.StorageService
import com.example.healthgrind.firebase.database.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    //@Binds abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}