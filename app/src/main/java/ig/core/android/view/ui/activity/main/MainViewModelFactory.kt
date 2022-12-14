package ig.core.android.view.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ig.core.android.data.repository.MainStateFlowRepository

class MainViewModelFactory(private val mainRepository: MainStateFlowRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(mainRepository) as T
}