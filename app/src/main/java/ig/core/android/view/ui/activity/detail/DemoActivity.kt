package ig.core.android.view.ui.activity.detail

/**
 * @author piseychheang
 * Created on 10/11/22 at 4:43 PM
 * Modified By piseychheang on 10/11/22 at 4:43 PM
 * File name: ig.core.android.view.ui.activity.detail.DemoActivity.kt
 */

import DemoViewModel
import ig.core.android.base.BaseActivity
import ig.core.android.R
import androidx.lifecycle.ViewModelProvider
import ig.core.android.di.Injection
import ig.core.android.databinding.ActivityDemoBinding

class DemoActivity : BaseActivity<ActivityDemoBinding, DemoViewModel>() {
    override val mLayoutId = R.layout.activity_demo
    override fun getViewModelClass(): Class<DemoViewModel> = DemoViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideDemoFactory

    override fun initView() {

    }
}