package com.cps.data.di

import com.cps.data.di.repository.LoginRepository
import com.cps.data.di.repository.LoginRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author piseychheang
 * Created on 11/1/22 at 2:04 PM
 * Modified By piseychheang on 11/1/22 at 2:04 PM
 * File name: DataModule.kt
 */

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsTopicRepository(
        loginRepository: LoginRepositoryImp
    ): LoginRepository
}