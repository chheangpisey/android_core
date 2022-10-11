package ig.core.android.di

import DemoViewModelFactory
import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.repository.LoginStateFlowRepository
import ig.core.android.service.repository.MainRepository
import ig.core.android.service.repository.MainStateFlowRepository
import ig.core.android.viewmodelfactory.LoginViewModelFactory
import ig.core.android.viewmodelfactory.MainViewModelFactory
import ig.core.android.webservice.WebService

object Injection {
    private val webService: WebService by lazy { WebService }
 //   private val apiServiceImpl: ApiServiceImpl by lazy { ApiServiceImpl }

    private val loginRepository: LoginStateFlowRepository by lazy { LoginStateFlowRepository(apiServiceImpl = ApiServiceImpl(
        webService)
    ) }
    private val mainRepository: MainRepository by lazy { MainRepository(webService) }

    private val LOGIN_STATE_REPOSITORY: MainStateFlowRepository by lazy { MainStateFlowRepository(apiServiceImpl = ApiServiceImpl(webService)) }

    val provideLoginViewModelFactory: LoginViewModelFactory by lazy { LoginViewModelFactory(loginRepository) }

    val provideMainViewModelFactory: MainViewModelFactory by lazy { MainViewModelFactory(
        LOGIN_STATE_REPOSITORY)
    }

    val provideDemoFactory: DemoViewModelFactory by lazy { DemoViewModelFactory() }
}