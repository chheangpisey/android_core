package ig.core.android.viewmodel

import androidx.lifecycle.MutableLiveData
import ig.core.android.base.BaseViewModel

class FragViewModel : BaseViewModel() {
    val data = MutableLiveData<String>()

    fun setData(str: String) {
        data.value = str
    }
}