package ig.core.android.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import ig.core.android.R
import ig.core.android.service.model.custom.ResourceResponse
import ig.core.android.utils.AppConstant.STARTACTIVITYANIM_LEFT
import ig.core.android.utils.AppConstant.STARTACTIVITYANIM_RIGHT
import ig.core.android.utils.AppConstant.STARTACTIVITYANIM_TOP
import ig.core.android.utils.LoadingView
import ig.core.android.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.default_toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

abstract class BaseExtension<B : ViewDataBinding, VM : BaseViewModel>: AppCompatActivity() {
    protected abstract val mLayoutId: Int @LayoutRes get
    protected open val mViewModel: MainViewModel by viewModels()
    protected open lateinit var mBinding: B
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, R.color.color_background))
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
        mBinding = DataBindingUtil.setContentView(this, mLayoutId)
//        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initView()
    }


    abstract fun getViewModelClass(): Class<VM>
    //abstract fun getViewModelFactory(): ViewModelProvider.Factory

    abstract fun initView()

    fun onSetUpGeneralToolbar(toolbarTitle: String) {
        setSupportActionBar(tbGeneralBackPress)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        tvToolbarTitle.text = toolbarTitle
    }

}