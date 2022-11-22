package ig.core.android.di

import android.content.Context
import ig.core.android.data.datasource.demoarch.DemoArchLocalDatasource
import ig.core.android.data.datasource.demoarch.dblocator.DemoArchDatabase
import ig.core.android.data.repository.DemoArchCreateUserRepository
import ig.core.android.data.repository.DemoArchRepository
import ig.core.android.domain.DemoArchUseCase
import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.repository.LoginStateFlowRepository
import ig.core.android.service.repository.MainRepository
import ig.core.android.service.repository.MainStateFlowRepository
import ig.core.android.view.ui.activity.demoArch.DemoArchViewModelFactory
import ig.core.android.viewmodelfactory.LoginViewModelFactory
import ig.core.android.viewmodelfactory.MainViewModelFactory
import ig.core.android.webservice.WebService

object Injection {
    private val webService: WebService by lazy { WebService }

    private val apiServiceImpl: ApiServiceImpl by lazy { ApiServiceImpl(webService) }
    private val loginRepository: LoginStateFlowRepository by lazy {
        LoginStateFlowRepository(apiServiceImpl = ApiServiceImpl(
        webService)
    ) }

    private val mainRepository: MainRepository by lazy { MainRepository(webService) }

    private val LOGIN_STATE_REPOSITORY: MainStateFlowRepository by lazy {
        MainStateFlowRepository(apiServiceImpl = ApiServiceImpl(webService)) }

    val provideLoginViewModelFactory: LoginViewModelFactory by lazy { LoginViewModelFactory(loginRepository) }

    val provideMainViewModelFactory: MainViewModelFactory by lazy { MainViewModelFactory(
        LOGIN_STATE_REPOSITORY)
    }

    //Sample Testing for architecture
   // private lateinit var local: DemoArchDataSourceImpl
//    private val demoArchRepository: DemoArchRepository by lazy { DemoArchRepository(
//        webService, DemoArchLocalDatasource()) }
//    private val demoArchUseCase: DemoArchUseCase by lazy { DemoArchUseCase(demoArchRepository) }
//    val provideDemoArchViewModelFactory: DemoArchViewModelFactory by lazy { DemoArchViewModelFactory(
//        demoArchUseCase
//    ) }


    fun provideDemoArchViewModelFactory(context: Context): DemoArchViewModelFactory =
        DemoArchViewModelFactory(
            DemoArchUseCase(
                DemoArchRepository(apiServiceImpl, DemoArchLocalDatasource(DemoArchDatabase.getInstance(context))),
                DemoArchCreateUserRepository(apiServiceImpl, DemoArchLocalDatasource(DemoArchDatabase.getInstance(context)))
            )
        )

}