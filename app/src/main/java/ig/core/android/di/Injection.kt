package ig.core.android.di

import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.repository.MainStateFlowRepository
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

}