package ig.core.android.view.ui.activity.demoArch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ig.core.android.domain.DemoArchUseCase

class DemoArchViewModelFactory(private val demoArchUseCase: DemoArchUseCase) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T = DemoArchViewModel(demoArchUseCase) as T
  }