package ig.core.android.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ig.core.android.service.repository.LoginStateFlowRepository
import ig.core.android.viewmodel.LoginViewModel

class LoginViewModelFactory(private val loginRepository: LoginStateFlowRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override  fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(loginRepository) as T
}