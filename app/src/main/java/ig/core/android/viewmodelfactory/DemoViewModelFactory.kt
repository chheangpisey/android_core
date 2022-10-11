import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ig.core.android.viewmodel.MainViewModel

class DemoViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DemoViewModel() as T
}