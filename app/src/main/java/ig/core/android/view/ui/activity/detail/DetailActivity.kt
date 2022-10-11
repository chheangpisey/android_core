package ig.core.android.view.ui.activity.detail

import androidx.lifecycle.ViewModelProvider
import ig.core.android.di.Injection
import com.squareup.picasso.Picasso
import ig.core.android.R
import ig.core.android.base.BaseActivity
import ig.core.android.databinding.ActivityDetailBinding
import ig.core.android.service.model.PostsModel
import ig.core.android.viewmodel.MainViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, MainViewModel>() {
    override val mLayoutId = R.layout.activity_detail
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    override fun initView() {
        onSetUpGeneralBackPressToolbar(getString(R.string.detail))
        val item = intent!!.extras!!.getParcelable<PostsModel>("item")
        Picasso.get().load(item!!.url).into(mBinding.urlImg)
    }
}