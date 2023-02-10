package ig.core.android.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ig.core.android.service.repository.MainStateFlowRepository
import ig.core.android.viewmodel.MainViewModel

class MainViewModelFactory(private val mainRepository: MainStateFlowRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel() as T
}