package ig.core.android.di

import android.content.Context
import ig.core.android.data.datasource.demoarch.DemoArchLocalDatasource
import ig.core.android.data.datasource.demoarch.dblocator.DemoArchDatabase
import ig.core.android.data.repository.DemoArchCreateUserRepository
import ig.core.android.data.repository.DemoArchRepository
import ig.core.android.domain.DemoArchUseCase
import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.repository.MainStateFlowRepository
import ig.core.android.view.ui.activity.demoArch.DemoArchViewModelFactory
import ig.core.android.viewmodelfactory.MainViewModelFactory
import ig.core.android.webservice.WebService

object Injection {
    private val webService: WebService by lazy { WebService }

    private val apiServiceImpl: ApiServiceImpl by lazy { ApiServiceImpl(webService) }

    private val LOGIN_STATE_REPOSITORY: MainStateFlowRepository by lazy {
        MainStateFlowRepository(apiServiceImpl = ApiServiceImpl(webService)) }

    val provideMainViewModelFactory: MainViewModelFactory by lazy { MainViewModelFactory(
        LOGIN_STATE_REPOSITORY)
    }

    fun provideDemoArchViewModelFactory(context: Context): DemoArchViewModelFactory =
        DemoArchViewModelFactory(
            DemoArchUseCase(
                DemoArchRepository(apiServiceImpl, DemoArchLocalDatasource(DemoArchDatabase.getInstance(context))),
                DemoArchCreateUserRepository(apiServiceImpl)
            )
        )

}